package com.martinfredriksson.merbull;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ball{
	
	BodyDef bodyDef;
	
	private Texture player;
	private Vector2 pos;
	private float size;
	private boolean shield = true;
	
    //final short CAT_PLAYER = 0x0001;
	//private short MASK_PLAYER  = 0x0002;
	
	Body ballBody;
	CircleShape circleShape;
	
	
	
	public Ball(World world, Vector2 pos, float size){
		this.pos = (new Vector2(0,0));
		this.size = size;
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody; 
		bodyDef.fixedRotation = true;
		bodyDef.position.set(pos.x,pos.y);
		ballBody = world.createBody(bodyDef);
		ballBody.setUserData("playerBall");
		
		circleShape = new CircleShape();
		circleShape.setRadius(size);
		
		//this.setVelocity(new Vector2(3f,8f));
		
		FixtureDef boxFixtureDef = new FixtureDef();
		boxFixtureDef.shape = circleShape;
	
		boxFixtureDef.density = 1;
		boxFixtureDef.restitution = 0.95f;
		
		//Setup collision filters and rules
		//boxFixtureDef.filter.categoryBits = CAT_PLAYER;
		//boxFixtureDef.filter.maskBits = MASK_PLAYER;
		//boxFixtureDef.filter.groupIndex = -1;
		ballBody.createFixture(boxFixtureDef);
	}
	
	//Constructor that uses default player size of 1
	public Ball(World world, Vector2 pos){
		this(world, pos, 1f);
	}
	
	public void update(float delta) {
		
	}
	

	public void setVelocity(Vector2 velocity){
		this.ballBody.setLinearVelocity(velocity);
	}
	
	public Vector2 getPosition(){
		return ballBody.getPosition();
	}
	
	public float getSize(){
		return size;
	}
	
	public void kill(){
		//alive = false;
	}
	
	public Body getBody(){
		return ballBody;
	}
	
	public CircleShape getSmallShape(){
		circleShape.setRadius(0.5f);
		return circleShape;
	}

	public void setShield(boolean b) {
		this.shield = b;
	}
	
	public boolean hasShield(){
		return shield;
	}


}
