package ca.dantav.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import ca.dantav.game.screen.PlayScreen;
import ca.dantav.game.sprite.SpriteActor;
import ca.dantav.game.utility.GameConstants;

import java.util.List;

public abstract class Entity {

    private int health;

    private boolean dead;

    private SpriteActor spriteActor;

    private PlayScreen playScreen;

    public Entity(PlayScreen playScreen, SpriteActor spriteActor, int health) {
        this.playScreen = playScreen;
        this.spriteActor = spriteActor;
        this.health = health;

        spriteActor.setDebug(GameConstants.DEBUG_MODE);

    }

    public abstract void update();

    public void register() {
        playScreen.getStage().addActor(spriteActor);
    }

    public void unregister() {
        spriteActor.remove();
    }

    public void draw(ShapeRenderer shapeRenderer, Camera camera) {

        if(isDead()) {
            return;
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        getSpriteActor().drawDebug(shapeRenderer);
        shapeRenderer.end();

    }

    public boolean overlaps (Entity entity) {
        return overlaps(entity.getSpriteActor());
    }

    public boolean overlaps (SpriteActor other) {
        return getX() < other.getX() + other
                .getWidth() && getX() + spriteActor.getWidth() > other.getX() &&
                getY() < other.getY() + other.getHeight() && getY() + spriteActor.getHeight() > other.getY();
    }

    public void transform(float x, float y) {
        setPosition(x + getX(), y + getY());
    }

    public SpriteActor getSpriteActor() {
        return spriteActor;
    }

    public void setPosition(float x, float y) {
        spriteActor.setPosition(x, y);
    }

    public int getHealth() {
        return health;
    }

    public PlayScreen getPlayScreen() {
        return playScreen;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpriteActor(SpriteActor spriteActor) {
        this.spriteActor = spriteActor;
    }

    public float getX() {
        return spriteActor.getX();
    }

    public float getY() {
        return spriteActor.getY();
    }


    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

}
