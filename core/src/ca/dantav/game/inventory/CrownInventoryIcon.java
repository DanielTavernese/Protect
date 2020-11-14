package ca.dantav.game.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ca.dantav.game.Entity;
import ca.dantav.game.King;
import ca.dantav.game.screen.PlayScreen;
import ca.dantav.game.sprite.SpriteHoverActor;

public class CrownInventoryIcon extends InventoryIcon {

    public CrownInventoryIcon(PlayScreen playScreen, Sprite inventory) {
        super(playScreen, inventory, new Sprite(playScreen.getCastleDefense().getAssets().get("crown", Texture.class)));
    }

    @Override
    public int getCoinAmount() {
        return 25;
    }

    @Override
    public void execute() {
        //testing
        SpriteHoverActor spriteHoverActor = new SpriteHoverActor(new Sprite(getPlayScreen().getCastleDefense().getAssets().get("king_idle_1", Texture.class)), getPlayScreen()) {

            @Override
            public boolean overlaps(Entity entity) {
                return entity.overlaps(this);
            }

            @Override
            public void onPlacement() {
                King king = new King(getPlayScreen(), this);

                getPlayScreen().getCastleDefense().getEntityManager().register(king);

            }
        };

        getPlayScreen().getStage().addActor(spriteHoverActor);
    }

    @Override
    public float getX() {
        return inventory.getX() + 11;
    }

    @Override
    public float getY() {
        return  (float) (getPlayScreen().getStage().getHeight() - (getSprite().getHeight() * 1.7));
    }

}
