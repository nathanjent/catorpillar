package com.nathanjent.catorpillar.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import com.nathanjent.catorpillar.model.Block;
import com.nathanjent.catorpillar.model.Character;
import com.nathanjent.catorpillar.model.Character.State;
import com.nathanjent.catorpillar.model.World;

public class WorldRenderer {
	private static final String TAG = WorldRenderer.class.getSimpleName();
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	private static final float RUNNING_FRAME_DURATION = 0.06f;
	
	private World world;
	private OrthographicCamera cam;
	
		
	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	private TextureRegion charIdleLeft;
	private TextureRegion charIdleRight;
	private TextureRegion blockTexture;
	private TextureRegion charFrame;
	private TextureRegion charJumpLeft;
	private TextureRegion charFallLeft;
	private TextureRegion charJumpRight;
	private TextureRegion charFallRight;
	
	private Animation walkLeftAnim;
	private Animation walkRightAnim;

	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;
	private float ppuY;
	
	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
	}
	
	public boolean isDebug() {
		return debug;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
	}
	
	public void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/textures.pack"));
		charIdleRight = atlas.findRegion("catOrPillar001");
		charIdleLeft = new TextureRegion(charIdleRight);
		charIdleLeft.flip(true, false);
		blockTexture = atlas.findRegion("worldBlock");
		TextureRegion[] walkRightFrames = new TextureRegion[5];
		for (int i = 0; i < walkRightFrames.length; i++){
			walkRightFrames[i] = atlas.findRegion("catOrPillar00" + (i + 2));
		}
		walkRightAnim = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0; i < walkLeftFrames.length; i++){
			walkLeftFrames[i] = new TextureRegion(walkRightFrames[i]);
			walkLeftFrames[i].flip(true, false);
		}
		walkLeftAnim = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);
		charJumpLeft = atlas.findRegion("jump");
		charJumpRight = new TextureRegion(charJumpLeft);
		charJumpRight.flip(true, false);
		charFallLeft = atlas.findRegion("fall");
		charFallRight = new TextureRegion(charFallLeft);
		charFallRight.flip(true, false);
	}
	
	public void render() {
		spriteBatch.begin();
		drawBlocks();
		drawCharacter();
		spriteBatch.end();
		if (debug)
			drawDebug();
	}
	
	private void drawBlocks() {
		for (Block block : world.getDrawableBlocks((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
			spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
		}
	}
	
	private void drawCharacter() {
		Character catOrPillar = world.getCharacter();
		charFrame = catOrPillar.isFacingLeft() ? charIdleLeft : charIdleRight;
		if (catOrPillar.getState().equals(State.WALKING)) {
			charFrame = catOrPillar.isFacingLeft() ? walkLeftAnim.getKeyFrame(catOrPillar.getStateTime(), true) : walkRightAnim.getKeyFrame(catOrPillar.getStateTime(), true);
		}
		else if (catOrPillar.getState().equals(State.JUMPING)) {
			if (catOrPillar.getVelocity().y > 0) {
				charFrame = catOrPillar.isFacingLeft() ? charJumpLeft : charJumpRight;
			} 
			else {
				charFrame = catOrPillar.isFacingLeft() ? charFallLeft : charFallRight;
			}
		}
		spriteBatch.draw(charFrame, catOrPillar.getPosition().x * ppuX, catOrPillar.getPosition().y * ppuY, Character.SIZE * ppuX, Character.SIZE * ppuY);
	}
	
	private void drawDebug() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		for (Block block : world.getBlocks()) {
			Rectangle rect = block.getBounds();
			float x1 = block.getPosition().x + rect.x;
			float y1 = block.getPosition().y + rect.y;
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}		
		Character catOrPillar = world.getCharacter();
		Rectangle rect = catOrPillar.getBounds();
		float x1 = catOrPillar.getPosition().x + rect.x;
		float y1 = catOrPillar.getPosition().y + rect.y;
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		debugRenderer.end();
	}
}
