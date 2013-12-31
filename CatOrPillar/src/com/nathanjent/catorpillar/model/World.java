package com.nathanjent.catorpillar.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
	private static final String TAG = World.class.getSimpleName();
	Array<Block> blocks = new Array<Block>();
	Character character;
	Level level;
	
	public Array<Block> getBlocks() {
		return blocks;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public World() {
		createDemoWorld();
	}

	/** Return only the blocks that need to be drawn **/
	public Array<Block> getDrawableBlocks(int width, int height) {
		int x = (int)character.getPosition().x - width;
		int y = (int)character.getPosition().y - height;
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;
		if (x2 > level.getWidth()) {
			x2 = level.getWidth() - 1;
		}
		if (y2 > level.getHeight()) {
			y2 = level.getHeight() - 1;
		}
		
		Array<Block> blocks = new Array<Block>();
		Block block;
		for (int col = x; col <= x2; col++) {
			for (int row = y; row <= y2; row++) {
				block = level.getBlocks()[col][row];
				if (block != null) {
					blocks.add(block);
				}
			}
		}
		return blocks;
	}
	
	private void createDemoWorld() {
		character = new Character(new Vector2(7, 2));
		level = new Level();
	}
}
