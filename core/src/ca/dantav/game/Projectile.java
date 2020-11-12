package ca.dantav.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ca.dantav.game.screen.PlayScreen;
import ca.dantav.game.sprite.SpriteActor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Projectile extends Entity {

    private static final float PROJECTILE_SPEED = 180;

    public Projectile(PlayScreen playScreen, float x) {
        super(playScreen, new SpriteActor(new Sprite(playScreen.getCastleDefense().getAssets().get("projectile", Texture.class))), 1);

        getSpriteActor().getSprite().rotate(-90);
        setPosition(x - getSpriteActor().getWidth(), playScreen.getStage().getHeight());
    }

    @Override
    public void update() {
        Optional<Attacker> candidate = getPlayScreen().getCastleDefense().getEntityManager().getAttackers().stream().filter((Attacker a) -> a.overlaps(getSpriteActor())).findFirst();

        if(candidate.isPresent() && !candidate.get().isDead()) {
            candidate.get().startDeath();
            getPlayScreen().getCastleDefense().getEntityManager().unregister(this);
        }

        setPosition(getX(), getY() - PROJECTILE_SPEED * Gdx.graphics.getDeltaTime());
    }

}
