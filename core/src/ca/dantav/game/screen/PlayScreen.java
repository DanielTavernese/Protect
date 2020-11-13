package ca.dantav.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ca.dantav.game.Attacker;
import ca.dantav.game.Castle;
import ca.dantav.game.CastleDefense;
import ca.dantav.game.Entity;
import ca.dantav.game.Fighter;
import ca.dantav.game.FighterSpawn;
import ca.dantav.game.Flyer;
import ca.dantav.game.King;
import ca.dantav.game.inventory.ArrowInventoryIcon;
import ca.dantav.game.inventory.CrownInventoryIcon;
import ca.dantav.game.inventory.NukeInventoryIcon;
import ca.dantav.game.sprite.SpriteActor;
import ca.dantav.game.sprite.SpriteActorButton;
import ca.dantav.game.sprite.SpriteHoverActor;
import ca.dantav.game.timer.GameTimer;
import ca.dantav.game.utility.GameConstants;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java8.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java8.util.stream.StreamSupport;

public class PlayScreen extends GameScreen {

    private Sprite background;

    private SpriteActor[] health = new SpriteActor[3];

    private Castle castle;

    private Sprite towerSprite;

    private FighterSpawn fighterSpawn;

    private Label coinText;

    private Label scoreText;

    private Label nukeTimer;

    private boolean nuking;


    /**
     * Default constructor
     * <p>
     * Calls start function
     *
     * @param castleDefense
     */
    public PlayScreen(CastleDefense castleDefense) {
        super(castleDefense);
        this.background = new Sprite(getCastleDefense().getAssets().get("game_background", Texture.class));
        this.castle = new Castle(this);
    }

    @Override
    public void start() {
        castle.register();

        //reset score
        getCastleDefense().setScore(0);

        //ui
        towerSprite = new Sprite(getCastleDefense().getAssets().get("tower_logo", Texture.class));
        addSpriteToScreen(towerSprite, 5, getStage().getHeight() - towerSprite.getHeight());

        this.fighterSpawn = new FighterSpawn(this);

        //inventory
        Sprite inventory = new Sprite(getCastleDefense().getAssets().get("inventory", Texture.class));
        addSpriteToScreen(inventory, getStage().getWidth()/2 - (inventory.getWidth()/2), getStage().getHeight() - (inventory.getHeight()));

        CrownInventoryIcon crownInventoryIcon = new CrownInventoryIcon(this, inventory);
        crownInventoryIcon.register();

        BitmapFont nukeFont = getCastleDefense().getAssets().get("score_font", BitmapFont.class);
        nukeTimer = (Label) addFontToScreen(nukeFont, "0", Color.RED, inventory.getX() + 99, getStage().getHeight() - (float) (nukeFont.getXHeight() * 3), 0.7f).getChild(0);
        nukeTimer.setVisible(false);

        NukeInventoryIcon nukeInventoryIcon = new NukeInventoryIcon(this, inventory, nukeTimer);
        nukeInventoryIcon.register();

        ArrowInventoryIcon arrowInventoryIcon = new ArrowInventoryIcon(this, inventory);
        arrowInventoryIcon.register();

        //coin
        Sprite coin = new Sprite(getCastleDefense().getAssets().get("coin", Texture.class));
        addSpriteToScreen(coin, 1, getStage().getHeight() - ((float) (towerSprite.getHeight() * 1.1)) - coin.getHeight());

        BitmapFont coinFont = getCastleDefense().getAssets().get("score_font", BitmapFont.class);
        coinText = (Label) addFontToScreen(coinFont, "" + getCastleDefense().getCoins(), Color.WHITE, (coin.getX() + 35 - ((String.valueOf(getCastleDefense().getCoins())).length() * 6)),
                coin.getY() - (float) (getStage().getHeight() * 0.04), 0.6f).getChild(0);

        BitmapFont scoreFont = getCastleDefense().getAssets().get("score_font", BitmapFont.class);
        scoreText = (Label) addFontToScreen(coinFont, "" + getCastleDefense().getScore(), Color.WHITE, getStage().getWidth() - 60, (float) (coin.getY() * 1.02f), 1.2f).getChild(0);

        //health
        for(int i = 0; i < health.length; i++) {
            Sprite redRectangle = new Sprite(getCastleDefense().getAssets().get("red_rect", Texture.class));
            float x = towerSprite.getX() + towerSprite.getWidth() + 10 + (i * redRectangle.getWidth());
            float y = towerSprite.getY();
            SpriteActor spriteActor = new SpriteActor(redRectangle);
            spriteActor.setPosition(x, y);
            health[i] = spriteActor;
            getStage().addActor(health[i]);
        }
    }

    @Override
    public void dispose() {
        castle.unregister();
        StreamSupport.stream(getCastleDefense().getEntityManager().getEntityList()).forEach((Entity e) -> getCastleDefense().getEntityManager().unregister(e));
        super.dispose();
    }

    @Override
    public Sprite getBackground() {
        return background;
    }

    public void showHealth() {
        for(int i = 0; i < getCastle().getHealth(); i++) {
            health[i].setVisible(true);
        }
        for(int i = getCastle().getHealth(); i < health.length; i++) {
            health[i].setVisible(false);
        }
    }

    @Override
    public void update() {
        if(castle.getHealth() <= 0) {
            getCastleDefense().switchScreen(new EndScreen(getCastleDefense()));
            getCastleDefense().playSound(getCastleDefense().getAssets().get("explode_sound", Sound.class));
            return;
        }

        coinText.setText(getCastleDefense().getCoins());

        //score
        String score = String.valueOf(getCastleDefense().getScore());
        scoreText.setText(getCastleDefense().getScore());
        scoreText.setX(scoreText.getOriginX() - ((score.length()-1) * 15));

        showHealth();

        StreamSupport.stream(getCastleDefense().getEntityManager().getEntityList()).filter(Objects::nonNull).forEach(Entity::update);
        fighterSpawn.update();
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        super.render(shapeRenderer);
        StreamSupport.stream(getCastleDefense().getEntityManager().getEntityList()).forEach((Entity e) -> e.draw(shapeRenderer, getStage().getCamera()));
    }

    @Override
    public void back() {

    }

    public Castle getCastle() {
        return castle;
    }

}
