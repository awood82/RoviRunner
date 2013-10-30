package com.androiddomainmentor.rovirunner.model.test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

import com.androiddomainmentor.rovirunner.model.AudioTrackMetadata;
import com.androiddomainmentor.rovirunner.model.impl.LocalSourceManager;
import com.androiddomainmentor.rovirunner.test.Utils;

import android.content.Context;
import android.provider.MediaStore;
import android.test.AndroidTestCase;

public class LocalSourceManagerTests extends AndroidTestCase
{

    public void testGetAudioTracks_WhenExternalMemoryPresent_ReturnsATrackList()
    {
        Context context = getContext();
        LocalSourceManager testClass = spy(new LocalSourceManager());
        ArrayList<AudioTrackMetadata> expectedTrackList = Utils.createTrackList(new String[] {"path1"});
        when(testClass.getAudioTracksFromMediaStore(any(Context.class), eq(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI))).thenReturn(expectedTrackList);
        
        ArrayList<AudioTrackMetadata> trackList = testClass.getAudioTracks(context);
        
        verify(testClass).getAudioTracksFromMediaStore(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        assertEquals("path1", trackList.get(0).getPath());
    }
    
    public void testGetAudioTracks_WhenCalled_ReturnsCombinedListOfExternalAndInternalTracks()
    {
        Context context = getContext();
        LocalSourceManager testClass = spy(new LocalSourceManager());
        ArrayList<AudioTrackMetadata> expectedTrackListInternal = Utils.createTrackList(new String[] {"internalPath"});
        ArrayList<AudioTrackMetadata> expectedTrackListExternal = Utils.createTrackList(new String[] {"externalPath"});
        when(testClass.getAudioTracksFromMediaStore(any(Context.class), eq(MediaStore.Audio.Media.INTERNAL_CONTENT_URI))).thenReturn(expectedTrackListInternal);
        when(testClass.getAudioTracksFromMediaStore(any(Context.class), eq(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI))).thenReturn(expectedTrackListExternal);
        
        ArrayList<AudioTrackMetadata> trackList = testClass.getAudioTracks(context);
        
        verify(testClass).getAudioTracksFromMediaStore(context, MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
        verify(testClass).getAudioTracksFromMediaStore(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        ArrayList<String> pathList = Utils.getPathList(trackList);
        assertTrue(pathList.contains("internalPath"));
        assertTrue(pathList.contains("externalPath"));
    }
}
