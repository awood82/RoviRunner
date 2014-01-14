package com.androiddomainmentor.rovirunner.model.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.androiddomainmentor.rovirunner.model.IRRDBHelper;
import com.androiddomainmentor.rovirunner.model.IRRDBUpdater;
import com.androiddomainmentor.rovirunner.model.ISongTempoGetter;
import com.androiddomainmentor.rovirunner.model.impl.RoviRunnerDBContract.SongInfo;

/*
 * This class must handle two things:
 * 
 * 1.  Any changes in MediaStore must be reflected in RRDB.
 *     a.  How can it handle updates from content observer?
 * 2.  Retrieve and store tempo for songs in RRDB.
 *     a.  
 * 
 * These tasks should be performed asynchronously.
 * 
 * An instance of this class should be run in a local service.
 */
public class RRDBUpdater implements IRRDBUpdater
{
    private static final String TAG = "RRDBUpdater";
    
    private Context m_context = null;
    
    // class that performs RRDB queries asynchronously
    private class RRDBQueryAsync extends AsyncTask<Cursor, Void, Void>
    {
        private IRRDBHelper m_rrdbHelper = null;
        private Context m_context = null;
        private ISongTempoGetter m_tempoGetter = null;
        
        public RRDBQueryAsync( Context context )
        {
            m_context = context;
        }
        
        @Override
        protected Void doInBackground( Cursor ... mediaStoreResults )
        {
            Cursor c = mediaStoreResults[0];
            if ( null == c )
            {
                return null;
            }
            
            if ( c.moveToFirst() )
            {
                if ( null == m_rrdbHelper )
                {
                    m_rrdbHelper = new RRDBHelper( m_context );
                }
                
                SQLiteDatabase db = m_rrdbHelper.getReadableDatabase();
                if ( null == db )
                {
                    Log.e( TAG, "Could not getReadableDatabase on RRDB!" );
                    return null;
                }
                
                if ( null == m_tempoGetter )
                {
                    m_tempoGetter = new SongTempoGetterRandom();
                }

                do
                {
                    ContentValues values = new ContentValues();
                    values.put( SongInfo.COLUMN_NAME_SONGID, 
                                c.getString( c.getColumnIndex( MediaStore.Audio.Media._ID ) ) );
                    // TODO [2013-12-09 KW] replace with real tempo getter
                    Double tempo = m_tempoGetter.getTempo( c.getString( c.getColumnIndex( MediaStore.Audio.Media.ARTIST ) ), 
                                                           c.getString( c.getColumnIndex( MediaStore.Audio.Media.TITLE ) ) );
                    values.put( SongInfo.COLUMN_NAME_BPM, tempo.toString() );
                    
                    // write row into our RR DB
                    long row = db.insert( SongInfo.TABLE_NAME,
                                          null,
                                          values );
                    Log.v( TAG, "Inserted row " + row + " into RRDB with BPM " + tempo );
                } while ( mediaStoreResults[0].moveToNext() );
            }
            
            /*
            String[] projectionColumns = { SongInfo._ID, 
                                           SongInfo.COLUMN_NAME_ARTIST, 
                                           SongInfo.COLUMN_NAME_TITLE, 
                                           SongInfo.COLUMN_NAME_BPM, 
                                           SongInfo.COLUMN_NAME_PATH, 
                                           SongInfo.COLUMN_NAME_MIME };
            
            Cursor rrdbResults = db.query( SongInfo.TABLE_NAME,
                                           projectionColumns,
                                           null,
                                           null,
                                           null,
                                           null,
                                           null );
            */
            
            // TODO [2013-11-30 KW]:  compare results from MediaStore to results from RRDB
            // TODO [2013-11-30 KW]:  get BPM for songs that don't have it
            
            return null;
        }
    }
    private RRDBQueryAsync m_rrdbQueryAsync = null;
    
    public RRDBUpdater( Context context )
    {
        m_context = context;
    }

    @Override
    public void processResultsFromMediaStore( Cursor results )
    {
        // what does RRDB currently have?
        if ( null == m_rrdbQueryAsync )
        {
            m_rrdbQueryAsync = new RRDBQueryAsync( m_context );
        }

        // can't run when it's already running
        if ( m_rrdbQueryAsync.getStatus() != AsyncTask.Status.RUNNING )
        {
            m_rrdbQueryAsync.execute( results );
        }
    }
}
