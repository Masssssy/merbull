package com.martinfredriksson.merbull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
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

public class EndScreen implements Screen {
	
	final MyGame game;
	OrthographicCamera camera;
	
	Stage stage;
	Table table;
	Skin skin;
	
	Sound gameOver;
	Sound victory;
	String buttonText;
	
	float time = 0;
	
	private boolean doOnce = true;
	
	private boolean win;
	
	public EndScreen(final MyGame game, boolean win){
		this.game = game;
		this.win = win;
		
		game.music.pause();
		if(!win){
			gameOver = Gdx.audio.newSound(Gdx.files.internal("gameover.ogg"));
			gameOver.play(1.0f);
			buttonText = "Try again";
		}else{
			victory = Gdx.audio.newSound(Gdx.files.internal("victory.ogg"));
			victory.play(1.0f);
			buttonText = "Next level";
		}
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		//skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
		
		//Table layout for menu
	    //table = new Table();
	   
	    skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));

	    
	    
		
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 1280);
        
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		time = time + delta;
        Gdx.gl.glClearColor(1, 102/255f, 102/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        
        stage.draw();
        game.batch.begin();
        if(win){
            game.font.draw(game.batch, "Level cleared! =)", 100, 1200);
            game.font.draw(game.batch, "Highscores", 100, 1000);
        }else{
        	game.font.draw(game.batch, "Highscores", 100, 1000);
            game.font.draw(game.batch, "You failed =(", 100, 1200);
        }

        game.batch.end();
        
        System.out.println(time);
        if(doOnce && time > 0.3){
            printScore();
            doOnce = false;
        }

        
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
        	//game.music.play();
            game.setScreen(new MenuScreen(game));
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

	private void printScore(){
		gameOver = Gdx.audio.newSound(Gdx.files.internal("gameover.ogg"));
		gameOver.play(1.0f);
		Database db = new Database();
		db.addEntry("name", 3, 0.02f);
		
		Scoreboard sb = db.getTop10();
		table = sb;
		db.closeConn();
		
	    table.setFillParent(true);
	    stage.addActor(table);
	    
		TextButton button1 = new TextButton(buttonText, skin);
	    button1.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button 1 pressed");
				return false;
			}
		});
	    table.add(button1);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		if(!win){
			gameOver.dispose();
		}else{
			victory.dispose();
		}
	}

}

