package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    private List<Enemy> activeEnemies;
    public static int enemiesSlain = 0;

    private final AssetManager assetManager;

    public EnemyManager(final AssetManager assetManager) {
        activeEnemies = new ArrayList<>();
        this.assetManager = assetManager;
    }

    /**
     * Handles the death of an enemy.
     *
     * @param slainEnemy the Enemy who has been slain
     * */
    public void handleEnemyDeath(Enemy slainEnemy) {
        removeEnemy(slainEnemy);
        incrementEnemiesSlain();
    }

    /**
     * The enemy to remove from the list of active enemies.
     *
     * @param enemy the enemy to remove from the active enemies
     * */
    private void removeEnemy(Enemy enemy) {
        activeEnemies.remove(enemy);
    }

    /**
     * Increments the number of enemies slain by the player character.
     * */
    private void incrementEnemiesSlain() {
        enemiesSlain++;
    }

    /**
     * Draws the enemies.
     *
     * @param batch the game's sprite batch as a SpriteBatch
     * */
    public void render(SpriteBatch batch) {
        for (Enemy enemy : activeEnemies) {
            enemy.render(batch);
        }
    }

    /**
     * Moves each enemy depending on the position of the player.
     *
     * @param playerPosition the position of the player
     * */
    public void updateEnemyPosition(Vector3 playerPosition) {
        for (Enemy enemy : activeEnemies) {
            enemy.move(playerPosition);
        }
    }
}
