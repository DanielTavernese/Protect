package ca.dantav.game.sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class SpriteAnimation {

    private SpriteAnimationData data;

    private SpriteActor spriteActor;

    private Sprite[] sprites;

    private int currIndex;

    private float x;

    private float y;

    public SpriteAnimation(SpriteAnimationData data, float x, float y) {
        this.sprites = new Sprite[data.getTextures().length];
        for(int i = 0; i < data.getTextures().length; i++) {
            sprites[i] = new Sprite(data.getTextures()[i]);
            sprites[i].setX(x);
            sprites[i].setY(y);
        }

        this.x = x;
        this.y = y;

        if(sprites.length == 0) {
            System.out.println("Sprites length is 0.");
            return;
        }

        this.spriteActor = new SpriteActor(sprites[0]);
    }

    public SpriteAnimation(SpriteAnimationData data, SpriteActor spriteActor) {
        this.sprites = new Sprite[data.getTextures().length];
        for(int i = 0; i < data.getTextures().length; i++) {
            sprites[i] = new Sprite(data.getTextures()[i]);
            sprites[i].setX(spriteActor.getX());
            sprites[i].setY(spriteActor.getY());
        }

        this.x = spriteActor.getX();
        this.y = spriteActor.getY();

        if(sprites.length == 0) {
            System.out.println("Sprites length is 0.");
            return;
        }

        this.spriteActor = spriteActor;
    }

    public Sprite getCurrSprite() {
        return spriteActor.getSprite();
    }

    public Sprite getNextSprite() {
        return sprites[(currIndex + 1) % sprites.length];
    }

    public void resize(float scale) {
        Arrays.stream(sprites).filter(Objects::nonNull).forEach((Sprite s) -> s.setScale(scale));
    }
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        spriteActor.setPosition(x, y);
        Arrays.stream(sprites).filter(Objects::nonNull).forEach((Consumer<Sprite>) s -> {
            s.setX(x);
            s.setY(y);
        });
    }

    public void rotateSprite(float angle) {
        Arrays.stream(sprites).filter(Objects::nonNull).forEach((Sprite s) -> s.rotate(angle));
}

    public void flipHorizontal() {
        Arrays.stream(sprites).forEach((Sprite s) -> s.flip(true, false));
    }

    public void scale(float scaleFactor) {
        Arrays.stream(sprites).filter(Objects::nonNull).forEach((Sprite s) -> s.setScale(scaleFactor));
    }

    public int loopAmount() {
        return (int) (currIndex / sprites.length);
    }

    public void reset() {
        currIndex = 0;
    }

    public void getAndIncrement() {
        Sprite nextsprite = getNextSprite();
        currIndex++;
        spriteActor.setSprite(nextsprite);
    }

    public int getCurrIndex() {
        return currIndex % sprites.length;
    }

    public int getEndIndex() {
        return sprites.length-1;
    }

    public SpriteActor getSpriteActor() {
        return spriteActor;
    }

    public void transform(float x, float y) {
        setPosition(getX() + x, getY() + y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
