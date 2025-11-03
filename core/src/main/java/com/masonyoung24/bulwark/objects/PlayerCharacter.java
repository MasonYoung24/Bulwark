package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.inputs.Inputs;
import com.masonyoung24.bulwark.resources.Assets;

public class PlayerCharacter extends Actor implements Shooter {
    private static final int CHARACTER_TARGET_ROW = 2;
    private static final int CHARACTER_TARGET_COLUMN = 3;
    public static final int BASE_HP = 3;
    public static final float BASE_CAST_SPEED = 4.0f;

    private final AssetManager assetManager;
    private final float castSpeed;


    public PlayerCharacter(int healthPoints,
                           float speed,
                           AssetManager assetManager) {
        super(healthPoints,
            speed,
            Actor.getSprite(assetManager.get(Assets.HUMANS_SPRITE_SHEET),
                CHARACTER_TARGET_ROW,
                CHARACTER_TARGET_COLUMN)
        );
        this.assetManager = assetManager;
        this.castSpeed = BASE_CAST_SPEED;
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
     * Sets the time since the last shot was fired.
     *
     * @param time the time since the last shot was fired.
     *
     */
    @Override
    public void setTimeSinceLastShot(float time) {

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

    @Override
    public Projectile fireProjectile(Vector3 targetPosition) {
        Vector3 direction = new Vector3(targetPosition).sub(this.getPosition()).nor();
        return new Projectile(this.getPosition(), castSpeed, direction, assetManager, 2.0F);
    }

    /**
     * Updates the time until the next shot can be fired.
     *
     * @param deltaTime the time elapsed since the last update
     *
     */
    @Override
    public void updateShootCooldown(float deltaTime) {

    }
}
