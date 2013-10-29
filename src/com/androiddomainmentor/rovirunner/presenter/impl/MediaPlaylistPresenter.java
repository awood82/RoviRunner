package com.androiddomainmentor.rovirunner.presenter.impl;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.androiddomainmentor.rovirunner.R;
import com.androiddomainmentor.rovirunner.presenter.IMediaPlaylistPresenter;
import com.androiddomainmentor.rovirunner.view.IMediaPlaylistView;

public class MediaPlaylistPresenter implements IMediaPlaylistPresenter, 
                                               LoaderCallbacks<Cursor>, 
                                               OnItemClickListener
{
    private IMediaPlaylistView m_view = null;
    private Context m_context = null;
    private SimpleCursorAdapter m_cursorAdapter = null;
    private LoaderManager m_loaderMgr = null;
    static final String[] PROJECTION = new String[] { MediaStore.Audio.AudioColumns._ID,
                                                      MediaStore.Audio.AudioColumns.ARTIST,
                                                      MediaStore.Audio.AudioColumns.TITLE, 
                                                      MediaStore.Audio.Media.DATA };

    public MediaPlaylistPresenter( IMediaPlaylistView view,
                                   Context applicationContext )
    {
        m_view = view;
        m_context = applicationContext;
    }

    @Override
    public void lifecycleStart()
    {
        String[] fromColumns = { MediaStore.Audio.AudioColumns.ARTIST, 
                                 MediaStore.Audio.AudioColumns.TITLE };
        int[] toViews = { R.id.mediaplaylist_row_artist, 
                          R.id.mediaplaylist_row_songName };

        if ( null == m_cursorAdapter )
        {
            m_cursorAdapter = new SimpleCursorAdapter( m_context,
                                                       R.layout.mediaplaylist_row_view,
                                                       null,
                                                       fromColumns,
                                                       toViews, 
                                                       0 );            
        }

        m_view.setListViewAdapter( m_cursorAdapter );
    }

    @Override
    public void lifecycleResume()
    {
        if ( null == m_loaderMgr )
        {
            m_loaderMgr = m_view.getTheLoaderManager();            
        }

        m_loaderMgr.initLoader( 0,
                                null,
                                this );
    }

    @Override
    public void lifecyclePause()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void lifecycleStop()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void lifecycleDestroy()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader( int arg0,
                                                                     Bundle arg1 )
    {
        return new CursorLoader( m_context,
                                 MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                 PROJECTION,
                                 null,
                                 null,
                                 null );
    }

    @Override
    public void onLoadFinished( android.support.v4.content.Loader<Cursor> arg0,
                                Cursor data )
    {
        m_cursorAdapter.swapCursor( data );
    }

    @Override
    public void onLoaderReset( android.support.v4.content.Loader<Cursor> arg0 )
    {
        m_cursorAdapter.swapCursor( null );
    }

    @Override
    public OnItemClickListener getItemClickListener()
    {
        return this;
    }

    @Override
    public void onItemClick( AdapterView<?> arg0, View arg1, int arg2, long arg3 )
    {
        Cursor c = m_cursorAdapter.getCursor();
        
        // TODO [2013-10-29 KW] is this even possible at this point?
        if ( null == c )
        {
            return;
        }
        
        // go to the row we clicked
        if ( c.moveToPosition( arg2 ) )
        {
            // get the file path corresponding to the row
            String path = c.getString( c.getColumnIndex( MediaStore.Audio.Media.DATA ) );

            Toast.makeText( m_context,
                            path,
                            Toast.LENGTH_LONG )
                 .show();
            // TODO [2013-10-29 KW] start media player activity
        }
    }
    
    
}
