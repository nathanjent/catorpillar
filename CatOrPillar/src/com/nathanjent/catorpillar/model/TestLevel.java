package com.nathanjent.catorpillar.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;

public class TestLevel extends Table{
	private InfiniteScrollBg background;
	private static TextureRegion backGround;
	
	public Cat cat;
	
	public TestLevel(TextureAtlas atlas) {
		atlas = new TextureAtlas(Gdx.files.internal("textures/textures.pack"));
		cat = new Cat(atlas);
		this.addActor(cat);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.setColor(Color.WHITE);
		super.draw(batch, parentAlpha);
	}
}
