package com.mygdx.game;

import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.sprite.SpriteActor;

public final class ProjectileManager {

    private Projectile[] projectiles = new Projectile[5];

    private PlayScreen playScreen;

    private SpriteActor projectileCircle;

    public ProjectileManager(PlayScreen playScreen, SpriteActor projectileCircle) {
        this.playScreen = playScreen;
        this.projectileCircle = projectileCircle;
    }

    public void loadProjectiles() {

        for(int i = 0; i < projectiles.length; i++) {
            float offsetX = projectileCircle.getWidth() * (( (float) (i + 1)) / projectiles.length);
            float x = projectileCircle.getX() + offsetX + 10;
            projectiles[i] = new Projectile(playScreen, x);
            playScreen.getCastleDefense().getEntityManager().register(projectiles[i]);
        }
    }

}
