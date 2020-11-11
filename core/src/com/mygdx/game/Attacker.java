package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.sprite.SpriteActor;

import java.util.List;

public abstract class Attacker extends Entity {

    private Face face;

    public enum Face {
        WEST(true),
        EAST(false);

        boolean face;

        Face(boolean face) {
            this.face = face;
        }

        public boolean getFace() {
            return face;
        }
    }

    public Attacker(PlayScreen playScreen, SpriteActor spriteActor, int health) {
        super(playScreen, spriteActor, health);
        this.face = Face.WEST;
    }

    public abstract void downTouch();

    public abstract void upTouch();

    public abstract int getCoinAmount();

    public abstract void onHitEntity();

    public abstract void startDeath();

    public void die() {
        getPlayScreen().getCastleDefense().getEntityManager().unregister(this);
        getPlayScreen().getCastleDefense().setScore(getPlayScreen().getCastleDefense().getScore() + 1);
        getPlayScreen().getCastleDefense().setCoins(getPlayScreen().getCastleDefense().getCoins() + getCoinAmount());
    }

    @Override
    public void update() {
        getSpriteActor().getSprite().setFlip(face.getFace(), false);
        onHitEntity();
    }

    @Override
    public void register() {
        super.register();
        getSpriteActor().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downTouch();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upTouch();
            }
        });
    }

    public void setFace(Face face) {
        this.face = face;
    }

}
