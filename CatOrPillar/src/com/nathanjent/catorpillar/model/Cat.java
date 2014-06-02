package com.nathanjent.catorpillar.model;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.nathanjent.catorpillar.CatOrPillar;

/**
 * Builds the catorpillar connecting all of the body parts.
 * @author natorx
 *
 */
public class Cat extends Group{
	private static final String TAG = CatOrPillar.class.getSimpleName();
	private class CatPart extends Actor{
		TextureRegion region;
		CatPart prev;
		
		/**
		 * Cat parts.
		 * @param x
		 * @param y
		 * @param region
		 */
		public CatPart(){
			this.x = 0;
			this.y = 0;
			this.region = null;
			this.prev = null;
		}

		@Override
		public void draw(SpriteBatch batch, float parentAlpha) {
			batch.draw(region, this.x, this.y);
			
		}

		@Override
		public Actor hit(float x, float y) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void act(float delta) {
			super.act(delta);
		}
	}
	
	private static int length = 4;
	CatPart head;
	CatPart tail;
	TextureRegion catHeadRegion;
	TextureRegion catTorsoRegion;
	TextureRegion catMidRegion;
	TextureRegion catHindRegion;
	TextureRegion catTailRegion;
	Vector2 position;
	
	public Cat(TextureAtlas atlas){
		position = new Vector2();
		catHeadRegion = atlas.findRegion("catParts-head");
		catTorsoRegion = atlas.findRegion("catParts-torso");
		catMidRegion = atlas.findRegion("catParts-mid");
		catHindRegion = atlas.findRegion("catParts-hind");
		catTailRegion = atlas.findRegion("catParts-tail");
		buildCat();
	}
	
	public void increase(int amount) {
		length += amount;
		buildCat();
	}
	
	public void decrease(int amount) {
		length -= amount;
		buildCat();
	}
	
	private void buildCat() {
		int count = 0;
		// Add parts based on length
		for (int i = 0; i < length; i++)
		{
			this.addActor(new CatPart());
		}
		// Iterate through children setting position and textures
		CatPart prev = null;
		for (Actor child : this.children)
		{
			CatPart kitten = (CatPart) child;
			kitten.prev = prev;
			
			if (count == 0 || prev == null) {
				kitten.x = position.x;
				kitten.y = position.y;
				kitten.region = catTailRegion;
			} else if (count == length - 1) {
				kitten.x = prev.x + 16f;
				kitten.y = prev.y;
				kitten.region = catHindRegion;
			} else if (count < (length-2)/5) {
				kitten.x = prev.x + 16f;
				kitten.y = prev.y;
				kitten.region = catMidRegion;				
			} else if (count > (length-2)*(4/5)) {
				kitten.x = prev.x + 16f;
				kitten.y = prev.y;
				kitten.region = catTorsoRegion;				
			} else {
				kitten.x = prev.x + 16f;
				kitten.y = prev.y;
				kitten.region = catHeadRegion;				
			}
			
			prev = kitten;
			count++;
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	public void tryMoveTo(int x, int y) {
		position.set(x, y);	
	}
}
