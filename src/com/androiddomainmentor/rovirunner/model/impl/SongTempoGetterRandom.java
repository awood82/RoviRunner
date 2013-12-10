package com.androiddomainmentor.rovirunner.model.impl;

import java.util.Random;

import com.androiddomainmentor.rovirunner.model.ISongTempoGetter;

public class SongTempoGetterRandom implements ISongTempoGetter
{
    private static final int MAX_BPM = 200;
    private static final int MIN_BPM = 30;
    
    private Random m_generator = null;
    
    public SongTempoGetterRandom()
    {
        m_generator = new Random();
    }

    @Override
    public Double getTempo( String artist, String song )
    {
        Double tempo = m_generator.nextDouble() * ( MAX_BPM - MIN_BPM ) + MIN_BPM;
        return tempo;
    }

}
