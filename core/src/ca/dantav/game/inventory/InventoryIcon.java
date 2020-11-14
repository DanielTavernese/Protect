package ca.dantav.game.inventory;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ca.dantav.game.screen.PlayScreen;
import ca.dantav.game.sprite.SpriteActor;
import ca.dantav.game.sprite.SpriteActorButton;

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
                public boolean playSound() {
                    return playScreen.getCastleDefense().getCoins() >= getCoinAmount();
                }

                @Override
                public void touchUpEvent(InputEvent event, float x, float y, int pointer, int button) {
                    if(playScreen.getCastleDefense().getCoins() < getCoinAmount()) {
                        playScreen.getCastleDefense().playSound(playScreen.getCastleDefense().getAssets().get("fail_sound", Sound.class));
                        return;
                    }
                    playScreen.getCastleDefense().setCoins(playScreen.getCastleDefense().getCoins() - getCoinAmount());
                    execute();
                }
            };
    }

    public abstract int getCoinAmount();

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
