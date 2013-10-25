package com.androiddomainmentor.rovirunner.presenter.test;

import java.io.FileDescriptor;
import java.io.IOException;
import org.mockito.Mockito;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer.OnPreparedListener;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.Suppress;
import com.androiddomainmentor.rovirunner.model.IRoviRunnerMediaPlayer;
import com.androiddomainmentor.rovirunner.presenter.IMediaPlayerPresenter;
import com.androiddomainmentor.rovirunner.presenter.impl.MediaPlayerPresenter;
import com.androiddomainmentor.rovirunner.view.IMediaPlayerView;

// note:  test methods must be named with a "test" prefix, e.g., "testThisOrThat", 
// in order for it to be picked up as a test.

public class MediaPlayerPresenterTest extends AndroidTestCase
{
    private IMediaPlayerPresenter m_presenter;
    private IMediaPlayerView m_view;
    private IRoviRunnerMediaPlayer m_player;
    private Context m_context;
    private SharedPreferences m_prefs;

    // this ctor is required by junit... see
    // http://developer.android.com/tools/testing/testing_eclipse.html
    public MediaPlayerPresenterTest()
    {
        super();
    }
    
    @Override
    public void setUp() throws Exception
    {
        super.setUp();

        // set up mocks
        m_view = Mockito.mock( IMediaPlayerView.class );
        m_player = Mockito.mock( IRoviRunnerMediaPlayer.class );
        m_context = Mockito.mock( Context.class );
        m_prefs = Mockito.mock( SharedPreferences.class );

        // this is what we're testing against
        m_presenter = Mockito.spy( new MediaPlayerPresenter( m_view,
                                                             m_context, 
                                                             m_prefs ) );

        // set up mock player
        Mockito.doReturn( m_player )
               .when( m_presenter )
               .makeNewMediaPlayer();
    }
    
    public void testLifecycleStart()
    {
        m_presenter.lifecycleStart();
        
        Mockito.verify( m_presenter ).makeNewMediaPlayer();
        Mockito.verify( m_player ).setOnPreparedListener( Mockito.any( OnPreparedListener.class ) );
    }

    @Suppress // TODO [2013-09-29 KW] just trying some stuff out, there must be an easier way to do this...
    public void testPlayRandomSong() throws IllegalArgumentException, IllegalStateException, IOException
    {
        AssetFileDescriptor afd = Mockito.mock( AssetFileDescriptor.class );
        Mockito.doReturn( afd ).when( m_presenter ).getRandomSongFileDescriptor();
        Mockito.doNothing().when( m_player ).prepareAsync();
        
        m_presenter.playSong( null );

        Mockito.verify( m_player )
               .setDataSource( (FileDescriptor)Mockito.any(),
                               Mockito.anyLong(),
                               Mockito.anyLong() );
        Mockito.verify( m_player )
               .prepareAsync();

        Mockito.verify( m_view )
               .setArtistText( Mockito.anyString() );
        Mockito.verify( m_view )
               .setSongText( Mockito.anyString() );
    }
}
