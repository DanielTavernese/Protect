package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.CastleDefense;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class InfoScreen extends GameScreen {

    private Sprite background;

    /**
     * Default constructor
     * <p>
     * Calls start function
     *
     * @param castleDefense
     */
    public InfoScreen(CastleDefense castleDefense) {
        super(castleDefense);
        this.background = new Sprite(getCastleDefense().getAssets().get("info_background", Texture.class));
    }

    @Override
    public void start() {

        getStage().addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                getCastleDefense().switchScreen(new StartScreen(getCastleDefense()));
            }
        });
    }

    @Override
    public Sprite getBackground() {
        return background;
    }

    @Override
    public void back() {
        getCastleDefense().switchScreen(new StartScreen(getCastleDefense()));
    }
}
