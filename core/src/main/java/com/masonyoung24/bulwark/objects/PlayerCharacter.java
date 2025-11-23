package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.resources.Assets;

public class PlayerCharacter extends Actor implements Shooter {
    private static final int CHARACTER_TARGET_ROW = 2;
    private static final int CHARACTER_TARGET_COLUMN = 3;
    public static final float BASE_CAST_SPEED = 4.0f;
    public static final float HALF_MULTIPLIER = 0.5f;
    public static Vector3 DEFAULT_SPAWN_POSITION = new Vector3(200, 200, 0);
    private static final float BASE_FIRE_RATE_MODIFIER = 0.25f;

    private final AssetManager assetManager;
    private final float castSpeed;
    private final Projectile projectile;
    private float timeSinceLastShot = 0.0F;
    private Vector3 previousPosition;
    private final float fireRateModifier;

    public PlayerCharacter(int healthPoints,
                           float speed,
                           AssetManager assetManager) {
        super(healthPoints,
            speed,
            Actor.getSprite(assetManager.get(Assets.HUMANS_SPRITE_SHEET),
                CHARACTER_TARGET_ROW,
                CHARACTER_TARGET_COLUMN),
            DEFAULT_SPAWN_POSITION
        );
        this.projectile = new Projectile(assetManager);
        this.assetManager = assetManager;
        this.castSpeed = BASE_CAST_SPEED;
        this.fireRateModifier = BASE_FIRE_RATE_MODIFIER;
    }

    /**
     * Gets the time since last shot as a float.
     *
     * @return the time since last shot as a float
     *
     */
    @Override
    public float getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    /**
     * Sets the position of the player character before moving.
     *
     * @param previousPosition the previous position as a Vector 3
     * */
    public void setPreviousPosition(final Vector3 previousPosition) {
        if (previousPosition != null) {
            this.previousPosition = previousPosition;
        }
    }

    /**
     * Gets the current movement direction of the player character.
     *
     * @return the movement direction of the player as a Vector3
     * */
    public Vector3 getMovementDirection() {
        return this.getPosition().sub(previousPosition);
    }

    /**
     * Determines if another shot can be fired.
     *
     * @return a boolean true if a shot can be fired, false otherwise
     *
     */
    @Override
    public boolean canFire() {
        return getTimeSinceLastShot() >= projectile.getRefractoryPeriod() * fireRateModifier;
    }

    /**
     * Fires a projectile.
     *
     * @param targetPosition the position of the target to shoot at as a Vector3
     * @return a projectile as a Projectile
     * */
    @Override
    public Projectile fireProjectile(Vector3 targetPosition) {
        Vector3 direction = new Vector3(targetPosition).sub(this.getPosition()).nor();
        Projectile newProjectile = new Projectile(
            bufferProjectileStartPosition(direction),
            castSpeed, direction,
            assetManager,
            2.0F);
        this.timeSinceLastShot = 0;
        return newProjectile;
    }

    /**
     * Updates the time until the next shot can be fired.
     *
     * @param deltaTime the time elapsed since the last update
     *
     */
    @Override
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

    /**
     * Handles the death of the player character. TODO
     * */
    @Override
    public void handleDeath() {
        System.out.println("The game is over!");
    }
}
