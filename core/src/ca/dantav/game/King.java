package ca.dantav.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ca.dantav.game.screen.PlayScreen;
import ca.dantav.game.sprite.SpriteActor;
import ca.dantav.game.sprite.entity.FlyerSpriteAnimationRun;
import ca.dantav.game.sprite.entity.KingSpriteAnimationRun;

import java.util.List;

public class King extends Entity {

    public enum KingStance {
        IDLE,
        DIE;
    }

    private KingStance stance;

    private KingSpriteAnimationRun kingSpriteAnimationRun;

    public King(PlayScreen playScreen, SpriteActor spriteActor) {
        super(playScreen, spriteActor, 20);
    }

    @Override
    public void update() {

    }

    @Override
    public void register() {
        stance = KingStance.IDLE;
        kingSpriteAnimationRun = new KingSpriteAnimationRun(this);
        kingSpriteAnimationRun.start();
    }

    @Override
    public void unregister() {
        super.unregister();
        kingSpriteAnimationRun.getTimer().stop();
        setDead(true);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Camera camera) {
        super.draw(shapeRenderer, camera);

        Stage stage = getPlayScreen().getStage();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(getSpriteActor().getSprite().getX(),
                getSpriteActor().getSprite().getY() + (float) (getSpriteActor().getHeight() * 1.07),
                getHealth() * 5, (float) (stage.getHeight() * 0.014));
        shapeRenderer.end();
    }

    @Override
    public void setHealth(int health) {
        super.setHealth(health);
        if(getHealth() <= 0) {
            stance = KingStance.DIE;
        }
    }

    public KingStance getStance() {
        return stance;
    }

}
