package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Assets;
import com.mygdx.game.CastleDefense;

public class LoadScreen extends GameScreen {

    /*
    The logo float
     */
    private float logoFloat = 0f;

    /*
    The background sprite
     */
    private Sprite background;

    /**
     * Default constructor
     * <p>
     * Calls start function
     *
     * @param castleDefense
     */
    public LoadScreen(CastleDefense castleDefense) {
        super(castleDefense);
        background = new Sprite(new Texture(Gdx.files.internal("images/tavtech.png")));
    }

    @Override
    public void start() {

    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
    }

    @Override
    public void update() {
        if(logoFloat < 1f) {
            float increment = logoFloat + 0.01f;
            logoFloat = (increment > 1f) ? 1f : increment;
            background.setAlpha(logoFloat);
            return;
        }

        if(getCastleDefense().getAssets().getAssetManager().isFinished()) {
            getCastleDefense().switchScreen(new StartScreen(getCastleDefense()));
            return;
        }

    }


    @Override
    public Sprite getBackground() {
        return background;
    }

    @Override
    public void back() {

    }
}
