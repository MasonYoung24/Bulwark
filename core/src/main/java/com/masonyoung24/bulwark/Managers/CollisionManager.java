package com.masonyoung24.bulwark.Managers;

import com.masonyoung24.bulwark.objects.Enemy;
import com.masonyoung24.bulwark.objects.PlayerCharacter;
import com.masonyoung24.bulwark.objects.Projectile;
import com.masonyoung24.bulwark.objects.spells.Ability;

import java.util.Iterator;
import java.util.List;

/**
 * Manages the collisions between different objects.
 *
 * @author Mason Young
 * @version 2025
 * */
public class CollisionManager {
    private final EnemyManager enemyManager;
    private final ProjectileManager projectileManager;
    private final PlayerCharacter playerCharacter;

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

    /**
     * Handles collisions between player fired projectiles and enemies.
     * */
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
     * Handles collisions between active abilities and enemies.
     * */
    public void detectAbilityCollisionWithEnemy(
        final List<Ability> activeAbilities,
        final List<Enemy> activeEnemies) {
        for (final Ability ability : activeAbilities) {
            for (Enemy enemy : activeEnemies) {
                ability.handleCollision(enemy);
            }
        }
    }

    public void detectEnemyCollisionWithPlayer(
        final PlayerCharacter playerCharacter,
        final List<Enemy> activeEnemies) {
        for (final Enemy enemy : activeEnemies) {
            if (
                playerCharacter.getSpriteBoundingBox().overlaps(enemy.getSpriteBoundingBox())
                    && !playerCharacter.getIsImmune()
            ) {
                playerCharacter.handleEnemyCollision();
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
