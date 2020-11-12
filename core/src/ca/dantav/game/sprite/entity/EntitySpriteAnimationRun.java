package ca.dantav.game.sprite.entity;

import ca.dantav.game.CastleDefense;
import ca.dantav.game.Fighter;
import ca.dantav.game.timer.GameTimer;

public abstract class EntitySpriteAnimationRun {

    private CastleDefense castleDefense;

    private GameTimer timer;

    public EntitySpriteAnimationRun(CastleDefense castleDefense) {
        this.castleDefense = castleDefense;
        timer = new GameTimer(5, true) {
            @Override
            public void execute() {
                fire();
            }

            @Override
            public boolean stoppingCondition() {
                return false;
            }
        };
    }

    public void start() {
        castleDefense.getGameTimerManager().start(timer);
    }

    public abstract void fire();

    public GameTimer getTimer() {
        return timer;
    }
}
