package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.math.Vector3;

public interface Shooter {
    /**
     * Gets the time since last shot as a float.
     *
     * @return the time since last shot as a float
     * */
    float getTimeSinceLastShot();

    /**
     * Determines if another shot can be fired.
     *
     * @return a boolean true if a shot can be fired, false otherwise
     * */
    boolean canFire();

    /**
     * Fires a projectile.
     *
     * @param targetPosition the position of the target to shoot at as a Vector3
     * @return a projectile as a Projectile
     * */
    Projectile fireProjectile(Vector3 targetPosition);

    /**
     * Updates the time until the next shot can be fired.
     *
     * @param deltaTime the time elapsed since the last update
     * */
    void updateTimeSinceLastShot(float deltaTime);
}
