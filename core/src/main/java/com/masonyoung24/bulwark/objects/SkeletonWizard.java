package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.resources.Assets;

/**
 * Represents a ranged skeleton wizard enemy.
 *
 * @author Mason Young
 * @version 2025
 */
public class SkeletonWizard extends RangedEnemy {
    /**
     * The row in the spritesheet with the desired Goblin sprite
     * */
    private static final int SKELETON_WIZARD_TARGET_ROW = 4;
    /**
     * The column in the spritesheet with the desired Goblin sprite
     * */
    private static final int SKELETON_WIZARD_TARGET_COLUMN = 2;

    /**
     * Constructs an object of type Bird.
     *
     * @param healthPoints the health of the goblin as an int
     * @param speed the movement speed of the goblin as a float
     * @param assetManager the AssetManger used to get the sprite
     * @param spawnPosition the spawn position as a Vector3
     * */
    public SkeletonWizard(final int healthPoints,
                          final float speed,
                          final AssetManager assetManager,
                          Vector3 spawnPosition) {
        super(healthPoints,
            speed,
            SpriteUtils.getSpriteFromSpriteSheet(
                assetManager.get(Assets.MONSTERS_SPRITE_SHEET),
                SKELETON_WIZARD_TARGET_ROW,
                SKELETON_WIZARD_TARGET_COLUMN,
                Enemy.SPRITE_SHEET_COLUMNS,
                Enemy.SPRITE_SHEET_ROWS
            ),
            spawnPosition,
            assetManager);
    }

    /**
     * Handles the death of an Enemy by flagging it as dead.
     *
     * @param enemyManager an enemy manager as an EnemyManager
     */
    @Override
    public void handleDeath(final EnemyManager enemyManager) {

    }
}
