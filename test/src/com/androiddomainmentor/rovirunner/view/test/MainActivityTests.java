package com.androiddomainmentor.rovirunner.view.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.androiddomainmentor.rovirunner.R;
import com.androiddomainmentor.rovirunner.view.impl.MainActivity;

public class MainActivityTests extends
                               ActivityInstrumentationTestCase2<MainActivity>
{

    private MainActivity mMainActivity;
    private Button mPlayLocalMusicButton;
    private ExpandableListView mPlayStreamingMusicExpList;

    public MainActivityTests()
    {
        super( MainActivity.class );
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        mMainActivity = getActivity();
        mPlayLocalMusicButton = (Button)mMainActivity.findViewById( R.id.button_play_local_music );
        mPlayStreamingMusicExpList = (ExpandableListView)mMainActivity.findViewById( R.id.expandableListView_internet_music );
    }

    public void testPreconditions()
    {
        assertNotNull( "mMainActivity is null",
                       mMainActivity );
        assertNotNull( "mPlayLocalMusicButton is null",
                       mPlayLocalMusicButton );
        assertNotNull( "mPlayStreamingMusicExpList is null",
                       mPlayStreamingMusicExpList );
    }

    public void testPlayMusicButton_labelText()
    {
        final String expected = mMainActivity.getString( R.string.play_local_files );
        final String actual = mPlayLocalMusicButton.getText()
                                                   .toString();
        assertEquals( expected,
                      actual );
    }

}
