package com.nathanjent.catorpillar.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nathanjent.catorpillar.CatOrPillar;
import com.nathanjent.catorpillar.controller.WorldController;
import com.nathanjent.catorpillar.model.World;

public class AbstractScreen implements Screen{
	private static final String TAG = CatOrPillar.class.getSimpleName();
	protected final CatOrPillar game;
	protected final SpriteBatch spriteBatch;
	protected final BitmapFont font;
	protected final Stage stage;
	private int width, height;

	public AbstractScreen(CatOrPillar game){
		this.game = game;
		this.spriteBatch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		this.font = new BitmapFont();
		this.stage = new Stage(0, 0, true);
	}
	
	protected String getName(){
		return getClass().getSimpleName();
	}
	
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
     
        // update and draw the stage actors
        stage.act( delta );
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
        Gdx.app.log( TAG, "Resizing screen: " + getName() + " to: " + width + " x " + height );
		this.width = width;
		this.height = height;
        stage.setViewport( width, height, true );
	}

	@Override
	public void show() {
		Gdx.app.log(TAG, "Showing screen: " + getName());
	}

	@Override
	public void hide() {
		Gdx.app.log(TAG, "Hiding screen: " + getName());
	}

	@Override
	public void pause() {
		Gdx.app.log(TAG, "Pausing screen: " + getName());
	}

	@Override
	public void resume() {
		Gdx.app.log(TAG, "Resuming screen: " + getName());
	}

	@Override
	public void dispose() {
		Gdx.app.log(TAG, "Disposing screen: " + getName());
		stage.dispose();
		spriteBatch.dispose();
		font.dispose();
	}
}
