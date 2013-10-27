package com.androiddomainmentor.rovirunner.view.impl;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.TextView;

import com.androiddomainmentor.rovirunner.R;
import com.androiddomainmentor.rovirunner.presenter.IMediaPlayerPresenter;
import com.androiddomainmentor.rovirunner.presenter.impl.MediaPlayerPresenter;
import com.androiddomainmentor.rovirunner.view.IMediaPlayerView;

public class MediaPlayerActivity extends Activity implements IMediaPlayerView
{
    private IMediaPlayerPresenter m_presenter = null;
    private MediaController m_mediaController;
    private TextView m_artistText;
    private TextView m_songText;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        // set layout
        setContentView( R.layout.mediaplayer_view );

        // assign layout elements
        m_artistText = (TextView)findViewById( R.id.artist_text_view );
        m_songText = (TextView)findViewById( R.id.song_text_view );
    }

    @Override
    public void setArtistText( String artistName )
    {
        m_artistText.setText( artistName );
    }

    @Override
    public void setSongText( String songName )
    {
        m_songText.setText( songName );
    }
    

    @Override
    public void showMediaController()
    {
        m_mediaController.show( 0 );
    }
    
    // TODO [2013-09-18 KW]:  implement lifecycle events
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        
        m_presenter = null;
        m_mediaController = null;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        m_presenter.lifecyclePause();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        
        // TODO [2013-09-21 KW]:  for now, play a song
        m_presenter.lifecycleResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        // instantiate presenter
        if ( null == m_presenter )
        {
            m_presenter = new MediaPlayerPresenter( this, 
                                                    getApplicationContext(), 
                                                    getPreferences( MODE_PRIVATE ));
        }
        
        if ( null == m_mediaController )
        {
            m_mediaController = new MediaController( this );

            // hook up mediacontroller to to mediaplayercontrol widget
            m_mediaController.setMediaPlayer( m_presenter.getMediaPlayerControl() );
            // TODO [2013-10-11 KW]:  figure out why layout can't be used
            // TODO [2013-10-11 KW]:  figure out why seek bar isn't a fixed size upon start of playback
            m_mediaController.setAnchorView( findViewById( R.id.mediaplayer_view ) );
        }
        
        // set up media player
        m_presenter.lifecycleStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        m_presenter.lifecycleStop();
        m_mediaController.hide();
        m_mediaController = null;
    }
    
    // handle touch event by toggling media controls
    @Override
    public boolean onTouchEvent( MotionEvent ev )
    {
        if (    MotionEvent.ACTION_UP == ev.getAction() 
             && m_mediaController != null )
        {
            toggleMediaControlsVisiblity();
        }
        return false;
    }
    
    private void toggleMediaControlsVisiblity()
    {
        if ( m_mediaController.isShowing() )
        {
            m_mediaController.hide();
        }
        else
        {
            m_mediaController.show( 0 );
        }
    }
    
    // pop activity when back is pressed, regardless 
    // of whether media controls are showing or not
    // http://stackoverflow.com/questions/6051825/android-back-button-and-mediacontroller
    @Override
    public boolean dispatchKeyEvent( KeyEvent event )
    {
        if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK )
        {
            onBackPressed();
        }
        
        return super.dispatchKeyEvent( event );
    }
}
