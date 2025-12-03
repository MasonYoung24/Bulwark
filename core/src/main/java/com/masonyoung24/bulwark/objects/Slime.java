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
                for (int i = 0; i < 2; i++) {
                    enemyManager.spawnSlime(1, SlimeSize.MEDIUM, 0.5f, 2, 2.0f, getRandomOffsetSpawnPosition());
                }
                break;
            case MEDIUM:
                for (int i = 0; i < 3; i++) {
                    enemyManager.spawnSlime(1, SlimeSize.SMALL, 0.2f, 1, 2.5f, getRandomOffsetSpawnPosition());
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
