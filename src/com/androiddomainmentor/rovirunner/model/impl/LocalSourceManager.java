package com.androiddomainmentor.rovirunner.model.impl;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.androiddomainmentor.rovirunner.model.AudioTrackMetadata;
import com.androiddomainmentor.rovirunner.model.ILocalSourceManager;

public class LocalSourceManager implements ILocalSourceManager
{
    private static final String TAG = "LocalSourceManager";
    
    @Override
    public ArrayList<AudioTrackMetadata> getAudioTracks(Context context)
    {
        ArrayList<AudioTrackMetadata> trackList = new ArrayList<AudioTrackMetadata>();
        
        // Get tracks from external SD card
        trackList.addAll(getAudioTracksFromMediaStore(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI));
        
        // Get tracks from internal SD card
        trackList.addAll(getAudioTracksFromMediaStore(context, MediaStore.Audio.Media.INTERNAL_CONTENT_URI));
        
        return trackList;
    }
    
    public ArrayList<AudioTrackMetadata> getAudioTracksFromMediaStore(Context context, Uri uri)
    {
        ArrayList<AudioTrackMetadata> trackList = new ArrayList<AudioTrackMetadata>();
        
        if (null == context)
        {
            return trackList;
        }
        
        ContentResolver cr = context.getContentResolver();
        String[] projectionColumns = {
                MediaStore.Audio.Media.DATA,   // path
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.MIME_TYPE
        };
        String whereClause = MediaStore.Audio.Media.MIME_TYPE + "=?"
                + " OR " + MediaStore.Audio.Media.MIME_TYPE + "=?"
                + " OR " + MediaStore.Audio.Media.MIME_TYPE + "=?";
        String[] whereParams = { "audio/mp3", "audio/mpeg", "audio/mp4" };
        
        // Look up a number of audio tracks on the device
        Cursor cursor = cr.query(uri, projectionColumns, whereClause, whereParams, null);
        
        // Loop through the database query results and add the metadata to our list of songs
        if (true == cursor.moveToFirst()) // Make sure at least one track was returned
        {
            do
            {
                AudioTrackMetadata meta = new AudioTrackMetadata();
                meta.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                meta.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                meta.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                meta.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                String mime = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
                Log.d(TAG, mime);
                
                trackList.add(meta);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        return trackList;
    }
    
}
