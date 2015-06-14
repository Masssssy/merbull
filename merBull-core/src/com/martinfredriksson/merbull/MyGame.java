package com.martinfredriksson.merbull;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MyGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    Music music;

    public void create() {
        batch = new SpriteBatch();
        //Use default arial font
        font = new BitmapFont();
        font.scale(3);
    	
		music = Gdx.audio.newMusic(Gdx.files.internal("loop.ogg"));	
		//music.play();
		music.setLooping(true);
		
        this.setScreen(new SplashScreen(this));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}