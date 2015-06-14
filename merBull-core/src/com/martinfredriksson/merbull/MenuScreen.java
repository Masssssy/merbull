package com.martinfredriksson.merbull;

import javax.swing.GroupLayout.Alignment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class MenuScreen implements Screen {
	
	final MyGame game;
	OrthographicCamera camera;
	
	Stage stage;
	Table table;
	Skin skin;
	
	public String player;
	
	public MenuScreen(final MyGame game){
		this.game = game;
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
		
		//Table layout for menu
	    table = new Table();
	    table.setFillParent(true);
	    
	    if(player == null)
	    	player = "Name";
	    
	    TextButton start = new TextButton("Start!", skin);
	    final TextField name = new TextField(player, skin);
	    TextButton highscore = new TextButton("Highscores", skin);

	    //name.setAlignment(Align.center);
	    
	    highscore.addListener(new InputListener() {
	    	public boolean touchDown (InputEvent event, float c, float y, int pointer, int button) {
	    		game.setScreen(new EndScreen(game, 1, player, 0, 0));
	    		dispose();
	    		return false;
	    	}
	    });
	    
	    start.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				player = name.getText();
				game.setScreen(new LoadScreen(game, new Level(player))); //GameScreen needs level so it knows what level it should run
	            dispose();
				return false;
			}
		});
	    
	    table.add(name).width(250).height(50).padBottom(10f);
	    table.row();
	    table.add(start).width(250).height(50).padBottom(10f);
	    table.row();
	    table.add(highscore).width(250).height(50).padBottom(10f);
	    stage.addActor(table);
		
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 1280);
        
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(101/255f, 182/255f, 241/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        
        stage.draw();
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
		stage.dispose();
		
	}

}

