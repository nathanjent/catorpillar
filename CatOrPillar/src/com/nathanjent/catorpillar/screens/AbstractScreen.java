package com.nathanjent.catorpillar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nathanjent.catorpillar.CatOrPillar;

public class AbstractScreen implements Screen{
	protected final CatOrPillar game;
	protected final SpriteBatch spriteBatch;
	protected final BitmapFont font;
	protected final Stage stage;

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
        // resize the stage
        stage.setViewport( width, height, true );
        Gdx.app.log( CatOrPillar.TAG, "Resizing screen: " + getName() + " to: " + width + " x " + height );

	}

	@Override
	public void show() {
		Gdx.app.log(CatOrPillar.TAG, "Showing screen: " + getName());
	}

	@Override
	public void hide() {
		Gdx.app.log(CatOrPillar.TAG, "Hiding screen: " + getName());
	}

	@Override
	public void pause() {
		Gdx.app.log(CatOrPillar.TAG, "Pausing screen: " + getName());
	}

	@Override
	public void resume() {
		Gdx.app.log(CatOrPillar.TAG, "Resuming screen: " + getName());
	}

	@Override
	public void dispose() {
		Gdx.app.log(CatOrPillar.TAG, "Disposing screen: " + getName());
		stage.dispose();
		spriteBatch.dispose();
		font.dispose();
	}
}
