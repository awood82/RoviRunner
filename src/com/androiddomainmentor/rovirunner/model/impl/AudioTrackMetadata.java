package com.androiddomainmentor.rovirunner.model.impl;

public class AudioTrackMetadata
{
    public static final int BPM_NOT_SET = 0;
    
    private String path = "";
    private String album = "";
    private String artist = "";
    private String title = "";
    private int bpm = BPM_NOT_SET; // beats per minute
    
    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getBpm()
    {
        return bpm;
    }

    public void setBpm(int bpm)
    {
        this.bpm = bpm;
    }
}
