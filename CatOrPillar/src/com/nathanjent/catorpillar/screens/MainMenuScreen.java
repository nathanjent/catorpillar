package com.nathanjent.catorpillar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nathanjent.catorpillar.CatOrPillar;

public class MainMenuScreen extends AbstractScreen implements InputProcessor {
	private static final String TAG = MainMenuScreen.class.getSimpleName();
	private OrthographicCamera camera;
	
	SpriteBatch spriteBatch = new SpriteBatch();
	BitmapFont font= new BitmapFont();
	
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
			game.setScreen(new GameScreen());
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log(CatOrPillar.TAG, "Resizing game to: " + width + " x "
				+ height);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		Gdx.app.log(CatOrPillar.TAG, "Pausing game");
	}

	@Override
	public void resume() {
		Gdx.app.log(CatOrPillar.TAG, "Resuming game");
	}

	@Override
	public void dispose() {
		Gdx.app.log(CatOrPillar.TAG, "Disposing game");
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
