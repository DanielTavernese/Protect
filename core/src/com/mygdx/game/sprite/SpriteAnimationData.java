package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

public final class SpriteAnimationData {

    private Texture[] textures;

    private int currIndex;

    public SpriteAnimationData(int size) {
        textures = new Texture[size];
    }

    public void setTexture(Texture texture, int index) {
        textures[index] = texture;
    }

    public float getHeight() {
        if(textures.length == 0) {
            return 0f;
        }
        return textures[0].getHeight();
    }

    public float getWidth() {
        if(textures.length == 0) {
            return 0f;
        }
        return textures[0].getWidth();
    }

    public Texture[] getTextures() {
        return textures;
    }
}
