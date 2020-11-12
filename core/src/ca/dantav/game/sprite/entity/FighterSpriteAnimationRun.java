package ca.dantav.game.sprite.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ca.dantav.game.Fighter;
import ca.dantav.game.sprite.SpriteAnimation;
import ca.dantav.game.utility.GameConstants;

public final class FighterSpriteAnimationRun extends EntitySpriteAnimationRun {

    private SpriteAnimation walkingAnimation;

    private SpriteAnimation dizzyAnimation;

    private SpriteAnimation deathAnimation;

    private SpriteAnimation attackAnimation;

    private Sprite pullSprite;

    private Sprite fallingSprite;

    private Fighter fighter;

    public FighterSpriteAnimationRun(Fighter fighter) {
        super(fighter.getPlayScreen().getCastleDefense());
        this.walkingAnimation = new SpriteAnimation(fighter.getPlayScreen().getCastleDefense().getSpriteAnimations("player_run"), fighter.getSpriteActor());
        this.dizzyAnimation = new SpriteAnimation(fighter.getPlayScreen().getCastleDefense().getSpriteAnimations("player_dizzy"), fighter.getSpriteActor());
        this.deathAnimation = new SpriteAnimation(fighter.getPlayScreen().getCastleDefense().getSpriteAnimations("player_die"), fighter.getSpriteActor());
        this.attackAnimation = new SpriteAnimation(fighter.getPlayScreen().getCastleDefense().getSpriteAnimations("player_attack"), fighter.getSpriteActor());

        this.pullSprite = new Sprite(fighter.getPlayScreen().getCastleDefense().getAssets().get("pull_up_player", Texture.class));
        this.fallingSprite = new Sprite(fighter.getPlayScreen().getCastleDefense().getAssets().get("falling_player", Texture.class));

        attackAnimation.resize(GameConstants.ATTACKER_SCALE_SPRITE);
        walkingAnimation.resize(GameConstants.ATTACKER_SCALE_SPRITE);
        pullSprite.setScale(GameConstants.ATTACKER_SCALE_SPRITE);
        fallingSprite.setScale(GameConstants.ATTACKER_SCALE_SPRITE);
        dizzyAnimation.resize(GameConstants.ATTACKER_SCALE_SPRITE);
        deathAnimation.resize(GameConstants.ATTACKER_SCALE_SPRITE);

        this.fighter = fighter;
    }

    @Override
    public void fire() {
        switch(fighter.getStance()) {
            case WALKING:
                walkingAnimation.setPosition(fighter.getX(), fighter.getY());
                walkingAnimation.getAndIncrement();
                break;
            case PULLED_UP:
                fighter.getSpriteActor().setSprite(pullSprite);
                break;
            case FALLING:
                fighter.getSpriteActor().setSprite(fallingSprite);
                break;
            case DIZZY:
                dizzyAnimation.setPosition(fighter.getX(), fighter.getY());
                dizzyAnimation.getAndIncrement();
                if(dizzyAnimation.loopAmount() >= 2) {
                    dizzyAnimation.reset();
                    fighter.setStance(Fighter.FighterStance.WALKING);
                }
                break;
            case DEATH:
                deathAnimation.setPosition(fighter.getX(), fighter.getY());
                deathAnimation.getAndIncrement();
                if(deathAnimation.loopAmount() >= 1) {
                    fighter.die();
                }
                break;
            case ATTACKING:
                attackAnimation.setPosition(fighter.getX(), fighter.getY());
                attackAnimation.getAndIncrement();
                if(attackAnimation.getCurrIndex() == attackAnimation.getEndIndex()) {
                    fighter.attack();
                }
                break;
        }
    }
}
