package com.mygdx.game.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/*
Game should operate in 60 fps meaning 1 tick = 1/60 seconds
Syncrhonous timer
 */
public final class GameTimerManager {

    private List<GameTimer> gameTimerList = new ArrayList<GameTimer>();

    private List<GameTimer> toRemove = new ArrayList<GameTimer>();

    public GameTimerManager() {

    }

    public void update() {

        new ArrayList<GameTimer>(gameTimerList).stream().forEach(new Consumer<GameTimer>() {

            @Override
            public void accept(GameTimer gameTimer) {

                if(gameTimer.stoppingCondition()) {
                    gameTimer.setRunning(false);
                    gameTimer.end();
                }

                if(!gameTimer.isRunning()) {
                    toRemove.add(gameTimer);
                    return;
                }

                if(gameTimer.getCurrTicks() <= 0) {
                    gameTimer.execute();
                    gameTimer.setCurrTicks(gameTimer.getTicks());
                    gameTimer.setTimesExecuted(gameTimer.getTimesExecuted() + 1);
                    return;
                }

                gameTimer.setCurrTicks(gameTimer.getCurrTicks() - 1);
            }
        });

        gameTimerList.removeAll(toRemove);
        toRemove.clear();
    }

    public void start(GameTimer timer) {
        timer.setRunning(true);
        gameTimerList.add(timer);
        if(timer.isImmediate()) {
            timer.execute();
            timer.setTimesExecuted(timer.getTimesExecuted() + 1);
        }
    }

}
