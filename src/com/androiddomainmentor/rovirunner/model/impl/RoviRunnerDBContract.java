package com.androiddomainmentor.rovirunner.model.impl;

import android.provider.BaseColumns;

// refer to http://developer.android.com/training/basics/data-storage/databases.html
public final class RoviRunnerDBContract
{
    public RoviRunnerDBContract()
    {}
    
    public static abstract class SongInfo implements BaseColumns
    {
        public static final String TABLE_NAME = "songs";
        public static final String COLUMN_NAME_SONGID = "songid";
        public static final String COLUMN_NAME_BPM = "bpm";
    }
}
