package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.Entity;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.utility.GameConstants;

import java.util.Objects;

public abstract class SpriteHoverActor extends SpriteActor {

    private PlayScreen playScreen;

    private boolean touched;

    private boolean interactable;

    public SpriteHoverActor(Sprite sprite, PlayScreen playScreen) {
        super(sprite);

        this.playScreen = playScreen;

        float originX = playScreen.getStage().getWidth() / 2 - sprite.getWidth() / 2;
        float originY = playScreen.getStage().getHeight() * GameConstants.FLOOR_HEIGHT_MODIFER;

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touched = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                boolean overlaps = playScreen.getCastleDefense().getEntityManager().getEntityList().stream().filter(Objects::nonNull).anyMatch((Entity e) -> overlaps(e));

                if(overlaps) {
                    remove();
                    return;
                }

                sprite.setAlpha(1f);
                interactable = true;
                touched = false;
                onPlacement();
            }
        });

        sprite.setAlpha(0.7f);
        setPosition(originX, originY);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(!touched || interactable) {
            return;
        }

        Vector2 adjustedPos = playScreen.getStage().screenToStageCoordinates(new Vector2(
                Gdx.input.getX(), Gdx.input.getY()
        ));

        float x = adjustedPos.x - (getSprite().getWidth() / 2);
        float y = getY();

        setPosition(x, y);
    }

    public abstract boolean overlaps(Entity entity);

    public abstract void onPlacement();

}
