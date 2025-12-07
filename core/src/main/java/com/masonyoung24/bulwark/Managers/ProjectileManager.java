package com.masonyoung24.bulwark.Managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.masonyoung24.bulwark.objects.Projectile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a ProjectileManager that manages the game's active projectiles.
 *
 * @author Mason Young
 * @version 2025
 * */
public class ProjectileManager {
    private static ProjectileManager instance;
    private final List<Projectile> activeProjectiles;

    /*
     * Constructs an object of type ProjectileManager.
     * */
    private ProjectileManager() {
        activeProjectiles = new ArrayList<>();
    }

    /**
     * Returns the shared instance of the ProjectileManager.
     *
     * @return the shared instance of the projectile manager as a Projectile Manager
     * */
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

    /**
     * Returns a String representation of this ProjectileManager.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("ProjectileManager{");
        stringBuilder.append("activeProjectiles=").append(activeProjectiles);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /**
     * Compares this ProjectileManager to another Object for equality.
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
        ProjectileManager that = (ProjectileManager) object;
        return Objects.equals(activeProjectiles, that.activeProjectiles);
    }

    /**
     * Returns a hash code for this ProjectileManager.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(activeProjectiles);
    }
}
