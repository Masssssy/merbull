package com.martinfredriksson.merbull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Scoreboard extends Table {
	Skin skin;
	public Scoreboard() {
		skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
		this.setSkin(skin);
		add("Name    ");
		add("Bounces    ");
		add("Time    ");
		add("Score    ");
	}
}
