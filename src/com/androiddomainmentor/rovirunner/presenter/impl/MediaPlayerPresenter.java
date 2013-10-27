package com.androiddomainmentor.rovirunner.presenter.impl;

import java.io.IOException;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.widget.MediaController.MediaPlayerControl;
import com.androiddomainmentor.rovirunner.model.IRoviRunnerMediaPlayer;
import com.androiddomainmentor.rovirunner.model.impl.RoviRunnerMediaPlayer;
import com.androiddomainmentor.rovirunner.presenter.IMediaPlayerPresenter;
import com.androiddomainmentor.rovirunner.view.IMediaPlayerView;

/*
 * this class is responsible for getting files to play, and should contain logic
 * for picking songs based on target BPM.
 */
public class MediaPlayerPresenter implements
                                 IMediaPlayerPresenter, 
                                 MediaPlayerControl,
                                 OnPreparedListener,
                                 OnErrorListener,
                                 OnCompletionListener
{
    private IMediaPlayerView m_view = null;
    private Context m_context = null;
    private IRoviRunnerMediaPlayer m_mediaPlayer = null;
    private SharedPreferences m_prefs = null;
    
    public MediaPlayerPresenter( IMediaPlayerView mediaPlayerActivity,
                                 Context context, 
                                 SharedPreferences prefs )
    {
        m_view = mediaPlayerActivity;
        m_context = context;
        m_prefs = prefs;
    }

    private void setUpMediaPlayer()
    {
        if ( null == m_mediaPlayer )
        {
            m_mediaPlayer = makeNewMediaPlayer();
            m_mediaPlayer.setOnPreparedListener( this );
            m_mediaPlayer.setOnCompletionListener( this );
            m_mediaPlayer.setOnErrorListener( this );
        }
    }

    @Override
    public IRoviRunnerMediaPlayer makeNewMediaPlayer()
    {
        return new RoviRunnerMediaPlayer();
    }

    @Override
    public MediaPlayerControl getMediaPlayerControl()
    {
        return this;
    }

    @Override
    public void onPrepared( MediaPlayer mp )
    {
        // restore last played position before playback
        int lastPlayedSongPosition = m_prefs.getInt( "lastPlayedSongPosition", 0 );
        mp.seekTo( lastPlayedSongPosition );

        mp.start();
        m_view.showMediaController();
    }

    @Override
    public boolean onError( MediaPlayer mp, int what, int extra )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onCompletion( MediaPlayer mp )
    {
        mp.pause();
    }

    @Override
    public void playSong( String song )
    {
        if ( m_mediaPlayer.isPlaying() )
        {
            return;
        }
        
        AssetFileDescriptor afd = null;
        if ( null == song )
        {
            afd = getRandomSongFileDescriptor();
        }
        else
        {
            afd = getSongFileDescriptor( song );
        }
        
        if ( null == afd )
        {
            // TODO [2013-10-24 KW] does this mean there are no songs?
        }
        
        try
        {
            m_mediaPlayer.setDataSource( afd.getFileDescriptor(),
                                         afd.getStartOffset(),
                                         afd.getLength() );
        } catch ( IllegalArgumentException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( IllegalStateException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        m_mediaPlayer.prepareAsync();

        // TODO [2013-10-24 KW] get actual artist and song name
        m_view.setArtistText( "zach kim" );
        m_view.setSongText( "robot dance" );
    }

    @Override
    public AssetFileDescriptor getRandomSongFileDescriptor()
    {
        // TODO [2013-09-21 KW]:  for now, just pick this song
        String filename = "get_lucky_30s.mp3";
        AssetFileDescriptor afd = null;
        try
        {
            AssetManager assetMgr = m_context.getAssets();
            // TODO [2013-09-28 KW]:  pick a random asset from assetMgr.list( ... )?
            afd = assetMgr.openFd( filename );
        } catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // TODO [2013-10-24 KW] I'm putting this here to demonstrate that we need to 
        // store the song filename in preferences every time a new one is played.
        SharedPreferences.Editor editor = m_prefs.edit();
        editor.putString( "lastPlayedSong", filename );

        // commit edits
        editor.commit();

        return afd;
    }
    
    @Override
    public AssetFileDescriptor getSongFileDescriptor( String songFilename )
    {
        AssetFileDescriptor afd = null;
        try
        {
            AssetManager assetMgr = m_context.getAssets();
            afd = assetMgr.openFd( songFilename );
        } catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // this is going to be the last played song
        // TODO [2013-10-24 KW] probably should go into playSong() instead?
        SharedPreferences.Editor editor = m_prefs.edit();
        editor.putString( "lastPlayedSong", songFilename );

        // commit edits
        editor.commit();

        return afd;
    }

    @Override
    public boolean canPause()
    {
        return ( m_mediaPlayer.isPlaying() ) ? true : false;
    }

    @Override
    public boolean canSeekBackward()
    {
        return ( m_mediaPlayer.getCurrentPosition() > 0 ) ? true : false;
    }

    @Override
    public boolean canSeekForward()
    {
        return ( m_mediaPlayer.getCurrentPosition() < m_mediaPlayer.getDuration() ) ? true : false;
    }

    @Override
    public int getBufferPercentage()
    {
        return 100;
    }

    @Override
    public int getCurrentPosition()
    {
        return m_mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration()
    {
        return m_mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying()
    {
        return m_mediaPlayer.isPlaying();
    }

    @Override
    public void pause()
    {
        m_mediaPlayer.pause();
    }

    @Override
    public void seekTo( int pos )
    {
        m_mediaPlayer.seekTo( pos );
    }

    @Override
    public void start()
    {
        m_mediaPlayer.start();
    }
    
    @Override
    public void lifecycleStart()
    {
        setUpMediaPlayer();
    }

    @Override
    public void lifecycleStop()
    {
        // store current song position
        SharedPreferences.Editor editor = m_prefs.edit();
        int currentPosition = m_mediaPlayer.getCurrentPosition();
        // we don't want to resume at the end of the song on next start if we're within 1 second of the end
        int duration = m_mediaPlayer.getDuration();
        if ( currentPosition > duration - 1000)
        {
            currentPosition = 0;
        }
        editor.putInt( "lastPlayedSongPosition", currentPosition );
 
        // commit edits
        editor.commit();

        m_mediaPlayer.stop();
        m_mediaPlayer.release();
        m_mediaPlayer = null;
    }
    
    @Override
    public void lifecycleDestroy()
    {
        
    }

    @Override
    public void lifecyclePause()
    {
        m_mediaPlayer.pause();
    }

    @Override
    public void lifecycleResume()
    {
        String lastPlayedSong = m_prefs.getString( "lastPlayedSong", null );

        playSong( lastPlayedSong );
    }
}