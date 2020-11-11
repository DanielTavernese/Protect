package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.sprite.SpriteActor;
import com.mygdx.game.sprite.SpriteActorButton;

public abstract class InventoryIcon {

    public Sprite inventory;

    private Sprite sprite;

    private SpriteActorButton button;

    private PlayScreen playScreen;

    public InventoryIcon(PlayScreen playScreen, Sprite inventory, Sprite sprite) {
            this.inventory = inventory;
            this.sprite = sprite;
            this.playScreen = playScreen;

            sprite.setPosition(getX(), getY());
            this.button = new SpriteActorButton(playScreen.getCastleDefense(), sprite) {
                @Override
                public void touchUpEvent(InputEvent event, float x, float y, int pointer, int button) {
                    execute();
                }
            };
    }

    public void register() {
        playScreen.getStage().addActor(button);
    }

    public void unregister() {
        button.remove();
    }

    public abstract void execute();

    public abstract float getX();

    public abstract float getY();

    public PlayScreen getPlayScreen() {
        return playScreen;
    }

    public SpriteActorButton getButton() {
        return button;
    }
    public Sprite getInventory() {
        return inventory;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
