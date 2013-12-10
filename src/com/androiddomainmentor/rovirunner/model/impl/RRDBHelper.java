package com.androiddomainmentor.rovirunner.model.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androiddomainmentor.rovirunner.model.IRRDBHelper;
import com.androiddomainmentor.rovirunner.model.impl.RoviRunnerDBContract.SongInfo;

public class RRDBHelper extends SQLiteOpenHelper implements IRRDBHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RoviRunner.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = 
        "CREATE TABLE " + SongInfo.TABLE_NAME 
        + "(" 
        + SongInfo._ID + " INTEGER PRIMARY KEY," 
        + SongInfo.COLUMN_NAME_SONGID + TEXT_TYPE + COMMA_SEP 
        + SongInfo.COLUMN_NAME_BPM + TEXT_TYPE + COMMA_SEP 
        + " )";
    private static final String SQL_DELETE_ENTRIES = 
        "DROP TABLE IF EXISTS " + SongInfo.TABLE_NAME;
    
    
    public RRDBHelper( Context context )
    {
        super( context,
               DATABASE_NAME,
               null,
               DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase db )
    {
        db.execSQL( SQL_CREATE_ENTRIES );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
    {
        db.execSQL( SQL_DELETE_ENTRIES );
        onCreate( db );
    }

}
