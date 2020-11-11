package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screen.EndScreen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.LoadScreen;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.screen.StartScreen;
import com.mygdx.game.sprite.SpriteAnimation;
import com.mygdx.game.sprite.SpriteAnimationData;
import com.mygdx.game.timer.GameTimerManager;
import com.mygdx.game.utility.FrameRate;
import com.mygdx.game.utility.GameConstants;
import com.mygdx.game.utility.json.AssetsJsonParser;
import com.mygdx.game.utility.json.SpriteAnimationJsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CastleDefense extends ApplicationAdapter {

	private GameScreen currentScreen;

	private ShapeRenderer shapeRenderer;

	private FrameRate frameRate;

	private GameTimerManager gameTimerManager;

	private EntityManager entityManager;

	private boolean muted;

	private int coins;

	private int score;

	private Map<String, SpriteAnimationData> spriteAnimationMap = new HashMap<String, SpriteAnimationData>();

	private Assets assets;

	@Override
	public void create() {
		assets = new Assets();
		frameRate = new FrameRate();
		gameTimerManager = new GameTimerManager();
		shapeRenderer = new ShapeRenderer();
		entityManager = new EntityManager();

		//load json
		new AssetsJsonParser(assets).execute();

		assets.getAssetManager().finishLoading();

		new SpriteAnimationJsonParser(this).execute();

		//load mute option
		Preferences preferences = Gdx.app.getPreferences("muted");

		boolean muted = preferences.getBoolean("muted", false);
		this.muted = muted;

		this.currentScreen = new EndScreen(this);

		currentScreen.start();
	}

	public void switchScreen(GameScreen screen) {
		if(currentScreen != null) {
			currentScreen.dispose();
		}
		this.currentScreen = screen;
		currentScreen.start();
	}

	@Override
	public void render() {
		//Logic
		update();

		//Drawing
		draw();
	}

	private void update() {

		gameTimerManager.update();
		if(currentScreen != null) {
			currentScreen.update();
		}

		if(GameConstants.SHOW_FRAMES_PER_SECOND) {
			frameRate.update();
		}
	}

	private void draw() {

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(currentScreen != null) {
			currentScreen.render(shapeRenderer);
		}
	}

	@Override
	public void resize(int width, int height) {
		if(currentScreen == null) {
			return;
		}
		currentScreen.getStage().getViewport().update(width, height, true);
		currentScreen.getStage().getViewport().apply(true);

	}

	public void dispose() {
		shapeRenderer.dispose();
		if(currentScreen != null) {
			currentScreen.dispose();
		}
		assets.getAssetManager().dispose();
	}

	public SpriteAnimationData getSpriteAnimations(String name) {
		if(spriteAnimationMap.containsKey(name)) {
			return spriteAnimationMap.get(name);
		}

		System.out.println("ERROR! No sprite animations for that name.");
		return null;
	}

	public void addSpriteAnimations(String name, SpriteAnimationData animation) {
		spriteAnimationMap.put(name, animation);
	}

	public void playSound(Sound sound) {
		if(!muted) {
			sound.play();
		}
	}

	public boolean isMuted() {
		return muted;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Assets getAssets() {
		return assets;
	}

	public GameTimerManager getGameTimerManager() {
		return gameTimerManager;
	}

	public EntityManager getEntityManager() { return entityManager; }

	public GameScreen getCurrentScreen() {
		return currentScreen;
	}
}
