package com.nathanjent.catorpillar.controller;

import java.util.HashMap;
import java.util.Map;

import com.nathanjent.catorpillar.model.Block;
import com.nathanjent.catorpillar.model.Character;
import com.nathanjent.catorpillar.model.Character.State;
import com.nathanjent.catorpillar.model.World;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class WorldController {
	enum Keys {
		LEFT, RIGHT, JUMP, FIRE
	}
	
	private static final String TAG = WorldController.class.getSimpleName();
	private static final long LONG_JUMP_PRESS = 150l;
	private static final float ACCELERATION = 20f;
	private static final float GRAVITY = -20f;
	private static final float MAX_JUMP_SPEED = 7f;
	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;

	private World world;
	private Character character;
	private long jumpPressedTime;
	private boolean jumpingPressed;
	private boolean grounded = false;
	
//	 This is the rectangle pool used in collision detection
//	 Good to avoid instantiation each frame
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};

	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.FIRE, false);
	};
	
	// Blocks that character can collide with any given frame
	private Array<Block> collidable = new Array<Block>();

	public WorldController(World world) {
		this.world = world;
		this.character = world.getCharacter();
	}

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
//		jumpingPressed = false;
	}
	public void fireReleased() {
		
		keys.get(keys.put(Keys.FIRE, false));
	}

	public void update(float delta) {
		processInput();
		
		// If grounded then reset the state to IDLE 
//		if (grounded && !character.getState().equals(State.JUMPING)) {
//			character.setState(State.IDLE);
//		}
//
//		character.getAcceleration().y = GRAVITY;
//		character.getAcceleration().mul(delta);
//		character.getVelocity().add(character.getAcceleration().x, character.getAcceleration().y);
//		
//		// checking collisions with the surrounding blocks depending on character's velocity
//		checkCollisionWithBlocks(delta);
//
//		// apply damping to halt character nicely 
//		character.getVelocity().x *= DAMP;
//		
//		if (character.getVelocity().x > MAX_VEL) {
//			character.getVelocity().x = MAX_VEL;
//		}
//		if (character.getVelocity().x < -MAX_VEL) {
//			character.getVelocity().x = -MAX_VEL;
//		}

		character.update(delta);
	}

	/** Collision checking **/
	private void checkCollisionWithBlocks(float delta) {
		// scale velocity to frame units 
		character.getVelocity().mul(delta);
		
		// Obtain the rectangle from the pool instead of instantiating it
		Rectangle characterRect = rectPool.obtain();
		// set the rectangle to character's bounding box
		characterRect.set(character.getBounds().x, character.getBounds().y, character.getBounds().width, character.getBounds().height);
		
		// we first check the movement on the horizontal X axis
		int startX, endX;
		int startY = (int) character.getBounds().y;
		int endY = (int) (character.getBounds().y + character.getBounds().height);
		// if character is heading left then we check if he collides with the block on his left
		// we check the block on his right otherwise
		if (character.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(character.getBounds().x + character.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(character.getBounds().x + character.getBounds().width + character.getVelocity().x);
		}

		// get the block(s) character can collide with
		populateCollidableBlocks(startX, startY, endX, endY);

		// simulate character's movement on the X
		characterRect.x += character.getVelocity().x;
		
		// clear collision boxes in world
		world.getBlocks().clear();
		
		// if character collides, make his horizontal velocity 0
		for (Block block : collidable) {
			if (block == null) continue;
			if (characterRect.overlaps(block.getBounds())) {
				character.getVelocity().x = 0;
				world.getBlocks().add(block);
				break;
			}
		}

		// reset the x position of the collision box
		characterRect.x = character.getPosition().x;
		
		// the same thing but on the vertical Y axis
		startX = (int) character.getBounds().x;
		endX = (int) (character.getBounds().x + character.getBounds().width);
		if (character.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(character.getBounds().y + character.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(character.getBounds().y + character.getBounds().height + character.getVelocity().y);
		}
		
		populateCollidableBlocks(startX, startY, endX, endY);
		
		characterRect.y += character.getVelocity().y;
		
		for (Block block : collidable) {
			if (block == null) continue;
			if (characterRect.overlaps(block.getBounds())) {
				if (character.getVelocity().y < 0) {
					grounded = true;
				}
				character.getVelocity().y = 0;
				world.getBlocks().add(block);
				break;
			}
		}
		// reset the collision box's position on Y
		characterRect.y = character.getPosition().y;	
		
		// update character's position
		character.getPosition().add(character.getVelocity());
		character.getBounds().x = character.getPosition().x;
		character.getBounds().y = character.getPosition().y;
		
		// un-scale velocity (not in frame time)
		character.getVelocity().mul(1 / delta);
		
	}

	/** populate the collidable array with the blocks found in the enclosing coordinates **/
	private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
		collidable.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidable.add(world.getLevel().get(x, y));
				}
			}
		}
	}
	
	/** Change Catorpillar's state and parameters based on input controls **/
	private boolean processInput() {
		if (keys.get(Keys.JUMP)) {
			if (!character.getState().equals(State.JUMPING)) {
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();
				character.setState(State.JUMPING);
				character.getVelocity().y = MAX_JUMP_SPEED;
			} else {
				if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
					jumpingPressed = false;
				} else {
					if (jumpingPressed) {
						character.getVelocity().y = MAX_JUMP_SPEED;
					}
				}
			}
		}
		if (keys.get(Keys.LEFT)) {
			// left is pressed
			character.setFacingLeft(true);
			character.setState(State.WALKING);
			character.getVelocity().x = -Character.SPEED;
		}
		if (keys.get(Keys.RIGHT)) {
			// right is pressed
			character.setFacingLeft(false);
			character.setState(State.WALKING);
			character.getVelocity().x = Character.SPEED;
		} 
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
				(!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			character.setState(State.IDLE);
			// acceleration is 0 on the x
			character.getAcceleration().x = 0;
			// horizontal speed is 0
			character.getVelocity().x = 0;
		}
		return grounded;
	}
}
