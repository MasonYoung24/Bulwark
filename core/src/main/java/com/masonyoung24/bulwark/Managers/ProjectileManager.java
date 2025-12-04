package com.masonyoung24.bulwark.Managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.masonyoung24.bulwark.objects.Projectile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectileManager {
    private static ProjectileManager instance;
    private List<Projectile> activeProjectiles;

    private ProjectileManager() {
        activeProjectiles = new ArrayList<>();
    }

    public static ProjectileManager getInstance() {
        if (instance == null) {
            instance = new ProjectileManager();
        }
        return instance;
    }

    /**
     * Adds a new Projectile to an array of active projectiles.
     *
     * @param projectile a new projectile of type Projectile
     * */
    public void addProjectile(Projectile projectile) {
        activeProjectiles.add(projectile);
    }

    /**
     * Draws the projectiles.
     *
     * @param batch the game's sprite batch as a SpriteBatch
     * */
    public void render(SpriteBatch batch) {
        for (Projectile projectile : activeProjectiles) {
            projectile.render(batch);
        }
    }

    /**
     * Updates the position of every active Projectile.
     * */
    public void updateProjectiles(
        float minBoundX,
        float maxBoundX,
        float minBoundY,
        float maxBoundY,
        float deltaTime) {
        ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile projectile : activeProjectiles) {
            projectile.updatePosition(deltaTime);
            if (projectile.getPosition().x < minBoundX || projectile.getPosition().x > maxBoundX ||
                projectile.getPosition().y < minBoundY || projectile.getPosition().y > maxBoundY) {
                projectilesToRemove.add(projectile);
            }
        }
        activeProjectiles.removeAll(projectilesToRemove);
    }

    /**
     * Returns an unmodifiable list of active Projectile objects.
     *
     * @return an unmodifiable list of active Projectile objects
     * */
    public List<Projectile> getProjectiles() {
        return activeProjectiles;
    }

    /**
     * Disposes of the active Projectiles.
     * */
    public void dispose() {
        activeProjectiles.clear();
        instance = null;
    }
}
