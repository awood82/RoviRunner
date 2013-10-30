package com.androiddomainmentor.rovirunner.presenter.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.widget.ExpandableListAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.androiddomainmentor.rovirunner.R;
import com.androiddomainmentor.rovirunner.model.IStreamingSourcesManager;
import com.androiddomainmentor.rovirunner.model.impl.StreamingSourcesManager;
import com.androiddomainmentor.rovirunner.presenter.IMainActivityPresenter;
import com.androiddomainmentor.rovirunner.view.IMainActivityView;
import com.androiddomainmentor.rovirunner.view.impl.MediaPlayerActivity;

public class MainActivityPresenter implements IMainActivityPresenter
{
    private IMainActivityView m_view = null;
    private IStreamingSourcesManager m_streamingSourcesMgr = null;
    private Context m_context = null;
    MediaPlayer m_player;

    public MainActivityPresenter( IMainActivityView view, 
                                  Context context )
    {
        m_view = view;
        m_context = context;
        m_streamingSourcesMgr = new StreamingSourcesManager();
    }
    @Override
    public void rescanLocalMusic(Context context)
    {
        File externalSD = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        MediaScannerConnection.scanFile(context,
                new String[] { externalSD.getPath() },
                null,
                null
        );
    }

    @Override
    public void playLocalMusic(Context context)
    {
        // TODO [2013-09-07 KW]: implement this
        // NOTE: LOL
        Toast.makeText( m_context,
                        "OONTZ OONTZ OONTZ OONTZ",
                        Toast.LENGTH_LONG )
             .show();
        
        //m_player = new RoviRunnerMediaPlayer(context);
        //m_player.start();
        
        
        
        Intent intent = new Intent( m_context,
                                    MediaPlayerActivity.class );

        // send the intent
        m_view.startActivity( intent );

    }

    @Override
    public ExpandableListAdapter getStreamingSourcesAdapter()
    {
        // refer to:  http://blog.denevell.org/android-SimpleExpandableListAdapter-example.html
        
        // keys for our maps we'll be creating
        final String KEY_GROUP_NAME = "GROUP_NAME";
        final String KEY_CHILD_NAME = "STREAM_SOURCE";
        
        // we only have one parent
        List<Map<String, String>> listOfParents = new ArrayList<Map<String, String>>();
        Map<String, String> parents = new HashMap<String, String>();
        parents.put( KEY_GROUP_NAME, "Stream music from..." );
        listOfParents.add( parents );

        // that one parent has children; each child needs to be its own map, which may 
        // seem interesting, but it's required by SimpleExpandableListAdapter
        List<List<Map<String, String>>> listOfChildLists = new ArrayList<List<Map<String, String>>>();
        List<Map<String, String>> children = new ArrayList<Map<String, String>>();
        for ( String value : m_streamingSourcesMgr.getAvailableStreamingSources().values() )
        {
            Map<String, String> child = new HashMap<String, String>();
            child.put( KEY_CHILD_NAME, value );
            children.add( child );
        }
        listOfChildLists.add( children );
        
        ExpandableListAdapter adapter = new SimpleExpandableListAdapter( m_context,
                                                                         listOfParents,
                                                                         R.layout.streaming_sources_group_view,
                                                                         new String[] { KEY_GROUP_NAME },
                                                                         new int[] { R.id.streaming_sources_group_name },
                                                                         listOfChildLists,
                                                                         R.layout.streaming_sources_row_view,
                                                                         new String[] { KEY_CHILD_NAME },
                                                                         new int[] { R.id.streaming_sources_row_name } );
        
        return adapter;
    }
}
