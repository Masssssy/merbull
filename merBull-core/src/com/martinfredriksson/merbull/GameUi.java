package com.martinfredriksson.merbull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameUi extends Stage {
	
	Table table;
	Skin skin;
	
	public GameUi(){
    skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
		
		//Table layout for menu
	    table = new Table();
	    table.setScale(5f);
	    table.setFillParent(true);
	    this.addActor(table);
	   
	    
	    TextButton button1 = new TextButton("test ui element", skin);
	    button1.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("test ui element pressed");
				return false;
			}
		});
	    table.add(button1);
	}
	

}
