package com.mygdx.game.sprite.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Flyer;
import com.mygdx.game.sprite.SpriteAnimation;
import com.mygdx.game.utility.GameConstants;

public final class FlyerSpriteAnimationRun extends EntitySpriteAnimationRun {

    private SpriteAnimation flyingAnimation;

    private SpriteAnimation deathAnimation;

    private Sprite fallingSprite;

    private Flyer flyer;

    public FlyerSpriteAnimationRun(Flyer flyer) {
        super(flyer.getPlayScreen().getCastleDefense());
        this.flyingAnimation = new SpriteAnimation(flyer.getPlayScreen().getCastleDefense().getSpriteAnimations("flying"), flyer.getSpriteActor());
        this.deathAnimation = new SpriteAnimation(flyer.getPlayScreen().getCastleDefense().getSpriteAnimations("player_die"), flyer.getSpriteActor());

        this.fallingSprite = new Sprite(flyer.getPlayScreen().getCastleDefense().getAssets().get("falling_player", Texture.class));

        deathAnimation.resize(GameConstants.ATTACKER_SCALE_SPRITE);
        flyingAnimation.resize(GameConstants.ATTACKER_SCALE_SPRITE);
        fallingSprite.setScale(GameConstants.ATTACKER_SCALE_SPRITE);

        this.flyer = flyer;
    }

    @Override
    public void fire() {
        switch(flyer.getStance()) {
            case FLYING:
                flyingAnimation.setPosition(flyer.getX(), flyer.getY());
                flyingAnimation.getAndIncrement();
                break;
            case FALLING:
                flyer.getSpriteActor().setSprite(fallingSprite);
                break;
            case DYING:
                deathAnimation.setPosition(flyer.getX(), flyer.getY());
                deathAnimation.getAndIncrement();
                if(deathAnimation.loopAmount() >= 1) {
                    flyer.die();
                }
                break;
        }
    }
}
