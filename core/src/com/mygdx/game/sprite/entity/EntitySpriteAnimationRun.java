package com.mygdx.game.sprite.entity;

import com.mygdx.game.CastleDefense;
import com.mygdx.game.Fighter;
import com.mygdx.game.timer.GameTimer;

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
