package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.sprite.SpriteActor;
import com.mygdx.game.sprite.entity.FlyerSpriteAnimationRun;
import com.mygdx.game.utility.GameConstants;

import java.util.List;

public class Flyer extends Attacker {

    public enum FlyerStance {
        FLYING,
        FALLING,
        DYING;
    }

    private FlyerStance stance;

    private FlyerSpriteAnimationRun flyerSpriteAnimationRun;

    public Flyer(PlayScreen playScreen) {
        super(playScreen, new SpriteActor(new Sprite(playScreen.getCastleDefense().getAssets().get("flyer_1", Texture.class))), 1);

        getSpriteActor().getSprite().setScale(GameConstants.ATTACKER_SCALE_SPRITE);
        getSpriteActor().getSprite().flip(true, false);
        float flyerPositionX = playScreen.getStage().getWidth() - getSpriteActor().getWidth();
        float flyerPositionY =  (float) (playScreen.getStage().getHeight() * 0.5);

        super.setPosition(flyerPositionX, flyerPositionY);

    }

    @Override
    public void register() {
        super.register();
        stance = FlyerStance.FLYING;
        flyerSpriteAnimationRun = new FlyerSpriteAnimationRun(this);
        flyerSpriteAnimationRun.start();
    }

    @Override
    public void unregister() {
        super.unregister();
        flyerSpriteAnimationRun.getTimer().stop();
    }

    @Override
    public void update() {
        super.update();

        switch(stance) {
            case FLYING:
                transform(-GameConstants.ATTACKER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime(), 0);
                break;
            case FALLING:
                transform(0, -1 * (float) (getPlayScreen().getStage().getHeight() * GameConstants.FALL_SPEED* Gdx.graphics.getDeltaTime()));
                if(getY() <= getPlayScreen().getStage().getHeight() * GameConstants.FLOOR_HEIGHT_MODIFER) {
                    getPlayScreen().getCastleDefense().playSound(getPlayScreen().getCastleDefense().getAssets().get("fall_sound", Sound.class));
                    stance = FlyerStance.DYING;
                    System.out.println("here");
                }
            break;
        }
    }


    @Override
    public void downTouch() {

    }

    @Override
    public void upTouch() {
        if(stance.equals(FlyerStance.FLYING)) {
            stance = FlyerStance.FALLING;
        }
    }

    @Override
    public int getCoinAmount() {
        return 2;
    }

    @Override
    public void onHitEntity() {
        if(getPlayScreen().getCastle().hitEntity(this)) {
            Sprite dyingSprite = new Sprite(getPlayScreen().getCastleDefense().getAssets().get("flyer_7", Texture.class));
            dyingSprite.flip(true, false);
            getSpriteActor().setSprite(dyingSprite);
            getPlayScreen().getCastle().setHealth(0);
        }
    }

    @Override
    public void startDeath() {
        stance = FlyerStance.FALLING;
        setDead(true);
    }

    public FlyerStance getStance() {
        return stance;
    }
}
