package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.sprite.SpriteActor;
import com.mygdx.game.utility.GameConstants;

import java.util.Arrays;

/*
Holds all castle parts
 */
public class Castle {

    private CastlePart[] castle = new CastlePart[5];

    private int health;

    private PlayScreen playScreen;

    public Castle(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.health = 3;
    }

    public boolean hitEntity(Entity entity) {
        return Arrays.stream(castle).anyMatch((CastlePart c) -> c.overlaps(entity));
    }

    public void register() {
        Sprite bottomCastle = new Sprite(playScreen.getCastleDefense().getAssets().get("castle1", Texture.class));
        bottomCastle.setPosition(0, playScreen.getStage().getHeight() * GameConstants.FLOOR_HEIGHT_MODIFER);
        castle[0] = new CastlePart(new SpriteActor(bottomCastle), playScreen);

        Sprite topCastle = new Sprite(playScreen.getCastleDefense().getAssets().get("castle2", Texture.class));
        topCastle.setPosition(0, bottomCastle.getY() + topCastle.getHeight());
        castle[1] = new CastlePart(new SpriteActor(topCastle), playScreen);

        Sprite topCastleOne = new Sprite(playScreen.getCastleDefense().getAssets().get("castle3", Texture.class));
        topCastleOne.setPosition(0, topCastle.getY() + topCastleOne.getHeight());
        castle[2] = new CastlePart(new SpriteActor((topCastleOne)), playScreen);

        Sprite topCastleTwo = new Sprite(playScreen.getCastleDefense().getAssets().get("castle4", Texture.class));
        topCastleTwo.setPosition(0, topCastleOne.getY() + topCastleTwo.getHeight());
        castle[3] = new CastlePart(new SpriteActor((topCastleTwo)), playScreen);

        Sprite topCastleThree = new Sprite(playScreen.getCastleDefense().getAssets().get("castle5", Texture.class));
        topCastleThree.setPosition(0, topCastleTwo.getY() + topCastleThree.getHeight());
        castle[4] = new CastlePart(new SpriteActor((topCastleThree)), playScreen);

        Arrays.stream(castle).forEach((CastlePart c) -> playScreen.getCastleDefense().getEntityManager().register(c));

    }

    public void decrementHealth() {
        health--;
    }

    public void unregister() {
        Arrays.stream(castle).forEach((CastlePart c) -> playScreen.getCastleDefense().getEntityManager().unregister(c));
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public CastlePart[] getCastle() {
        return castle;
    }

    public int getHealth() {
        return health;
    }
}
