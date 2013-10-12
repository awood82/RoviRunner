package com.androiddomainmentor.rovirunner.presenter;

import android.content.res.AssetFileDescriptor;
import android.widget.MediaController.MediaPlayerControl;
import com.androiddomainmentor.rovirunner.model.IRoviRunnerMediaPlayer;

public interface IMediaPlayerPresenter
{
    void playRandomSong();

    IRoviRunnerMediaPlayer makeNewMediaPlayer();

    AssetFileDescriptor getRandomSongFileDescriptor();

    void setUpMediaPlayer();

    MediaPlayerControl getMediaPlayerControl();

    void lifecycleStop();
}
