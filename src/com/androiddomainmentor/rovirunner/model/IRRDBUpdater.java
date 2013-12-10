package com.androiddomainmentor.rovirunner.model;

import android.database.Cursor;

public interface IRRDBUpdater
{

    void processResultsFromMediaStore( Cursor results );
}
