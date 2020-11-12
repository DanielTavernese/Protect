package ca.dantav.game.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ca.dantav.game.Attacker;
import ca.dantav.game.Entity;
import ca.dantav.game.King;
import ca.dantav.game.ProjectileManager;
import ca.dantav.game.screen.PlayScreen;
import ca.dantav.game.sprite.SpriteHoverActor;

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
