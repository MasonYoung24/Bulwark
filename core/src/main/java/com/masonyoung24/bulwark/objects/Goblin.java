package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.masonyoung24.bulwark.resources.Assets;

public class Goblin extends Enemy {
    /**
     * The row in the spritesheet with the desired Goblin sprite
     * */
    private static final int GOBLIN_TARGET_ROW = 1;
    /**
     * The column in the spritesheet with the desired Goblin sprite
     * */
    private static final int GOBLIN_TARGET_COLUMN = 3;

    /**
     * Constructs an object of type Bird.
     *
     * @param healthPoints the health of the goblin as an int
     * @param speed the movement speed of the goblin as a float
     * @param assetManager the AssetManger used to get the sprite
     * */
    public Goblin(final int healthPoints, final float speed, final AssetManager assetManager) {
        super(healthPoints,
            speed,
            SpriteUtils.getSpriteFromSpriteSheet(
                assetManager.get(Assets.MONSTERS_SPRITE_SHEET),
                GOBLIN_TARGET_ROW,
                GOBLIN_TARGET_COLUMN,
                Enemy.SPRITE_SHEET_COLUMNS,
                Enemy.SPRITE_SHEET_ROWS
            ));
    }
}
