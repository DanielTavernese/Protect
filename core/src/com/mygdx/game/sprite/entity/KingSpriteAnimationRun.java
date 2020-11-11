package com.mygdx.game.sprite.entity;

import com.mygdx.game.CastleDefense;
import com.mygdx.game.King;
import com.mygdx.game.sprite.SpriteAnimation;

public class KingSpriteAnimationRun extends EntitySpriteAnimationRun {

    private King king;

    private SpriteAnimation kingIdleAnimation;

    public KingSpriteAnimationRun(King king) {
        super(king.getPlayScreen().getCastleDefense());
        this.kingIdleAnimation = new SpriteAnimation(king.getPlayScreen().getCastleDefense().getSpriteAnimations("king_idle"), king.getSpriteActor());
        this.king = king;
    }

    @Override
    public void fire() {
        switch(king.getStance()) {
            case IDLE:
                kingIdleAnimation.setPosition(king.getX(), king.getY());
                kingIdleAnimation.getAndIncrement();
                break;
        }
    }
}
