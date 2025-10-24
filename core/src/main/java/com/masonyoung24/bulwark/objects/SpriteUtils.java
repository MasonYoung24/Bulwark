package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteUtils {
    public SpriteUtils() {

    }

    /*
     * Creates a sprite from the passed sprite sheet.
     *
     * @param spriteSheet the sprite sheet that holds the Actor frame
     * @return the sprite of the Actor as a TextureRegion[];
     * */
    public static Sprite getSprite(final Texture spriteSheet,
                                   final int targetRow,
                                   final int targetColumn,
                                   final int sprite_sheet_columns,
                                   final int sprite_sheet_rows) {
        TextureRegion[][] splitSpriteSheet = TextureRegion.split(
            spriteSheet,
            spriteSheet.getWidth() / sprite_sheet_columns,
            spriteSheet.getHeight() / sprite_sheet_rows);

        TextureRegion actorFrame = splitSpriteSheet[targetRow][targetColumn];

        return new Sprite(actorFrame);
    }
}
