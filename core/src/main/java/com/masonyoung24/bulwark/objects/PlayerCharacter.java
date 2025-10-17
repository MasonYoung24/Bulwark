package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.masonyoung24.bulwark.resources.Assets;

public class PlayerCharacter extends Actor {
    private static final int SPRITE_SHEET_ROWS = 7;
    private static final int SPRITE_SHEET_COLUMNS = 7;
    private static final int CHARACTER_TARGET_ROW = 2;
    private static final int CHARACTER_TARGET_COLUMN = 3;
    private static final int SINGLE_FRAME_TEXTURE_REGION_LENGTH = 1;
    public static final int BASE_HP = 3;

//    private final Sprite playerSprite;

//    private final TextureRegion playerCharacterFrame;

    public PlayerCharacter(int healthPoints,
                           float speed,
                           AssetManager assetManager) {
        super(healthPoints,
            speed,
            Actor.getSprite(assetManager.get(Assets.HUMANS_SPRITE_SHEET),
                CHARACTER_TARGET_ROW,
                CHARACTER_TARGET_COLUMN));
    }
}
