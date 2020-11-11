package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Attacker;
import com.mygdx.game.Entity;
import com.mygdx.game.King;
import com.mygdx.game.ProjectileManager;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.sprite.SpriteHoverActor;

public class ArrowInventoryIcon extends InventoryIcon{

    public ArrowInventoryIcon(PlayScreen playScreen, Sprite inventory) {
        super(playScreen, inventory, new Sprite(playScreen.getCastleDefense().getAssets().get("arrow", Texture.class)));
    }

    @Override
    public void execute() {
        //testing
        SpriteHoverActor spriteHoverActor = new SpriteHoverActor(new Sprite(getPlayScreen().getCastleDefense().getAssets().get("projectile_circle", Texture.class)), getPlayScreen()) {

            @Override
            public boolean overlaps(Entity entity) {
                if(entity.overlaps(this)) {
                    return !(entity instanceof Attacker);
                }
                return false;
            }

            @Override
            public void onPlacement() {
                ProjectileManager projectileManager = new ProjectileManager(getPlayScreen(), this);
                projectileManager.loadProjectiles();
                remove();
            }
        };

        getPlayScreen().getStage().addActor(spriteHoverActor);
    }

    @Override
    public float getX() {
        return inventory.getX() + 207;
    }

    @Override
    public float getY() {
        return  (float) (getPlayScreen().getStage().getHeight() - (getSprite().getHeight() * 1.7));
    }
}
