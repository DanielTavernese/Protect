package ca.dantav.game.utility.json;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.JsonValue;
import ca.dantav.game.Assets;
import ca.dantav.game.CastleDefense;
import ca.dantav.game.sprite.SpriteAnimation;
import ca.dantav.game.sprite.SpriteAnimationData;
import ca.dantav.game.utility.JsonParser;

public class SpriteAnimationJsonParser extends JsonParser {

    private CastleDefense castleDefense;

    public SpriteAnimationJsonParser(CastleDefense castleDefense) {
        super("animations.json");
        this.castleDefense = castleDefense;
    }

    @Override
    public void parse(JsonValue base) {
        for (JsonValue entry = base.child; entry != null; entry = entry.next) {
            String name = entry.getString("name");
            JsonValue value = entry.get("paths");

            SpriteAnimationData spriteAnimation = new SpriteAnimationData(value.size);

            for(int i = 0; i < value.size; i++) {
                spriteAnimation.setTexture(castleDefense.getAssets().get(value.getString(i), Texture.class), i);
            }

            castleDefense.addSpriteAnimations(name, spriteAnimation);
        }

    }


}
