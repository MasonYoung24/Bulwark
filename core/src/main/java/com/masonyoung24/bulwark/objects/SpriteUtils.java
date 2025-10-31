package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteUtils {
    public SpriteUtils() {

    }

    /**
     * Creates a sprite from the passed sprite sheet.
     *
     * @param spriteSheet the sprite sheet that holds the Actor frame
     * @param targetColumn the column of the target sprite as an int
     * @param targetRow the row of the target sprite as an int
     * @param sprite_sheet_columns the number of columns in the spritesheet
     * @param sprite_sheet_rows the number of rows in the spritesheet
     * @return the sprite of the Actor as a Sprite;
     * */
    public static Sprite getSpriteFromSpriteSheet(final Texture spriteSheet,
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

    /**
     * Tints the sprite to a new color.
     *
     * @param sprite a sprite to tint
     * @param color the color to tint the sprite
     * */
    public static void tint(Sprite sprite, Color color) {
        sprite.setColor(color);
    }
}
