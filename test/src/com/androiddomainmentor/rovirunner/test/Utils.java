
package com.androiddomainmentor.rovirunner.test;

import java.util.ArrayList;
import java.util.List;
import com.androiddomainmentor.rovirunner.model.impl.AudioTrackMetadata;

public class Utils
{
    public static List<AudioTrackMetadata> createTrackList( String[] paths )
    {
        List<AudioTrackMetadata> trackList = new ArrayList<AudioTrackMetadata>();

        for ( String path : paths )
        {
            AudioTrackMetadata track = new AudioTrackMetadata();
            track.setPath( path );
            trackList.add( track );
        }

        return trackList;
    }

    public static List<String> getPathList( List<AudioTrackMetadata> trackList )
    {
        List<String> pathList = new ArrayList<String>();

        for ( AudioTrackMetadata track : trackList )
        {
            pathList.add( track.getPath() );
        }

        return pathList;
    }
}
