package ca.dantav.game;

import ca.dantav.game.screen.PlayScreen;
import ca.dantav.game.sprite.SpriteAnimationData;

public final class FighterSpawn {

    private int ticks;

    private int wave;

    private int fighters;

    private int timer;

    private int flyers;

    private int flyerTimer;

    private int flyerTicks;

    private PlayScreen playScreen;

    private SpriteAnimationData fighterData;

    public FighterSpawn(PlayScreen playScreen) {
        fighterData = playScreen.getCastleDefense().getSpriteAnimations("player_run");
        this.playScreen = playScreen;
        nextWave();
    }

    public void update() {

        if(fighters <= 0) {
            nextWave();
        }

        if(flyerTicks >= flyerTimer && flyers > 0) {
            Flyer flyer = new Flyer(playScreen);
            playScreen.getCastleDefense().getEntityManager().register(flyer);
            flyers--;
            flyerTicks = 0;
        }

        if(ticks >= timer && fighters > 0) {
            Fighter fighter = new Fighter(playScreen);
            playScreen.getCastleDefense().getEntityManager().register(fighter);
            fighters--;
            ticks = 0;
        }

        ticks++;
        flyerTicks++;
    }

    private void nextWave() {
        wave++;
        fighters = wave * 3;
        flyers = wave + 1;
        timer = 100 - (wave * 4);
        timer = timer < 30 ? 30 : timer;
        flyerTimer = Math.round((fighters / flyers)) * timer;
    }

}
