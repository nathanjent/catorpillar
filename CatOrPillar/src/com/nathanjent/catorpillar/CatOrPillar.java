package com.nathanjent.catorpillar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.nathanjent.catorpillar.view.WorldScreen;
import com.nathanjent.catorpillar.view.MainMenuScreen;
import com.nathanjent.catorpillar.view.SplashScreen;

public class CatOrPillar extends Game {
	public static final String TAG = CatOrPillar.class.getSimpleName();
	public static FPSLogger fpsLogger;

	public Screen getMainMenuScreen(){
		return new MainMenuScreen(this);
	}

	public Screen getSplashScreen(){
		return new SplashScreen(this);
	}

	public Screen getWorldScreen(){
		return new WorldScreen(this);
	}
	
	@Override
	public void create() {
		Gdx.app.log(CatOrPillar.TAG, "Creating game");
        fpsLogger = new FPSLogger();
        setScreen(new SplashScreen(this));
	}

	public void render() {
		super.render();
		// log frames per second to the console.
		fpsLogger.log();
	}
}
