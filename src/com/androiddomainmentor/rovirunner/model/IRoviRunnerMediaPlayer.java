package com.androiddomainmentor.rovirunner.model;

import java.io.FileDescriptor;
import java.io.IOException;
import android.media.MediaPlayer;

public interface IRoviRunnerMediaPlayer
{
    public abstract void setOnPreparedListener( MediaPlayer.OnPreparedListener listener );

    public abstract void setOnCompletionListener( MediaPlayer.OnCompletionListener listener );

    public abstract void setOnErrorListener( MediaPlayer.OnErrorListener listener );

    public abstract int getCurrentPosition();

    public abstract int getDuration();

    public abstract boolean isPlaying();

    public abstract void pause();

    public abstract void seekTo( int pos );

    public abstract void start();

    // TODO [2013-10-29 KW] need to expose different API for setting source via path
    public abstract void setDataSource( FileDescriptor fileDescriptor,
                                        long startOffset,
                                        long length ) throws IllegalArgumentException, IllegalStateException, IOException;

    public abstract void prepareAsync();

    public abstract void stop();

    public abstract void release();
}
