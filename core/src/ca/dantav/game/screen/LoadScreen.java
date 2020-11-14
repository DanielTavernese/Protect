package ca.dantav.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ca.dantav.game.Assets;
import ca.dantav.game.CastleDefense;
import ca.dantav.game.sprite.SpriteActor;

public class LoadScreen extends GameScreen {

    /*
    The logo float
     */
    private float logoFloat = 0f;

    /*
    The background sprite
     */
    private Sprite logo;


    /**
     * Default constructor
     * <p>
     * Calls start function
     *
     * @param castleDefense
     */
    public LoadScreen(CastleDefense castleDefense) {
        super(castleDefense);
        logo = new Sprite(new Texture(Gdx.files.internal("images/tav_tech_white.png")));
    }

    @Override
    public void start() {
        SpriteActor actor = new SpriteActor(logo);
        actor.setPosition(getStage().getWidth()/2 - (logo.getWidth()/2), (getStage().getHeight()/2) - (logo.getHeight()/2));
        getStage().addActor(actor);
    }

    @Override
    public void dispose() {
        logo.getTexture().dispose();
    }

    @Override
    public void update() {
        if(logoFloat < 1f) {
            float increment = logoFloat + 0.01f;
            logoFloat = (increment > 1f) ? 1f : increment;
            logo.setAlpha(logoFloat);
            return;
        }

        if(getCastleDefense().getAssets().getAssetManager().isFinished()) {
            getCastleDefense().switchScreen(new StartScreen(getCastleDefense()));
            return;
        }

    }


    @Override
    public Sprite getBackground() {
        return null;
    }

    @Override
    public Color getBackgroundColor() {
        return Color.BLACK;
    }

    @Override
    public void back() {

    }
}
