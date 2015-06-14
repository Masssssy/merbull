package com.martinfredriksson.merbull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class SplashScreen implements Screen {
	
	final MyGame game;
	OrthographicCamera camera;
	
	private Texture splash;
	
	public SplashScreen(final MyGame game){
		this.game = game;
		
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 1280);
        
        splash = new Texture(Gdx.files.internal("splash.png"));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        
        game.batch.begin();
            game.batch.draw(splash, 104, 300, 512,512);
        game.batch.end();
        
        if (Gdx.input.isTouched()) {
            game.setScreen(new MenuScreen(game)); //GameScreen needs level so it knows what level it should run
            dispose();
        }
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		//Dispose textures etc. 
		splash.dispose();
		
	}

}