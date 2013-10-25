package com.androiddomainmentor.rovirunner.presenter;

import android.content.res.AssetFileDescriptor;
import android.widget.MediaController.MediaPlayerControl;
import com.androiddomainmentor.rovirunner.model.IRoviRunnerMediaPlayer;

public interface IMediaPlayerPresenter
{
    IRoviRunnerMediaPlayer makeNewMediaPlayer();

    MediaPlayerControl getMediaPlayerControl();

    void lifecycleStop();

    void lifecyclePause();

    void lifecycleStart();

    void lifecycleDestroy();

    void lifecycleResume();

    void playSong( String song );

    AssetFileDescriptor getRandomSongFileDescriptor();

    AssetFileDescriptor getSongFileDescriptor( String songFilename );
}
