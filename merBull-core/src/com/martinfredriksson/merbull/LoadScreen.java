package com.martinfredriksson.merbull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;

public class LoadScreen implements Screen {
	
	private MyGame game;
	private Level level;
	private AssetManager manager;
	
	public LoadScreen(MyGame game, Level level){
		this.level = level;
		this.game = game;
		manager = new AssetManager();
		level.setAssetManager(manager);
	}

	@Override
	public void show() {
		//Add things necessary on this level to the loading queue
		
		//Player should always load
		manager.load("ball_shield_hi.png", Texture.class);
		manager.load("ball_shield_mi.png", Texture.class);
		manager.load("ball_shield_lo.png", Texture.class);
		
		manager.load("ball.png", Texture.class);
		
		manager.load("goal.png", Texture.class);
		
		manager.load("boost.png", Texture.class);
		manager.load("slow.png", Texture.class);
		
		//for now all textures will also always load
		manager.load("wall.png", Texture.class);
		manager.load("wall_black.png", Texture.class);
		
		manager.load("trap.png", Texture.class);
	}

	@Override
	public void render(float delta) {
		
	   if(manager.update()) {
	          // we are done loading, let's move to another screen!
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched()){
	            game.setScreen(new GameScreen(game, level)); //GameScreen needs level so it knows what level it should run
	            dispose();
			}
	   }
	   
	   //Loading progress
	   float progress = manager.getProgress();
	   System.out.println(progress);
	   
		//Render the load screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "Please wait..", 100, 150);
        game.font.draw(game.batch, "Game is loading", 100, 100);
        game.batch.end();
		
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
		
	}
	
}