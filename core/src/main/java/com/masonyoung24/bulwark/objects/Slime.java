package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.enums.SlimeSize;
import com.masonyoung24.bulwark.resources.Assets;

/**
 * Represents a Slime enemy.
 *
 * @author Mason Young
 * @version 2025
 * */
public class Slime extends Enemy {

    private static final float DEFAULT_SCALE_SIZE = 2.0f;
    private static final int SPAWN_RADIUS_MULTIPLIER = 2;
    private static final int LARGE_SLIME_SPLIT_COUNT = 2;
    private static final int MEDIUM_SLIME_SPLIT_COUNT = 3;
    private static final int LARGE_TO_MEDIUM_HP = 1;
    private static final float LARGE_TO_MEDIUM_SCALE = 0.5F;
    private static final float LARGE_TO_MEDIUM_SPEED = 1.8f;
    private static final int MEDIUM_TO_SMALL_HP = 1;
    private static final float MEDIUM_TO_SMALL_SCALE = 0.2f;
    private static final float MEDIUM_TO_SMALL_SPEED = 2.3f;

    private final SlimeSize size;
    private final float scaleFactor;

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
        ), spawnPosition);
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
     * Splits a slime into multiple slimes depending on size.
     *
     * @param enemyManager the enemy manager that handles spawning the slimes as an Enemy Manager
     * */
    private void split(EnemyManager enemyManager) {
        switch(this.getSlimeSize()) {
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
}
