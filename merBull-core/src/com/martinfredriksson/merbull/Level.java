package com.martinfredriksson.merbull;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
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
	Texture ballShieldTexture;
	Texture wallTexture;
	Texture trapTexture;

	AssetManager manager;
	private boolean gameOver = false;
	private int allowedBounces;
	private boolean shield = true;
	private boolean win = false;
	private Vector2 angle;
	
	public Level(){
		//IF TIME:
		//Create the level from text file or equivalent
		//playerpos, wallpositions, weapon pos etc
	}

	//Actually initialize the level 
	public void init(World world) {
		
		//Setup level settings
		allowedBounces = 5;
		
		//Initiate the level world
		balls.add(new Ball(world, new Vector2(0,0)));
		ballShieldTexture = manager.get("ball_shield.png", Texture.class);
		ballTexture = manager.get("ball.png", Texture.class);
		
		//Add some walls
		walls.add(new Wall(world, new Vector2(1f, 10f), new Vector2(-9f, 5f)));
		walls.add(new Wall(world, new Vector2(1f, 10f), new Vector2(9f, 5f)));
		walls.add(new Wall(world, new Vector2(3f,1f), new Vector2(-3f,10f), 2f));
		walls.add(new Wall(world, new Vector2(3f,1f), new Vector2(4f,10f), 2f));
		
		wallTexture = manager.get("wall.png", Texture.class);
		
		fields.add(new Field(world,new Vector2(5f,5f), new Vector2(0f,10f), 2f, true));
		goals.add(new Goal(world,new Vector2(5f,5f), new Vector2(0f,30f)));
		
		//Add death trap
		traps.add(new Trap(world, new Vector2(4f,2f)));
		trapTexture = manager.get("trap.png", Texture.class);
	}

	public void draw(SpriteBatch batch){ 
	   // Draw level
		
	    //Draw balls, with or without shield depending on state
	    Iterator<Ball> a = balls.iterator();
	    while (a.hasNext()){
	    	Ball ball = a.next();
	    	if(ball.hasShield()){
	    		//Render ball with shield
	    		batch.draw(ballShieldTexture,ball.getPosition().x-(ball.getSize()),ball.getPosition().y-(ball.getSize()),ball.getSize()*2,ball.getSize()*2);
	    	}else{
	    		//Else render ball without shield
		    	batch.draw(ballTexture,ball.getPosition().x-(ball.getSize()/2),ball.getPosition().y-(ball.getSize()/2),ball.getSize(),ball.getSize());
	    	}
	    }
	    
	    //Draw walls
	    TextureRegion tst = new TextureRegion(wallTexture);
	    Iterator<Wall> b = walls.iterator();
	    while (b.hasNext()){
	    	Wall wall = b.next();
	    	if(wall.isRotating()){
	    		System.out.println(wall.getBody().getAngle()*MathUtils.radiansToDegrees % 360);
		    	batch.draw(tst, wall.getDrawPos().x, wall.getDrawPos().y, 0, 0, wall.getSize().x, wall.getSize().y, 2, 2, wall.getBody().getAngle()*MathUtils.radiansToDegrees % 360);
	    	}else{
		    	batch.draw(wallTexture, wall.getDrawPos().x, wall.getDrawPos().y, wall.getSize().x*2f, wall.getSize().y*2f, 0, 0, wall.getSize().x*6, wall.getSize().y*6);	    
	    	}
	    }
	    
	    //Draw death traps
	    Iterator<Trap> c = traps.iterator();
	    while (c.hasNext()){
	    	Trap trap = c.next();
	    	batch.draw(trapTexture,trap.getPosition().x-(trap.getSize().x),trap.getPosition().y-(trap.getSize().y),trap.getSize().x*2,trap.getSize().y*2);
	    }
	    
	    
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