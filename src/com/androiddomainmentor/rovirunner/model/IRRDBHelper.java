package com.androiddomainmentor.rovirunner.model;

import android.database.sqlite.SQLiteDatabase;

public interface IRRDBHelper
{
    SQLiteDatabase getReadableDatabase();
}
