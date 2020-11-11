package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Entity;
import com.mygdx.game.King;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.sprite.SpriteActor;
import com.mygdx.game.sprite.SpriteHoverActor;

public class CrownInventoryIcon extends InventoryIcon {

    public CrownInventoryIcon(PlayScreen playScreen, Sprite inventory) {
        super(playScreen, inventory, new Sprite(playScreen.getCastleDefense().getAssets().get("crown", Texture.class)));
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
