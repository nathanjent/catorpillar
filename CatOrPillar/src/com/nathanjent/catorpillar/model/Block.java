package com.nathanjent.catorpillar.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {
	private static final String TAG = Block.class.getSimpleName();
	public static final float SIZE = 1f;
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public Block(Vector2 pos) {
		this.position = pos;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}

	public Rectangle getBounds() {
		return bounds;		
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
}
