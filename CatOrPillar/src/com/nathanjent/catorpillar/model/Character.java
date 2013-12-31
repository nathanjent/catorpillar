package com.nathanjent.catorpillar.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Character {
	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}
	
	private static final String TAG = Character.class.getSimpleName();
	public static final float SPEED = 4f;
	public static final float JUMP_VELOCITY = 4f;
	public static final float SIZE = 0.5f;
	
	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	boolean facingLeft = true;
	float stateTime = 0;
	
	public Character(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public State getState() {
		return state;
	}
	
	public void setState(State newState) {
		this.state = newState;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void update(float delta) {
		stateTime += delta;
		position.add(velocity.tmp().mul(delta)); 
	}
}
