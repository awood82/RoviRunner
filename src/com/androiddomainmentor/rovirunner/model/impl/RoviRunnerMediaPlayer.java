package com.androiddomainmentor.rovirunner.model.impl;

import java.io.FileDescriptor;
import java.io.IOException;
import android.media.MediaPlayer;
import com.androiddomainmentor.rovirunner.model.IRoviRunnerMediaPlayer;

public class RoviRunnerMediaPlayer implements IRoviRunnerMediaPlayer
{
    private MediaPlayer m_player = null;

    public RoviRunnerMediaPlayer()
    {
        m_player = new MediaPlayer();
    }
    
    @Override
    public void setOnPreparedListener( MediaPlayer.OnPreparedListener listener )
    {
        m_player.setOnPreparedListener( listener );
    }
    
    @Override
    public void setOnCompletionListener( MediaPlayer.OnCompletionListener listener )
    {
        m_player.setOnCompletionListener( listener );
    }
    
    @Override
    public void setOnErrorListener( MediaPlayer.OnErrorListener listener )
    {
        m_player.setOnErrorListener( listener );
    }

    @Override
    public int getCurrentPosition()
    {
        return m_player.getCurrentPosition();
    }

    @Override
    public int getDuration()
    {
        return m_player.getDuration();
    }

    @Override
    public boolean isPlaying()
    {
        return m_player.isPlaying();
    }

    @Override
    public void pause()
    {
        m_player.pause();
    }

    @Override
    public void seekTo( int pos )
    {
        m_player.seekTo( pos );
    }

    @Override
    public void start()
    {
        m_player.start();
    }

    @Override
    public void setDataSource( FileDescriptor fileDescriptor,
                               long startOffset,
                               long length ) throws IllegalArgumentException, IllegalStateException, IOException
    {
        m_player.setDataSource( fileDescriptor,
                                startOffset,
                                length );
    }

    @Override
    public void prepareAsync()
    {
        m_player.prepareAsync();
    }

    @Override
    public void stop()
    {
        m_player.stop();
    }

    @Override
    public void release()
    {
        m_player.release();
    }
}
