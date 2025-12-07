package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.Managers.EnemyManager;
import com.masonyoung24.bulwark.data.EnemySaveData;
import com.masonyoung24.bulwark.enums.EnemyType;
import com.masonyoung24.bulwark.resources.Assets;

import java.util.Objects;

/**
 * Represents a ranged skeleton wizard enemy.
 *
 * @author Mason Young
 * @version 2025
 */
public class SkeletonWizard extends RangedEnemy {

    private static final int SKELETON_WIZARD_TARGET_ROW = 4;
    private static final int SKELETON_WIZARD_TARGET_COLUMN = 2;

    /**
     * Constructs an object of type SkeletonWizard.
     *
     * @param healthPoints the health of the skeleton wizard as an int
     * @param speed the movement speed of the skeleton wizard  as a float
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
            assetManager,
            EnemyType.SKELETON_WIZARD);
    }

    /**
     * Handles the death of an Enemy by flagging it as dead.
     *
     * @param enemyManager an enemy manager as an EnemyManager
     */
    @Override
    public void handleDeath(final EnemyManager enemyManager) {

    }

    /**
     * Sets the instance variables of the SkeletonWizard based on saved data.
     *
     * @param base the save data for a base enemy as an EnemySaveData
     * @throws IllegalArgumentException if the data provided is null
     * */
    @Override
    public void loadData(EnemySaveData base) {
        if (base == null) {
            throw new IllegalArgumentException("Provided save data must not be null.");
        }

        this.setCurrentHP(base.getHP());
        this.sprite.setPosition(base.getXCoordinate(), base.getYCoordinate());
        this.setSpeed(base.getSpeed());
    }

    /**
     * Returns a String representation of this SkeletonWizard.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("SkeletonWizard{");
        stringBuilder.append(super.toString());
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /**
     * Compares this SkeletonWizard to another Object for equality.
     *
     * @param object an Object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(final Object object) {
        return super.equals(object);
    }

    /**
     * Returns a hash code for this SkeletonWizard.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(
            super.hashCode()
        );
    }
}
