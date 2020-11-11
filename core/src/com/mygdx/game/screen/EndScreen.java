package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.CastleDefense;
import com.mygdx.game.sprite.SpriteActorButton;
import com.mygdx.game.utility.GameConstants;

public class EndScreen extends GameScreen {

    private Sprite background;

    /**
     * Default constructor
     * <p>
     * Calls start function
     *
     * @param castleDefense
     */
    public EndScreen(CastleDefense castleDefense) {
        super(castleDefense);
        this.background = new Sprite(getCastleDefense().getAssets().get("game_background", Texture.class));
    }

    @Override
    public void start() {
        Sprite homeButton = new Sprite(getCastleDefense().getAssets().get("home_button", Texture.class));

        SpriteActorButton homeButtonAction = new SpriteActorButton(getCastleDefense(), homeButton) {
          @Override
          public void touchUpEvent(InputEvent event, float x, float y, int pointer, int button) {
              getCastleDefense().switchScreen(new StartScreen(getCastleDefense()));
          }
        };
        homeButtonAction.setPosition(getStage().getWidth()/2 - (homeButton.getWidth()/2), (float) (getStage().getHeight() * 0.15));
        getStage().addActor(homeButtonAction);

        Sprite twitterButton = new Sprite(getCastleDefense().getAssets().get("twitter_button", Texture.class));
        SpriteActorButton twitterButtonAction = new SpriteActorButton(getCastleDefense(), twitterButton) {
            @Override
            public void touchUpEvent(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.net.openURI(GameConstants.TWITTER_URL);
            }
        };
        twitterButtonAction.setPosition((float) (getStage().getWidth()*0.33) - (twitterButton.getWidth()/2), (float) (getStage().getHeight() * 0.15));
        getStage().addActor(twitterButtonAction);

        Sprite instaButton = new Sprite(getCastleDefense().getAssets().get("instagram_button", Texture.class));
        SpriteActorButton instaButtonAction = new SpriteActorButton(getCastleDefense(), instaButton) {
            @Override
            public void touchUpEvent(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.net.openURI(GameConstants.INSTAGRAM_URL);
            }
        };
        instaButtonAction.setPosition((float) (getStage().getWidth()*0.66) - (twitterButton.getWidth()/2), (float) (getStage().getHeight() * 0.15));
        getStage().addActor(instaButtonAction);

        BitmapFont font = getCastleDefense().getAssets().get("score_font", BitmapFont.class);
        String scoreText = String.valueOf(getCastleDefense().getScore());
        addFontToScreen(font, scoreText, Color.BLACK,383 - ((scoreText.length()-1) * 10), (float) (getStage().getHeight() * 0.7), 1.5f);

        Preferences preferences = Gdx.app.getPreferences("highScore");

        int highscore = preferences.getInteger("highScore", 0);

        if(getCastleDefense().getScore() > highscore) {
            highscore = getCastleDefense().getScore();
            preferences.putInteger("highScore", highscore);
            preferences.flush();
        }

        BitmapFont bestFont = getCastleDefense().getAssets().get("aller_bd", BitmapFont.class);
        String highScoreNumber = String.valueOf(highscore);
        String highScoreText = "BEST: " + highScoreNumber;
        addFontToScreen(bestFont, highScoreText, Color.BLACK,315 - ((highScoreNumber.length()-1) * 10), (float) (getStage().getHeight() * 0.5), 0.7f);
    }


    @Override
    public Sprite getBackground() {
        return background;
    }

    @Override
    public void back() {

    }
}
