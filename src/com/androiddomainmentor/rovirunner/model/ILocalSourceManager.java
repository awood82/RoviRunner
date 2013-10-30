package com.androiddomainmentor.rovirunner.model;

import java.util.ArrayList;

import android.content.Context;

public interface ILocalSourceManager
{
    public ArrayList<AudioTrackMetadata> getAudioTracks(Context context);
}
