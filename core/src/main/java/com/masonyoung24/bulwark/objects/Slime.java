package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.enums.SlimeSize;
import com.masonyoung24.bulwark.resources.Assets;

public class Slime extends Enemy {

    private static final float DEFAULT_SCALE_SIZE = 2.0f;

    /**
     * Constructs an object of type Slime.
     *
     * @param healthPoints  the HP of the Slime as an int
     * @param speed         the movement speed of the Slime as a float
     * @param assetManager  the AssetManager to retrieve the sprite from
     * @param spawnPosition the spawn position of the Slime as a Vector3
     *
     */
    public Slime(
        final SlimeSize slimeSize,
        final int healthPoints,
        final float speed,
        final AssetManager assetManager,
        final Vector3 spawnPosition) {
        super(healthPoints, speed, SpriteUtils.getSpriteFromSpriteSheet(
            assetManager.get(Assets.MONSTERS_SPRITE_SHEET),
            2,
            1,
            Enemy.SPRITE_SHEET_COLUMNS,
            Enemy.SPRITE_SHEET_ROWS
        ), spawnPosition);
        this.scaleActorSize(DEFAULT_SCALE_SIZE);
    }
}
