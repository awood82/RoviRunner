package com.androiddomainmentor.rovirunner.test;

import java.util.ArrayList;

import com.androiddomainmentor.rovirunner.model.AudioTrackMetadata;

public class Utils
{
    public static ArrayList<AudioTrackMetadata> createTrackList(String[] paths)
    {
        ArrayList<AudioTrackMetadata> trackList = new ArrayList<AudioTrackMetadata>();
        
        for (String path : paths)
        {
            AudioTrackMetadata track = new AudioTrackMetadata();
            track.setPath(path);
            trackList.add(track);
        }
        
        return trackList;
    }
    
    public static ArrayList<String> getPathList(ArrayList<AudioTrackMetadata> trackList)
    {
        ArrayList<String> pathList = new ArrayList<String>();
        
        for (AudioTrackMetadata track : trackList)
        {
            pathList.add(track.getPath());
        }
        
        return pathList;
    }
}
