package com.nathanjent.catorpillar.screens;

import com.nathanjent.catorpillar.controller.WorldController;
import com.nathanjent.catorpillar.model.World;
import com.nathanjent.catorpillar.view.WorldRenderer;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen, InputProcessor {
	private static final String TAG = GameScreen.class.getSimpleName();
	private World world;
	private WorldRenderer renderer;
	private WorldController controller;
	
	private int width, height;
	
	// Screen
	
	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, true);
		controller = new WorldController(world);
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
		Gdx.app.log(GameScreen.TAG, "Resizing game to: " + width + " x " + height );
	}

	
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		
	}

	@Override
	public void pause() {
		Gdx.app.log( GameScreen.TAG, "Pausing game" );
	}

	@Override
	public void resume() {
		Gdx.app.log( GameScreen.TAG, "Resuming game" );
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		Gdx.app.log( GameScreen.TAG, "Disposing game" );
	}

	// InputProcessor
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftPressed();
		if (keycode == Keys.RIGHT)
			controller.rightPressed();
		if (keycode == Keys.Z)
			controller.jumpPressed();
		if (keycode == Keys.X)
			controller.firePressed();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftReleased();
		if (keycode == Keys.RIGHT)
			controller.rightReleased();
		if (keycode == Keys.Z)
			controller.jumpReleased();
		if (keycode == Keys.X)
			controller.fireReleased();
		if (keycode == Keys.D)
			renderer.setDebug(!renderer.isDebug());
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		if (screenX < width / 2 && screenY > height / 2) {
			controller.leftPressed();
		}
		if (screenX > width / 2 && screenY > height / 2) {
			controller.rightPressed();
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		if (screenX < width / 2 && screenY > height / 2) {
			controller.leftReleased();
		}
		if (screenX > width / 2 && screenY > height / 2) {
			controller.rightReleased();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
