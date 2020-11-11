package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.CastleDefense;
import com.mygdx.game.sprite.SpriteActor;
import com.mygdx.game.sprite.SpriteActorButton;
import com.mygdx.game.utility.GameConstants;
import com.mygdx.game.utility.LabelGroup;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/*
 * Holds a game screen
 */
public abstract class GameScreen {

    /*
    The stage on which the actors will be placed
     */
    private Stage stage;

    /*
    The game
    */
    private CastleDefense castleDefense;


    /**
     * Default constructor
     *
     * Calls start function
     */
    public GameScreen(CastleDefense castleDefense) {
        this.castleDefense = castleDefense;
        this.stage = new Stage(new ExtendViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
    }

    public abstract void start();

    /*
     * Dispose method
     */
    public void dispose() {
        stage.dispose();
    }

    /*
      Updates screen
     */
    public void update() {
        stage.getCamera().update();
    }

    public void addSpriteToScreen(Sprite sprite, float x, float y) {
        SpriteActor spriteActor = new SpriteActor(sprite);
        spriteActor.setPosition(x, y);
        getStage().addActor(spriteActor);
    }

    public SpriteActorButton addSpriteButtonToScreen(Sprite sprite, float x, float y) {
        SpriteActorButton spriteActor = new SpriteActorButton(getCastleDefense(), sprite);
        spriteActor.setPosition(x, y);
        getStage().addActor(spriteActor);
        return spriteActor;
    }

    public Group addFontToScreen(BitmapFont font, String text, Color color, float x, float y, float scale) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label label = new Label(text, labelStyle);
        label.setColor(color);
        Group textGroup = new LabelGroup(label);
        textGroup.setPosition(x, y);
        textGroup.setScale(scale);
        getStage().addActor(textGroup);
        return textGroup;
    }

    /*
     * Rendering stage onto the screen
     */
    public void render(ShapeRenderer shapeRenderer) {
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);

        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        getBackground().setSize(getStage().getCamera().viewportWidth, getStage().getCamera().viewportHeight);
        getBackground().draw(getStage().getBatch());
        getStage().getBatch().end();

        stage.draw();
    }

    public abstract Sprite getBackground();

    public abstract void back();

    public CastleDefense getCastleDefense() {
        return castleDefense;
    }

    public Stage getStage() {
        return stage;
    }


}
