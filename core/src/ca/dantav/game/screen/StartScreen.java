package ca.dantav.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ca.dantav.game.CastleDefense;
import ca.dantav.game.sprite.SpriteActor;
import ca.dantav.game.sprite.SpriteActorButton;
import ca.dantav.game.sprite.SpriteAnimation;
import ca.dantav.game.sprite.SpriteAnimationData;
import ca.dantav.game.sprite.SpriteAnimationRun;
import ca.dantav.game.timer.GameTimer;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class StartScreen extends GameScreen {

    private Sprite background;

    private Group text;

    private boolean decrementingText;

    private Sprite fingerPlayer;

    private Sprite fallingPlayer;

    private SpriteAnimation spriteAnimation;

    /**
     * Default constructor
     * <p>
     * Calls start function
     *
     * @param castleDefense
     */
    public StartScreen(CastleDefense castleDefense) {
        super(castleDefense);
        this.background = new Sprite(getCastleDefense().getAssets().get("game_background", Texture.class));
        this.fingerPlayer =  new Sprite(getCastleDefense().getAssets().get("finger_player", Texture.class));
        fingerPlayer.flip(true, false);

        this.fallingPlayer =  new Sprite(getCastleDefense().getAssets().get("falling_player", Texture.class));
        fallingPlayer.flip(true, false);
    }

    @Override
    public void start() {

        Sprite infoButton = new Sprite(getCastleDefense().getAssets().get("info_button", Texture.class));
        SpriteActorButton infoButtonActor = new SpriteActorButton(getCastleDefense(), infoButton) {
            @Override
            public void touchUpEvent(InputEvent event, float x, float y, int pointer, int button) {
                getCastleDefense().switchScreen(new InfoScreen(getCastleDefense()));
            }
        };
        infoButtonActor.setPosition(10, (float) (getStage().getHeight()*0.45) - (infoButton.getHeight()/2));
        getStage().addActor(infoButtonActor);

        Sprite audioButton = new Sprite(getCastleDefense().getAssets().get("audio_button_on", Texture.class));
        Sprite audioButtonOff = new Sprite(getCastleDefense().getAssets().get("audio_button_off", Texture.class));

        SpriteActorButton audioButtonActor = new SpriteActorButton(getCastleDefense(), getCastleDefense().isMuted() ? audioButtonOff : audioButton) {
            @Override
            public void touchUpEvent(InputEvent event, float x, float y, int pointer, int button) {
                getCastleDefense().setMuted(!getCastleDefense().isMuted());
                setSprite(getCastleDefense().isMuted() ? audioButtonOff : audioButton);

                Preferences preferences = Gdx.app.getPreferences("muted");

                preferences.putBoolean("muted", getCastleDefense().isMuted());
                preferences.flush();
            }
        };
        audioButtonActor.setPosition(10, (float) (getStage().getHeight()*0.25) - (audioButton.getHeight()/2));
        getStage().addActor(audioButtonActor);

        Sprite logo = new Sprite(getCastleDefense().getAssets().get("protect_logo", Texture.class));
        addSpriteToScreen(logo, getStage().getWidth()/2 - (logo.getWidth()/2), (float) (getStage().getHeight() * 0.6));

        BitmapFont font = getCastleDefense().getAssets().get("aller_bd", BitmapFont.class);
        text = addFontToScreen(font, "TAP ANYWHERE TO START!", Color.WHITE, 100, (float) (getStage().getHeight() * 0.15), 0.7f);

        SpriteAnimationData playerRunData = getCastleDefense().getSpriteAnimations("player_run");

        SpriteAnimation spriteAnimation = new SpriteAnimation(playerRunData,
                700, (float) (playerRunData.getHeight() * 0.45));

        runPlayerAnimation(spriteAnimation);

        getStage().addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                AtomicBoolean hit = new AtomicBoolean(false);

                for(Actor actor : getStage().getActors().toArray()) {
                    Actor hitActor = getStage().hit(x, y, true);

                    if(hitActor != null) {
                        hit.set(true);
                        return;
                    }
                }

                if((!hit.get())) {
                    getCastleDefense().switchScreen(new PlayScreen(getCastleDefense()));
                    getCastleDefense().playSound(getCastleDefense().getAssets().get("button_sound", Sound.class));
                }
            }
        });

    }

    private void runPlayerAnimation(SpriteAnimation spriteAnimation) {
        spriteAnimation.flipHorizontal();
        getStage().addActor(spriteAnimation.getSpriteActor());
        new SpriteAnimationRun(getCastleDefense(), spriteAnimation, 5) {

            @Override
            public void onExecute() {
                spriteAnimation.transform(-2, 0);

                if(spriteAnimation.getX() <= 620) {
                    getGameTimer().stop();
                    fingerPlayer.setX(spriteAnimation.getX());
                    fingerPlayer.setY(spriteAnimation.getY());
                    spriteAnimation.getSpriteActor().setSprite(fingerPlayer);

                    getCastleDefense().getGameTimerManager().start(new GameTimer(5) {
                        @Override
                        public void execute() {
                            float increaseY = (float) (getStage().getHeight() * 0.02);
                            spriteAnimation.getSpriteActor().getSprite().setY(
                                    spriteAnimation.getSpriteActor().getSprite().getY() + increaseY);
                        }

                        @Override
                        public boolean stoppingCondition() {
                            return fingerPlayer.getY() >= (getStage().getHeight()/2);
                        }

                        @Override
                        public void end() {
                            fallingPlayer.setX(fingerPlayer.getX());
                            fallingPlayer.setY(fingerPlayer.getY());
                            spriteAnimation.getSpriteActor().setSprite(fallingPlayer);

                            getCastleDefense().getGameTimerManager().start(new GameTimer(5) {
                                @Override
                                public void execute() {
                                    float increaseY = (float) (getStage().getHeight() * 0.02);
                                    spriteAnimation.getSpriteActor().getSprite().setY(
                                            spriteAnimation.getSpriteActor().getSprite().getY() - increaseY);
                                }

                                @Override
                                public boolean stoppingCondition() {
                                    return fallingPlayer.getY() <= fallingPlayer.getHeight() * 0.45;
                                }

                                @Override
                                public void end() {

                                    spriteAnimation.getSpriteActor().remove();

                                    SpriteAnimationData playerRunData = getCastleDefense().getSpriteAnimations("player_run");

                                    SpriteAnimation spriteAnimation = new SpriteAnimation(playerRunData,
                                            700, (float) (playerRunData.getHeight() * 0.45));

                                    runPlayerAnimation(spriteAnimation);
                                }
                            });
                        }
                    });
                }
            }

        };
    }


    @Override
    public void update() {
        if(text.getScaleX() >= 0.77) {
            decrementingText = true;
        } else if(text.getScaleX() <= 0.7) {
            decrementingText = false;
        }

        float scaleFactor = decrementingText ? -0.001f : 0.001f;
        text.setScale(text.getScaleX() + scaleFactor, text.getScaleY() + scaleFactor);

    }

    @Override
    public Sprite getBackground() {
        return background;
    }

    @Override
    public void back() {

    }
}
