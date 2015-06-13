package com.martinfredriksson.merbull.level;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Trap{
	
	BodyDef bodyDef;
	
	private Vector2 pos;
	private Vector2 size;
	
    //final short CAT_PLAYER = 0x0001;
	//private short MASK_PLAYER  = 0x0002;
	
	Body trapBody;
	
	
	public Trap(World world, Vector2 pos, Vector2 size){
		this.pos = (new Vector2(0,0));
		this.size = size;
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody; 
		bodyDef.fixedRotation = true;
		bodyDef.position.set(pos.x,pos.y);
		trapBody = world.createBody(bodyDef);
		trapBody.setUserData("trap");
		
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(size.x, size.y);
		
		
		FixtureDef boxFixtureDef = new FixtureDef();
		boxFixtureDef.shape = boxShape;
		
		trapBody.createFixture(boxFixtureDef);
	}
	
	public Vector2 getPosition(){
		return trapBody.getPosition();
	}
	
	public Vector2 getSize(){
		return size;
	}
	
	//Constructor that uses default player size of 1
	public Trap(World world, Vector2 pos){
		this(world, pos, new Vector2(1f,1f));
	}

}