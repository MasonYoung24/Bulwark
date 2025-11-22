package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.resources.Assets;

public class SkeletonWizard extends Enemy implements Shooter{
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
            spawnPosition);
    }

    /**
     * Gets the time since last shot as a float.
     *
     * @return the time since last shot as a float
     *
     */
    @Override
    public float getTimeSinceLastShot() {
        return 0;
    }

    /**
     * Determines if another shot can be fired.
     *
     * @return a boolean true if a shot can be fired, false otherwise
     *
     */
    @Override
    public boolean canFire() {
        return false;
    }

    /**
     * Fires a projectile.
     *
     * @param targetPosition the position of the target to shoot at as a Vector3
     * @return a projectile as a Projectile
     *
     */
    @Override
    public Projectile fireProjectile(final Vector3 targetPosition) {
        return null;
    }

    /**
     * Updates the time until the next shot can be fired.
     *
     * @param deltaTime the time elapsed since the last update
     *
     */
    @Override
    public void updateTimeSinceLastShot(final float deltaTime) {

    }
}
