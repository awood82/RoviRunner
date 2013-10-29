package com.androiddomainmentor.rovirunner.view;

import android.support.v4.app.LoaderManager;
import android.support.v4.widget.CursorAdapter;

public interface IMediaPlaylistView
{

    void setListViewAdapter( CursorAdapter m_cursorAdapter );

    LoaderManager getTheLoaderManager();

}
