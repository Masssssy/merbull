package com.martinfredriksson.merbull.level;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.martinfredriksson.merbull.Level;

public class CollisionDetector implements ContactListener {
	
	Level level;
	
	public CollisionDetector(Level level){
	this.level = level;	
	}

	@Override
	public void beginContact(Contact contact) {
		//Get colliding fixtures
		System.out.println("Contact detected 123");
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		System.out.println(fixA.getBody().getUserData());
		System.out.println(fixB.getBody().getUserData());
		
		if(fixA.getBody().getUserData() == "playerBall" && fixB.getBody().getUserData() == "accfield" || 
				   fixA.getBody().getUserData() == "accfield" && fixB.getBody().getUserData() == "playerBall"){
					//Player and fricfield collided
					level.playSwoosh();
					System.out.println("accfield/fricfield and player collided");
					level.getBall().getBody().setLinearDamping(-5f);
		}
		
		if(fixA.getBody().getUserData() == "playerBall" && fixB.getBody().getUserData() == "fricfield" || 
				   fixA.getBody().getUserData() == "fricfield" && fixB.getBody().getUserData() == "playerBall"){
					//Player and fricfield collided
					level.playScratch();
					System.out.println("accfield/fricfield and player collided");
					level.getBall().getBody().setLinearDamping(10f);
		}
		
		if(fixA.getBody().getUserData() == "playerBall" && fixB.getBody().getUserData() == "trap" || 
				   fixA.getBody().getUserData() == "trap" && fixB.getBody().getUserData() == "playerBall"){
					//Player and fricfield collided
					System.out.println("trap and player collided, die pls");
					level.die();
		}
		
		
		if(fixA.getBody().getUserData() == "playerBall" && fixB.getBody().getUserData() == "bouncer" || 
				   fixA.getBody().getUserData() == "bouncer" && fixB.getBody().getUserData() == "playerBall"){
					//Player and fricfield collided
					System.out.println("reduce bounce count");
					level.playDonk();
					level.reduceBounce();
		}
		
		if(fixA.getBody().getUserData() == "playerBall" && fixB.getBody().getUserData() == "goal" || 
				   fixA.getBody().getUserData() == "goal" && fixB.getBody().getUserData() == "playerBall"){
					//Player and fricfield collided
					System.out.println("Yay, you win!");
					level.win();
		}
		//Other collision checks
		//if(....){
		//Something collides with something
		//}
		
	}

	@Override
	public void endContact(Contact contact) {
		System.out.println("Contact ended");
		
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if(fixA.getBody().getUserData() == "playerBall" && fixB.getBody().getUserData() == "fricfield" || 
				   fixA.getBody().getUserData() == "fricfield" && fixB.getBody().getUserData() == "playerBall"){
					//Player and fricfield stops colliding
					System.out.println("fricfield and player ended contact");
					level.getBall().getBody().setLinearDamping(0f);
					level.getBall().setVelocity(level.getBall().getBody().getLinearVelocity());
		}
		
		if(fixA.getBody().getUserData() == "playerBall" && fixB.getBody().getUserData() == "accfield" || 
				   fixB.getBody().getUserData() == "accfield" && fixA.getBody().getUserData() == "playerBall"){
					//Player and fricfield collided
					System.out.println("accfield and player ended collision");
					level.getBall().getBody().setLinearDamping(0f);
					level.getBall().setVelocity(level.getBall().getBody().getLinearVelocity());
		}
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
