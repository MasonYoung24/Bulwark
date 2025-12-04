package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/**
 * Represents a ranged enemy.
 *
 * @author Mason Yooung
 * @version 2025
 * */
public abstract class RangedEnemy extends Enemy {
    public static final float BASE_CAST_SPEED = 4.0f;
    public static final float HALF_MULTIPLIER = 0.5f;
    private static final float TIME_SINCE_LAST_SHOT_RESET_VALUE = 0.0f;
    private static final float DEFAULT_MINIMUM_DISTANCE = 250.0f;
    private static final float RANDOM_TIME_SINCE_LAST_SHOT_LOWER_BOUND = 0.0f;
    private static final float RANDOM_TIME_SINCE_LAST_SHOT_UPPER_BOUND = 3.0f;
    private static final float BASE_REFRACTORY_PERIOD_MODIFIER = 2.0f;

    private final AssetManager assetManager;
    private final float castSpeed;
    private final Projectile projectile;
    private float timeSinceLastShot;

    /**
     * Creates an object of type RangedEnemy.
     *
     * @param maxHP the maximum health of the RangedEnemy as an int
     * @param speed the movement speed of the RangedEnemy as a float
     * @param sprite the sprite to used for the RangedEnemy as a Sprite
     * @param spawnPosition the spawn position of the RangedEnemy as a Vector3
     * @param assetManager the asset manager of the game as an AssetManager
     * */
    public RangedEnemy(final int maxHP,
                       final float speed,
                       final Sprite sprite,
                       final Vector3 spawnPosition,
                       final AssetManager assetManager) {
        super(maxHP, speed, sprite, spawnPosition);
        this.projectile = new Projectile(assetManager);
        this.assetManager = assetManager;
        this.castSpeed = BASE_CAST_SPEED;
        this.timeSinceLastShot = MathUtils.random(RANDOM_TIME_SINCE_LAST_SHOT_LOWER_BOUND,
            RANDOM_TIME_SINCE_LAST_SHOT_UPPER_BOUND);
    }

    /**
     * Moves the Ranged Enemy depending on the target position.
     *
     * @param targetPosition the target position as a float
     * */
    @Override
    public void move(Vector3 targetPosition) {
        if (getIsFrozen()) {
            Vector3 direction = new Vector3(targetPosition).sub(this.getPosition()).nor();
            this.updateDirectionFacing(direction);
            float distanceBetweenEnemyAndPlayer = targetPosition.dst(this.getPosition());
            if (distanceBetweenEnemyAndPlayer > DEFAULT_MINIMUM_DISTANCE) {
                this.sprite.translate(direction.x, direction.y);
            }
        }
    }

    /**
     * Gets the time since last shot as a float.
     *
     * @return the time since last shot as a float
     *
     */
    public float getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    /**
     * Determines if another shot can be fired.
     *
     * @return a boolean true if a shot can be fired, false otherwise
     *
     */
    public boolean canFire() {
        return getTimeSinceLastShot() >= projectile.getRefractoryPeriod()
            * BASE_REFRACTORY_PERIOD_MODIFIER;
    }

    /**
     * Fires a projectile.
     *
     * @param targetPosition the position of the target to shoot at as a Vector3
     * @return a projectile as a Projectile
     * */
    public Projectile fireProjectile(Vector3 targetPosition) {
        Vector3 direction = new Vector3(targetPosition).sub(this.getPosition()).nor();
        Projectile newProjectile = new Projectile(
            bufferProjectileStartPosition(direction),
            castSpeed, direction,
            assetManager,
            2.0F);
        newProjectile.setIsFriendly(false);
        this.timeSinceLastShot = TIME_SINCE_LAST_SHOT_RESET_VALUE;
        return newProjectile;
    }

    /**
     * Updates the time until the next shot can be fired.
     *
     * @param deltaTime the time elapsed since the last update
     *
     */
    public void updateTimeSinceLastShot(float deltaTime) {
        this.timeSinceLastShot += deltaTime;
    }

    /**
     * Buffers the starting position of a fired projectile to account for sprite size.
     *
     * @param projectileDirection the direction of the fired projectile as a Vector3
     * @return the buffered starting position of the projectile as a Vector3
     * */
    private Vector3 bufferProjectileStartPosition(Vector3 projectileDirection) {
        Vector3 projectileDirectionNormalized = new Vector3(projectileDirection).nor();
        float halfSpriteWidth = sprite.getWidth() * HALF_MULTIPLIER;
        float halfSpriteHeight = sprite.getHeight() * HALF_MULTIPLIER;

        float bufferDistance = (float) Math.sqrt(
            (projectileDirectionNormalized.x * halfSpriteWidth) *
                (projectileDirectionNormalized.x * halfSpriteWidth) +
                (projectileDirectionNormalized.y * halfSpriteHeight) *
                    (projectileDirectionNormalized.y * halfSpriteHeight)
        );

        return new Vector3(getPosition()).add(projectileDirectionNormalized.scl(bufferDistance));
    }
}
