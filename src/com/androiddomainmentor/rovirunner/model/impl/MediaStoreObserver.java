package com.androiddomainmentor.rovirunner.model.impl;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import com.androiddomainmentor.rovirunner.model.IMediaStoreObserver;
import com.androiddomainmentor.rovirunner.model.IRRDBUpdater;

public class MediaStoreObserver extends ContentObserver implements IMediaStoreObserver
{
    private static final int QUERY_MEDIASTORE = 0;
    
    private IRRDBUpdater m_rrdbUpdater = null;
    
    private Context m_context = null;
    private static final class QueryHandler extends AsyncQueryHandler
    {
        private IMediaStoreObserver m_observer = null;
        
        public QueryHandler( ContentResolver cr, 
                             IMediaStoreObserver observer )
        {
            super( cr );
            m_observer = observer;
        }

        @Override
        protected void onQueryComplete( int token, 
                                        Object cookie, 
                                        Cursor cursor )
        {
            if ( QUERY_MEDIASTORE == token )
            {
                m_observer.updateRRDB( cursor );
            }
        }
        
    }
    private QueryHandler m_queryHandler = null;
    
    public MediaStoreObserver( Handler handler, 
                               Context context )
    {
        super( handler );
        m_context = context;
        m_queryHandler = makeNewQueryHandler();
    }
    
    public QueryHandler makeNewQueryHandler()
    {
        ContentResolver cr = m_context.getContentResolver();
        return new QueryHandler( cr, 
                                 this );
    }

    @Override
    public void onChange( boolean selfChange )
    {
        this.onChange( selfChange, 
                       null );
    }
    
    @Override
    public void onChange( boolean selfChange, 
                          Uri uri )
    {
        // query the DB asynchronously
        // TODO [2013-12-09 KW] can I get a cursor of removed rows?
        String[] projectionColumns = { MediaStore.Audio.Media._ID, 
                                       MediaStore.Audio.Media.DATA, // path
                                       MediaStore.Audio.Media.ARTIST,
                                       MediaStore.Audio.Media.ALBUM,
                                       MediaStore.Audio.Media.TITLE,
                                       MediaStore.Audio.Media.MIME_TYPE };
        String whereClause =   MediaStore.Audio.Media.MIME_TYPE 
                             + "=?"
                             + " OR "
                             + MediaStore.Audio.Media.MIME_TYPE
                             + "=?"
                             + " OR "
                             + MediaStore.Audio.Media.MIME_TYPE
                             + "=?";
        String[] whereParams = { "audio/mp3",
                                 "audio/mpeg",
                                 "audio/mp4" };
        
        m_queryHandler.startQuery( QUERY_MEDIASTORE,
                                   null,
                                   uri,
                                   projectionColumns,
                                   whereClause,
                                   whereParams,
                                   null );
    }
    
    @Override
    public void updateRRDB( Cursor input )
    {
        if ( null == m_rrdbUpdater )
        {
            m_rrdbUpdater = new RRDBUpdater( m_context );
        }
        
        m_rrdbUpdater.processResultsFromMediaStore( input );
    }
}
