package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character {
    private static final int SPRITE_SHEET_ROWS = 7;
    private static final int SPRITE_SHEET_COLUMNS = 7;
    private static final int SINGLE_FRAME_TEXTURE_REGION_LENGTH = 1;

    /*
     * Isolates the character frame from the passed sprite sheet.
     *
     * @param spriteSheet the sprite sheet that holds the character frame
     * @return characterFrame the frame of the character as a TextureRegion[];
     * */
    private TextureRegion[] getCharacterSpriteFrame(Texture spriteSheet) {
        TextureRegion[][] splitSpriteSheet = TextureRegion.split(
            spriteSheet,
            spriteSheet.getWidth() / SPRITE_SHEET_COLUMNS,
            spriteSheet.getHeight() / SPRITE_SHEET_ROWS);
        TextureRegion[] characterFrame = new TextureRegion[SINGLE_FRAME_TEXTURE_REGION_LENGTH];
        int targetRow = 2;
        int targetColumn = 3;
        characterFrame[0] = splitSpriteSheet[targetRow][targetColumn];

        return characterFrame;
    }
}
