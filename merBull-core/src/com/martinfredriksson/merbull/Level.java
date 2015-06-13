package com.martinfredriksson.merbull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.martinfredriksson.merbull.level.Wall;

/** Level is the object passed to GameScreen so that it knows what the level should look like **/
public class Level {
	
	List<Ball> balls = new ArrayList<Ball>();
	List<Wall> walls = new ArrayList<Wall>();
	
	Texture ballTexture;
	Texture wallTexture;

	AssetManager manager;
	
	public Level(){
		//IF TIME:
		//Create the level from text file or equivalent
		//playerpos, wallpositions, weapon pos etc
	}

	//Actually initialize the level 
	public void init(World world) {
		//Initiate the level world
		balls.add(new Ball(world, new Vector2(0,0)));
		ballTexture = manager.get("ball.png", Texture.class);
		
		//Add some walls
		walls.add(new Wall(world, new Vector2(1f, 10f), new Vector2(-9f, 5f)));
		walls.add(new Wall(world, new Vector2(1f, 10f), new Vector2(9f, 5f)));
		wallTexture = manager.get("wall.png", Texture.class);
		
	}

	public void draw(SpriteBatch batch){ 
	   // Draw level
		
	    //Draw balls
	    Iterator<Ball> a = balls.iterator();
	    while (a.hasNext()){
	    	Ball ball = a.next();
	    	batch.draw(ballTexture,ball.getPosition().x-(ball.getSize()),ball.getPosition().y-(ball.getSize()),ball.getSize()*2,ball.getSize()*2);
	    }
	    
	    //Draw walls
	    Iterator<Wall> b = walls.iterator();
	    while (b.hasNext()){
	    	Wall wall = b.next();
	    	batch.draw(wallTexture, wall.getDrawPos().x, wall.getDrawPos().y, wall.getSize().x*2f, wall.getSize().y*2f, 0, 0, wall.getSize().x*6, wall.getSize().y*6);
	    }
	}

	public void setAssetManager(AssetManager manager) {
		this.manager = manager;
	}
	
}
