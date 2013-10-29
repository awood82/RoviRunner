package com.androiddomainmentor.rovirunner.presenter;

import android.widget.AdapterView.OnItemClickListener;

public interface IMediaPlaylistPresenter
{

    void lifecycleStart();

    void lifecycleResume();

    void lifecyclePause();

    void lifecycleStop();

    void lifecycleDestroy();

    OnItemClickListener getItemClickListener();

}
