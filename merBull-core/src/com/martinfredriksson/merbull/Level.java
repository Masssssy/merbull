package com.martinfredriksson.merbull;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.martinfredriksson.merbull.level.Field;
import com.martinfredriksson.merbull.level.Goal;
import com.martinfredriksson.merbull.level.Trap;
import com.martinfredriksson.merbull.level.Wall;

/** Level is the object passed to GameScreen so that it knows what the level should look like **/
public class Level {
	
	List<Ball> balls = new ArrayList<Ball>();
	List<Wall> walls = new ArrayList<Wall>();
	List<Field> fields = new ArrayList<Field>();
	List<Trap> traps = new ArrayList<Trap>();
	List<Goal> goals = new ArrayList<Goal>();
	
	Texture ballTexture;
	Texture ballShieldHiTexture;
	Texture ballShieldMiTexture;
	Texture ballShieldLoTexture;
	Texture wallTexture;
	Texture wallBlackTexture;
	Texture wallGreenTexture;
	Texture goalTexture;
	Texture trapTexture;
	Texture boostTexture;
	Texture slowTexture;

	AssetManager manager;
	private boolean gameOver = false;
	private int allowedBounces;
	private static int max_bounces=5;
	private boolean shield = true;
	private boolean win = false;
	private Vector2 angle;
	
	Sound donk;
	Sound swoosh;
	Sound scratch;
	
	private String player;
	
	ParticleEffectPool pool;
	Array<PooledEffect> effects = new Array();
	ParticleEffect blackHole = new ParticleEffect();
	
	public Level(String p){
		//IF TIME:
		//Create the level from text file or equivalent
		//playerpos, wallpositions, weapon pos etc
		player = p;
	}
	
	public int getBounces() {
		return allowedBounces;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public static int getMaxBounces(){
		return max_bounces;
	}

	//Actually initialize the level 
	public void init(World world) {
		
		donk = Gdx.audio.newSound(Gdx.files.internal("donk.ogg"));
		swoosh = Gdx.audio.newSound(Gdx.files.internal("swoosh.ogg"));
		scratch = Gdx.audio.newSound(Gdx.files.internal("scratch.ogg"));

			
		//Setup level settings
		allowedBounces = max_bounces;
		
		//Initiate the level world
		balls.add(new Ball(world, new Vector2(0,0)));
		ballShieldHiTexture = manager.get("ball_shield_hi.png", Texture.class);
		ballShieldMiTexture = manager.get("ball_shield_mi.png", Texture.class);
		ballShieldLoTexture = manager.get("ball_shield_lo.png", Texture.class);
		ballTexture = manager.get("ball.png", Texture.class);
		
		//Add some walls
		walls.add(new Wall(world, new Vector2(1f, 30f), new Vector2(-9f, 5f)));
		walls.add(new Wall(world, new Vector2(1f, 30f), new Vector2(9f, 5f)));
		walls.add(new Wall(world, new Vector2(8f, 1f), new Vector2(0f, -4f)));
		//Add rotating walls
		walls.add(new Wall(world, new Vector2(3f,1f), new Vector2(-3f,10f), 2f));
		walls.add(new Wall(world, new Vector2(3f,1f), new Vector2(4f,10f), 2f));
		
		wallTexture = manager.get("wall.png", Texture.class);
		wallBlackTexture = manager.get("wall_black.png", Texture.class);
		//wallGreenTexture = manager.get("wall_green.png", Texture.class);
		
		fields.add(new Field(world,new Vector2(2f,1f), new Vector2(0f,10f), 0.5f, true));
		fields.add(new Field(world,new Vector2(2f,5f), new Vector2(0f,17f), -1000f, false));
		boostTexture = manager.get("boost.png", Texture.class);
		slowTexture = manager.get("slow.png", Texture.class);
		
		goalTexture = manager.get("goal.png", Texture.class);
		goals.add(new Goal(world,new Vector2(8f,2f), new Vector2(0f,33f)));
		
		//Add death trap
		traps.add(new Trap(world, new Vector2(4f,2f)));

		blackHole.load(Gdx.files.internal("particle.p"), Gdx.files.internal(""));
		pool = new ParticleEffectPool(blackHole, 1, 10);
		PooledEffect effect = pool.obtain();
		effect.setPosition(4f,2f);
		effects.add(effect);
		
		PooledEffect effect2 = pool.obtain();
		effect2.setPosition(-3f,19f);
		effects.add(effect2);
		
		traps.add(new Trap(world, new Vector2(-3f,19f)));
		
		trapTexture = manager.get("trap.png", Texture.class);
		
	}

	public void draw(SpriteBatch batch){ 
	   // Draw level
	    
	    //Draw fields
	    TextureRegion boostReg = new TextureRegion(boostTexture);
		boostTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		TextureRegion slowReg = new TextureRegion(boostTexture);
		slowTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
	    Iterator<Field> e = fields.iterator();
	    while (e.hasNext()){
	    	Field field = e.next();
	    	if(field.getFricAcc()){
	    		//acc field
		    	batch.draw(boostTexture, field.getDrawPos().x, field.getDrawPos().y, field.getSize().x*2f, field.getSize().y*2f, 0, 0, field.getSize().x, field.getSize().y);
	    	}else{
		    	batch.draw(slowTexture, field.getDrawPos().x, field.getDrawPos().y, field.getSize().x*2f, field.getSize().y*2f, 0, 0, field.getSize().x, field.getSize().y);
	    	}
	    }
	    
	    
	    //Draw walls
	    TextureRegion tst = new TextureRegion(wallTexture);
		wallTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	    
	    Iterator<Wall> b = walls.iterator();
	    while (b.hasNext()){
	    	Wall wall = b.next();
	    	if(wall.isRotating()){
		    	batch.draw(tst, wall.getDrawPos().x+(wall.getSize().x/2), wall.getDrawPos().y+(wall.getSize().y/2), 1.5f, 0.5f, wall.getSize().x, wall.getSize().y, 2, 2, wall.getBody().getAngle()*MathUtils.radiansToDegrees % 360);
	    	}else{
		    	batch.draw(wallTexture, wall.getDrawPos().x, wall.getDrawPos().y, wall.getSize().x*2f, wall.getSize().y*2f, 0, 0, wall.getSize().x*6, wall.getSize().y*6);	    
	    	}
	    }
	    
	    //Draw death traps
	    Iterator<Trap> c = traps.iterator();
	    while (c.hasNext()){
	    	Trap trap = c.next();
	    	//batch.draw(trapTexture,trap.getPosition().x-(trap.getSize().x),trap.getPosition().y-(trap.getSize().y),trap.getSize().x*2,trap.getSize().y*2);
	    }
	    
	    //Draw goal
	    TextureRegion goalReg = new TextureRegion(goalTexture);
		goalTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	    Iterator<Goal> d = goals.iterator();
	    while (d.hasNext()){
	    	Goal goal = d.next();
	    	batch.draw(goalTexture, goal.getDrawPos().x, goal.getDrawPos().y, goal.getSize().x*2f, goal.getSize().y*2f, 0, 0, goal.getSize().x, goal.getSize().y);	    
	    }
	    
	    
		
	    //Draw balls, with or without shield depending on state
	    Iterator<Ball> a = balls.iterator();
	    while (a.hasNext()){
	    	Ball ball = a.next();
	    	if(ball.hasShield() && this.getBounces() >= 5){
	    		//Render ball with green
	    		batch.draw(ballShieldHiTexture,ball.getPosition().x-(ball.getSize()),ball.getPosition().y-(ball.getSize()),ball.getSize()*2,ball.getSize()*2);
	    	}else if(ball.hasShield() && this.getBounces() >= 3){
	    		//yellow ball
	    		batch.draw(ballShieldMiTexture,ball.getPosition().x-(ball.getSize()),ball.getPosition().y-(ball.getSize()),ball.getSize()*2,ball.getSize()*2);
	    	}else if(ball.hasShield() && this.getBounces() >= 1){
	    		//red ball
	    		batch.draw(ballShieldLoTexture,ball.getPosition().x-(ball.getSize()),ball.getPosition().y-(ball.getSize()),ball.getSize()*2,ball.getSize()*2);
	    	}else{
	    		//Else render ball without shield
		    	batch.draw(ballTexture,ball.getPosition().x-(ball.getSize()/2),ball.getPosition().y-(ball.getSize()/2),ball.getSize(),ball.getSize());
	    	}
	    }
	    
	    for (int i = effects.size - 1; i >= 0; i--) {
	        PooledEffect effect = effects.get(i);
	        effect.draw(batch, 0.16f);
	        if (effect.isComplete()) {
	            effect.free();
	            effects.removeIndex(i);
	        }
	    }
	    
	    
	}
	
    public void playDonk(){
		donk.play(1.0f);	
    }
    
    public void playSwoosh(){
		swoosh.play(1.0f);	
    }
    
    public void playScratch(){
		scratch.play(1.0f);	
    }

	public void setAssetManager(AssetManager manager) {
		this.manager = manager;
	}
	
	public Ball getBall(){
		return balls.get(0);
	}
	
	public void die(){
		System.out.println("Level lost");
		gameOver = true;
	}
	
	public boolean gameOver(){
		return gameOver;
	}
	
	public void win(){
		win = true;
	}
	
	public boolean hasWon(){
		return win;
	}

	public void reduceBounce() {
		this.allowedBounces--;
		System.out.println("Bounces left:" + allowedBounces);
		if(allowedBounces < 0){
			//Lost level
			this.die();
		}else if(allowedBounces == 0){
			//Remove shield
			getBall().setShield(false);
			this.getBall().getBody().getFixtureList().first().getShape().setRadius(0.5f);
			
		}
	}
	

	
	public void LaunchControl(Ball b, Vector2 vc){
		if(b.state == 0) {
			b.state++;
		}
		else if(b.state == 1) {
			angle = vc.nor();
			b.state++;
		}
		else if(b.state == 2) {
			angle = angle.scl(vc.len2());
			System.out.println(vc.len2());
			b.setVelocity(angle);
			b.state++;
		}
	}	
}