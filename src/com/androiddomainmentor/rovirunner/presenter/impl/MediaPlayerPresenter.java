package com.androiddomainmentor.rovirunner.presenter.impl;

import java.io.IOException;

import android.content.Context;
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
                                 OnPreparedListener,
                                 OnErrorListener,
                                 OnCompletionListener
{
    private IMediaPlayerView m_view = null;
    private Context m_context = null;
    private IRoviRunnerMediaPlayer m_mediaPlayer = null;

    public MediaPlayerPresenter( IMediaPlayerView mediaPlayerActivity,
                                 Context context )
    {
        m_view = mediaPlayerActivity;
        m_context = context;
    }

    @Override
    public void setUpMediaPlayer()
    {
        m_mediaPlayer = makeNewMediaPlayer();
        m_mediaPlayer.setOnPreparedListener( this );
        m_mediaPlayer.setOnCompletionListener( this );
        m_mediaPlayer.setOnErrorListener( this );
    }

    @Override
    public IRoviRunnerMediaPlayer makeNewMediaPlayer()
    {
        return new RoviRunnerMediaPlayer();
    }

    @Override
    public MediaPlayerControl getMediaPlayerControl()
    {
        return m_mediaPlayer;
    }

    @Override
    public void onPrepared( MediaPlayer mp )
    {
        mp.start();
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
        // TODO Auto-generated method stub

    }

    @Override
    public void playRandomSong()
    {
        AssetFileDescriptor afd = getRandomSongFileDescriptor();
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

        m_view.setArtistText( "zach kim" );
        m_view.setSongText( "robot dance" );
    }

    @Override
    public AssetFileDescriptor getRandomSongFileDescriptor()
    {
        AssetFileDescriptor afd = null;
        try
        {
            AssetManager assetMgr = m_context.getAssets();
            // TODO [2013-09-28 KW]:  pick a random asset from assetMgr.list( ... )?
            // TODO [2013-09-21 KW]:  for now, just pick this song
            afd = assetMgr.openFd( "get_lucky_30s.mp3" );
        } catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return afd;
    }
}
