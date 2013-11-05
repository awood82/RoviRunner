package com.androiddomainmentor.rovirunner.model;

import java.util.List;
import com.androiddomainmentor.rovirunner.model.impl.AudioTrackMetadata;

import android.content.Context;

public interface ILocalSourceManager
{
    public List<AudioTrackMetadata> getAudioTracks(Context context);
}
