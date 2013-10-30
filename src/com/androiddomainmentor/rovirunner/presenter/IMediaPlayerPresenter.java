package com.androiddomainmentor.rovirunner.presenter;

import com.androiddomainmentor.rovirunner.model.AudioTrackMetadata;
import com.androiddomainmentor.rovirunner.model.ILocalSourceManager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.widget.MediaController.MediaPlayerControl;

public interface IMediaPlayerPresenter
{
    MediaPlayer makeNewMediaPlayer();
    
    MediaPlayerControl getMediaPlayerControl();
    
    void setLocalSourceManager(ILocalSourceManager mgr);

    void lifecycleStop();

    void lifecyclePause();

    void lifecycleStart();

    void lifecycleDestroy();

    void lifecycleResume();
    
    AudioTrackMetadata getRandomSong();

    void playSong( AudioTrackMetadata songMetadata );
}
