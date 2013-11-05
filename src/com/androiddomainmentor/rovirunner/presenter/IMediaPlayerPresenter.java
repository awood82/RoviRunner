package com.androiddomainmentor.rovirunner.presenter;

import android.media.MediaPlayer;
import android.widget.MediaController.MediaPlayerControl;
import com.androiddomainmentor.rovirunner.model.ILocalSourceManager;
import com.androiddomainmentor.rovirunner.model.impl.AudioTrackMetadata;

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
