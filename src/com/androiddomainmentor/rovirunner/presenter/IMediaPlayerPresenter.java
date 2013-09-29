package com.androiddomainmentor.rovirunner.presenter;

import com.androiddomainmentor.rovirunner.model.IRoviRunnerMediaPlayer;

import android.content.res.AssetFileDescriptor;
import android.widget.MediaController.MediaPlayerControl;

public interface IMediaPlayerPresenter
{

    MediaPlayerControl getMediaPlayerControl();

    void playRandomSong();

    IRoviRunnerMediaPlayer makeNewMediaPlayer();

    AssetFileDescriptor getRandomSongFileDescriptor();

    void setUpMediaPlayer();
}
