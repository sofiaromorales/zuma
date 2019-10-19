package com.sofiar.zuma.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.sofiar.zuma.Zuma;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Zuma.WIDTH;
		config.height = Zuma.HEIGHT;
		config.title = Zuma.TITLE;
		new LwjglApplication(new Zuma(), config);
	}
}
