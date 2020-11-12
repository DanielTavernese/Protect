package ca.dantav.game.sprite;

import ca.dantav.game.CastleDefense;
import ca.dantav.game.timer.GameTimer;

public class SpriteAnimationRun {

    private GameTimer gameTimer;

    private SpriteAnimation spriteAnimation;

    private boolean paused;

    public SpriteAnimationRun(CastleDefense castleDefense, SpriteAnimation spriteAnimation, int ticks) {
        this.spriteAnimation = spriteAnimation;

        this.gameTimer = new GameTimer(ticks) {
            @Override
            public void execute() {
                if(paused) {
                    return;
                }

                spriteAnimation.getAndIncrement();
                onExecute();
            }

            @Override
            public boolean stoppingCondition() {
                return false;
            }
        };

        castleDefense.getGameTimerManager().start(gameTimer);
    }

    public void onExecute() {

    }

    public SpriteAnimation getSpriteAnimation() {
        return spriteAnimation;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }
}
