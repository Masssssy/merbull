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
	
	public Wall(World world, Vector2 size, Vector2 pos){
		this.size = size;
		drawPos = new Vector2(pos.x-((size.x*2)/2), pos.y-(size.y*2)/2);
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody; 
		bodyDef.position.set(pos);
		wallBody = world.createBody(bodyDef);
		
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(size.x, size.y);
		  
		FixtureDef boxFixtureDef = new FixtureDef();
		boxFixtureDef.shape = boxShape;
		boxFixtureDef.density = 1;
		//Setup collision filters and rules
		boxFixtureDef.filter.categoryBits = CAT_WALLS;
		//boxFixtureDef.filter.maskBits = MASK_WALLS;
		//boxFixtureDef.filter.groupIndex = -1;
		wallBody.createFixture(boxFixtureDef);
	}
	
	public Vector2 getSize(){
		return size;
	}
	
	public Vector2 getDrawPos(){
		return drawPos;
	}

}
