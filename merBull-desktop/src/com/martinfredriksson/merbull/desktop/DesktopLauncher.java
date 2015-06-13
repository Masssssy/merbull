package com.martinfredriksson.merbull.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.martinfredriksson.merbull.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Project MerBull";
		config.height = 640;
		config.width = 360;
		//Use HDPI on OSX
		config.useHDPI = true;
		new LwjglApplication(new MyGame(), config);
	}
}
