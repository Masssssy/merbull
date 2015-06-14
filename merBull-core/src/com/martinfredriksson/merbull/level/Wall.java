package com.martinfredriksson.merbull.level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Wall {
	
	private BodyDef bodyDef;
	private Body wallBody;
	
	private Vector2 size;
	private Vector2 drawPos;
	
    final short CAT_WALLS = 0x0002;
    //final short MASK_WALLS = ~CAT_WALLS;
	
	public Wall(World world, Vector2 size, Vector2 pos, float rotation){
		this.size = size;
		drawPos = new Vector2(pos.x-((size.x*2)/2), pos.y-(size.y*2)/2);
		
		bodyDef = new BodyDef();
		if(rotation != 0){
			bodyDef.type = BodyType.DynamicBody; 
		}else{
			bodyDef.type = BodyType.StaticBody;
		}
		bodyDef.fixedRotation = true;
		bodyDef.position.set(pos);
		wallBody = world.createBody(bodyDef);
		wallBody.setAngularVelocity(rotation);
		wallBody.setUserData("bouncer");
		
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(size.x, size.y);
		  
		FixtureDef boxFixtureDef = new FixtureDef();
		boxFixtureDef.shape = boxShape;
		boxFixtureDef.density = 100f;
		//Setup collision filters and rules
		boxFixtureDef.filter.categoryBits = CAT_WALLS;
		wallBody.createFixture(boxFixtureDef);
	}
	
	public Wall(World world, Vector2 size, Vector2 pos){
		this(world, size, pos, 0f);
	}
	
	public Vector2 getSize(){
		return size;
	}
	
	public Vector2 getDrawPos(){
		return drawPos;
	}
	
	public Body getBody(){
		return wallBody;
	}
	
	public boolean isRotating(){
		if(wallBody.getAngularVelocity() > 0){
			return true;
		}
		return false;
	}
	

}
