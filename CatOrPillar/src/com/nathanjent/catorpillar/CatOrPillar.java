package com.nathanjent.catorpillar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.nathanjent.catorpillar.screens.MainMenuScreen;

public class CatOrPillar extends Game {
	public static final String TAG = CatOrPillar.class.getSimpleName();
	public static FPSLogger fpsLogger;
	
	@Override
	public void create() {
		Gdx.app.log(CatOrPillar.TAG, "Creating game");
        fpsLogger = new FPSLogger();
        setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render();
		// log frames per second to the console.
		fpsLogger.log();
	}
}
