package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.enums.AbilityName;
import com.masonyoung24.bulwark.objects.spells.Blink;
import com.masonyoung24.bulwark.objects.spells.FrostWave;
import com.masonyoung24.bulwark.resources.Assets;

/**
 * Represents the controllable player character.
 *
 * @author Mason Young
 * @version 2025
 * */
public class PlayerCharacter extends Actor implements Shooter {
    private static final int CHARACTER_TARGET_ROW = 4;
    private static final int CHARACTER_TARGET_COLUMN = 1;
    private static final float BASE_CAST_SPEED = 4.0f;
    private static final float HALF_MULTIPLIER = 0.5f;
    private static final float BASE_FIRE_RATE_MODIFIER = 0.25f;
    private static final float COLLISION_IMMUNITY_DURATION = 1.0f;
    private static final boolean DEFAULT_IS_IMMUNE = false;
    private static final float IMMUNITY_TIMER_START_TIME = 0.0f;

    private final AssetManager assetManager;
    private boolean isAlive;
    private final float castSpeed;
    private final Projectile projectile;
    private float timeSinceLastShot = 0.0F;
    private final float fireRateModifier;
    private Vector3 previousPosition;
    private float immunityTimer;
    private boolean isImmune;


    /**
     * Constructs an object of type PlayerCharacter.
     *
     * @param healthPoints the number of health points that the player has as an int
     * @param speed the speed of the player character as a float
     * @param assetManager the asset manager of the game as an AssetManager
     * @param spawnPosition the initial position of the player as a Vector3
     * */
    public PlayerCharacter(int healthPoints,
                           float speed,
                           AssetManager assetManager,
                           Vector3 spawnPosition) {
        super(healthPoints,
            speed,
            Actor.getSprite(assetManager.get(Assets.HUMANS_SPRITE_SHEET),
                CHARACTER_TARGET_ROW,
                CHARACTER_TARGET_COLUMN),
            spawnPosition
        );
        this.projectile = new Projectile(assetManager);
        this.assetManager = assetManager;
        this.addAbility(AbilityName.BLINK, new Blink(0, 1.0f));
        this.addAbility(AbilityName.FROST_WAVE, new FrostWave(1, 5.0f, this.getPosition()));
        this.castSpeed = BASE_CAST_SPEED;
        this.fireRateModifier = BASE_FIRE_RATE_MODIFIER;
        this.isAlive = true;
        immunityTimer = IMMUNITY_TIMER_START_TIME;
        isImmune = DEFAULT_IS_IMMUNE;
    }

    /**
     * Gets the time since last shot as a float.
     *
     * @return the time since last shot as a float
     */
    @Override
    public float getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    /**
     * Returns the fire rate modifier of the PlayerCharacter.
     *
     * @return the fire rate modifier as a float
     */
    public float getFireRateModifier() {
        return fireRateModifier;
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
     * Returns true if the player character is alive, otherwise false.
     *
     * @return true if the player character is alive, otherwise false as a boolean
     * */
    public boolean getIsAlive() {
        return this.isAlive;
    }

    /**
     * Returns true if the player character is immune, otherwise false.
     *
     * @return true if the player character is immune, otherwise false as a boolean
     * */
    public boolean getIsImmune() {
        return this.isImmune;
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
    public Projectile fireProjectile(final Vector3 targetPosition) {
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
     * Updates the state of the player character.
     *
     * @param deltaTime the time since the last update
     * */
    public void update(final float deltaTime) {
        updateTimeSinceLastShot(deltaTime);
        updateTimeSinceCast(deltaTime);
        updateCollisionImmunity(deltaTime);
    }

    public void updateCollisionImmunity(final float deltaTime) {
        if (isImmune) {
            immunityTimer += deltaTime;
            if (immunityTimer >= COLLISION_IMMUNITY_DURATION) {
                immunityTimer = 0f;
                isImmune = false;
            }
        }
    }

    /**
     * Updates the time until the next shot can be fired.
     *
     * @param deltaTime the time elapsed since the last update
     *
     */
    @Override
    public void updateTimeSinceLastShot(final float deltaTime) {
        this.timeSinceLastShot += deltaTime;
    }

    /**
     * Buffers the starting position of a fired projectile to account for sprite size.
     *
     * @param projectileDirection the direction of the fired projectile as a Vector3
     * @return the buffered starting position of the projectile as a Vector3
     * */
    private Vector3 bufferProjectileStartPosition(final Vector3 projectileDirection) {
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
     * Handles the collision between an Enemy and the PlayerCharacter
     * */
    public void handleEnemyCollision() {
        if (!this.isImmune) {
            this.takeDamage();
        }
        this.isImmune = true;
    }

    /**
     * Handles the death of the player character.
     * */
    @Override
    public void handleDeath() {
        this.isAlive = false;
    }
}
