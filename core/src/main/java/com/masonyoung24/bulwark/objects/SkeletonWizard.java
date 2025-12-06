package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.data.EnemySaveData;
import com.masonyoung24.bulwark.data.SkeletonWizardSaveData;
import com.masonyoung24.bulwark.enums.EnemyType;
import com.masonyoung24.bulwark.resources.Assets;

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
     * Saves the state of the skeleton wizard to a new SkeletonWizardSaveData object.
     *
     * @return the created skeleton wizard save data as a SkeletonWizardSaveData
     * */
    @Override
    public SkeletonWizardSaveData saveData() {
        SkeletonWizardSaveData data = new SkeletonWizardSaveData();

        data.setXCoordinate(this.getPosition().x);
        data.setYCoordinate(this.getPosition().y);
        data.setHP(this.getCurrentHP());
        data.setMaxHP(this.getMaxHP());
        data.setEnemyType(this.getEnemyType());
        data.setSpeed(this.getSpeed());

        return data;
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
        SkeletonWizardSaveData data = (SkeletonWizardSaveData) base;

        this.setCurrentHP(data.getHP());
        this.sprite.setPosition(data.getXCoordinate(), data.getYCoordinate());
        this.setSpeed(data.getSpeed());
    }
}
