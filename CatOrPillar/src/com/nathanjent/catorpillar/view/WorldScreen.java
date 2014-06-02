package com.nathanjent.catorpillar.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.nathanjent.catorpillar.CatOrPillar;
import com.nathanjent.catorpillar.model.TestLevel;

public class WorldScreen extends AbstractScreen implements InputProcessor{
	private static final String TAG = WorldScreen.class.getSimpleName();
	private OrthographicCamera camera;
	private TestLevel level;
	private static TextureAtlas atlas;
	
	public WorldScreen(CatOrPillar game) {
		super(game);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		level = new TestLevel(atlas);
		stage.addActor(level);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		stage.act( delta );
        stage.draw();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		Gdx.app.log(TAG, "Disposing screen: " + getName());
		stage.dispose();
		spriteBatch.dispose();
		font.dispose();
		atlas.dispose();
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
	public boolean touchDown(int x, int y, int pointer, int button) {
		level.cat.tryMoveTo(x, y);
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
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
