package com.martinfredriksson.merbull;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.martinfredriksson.merbull.level.CollisionDetector;


public class GameScreen implements Screen, InputProcessor{
	
	World world;
	SpriteBatch batch;
	MyGame game;
	Level level;
	
	
	CollisionDetector colDet;
	
    float w = Gdx.graphics.getWidth();
    static float h = Gdx.graphics.getHeight();
	
	//Rendering stuff
	private Viewport viewport;
	private OrthographicCamera cam;
	Box2DDebugRenderer debugRenderer;
	
	private long startTime;
	
	public GameScreen(final MyGame game, Level level) {
		this.game = game;
		this.level = level;
		
		Gdx.input.setInputProcessor(this);
		
		this.colDet = new CollisionDetector(level);
		
		world = new World(new Vector2(0,0), false);
		world.setContactListener(colDet);
		batch = new SpriteBatch();
	
	    cam = new OrthographicCamera(20, 20*(16f/9f));
	    viewport = new FitViewport(20, 20*(16f/9f), cam);
		debugRenderer = new Box2DDebugRenderer(true, true, true, true, false, false);
		
		level.init(world);
		cam.position.set(0,10,0);
		startTime = System.currentTimeMillis();
	}


	@Override
	public void render (float delta) {
		//levelTime is time since level started
		long levelTime = System.currentTimeMillis() - startTime;
		
		if(level.gameOver()){
			game.setScreen(new EndScreen(game, false)); //GameScreen needs level so it knows what level it should run
            dispose();
		}
		
		if(level.hasWon()){
			game.setScreen(new EndScreen(game, true));
			dispose();
		}
		
		
		updateGame(level, delta);
		updateCamera();
		
		Gdx.gl.glClearColor(190/255f, 223/255f, 136/255f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(1/300f, 6, 2);
		game.batch.setProjectionMatrix(cam.combined);
			game.batch.begin();
			 	level.draw(game.batch);
			game.batch.end();
		debugRenderer.render(world, cam.combined);
	}
	

	private void updateCamera() {
		Vector3 camPos = new Vector3(level.getBall().getPosition().x, level.getBall().getPosition().y+15, 0);
		cam.position.lerp(camPos, 0.1f);
		cam.update(); 
	}


	private void updateGame(Level level, float delta){
		
	}
	
	
	public World getWorld(){
		return world;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resize(int width, int height) {
		//When window is resized, do some magic stuff
		viewport.update(width, height);
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
		//Things to remove when the gameScreen is disposed
		level.manager.dispose();
	}


	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("x: " + screenX + " y: " + screenY);
		Vector3 tmp = new Vector3(screenX, screenY, 0);
		cam.unproject(tmp);
		Vector2 tmp2 = new Vector2(tmp.x, tmp.y);
		tmp2.nor();
		System.out.println(tmp2);
		level.getBall().setVelocity(new Vector2(tmp2.x*15, tmp2.y*15));
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	//public void ... more functions here
}
