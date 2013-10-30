package com.androiddomainmentor.rovirunner.presenter.impl;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.widget.MediaController.MediaPlayerControl;
import com.androiddomainmentor.rovirunner.model.AudioTrackMetadata;
import com.androiddomainmentor.rovirunner.model.ILocalSourceManager;
import com.androiddomainmentor.rovirunner.model.impl.LocalSourceManager;
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
    private static final AudioTrackMetadata RANDOM_SONG = null;
    // TODO: Replace all these saved values w/ one saved ID to find the song in our future database
    private static final String KEY_LAST_PLAYED_SONG_PATH = "lastSavedSongPath";
    private static final String KEY_LAST_PLAYED_SONG_ARTIST = "lastSavedSongArtist";
    private static final String KEY_LAST_PLAYED_SONG_TITLE = "lastSavedSongTitle";
    private static final String KEY_LAST_PLAYED_SONG_POSITION = "lastSavedSongPosition";
    
    private IMediaPlayerView m_view = null;
    private Context m_context = null;
    private MediaPlayer m_mediaPlayer = null;
    private ILocalSourceManager m_songsMgr = null;
    private SharedPreferences m_prefs = null;
    
    public MediaPlayerPresenter( IMediaPlayerView mediaPlayerActivity,
                                 Context context, 
                                 SharedPreferences prefs )
    {
        m_view = mediaPlayerActivity;
        m_context = context;
        m_prefs = prefs;
        m_songsMgr = new LocalSourceManager(); // default
    }
    
    @Override
    public void setLocalSourceManager(ILocalSourceManager mgr)
    {
        m_songsMgr = mgr;
    }

    private void setUpMediaPlayer()
    {
        if ( null == m_mediaPlayer )
        {
            m_mediaPlayer = makeNewMediaPlayer();
        }
        m_mediaPlayer.setOnPreparedListener( this );
        m_mediaPlayer.setLooping(false);
    }

    @Override
    public MediaPlayer makeNewMediaPlayer()
    {
        return new MediaPlayer();
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
        int lastPlayedSongPosition = loadLastPlayedSongPosition();
        mp.seekTo( lastPlayedSongPosition );

        mp.start();
		
        // NOTE: set completion listener AFTER start is called
        // (http://stackoverflow.com/questions/4813486/oncompletion-isnt-being-called-when-i-would-expect-it-to)
        m_mediaPlayer.setOnCompletionListener( this );
        m_mediaPlayer.setOnErrorListener( this );
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
        playSong( RANDOM_SONG );
    }

    @Override
    public void playSong( AudioTrackMetadata songMetadata )
    {
        if ( m_mediaPlayer.isPlaying() )
        {
            return;
        }
        
        if ( RANDOM_SONG == songMetadata || "".equals(songMetadata.getPath()) )
        {
            songMetadata = getRandomSong();
        }
        
        try
        {
            m_mediaPlayer.reset();
            setUpMediaPlayer();
            m_mediaPlayer.setDataSource( songMetadata.getPath() );
            
            m_mediaPlayer.prepareAsync();

            // Update the view with new metadata
            updateViewWithSongInfo(songMetadata);
            
            // TODO [2013-10-24 KW] I'm putting this here to demonstrate that we need to 
            // store the song filename in preferences every time a new one is played.
            saveLastPlayedSongMetadata(songMetadata);
            saveLastPlayedSongPosition(0);
        }
        catch ( IllegalArgumentException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch ( IllegalStateException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public AudioTrackMetadata getRandomSong()
    {
        ArrayList<AudioTrackMetadata> tracks = m_songsMgr.getAudioTracks(m_context);

        // Check that at least one song was found
        if (tracks.size() == 0)
        {
            return new AudioTrackMetadata();
        }
        
        // Choose a random song from the list
        Random rng = new Random();
        int nextSong = rng.nextInt(tracks.size());
        
        return tracks.get(nextSong);
    }
	
	public void updateViewWithSongInfo(AudioTrackMetadata meta)
    {
        m_view.setArtistText(meta.getArtist());
        m_view.setSongText(meta.getTitle());
    }
	
    private AudioTrackMetadata loadLastPlayedSongMetadata()
    {
        AudioTrackMetadata songMetadata = new AudioTrackMetadata();
        songMetadata.setPath( m_prefs.getString( KEY_LAST_PLAYED_SONG_PATH, "" ) );
        songMetadata.setArtist( m_prefs.getString( KEY_LAST_PLAYED_SONG_ARTIST, "" ) );
        songMetadata.setTitle( m_prefs.getString( KEY_LAST_PLAYED_SONG_TITLE, "" ) );
        
        return songMetadata;
    }
    
    private void saveLastPlayedSongMetadata(AudioTrackMetadata songMetadata)
    {
        SharedPreferences.Editor editor = m_prefs.edit();
        editor.putString( KEY_LAST_PLAYED_SONG_PATH, songMetadata.getPath() );
        editor.putString( KEY_LAST_PLAYED_SONG_ARTIST, songMetadata.getArtist() );
        editor.putString( KEY_LAST_PLAYED_SONG_TITLE, songMetadata.getTitle() );
        editor.commit();
    }
    
    private int loadLastPlayedSongPosition()
    {
        int lastPlayedSongPosition = m_prefs.getInt( KEY_LAST_PLAYED_SONG_POSITION, 0 );
        
        return lastPlayedSongPosition;
    }
    
    private void saveLastPlayedSongPosition(int currentPosition)
    {
        // Store current song position
        SharedPreferences.Editor editor = m_prefs.edit();
        editor.putInt( KEY_LAST_PLAYED_SONG_POSITION, currentPosition );
        editor.commit();
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
        // Store current song position
        int currentPosition = m_mediaPlayer.getCurrentPosition();
        // We don't want to resume at the end of the song on next start if we're within 1 second of the end
        int duration = m_mediaPlayer.getDuration();
        if ( currentPosition > duration - 1000)
        {
            currentPosition = 0;
        }
        saveLastPlayedSongPosition(currentPosition);

        // Shutdown the media player
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
        AudioTrackMetadata lastPlayedSong = loadLastPlayedSongMetadata();
        playSong( lastPlayedSong );
    }
}