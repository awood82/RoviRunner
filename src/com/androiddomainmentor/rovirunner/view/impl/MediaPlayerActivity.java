package com.androiddomainmentor.rovirunner.view.impl;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.androiddomainmentor.rovirunner.R;
import com.androiddomainmentor.rovirunner.model.impl.MediaControllerNoHide;
import com.androiddomainmentor.rovirunner.presenter.IMediaPlayerPresenter;
import com.androiddomainmentor.rovirunner.presenter.impl.MediaPlayerPresenter;
import com.androiddomainmentor.rovirunner.view.IMediaPlayerView;

public class MediaPlayerActivity extends Activity implements IMediaPlayerView
{
    private IMediaPlayerPresenter m_presenter;
    private MediaControllerNoHide m_mediaController;
    private TextView m_artistText;
    private TextView m_songText;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        // instantiate presenter
        m_presenter = new MediaPlayerPresenter( this, 
                                                getApplicationContext() );

        // set layout
        setContentView( R.layout.mediaplayer_view );

        // assign layout elements
        m_mediaController = new MediaControllerNoHide( this );
        m_artistText = (TextView)findViewById( R.id.artist_text_view );
        m_songText = (TextView)findViewById( R.id.song_text_view );

        // hook up mediacontroller to to mediaplayercontrol widget
        m_mediaController.setMediaPlayer( m_presenter.getMediaPlayerControl() );
        // TODO [2013-10-11 KW]:  figure out why layout can't be used
        // TODO [2013-10-11 KW]:  figure out why seek bar isn't a fixed size upon start of playback
        m_mediaController.setAnchorView( findViewById( R.id.mediaplayer_view ) );
        
        // set up media player
        m_presenter.setUpMediaPlayer();
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
        m_mediaController.show();
    }
    
    // TODO [2013-09-18 KW]:  implement lifecycle events
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        
        m_mediaController.setMediaPlayer(null);
        m_mediaController = null;
        m_presenter.lifecycleStop();
    }

    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        
        m_mediaController.hide();
    }

    @Override
    protected void onRestart()
    {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        
        // TODO [2013-09-21 KW]:  for now, play a song
        m_presenter.playRandomSong();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

}
