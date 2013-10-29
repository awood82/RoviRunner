package com.androiddomainmentor.rovirunner.view.impl;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.CursorAdapter;
import android.widget.ListView;

import com.androiddomainmentor.rovirunner.R;
import com.androiddomainmentor.rovirunner.presenter.IMediaPlaylistPresenter;
import com.androiddomainmentor.rovirunner.presenter.impl.MediaPlaylistPresenter;
import com.androiddomainmentor.rovirunner.view.IMediaPlaylistView;

public class MediaPlaylistActivity extends FragmentActivity implements IMediaPlaylistView
{
    private IMediaPlaylistPresenter m_presenter = null;
    private ListView m_listView = null;
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        
        // set layout
        setContentView( R.layout.mediaplaylist_view );
        
        m_listView = (ListView)findViewById( R.id.mediaPlaylistListView );
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        
        if ( null == m_presenter )
        {
            m_presenter = new MediaPlaylistPresenter( this, 
                                                      getApplicationContext() );
        }

        m_listView.setOnItemClickListener( m_presenter.getItemClickListener() );
        
        m_presenter.lifecycleStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        
        m_presenter.lifecycleResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        
        m_presenter.lifecyclePause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        
        m_presenter.lifecycleStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        
        m_presenter.lifecycleDestroy();
    }

    @Override
    public void setListViewAdapter( CursorAdapter cursorAdapter )
    {
        m_listView.setAdapter( cursorAdapter );
    }

    @Override
    public LoaderManager getTheLoaderManager()
    {
        return getSupportLoaderManager();
    }
}
