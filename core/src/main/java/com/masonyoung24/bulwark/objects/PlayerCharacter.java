package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.masonyoung24.bulwark.resources.Assets;

public class PlayerCharacter {
    private static final int SPRITE_SHEET_ROWS = 7;
    private static final int SPRITE_SHEET_COLUMNS = 7;
    private static final int CHARACTER_TARGET_ROW = 2;
    private static final int CHARACTER_TARGET_COLUMN = 3;
    private static final int SINGLE_FRAME_TEXTURE_REGION_LENGTH = 1;
    public static final int BASE_HP = 3;

    private final TextureRegion playerCharacterFrame;

    public PlayerCharacter(AssetManager assetManager) {
        Texture spriteSheet = assetManager.get(Assets.HUMANS_SPRITE_SHEET);
        playerCharacterFrame = getCharacterSpriteFrame(spriteSheet)[0];
    }

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

        characterFrame[0] = splitSpriteSheet[CHARACTER_TARGET_ROW][CHARACTER_TARGET_COLUMN];

        return characterFrame;
    }

    /**
     * Returns the playerCharacterFrame.
     *
     * @return playerCharacterFrame as a TextureRegion
     */
    public TextureRegion getFrame() {
        return this.playerCharacterFrame;
    }
}
