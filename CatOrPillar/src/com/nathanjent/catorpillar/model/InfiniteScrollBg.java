package com.nathanjent.catorpillar.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Forever;
import com.badlogic.gdx.scenes.scene2d.actions.MoveTo;
import com.badlogic.gdx.scenes.scene2d.actions.Sequence;

public class InfiniteScrollBg extends Actor{
	
	public InfiniteScrollBg(float width, float height) {
		this.width = width;
		this.height = height;
		this.x = width;
		this.y = 0;
		Forever action = Forever.$(Sequence.$(MoveTo.$(0, 0, 1f), MoveTo.$(width, 0, 0)));
		this.action(action);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(new TextureRegion(), x - width, y, width * 2, height);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

}
