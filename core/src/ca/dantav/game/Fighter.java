package ca.dantav.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ca.dantav.game.screen.PlayScreen;
import ca.dantav.game.sprite.entity.FighterSpriteAnimationRun;
import ca.dantav.game.sprite.SpriteActor;
import ca.dantav.game.utility.GameConstants;

import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

public class Fighter extends Attacker {

    public enum FighterStance {
        WALKING,
        ATTACKING,
        PULLED_UP,
        FALLING,
        DIZZY,
        DEATH;
    }

    private King king;

    private FighterSpriteAnimationRun fighterSpriteAnimationRun;

    private Sprite falling;

    private float fallHeight;

    private FighterStance stance;

    public Fighter(PlayScreen playScreen) {
        super(playScreen, new SpriteActor(new Sprite(playScreen.getCastleDefense().getAssets().get("player_run_1", Texture.class))), 10);
        getSpriteActor().getSprite().flip(true, false);
        this.falling = new Sprite(playScreen.getCastleDefense().getAssets().get("falling_player", Texture.class));

        float figherPositionX = playScreen.getStage().getWidth() - getSpriteActor().getWidth();
        float fightPositionY =  (playScreen.getStage().getHeight() * GameConstants.FLOOR_HEIGHT_MODIFER);

        super.setPosition(figherPositionX, fightPositionY);
    }

    public void register() {
        super.register();
        stance = FighterStance.WALKING;
        fighterSpriteAnimationRun = new FighterSpriteAnimationRun(this);
        fighterSpriteAnimationRun.start();
    }

    public void unregister() {
        super.unregister();
        fighterSpriteAnimationRun.getTimer().stop();
    }

    @Override
    public void downTouch() {
        if(!stance.equals(FighterStance.WALKING) && !stance.equals(FighterStance.ATTACKING)) {
            return;
        }

        stance = FighterStance.PULLED_UP;
    }

    @Override
    public void upTouch() {
        if(!stance.equals(FighterStance.PULLED_UP)) {
            return;
        }

        stance = FighterStance.FALLING;
        Vector2 adjustedPos = getPlayScreen().getStage().screenToStageCoordinates(new Vector2(
                Gdx.input.getX(), Gdx.input.getY()));
        fallHeight = adjustedPos.y;
    }

    @Override
    public int getCoinAmount() {
        return 1;
    }

    @Override
    public void onHitEntity() {

        if(stance.equals(FighterStance.DEATH)) {
            return;
        }

        if(king != null) {
            if(overlaps(king)) {
                stance = FighterStance.ATTACKING;
            }

            return;
        }

        if(getPlayScreen().getCastle().hitEntity(this)) {
            if(!stance.equals(FighterStance.WALKING)) {
                return;
            }
            stance = FighterStance.ATTACKING;
        }
    }

    @Override
    public void startDeath() {
        setStance(FighterStance.DEATH);
        setDead(true);
    }

    private King findClosestKing() {
        List<Entity> kingEntitites = StreamSupport.stream(getPlayScreen().getCastleDefense().getEntityManager().getEntityList()).filter((Entity e) -> e instanceof King).collect(Collectors.toList());
        if(!kingEntitites.isEmpty()) {
            Entity closest = kingEntitites.get(0);
            for(int i = 1; i < kingEntitites.size(); i++) {
                Entity candidate = (Entity) kingEntitites.get(i);
                float closestOffset = Math.abs(closest.getX() - getX());
                float candidateOffset = Math.abs(candidate.getX() - getX());
                if(candidateOffset < closestOffset) {
                    closest = candidate;
                }
            }
            return (King) closest;
        }
        return null;
    }

    @Override
    public void update() {
        super.update();


        if(king != null && king.isDead()) {
            setStance(FighterStance.WALKING);
            setFace(Face.WEST);
            king = null;
        }

        this.king = findClosestKing();

        switch(stance) {
            case WALKING:
                if(king == null) {
                    transform(-GameConstants.ATTACKER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime(), 0);
                    getSpriteActor().getSprite().setFlip(true, false);
                    setFace(Face.WEST);

                } else {
                    int direction = getX() >= king.getX() ? -1 : 1;
                    transform(direction * GameConstants.ATTACKER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime(), 0);

                    if(direction == 1) {
                        setFace(Face.EAST);
                    }
                }

                break;
            case PULLED_UP:
                Vector2 adjustedPos = getPlayScreen().getStage().screenToStageCoordinates(new Vector2(
                        Gdx.input.getX(), Gdx.input.getY()
                ));
                float newYPos = adjustedPos.y - (float) (getSpriteActor().getHeight() * 0.65);
                if(newYPos <= getPlayScreen().getStage().getHeight() * GameConstants.FLOOR_HEIGHT_MODIFER) {
                    return;
                }
                setPosition(getX(), newYPos);
                break;
            case FALLING:
                transform(0, -1 * (float) (getPlayScreen().getStage().getHeight() * GameConstants.FALL_SPEED * Gdx.graphics.getDeltaTime()));
                if(getY() <= getPlayScreen().getStage().getHeight() * GameConstants.FLOOR_HEIGHT_MODIFER) {
                    getPlayScreen().getCastleDefense().playSound(getPlayScreen().getCastleDefense().getAssets().get("fall_sound", Sound.class));
                    setPosition(getX(), getPlayScreen().getStage().getHeight() * GameConstants.FLOOR_HEIGHT_MODIFER);
                    stance = FighterStance.WALKING;
                    damage();

                    if(getHealth() > 0) {
                        stance = FighterStance.DIZZY;
                    } else {
                        stance = FighterStance.DEATH;
                    }
                }
                break;
        }

    }

    public void attack() {
        if(king == null) {
            getPlayScreen().getCastle().decrementHealth();
        } else {
            king.setHealth(king.getHealth() - 2);
        }
        getPlayScreen().getCastleDefense().playSound(getPlayScreen().getCastleDefense().getAssets().get("attack_sound", Sound.class));
    }

    private void damage() {
        float floorHeight = getPlayScreen().getStage().getHeight() * GameConstants.FLOOR_HEIGHT_MODIFER;
        float distanceFell = fallHeight - floorHeight;
        setHealth(getHealth() - (int) (distanceFell/25));
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Camera camera) {
        super.draw(shapeRenderer, camera);
        if(getHealth() <= 0) {
            return;
        }

        Stage stage = getPlayScreen().getStage();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getSpriteActor().getSprite().getX() + (float) (getSpriteActor().getSprite().getWidth() * 0.1),
                getSpriteActor().getSprite().getY() + (float) (getSpriteActor().getSprite().getHeight() * 0.8),
                getHealth() * 5, (float) (stage.getHeight() * 0.014));
        shapeRenderer.end();
    }

    public FighterStance getStance() {
        return stance;
    }

    public void setStance(FighterStance stance) {
        this.stance = stance;
    }
}
