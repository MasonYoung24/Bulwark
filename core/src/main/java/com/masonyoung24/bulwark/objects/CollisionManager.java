package com.masonyoung24.bulwark.objects;

import com.masonyoung24.bulwark.Managers.ProjectileManager;

import javax.management.modelmbean.InvalidTargetObjectTypeException;
import java.util.Iterator;

public class CollisionManager {
    private EnemyManager enemyManager;
    private ProjectileManager projectileManager;
    private PlayerCharacter playerCharacter;

    /**
     * Constructs an object of type CollisionManager.
     *
     * @param enemyManager an enemy manager of type EnemyManager
     * @param projectileManager a projectile manager of type ProjectileManager
     * @param playerCharacter a player character of type PlayerCharacter
     * */
    public CollisionManager(final EnemyManager enemyManager,
                            final ProjectileManager projectileManager,
                            final PlayerCharacter playerCharacter) {
        if (enemyManager == null) {
            throw new IllegalArgumentException("Null enemyManager");
        }
        if (projectileManager == null) {
            throw new IllegalArgumentException("Null projectileManager");
        }
        if (playerCharacter == null) {
            throw new IllegalArgumentException("Null playerCharacter");
        }
        this.enemyManager = enemyManager;
        this.projectileManager = ProjectileManager.getInstance();
        this.playerCharacter = playerCharacter;
    }

    public void detectProjectileCollisionWithEnemy() {
        Iterator<Projectile> projectileIterator = projectileManager.getProjectiles().iterator();

        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            for (Enemy enemy : enemyManager.getActiveEnemies()) {
                if (projectile.getBoundingBox().overlaps(enemy.getSpriteBoundingBox())
                    && projectile.getIsFriendly()) {
                    projectileIterator.remove();
                    enemy.takeDamage();
                    break;
                }
            }
        }
    }

    /**
     * Handles the collision between an enemy projectile and the player character.
     * */
    public void handleProjectileCollisionWithPlayerCharacter() {
        Iterator<Projectile> projectileIterator = projectileManager.getProjectiles().iterator();

        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            if (projectile.getBoundingBox().overlaps(playerCharacter.getSpriteBoundingBox())
                && !projectile.getIsFriendly()) {
                projectileIterator.remove();
                playerCharacter.takeDamage();
                break;
            }
        }
    }
}
