package com.androiddomainmentor.rovirunner.presenter;

import android.content.Context;
import android.widget.ExpandableListAdapter;

public interface IMainActivityPresenter
{
    void rescanLocalMusic(Context context);

    void playLocalMusic(Context context);

    /*!
     * returns an adapter that is hooked up to a list of streaming sources
     */
    ExpandableListAdapter getStreamingSourcesAdapter();

}
