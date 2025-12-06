package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.data.EnemySaveData;
import com.masonyoung24.bulwark.enums.EnemyType;
import com.masonyoung24.bulwark.resources.Assets;

/**
 * Represents a Goblin.
 *
 * @author Mason Young
 * @version 2025
 * */
public class Goblin extends Enemy {
    /**
     * The row in the spritesheet with the desired Goblin sprite
     * */
    private static final int GOBLIN_TARGET_ROW = 0;
    /**
     * The column in the spritesheet with the desired Goblin sprite
     * */
    private static final int GOBLIN_TARGET_COLUMN = 2;

    /**
     * Constructs an object of type Goblin.
     *
     * @param healthPoints the health of the goblin as an int
     * @param speed the movement speed of the goblin as a float
     * @param assetManager the AssetManger used to get the sprite
     * */
    public Goblin(final int healthPoints, final float speed, final AssetManager assetManager, Vector3 spawnPosition) {
        super(healthPoints,
            speed,
            SpriteUtils.getSpriteFromSpriteSheet(
                assetManager.get(Assets.MONSTERS_SPRITE_SHEET),
                GOBLIN_TARGET_ROW,
                GOBLIN_TARGET_COLUMN,
                Enemy.SPRITE_SHEET_COLUMNS,
                Enemy.SPRITE_SHEET_ROWS
            ),
            spawnPosition,
            EnemyType.GOBLIN);
    }

    /**
     * Handles the death of an Enemy by flagging it as dead.
     *
     * @param enemyManager an enemy manager as an EnemyManager
     */
    @Override
    public void handleDeath(final EnemyManager enemyManager) {

    }

    @Override
    public EnemySaveData saveData() {
        EnemySaveData data = new EnemySaveData();

        data.setXCoordinate(this.getPosition().x);
        data.setYCoordinate(this.getPosition().y);
        data.setHP(this.getCurrentHP());
        data.setMaxHP(this.getMaxHP());
        data.setEnemyType(this.getEnemyType());
        data.setSpeed(this.getSpeed());

        return data;
    }

    @Override
    public void loadData(EnemySaveData base) {

        this.setCurrentHP(base.getHP());
        this.sprite.setPosition(base.getXCoordinate(), base.getYCoordinate());
        this.setSpeed(base.getSpeed());
    }
}
