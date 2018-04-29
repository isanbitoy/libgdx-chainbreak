package com.chainbreak.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.chainbreak.game.ChainBreak;
import com.chainbreak.game.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ChainBreak(), config);
		config.width = Constants.V_WIDTH;
		config.height = Constants.V_HEIGHT;
	}
}
