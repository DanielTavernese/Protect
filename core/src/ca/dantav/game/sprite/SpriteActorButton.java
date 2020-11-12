package ca.dantav.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ca.dantav.game.CastleDefense;
import ca.dantav.game.timer.GameTimer;

/*
    Adds some animation to button click sprites
 */
public class SpriteActorButton extends SpriteActor {


    private InputListener inputListener;

    public SpriteActorButton(CastleDefense game, Sprite sprite) {
        super(sprite);
        Sound sound = game.getAssets().get("button_sound", Sound.class);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getSprite().setScale(1.2f);
                game.playSound(sound);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getSprite().setScale(1.0f);
                game.getGameTimerManager().start(new GameTimer(5) {
                    @Override
                    public void execute() {
                        touchUpEvent(event, x, y, pointer, button);
                    }

                    @Override
                    public boolean stoppingCondition() {
                        return getTimesExecuted() >= 1;
                    }
                });

            }
        });
    }

    public void touchUpEvent(InputEvent event, float x, float y, int pointer, int button) {

    }


}
