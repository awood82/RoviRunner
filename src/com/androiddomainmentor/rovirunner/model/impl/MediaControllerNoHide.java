package com.androiddomainmentor.rovirunner.model.impl;

import android.content.Context;
import android.widget.MediaController;

public class MediaControllerNoHide extends MediaController
{

    public MediaControllerNoHide( Context context )
    {
        super( context );
    }

    @Override
    public void hide()
    {
        // do nothing
    }

    public void actuallyHide()
    {
        super.hide();
    }
}
