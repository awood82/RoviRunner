package com.androiddomainmentor.rovirunner.presenter.test;

import org.mockito.Mockito;

import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.androiddomainmentor.rovirunner.model.IRoviRunnerMediaPlayer;
import com.androiddomainmentor.rovirunner.presenter.IMediaPlayerPresenter;
import com.androiddomainmentor.rovirunner.presenter.impl.MediaPlayerPresenter;
import com.androiddomainmentor.rovirunner.view.IMediaPlayerView;

public class MediaPlayerPresenterTest extends AndroidTestCase
{
    private IMediaPlayerPresenter m_presenter = null;
    private IMediaPlayerView m_view = null;
    private IRoviRunnerMediaPlayer m_player = null;
    
    // this ctor is required by junit...  see
    // http://developer.android.com/tools/testing/testing_eclipse.html
    public MediaPlayerPresenterTest()
    {
        super();
    }
    
    @Override
    public void setUp()
    {
        // set up mocks
        m_view = Mockito.mock( IMediaPlayerView.class );
        m_player = Mockito.mock( IRoviRunnerMediaPlayer.class );
        Mockito.doReturn( m_player ).when( m_presenter ).makeNewMediaPlayer();
        
        // this is what we're testing against
        m_presenter = Mockito.spy( new MediaPlayerPresenter( m_view, 
                                                             new MockContext() ) );
    }
    
    public void blah()
    {
        assertTrue( true );
    }
}
