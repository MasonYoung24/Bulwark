package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.Managers.EnemyManager;
import com.masonyoung24.bulwark.data.EnemySaveData;
import com.masonyoung24.bulwark.data.SlimeSaveData;
import com.masonyoung24.bulwark.enums.EnemyType;
import com.masonyoung24.bulwark.enums.SlimeSize;
import com.masonyoung24.bulwark.resources.Assets;

import java.util.Objects;

/**
 * Represents a Slime enemy.
 *
 * @author Mason Young
 * @version 2025
 * */
public class Slime extends Enemy {

    private static final int SPAWN_RADIUS_MULTIPLIER = 2;
    private static final int LARGE_SLIME_SPLIT_COUNT = 2;
    private static final int MEDIUM_SLIME_SPLIT_COUNT = 3;
    private static final int LARGE_TO_MEDIUM_HP = 2;
    private static final float LARGE_TO_MEDIUM_SCALE = 0.5F;
    private static final float LARGE_TO_MEDIUM_SPEED = 1.8f;
    private static final int MEDIUM_TO_SMALL_HP = 1;
    private static final float MEDIUM_TO_SMALL_SCALE = 0.2f;
    private static final float MEDIUM_TO_SMALL_SPEED = 2.2f;

    private SlimeSize size;
    private float scaleFactor;

    /**
     * Constructs an object of type Slime.
     *
     * @param healthPoints  the HP of the Slime as an int
     * @param speed         the movement speed of the Slime as a float
     * @param assetManager  the AssetManager to retrieve the sprite from
     * @param spawnPosition the spawn position of the Slime as a Vector3
     */
    public Slime(
        final SlimeSize size,
        final float scaleFactor,
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
            ), spawnPosition,
            EnemyType.SLIME);
        this.scaleFactor = scaleFactor;
        this.scaleActorSize(scaleFactor);
        this.size = size;
    }

    /**
     * Returns the size of the slime.
     *
     * @return the size of the slime as a SlimeSize
     * */
    public SlimeSize getSlimeSize() {
        return this.size;
    }

    /**
     * Sets the size of the slime.
     *
     * @param slimeSize the size of the slime to set as a SlimeSize
     * */
    public void setSlimeSize(final SlimeSize slimeSize) {
        this.size = slimeSize;
    }

    /**
     * Returns the scale factor of the slime.
     *
     * @return the scale factor of the slime as a float
     * */
    public float getSlimeScaleFactor() {
        return this.scaleFactor;
    }

    /**
     * Sets the scale factor of the slime.
     *
     * @param scaleFactor the scale factor to set for the slime as a float
     * */
    public void setSlimeScaleFactor(final float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    /**
     * Splits a slime into multiple slimes depending on size.
     *
     * @param enemyManager the enemy manager that handles spawning the slimes as an Enemy Manager
     * */
    private void split(final EnemyManager enemyManager) {
        switch (this.getSlimeSize()) {
            case LARGE:
                for (int i = 0; i < LARGE_SLIME_SPLIT_COUNT; i++) {
                    enemyManager.spawnSlime(
                        SlimeSize.MEDIUM,
                        LARGE_TO_MEDIUM_SCALE,
                        LARGE_TO_MEDIUM_HP,
                        LARGE_TO_MEDIUM_SPEED,
                        getRandomOffsetSpawnPosition());
                }
                break;
            case MEDIUM:
                for (int i = 0; i < MEDIUM_SLIME_SPLIT_COUNT; i++) {
                    enemyManager.spawnSlime(
                        SlimeSize.SMALL,
                        MEDIUM_TO_SMALL_SCALE,
                        MEDIUM_TO_SMALL_HP,
                        MEDIUM_TO_SMALL_SPEED,
                        getRandomOffsetSpawnPosition());
                }
                break;
            default:
                break;
        }
    }

    /**
     * Generates a randomly offset spawn position.
     *
     * @return the randomly offset spawn position as a Vector3
     * */
    private Vector3 getRandomOffsetSpawnPosition() {
        float radius = 25f;

        float offsetX = (float) (Math.random() * radius * SPAWN_RADIUS_MULTIPLIER - radius);
        float offsetY = (float) (Math.random() * radius * SPAWN_RADIUS_MULTIPLIER - radius);

        return this.getPosition().cpy().add(offsetX, offsetY, 0);
    }


    /**
     * Handles the death of an Enemy by flagging it as dead.
     *
     * @param enemyManager an enemy manager as an EnemyManager
     */
    @Override
    public void handleDeath(final EnemyManager enemyManager) {
        split(enemyManager);
    }

    /**
     * Saves the state of the Slime to a new SlimeSaveData object.
     *
     * @return the created slime save data as a SlimeSaveData
     * */
    @Override
    public SlimeSaveData saveData() {
        SlimeSaveData data = new SlimeSaveData();

        data.setXCoordinate(this.getPosition().x);
        data.setYCoordinate(this.getPosition().y);
        data.setHP(this.getCurrentHP());
        data.setMaxHP(this.getMaxHP());
        data.setScaleFactor(this.getSlimeScaleFactor());
        data.setSlimeSize(this.getSlimeSize());
        data.setEnemyType(this.getEnemyType());
        data.setSpeed(this.getSpeed());

        return data;
    }

    /**
     * Sets the instance variables of the Slime based on saved data.
     *
     * @param base the save data for a base enemy as an EnemySaveData
     * @throws IllegalArgumentException if the data provided is null
     * */
    @Override
    public void loadData(final EnemySaveData base) {
        if (base == null) {
            throw new IllegalArgumentException("Provided save data must not be null.");
        }
        SlimeSaveData data = (SlimeSaveData) base;

        this.setCurrentHP(data.getHP());
        this.sprite.setPosition(data.getXCoordinate(), data.getYCoordinate());
        this.setSpeed(data.getSpeed());
        this.setSlimeSize(data.getSlimeSize());
        this.setSlimeScaleFactor(data.getScaleFactor());
    }

    /**
     * Returns a String representation of this Slime.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("Slime{");
        stringBuilder.append(super.toString());
        stringBuilder.append(", size=").append(size);
        stringBuilder.append(", scaleFactor=").append(scaleFactor);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /**
     * Compares this Slime to another Object for equality.
     *
     * @param object an Object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        Slime slime = (Slime) object;
        return Float.compare(scaleFactor, slime.scaleFactor) == 0
            && size == slime.size;
    }

    /**
     * Returns a hash code for this Slime.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(
            super.hashCode(),
            size,
            scaleFactor
        );
    }
}
