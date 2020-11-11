package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Attacker;
import com.mygdx.game.Entity;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.timer.GameTimer;
import com.mygdx.game.utility.GameConstants;

import java.util.Locale;

public class NukeInventoryIcon extends InventoryIcon {

    private Label nukeTimer;

    private boolean nuking;

    public NukeInventoryIcon(PlayScreen playScreen, Sprite inventory, Label nukeTimer) {
        super(playScreen, inventory, new Sprite(playScreen.getCastleDefense().getAssets().get("bomb", Texture.class)));
        this.nukeTimer = nukeTimer;
    }

    @Override
    public void execute() {

        if(nuking) {
            return;
        }

        getPlayScreen().getCastleDefense().getGameTimerManager().start(new GameTimer(1) {

            @Override
            public void execute() {
                nukeTimer.setVisible(true);
                getButton().setVisible(false);

                double offset = ((double) getTimesExecuted() + 1) / (GameConstants.NUKE_TIMER * 60);
                double time = GameConstants.NUKE_TIMER * (1- offset);
                nukeTimer.setText(String.format(Locale.ROOT, "%.1f", time));
                nuking = true;
            }

            @Override
            public boolean stoppingCondition() {
                return getTimesExecuted() >= GameConstants.NUKE_TIMER * 60;
            }

            @Override
            public void end() {
                getPlayScreen().getCastleDefense().getEntityManager().getAttackers().stream().forEach((Attacker a) -> a.startDeath());
                getButton().setVisible(true);
                nukeTimer.setVisible(false);
                nuking = false;
            }
        });
    }

    @Override
    public float getX() {
        return inventory.getX() + 105;
    }

    @Override
    public float getY() {
        return (float) (getPlayScreen().getStage().getHeight() - (getSprite().getHeight() * 1.3));
    }
}
