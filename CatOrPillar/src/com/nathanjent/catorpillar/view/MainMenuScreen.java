package com.nathanjent.catorpillar.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nathanjent.catorpillar.CatOrPillar;

public class MainMenuScreen extends AbstractScreen {
	private static final String TAG = MainMenuScreen.class.getSimpleName();
	private OrthographicCamera camera;
	
	public MainMenuScreen(CatOrPillar game) {
		super(game);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	// Screen
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		spriteBatch.begin();
		font.draw(spriteBatch, "Cat or Pillar", 100, 150);
		font.draw(spriteBatch, "Tap Anywhere!", 100, 100);
		spriteBatch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(game.getWorldScreen());
			dispose();
		}
	}
}
