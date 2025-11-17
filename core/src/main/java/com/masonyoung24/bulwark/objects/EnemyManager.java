package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EnemyManager {
    private final List<Enemy> activeEnemies;
    public static int enemiesSlain = 0;
    public static final Random RANDOM = new Random();
    public static final float MINIMUM_SPAWN_POSITION_X_COORDINATE = 0.0f;
    public static final float MINIMUM_SPAWN_POSITION_Y_COORDINATE = 0.0f;

    private final AssetManager assetManager;

    public EnemyManager(final AssetManager assetManager) {
        activeEnemies = new ArrayList<>();
        this.assetManager = assetManager;
    }

    public List<Enemy> getActiveEnemies() {
        return activeEnemies;
    }

    /**
     * Handles the death of an enemy.
     * */
    public void handleEnemyDeath() {
        Iterator<Enemy> iterator = activeEnemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (enemy.getIsLiving()) {
                iterator.remove();
                incrementEnemiesSlain();
            }
        }
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
            enemy.moveToTarget(playerPosition);
        }
    }

    /**
     * Determines randomly the spawn position of the Enemy on the edge of the viewport.
     *
     * @param viewportWidth the width of the game screen viewport as a float
     * @param viewportHeight the height of the game screen viewport as a float
     * */
    private Vector3 determineSpawnPosition(float viewportWidth, float viewportHeight) {
        String[] sidesOfScreen = {"Top", "Bottom", "Left", "Right"};
        Vector3 spawnPosition;
        switch (sidesOfScreen[RANDOM.nextInt(sidesOfScreen.length)]) {
            case "Top": {
                spawnPosition = new Vector3(RANDOM.nextFloat(viewportWidth), viewportHeight, 0);
                break;
            }
            case "Bottom": {
                spawnPosition = new Vector3(RANDOM.nextFloat(viewportWidth), MINIMUM_SPAWN_POSITION_Y_COORDINATE, 0);
                break;
            }
            case "Left": {
                spawnPosition = new Vector3(MINIMUM_SPAWN_POSITION_X_COORDINATE, RANDOM.nextFloat(viewportHeight), 0);
                break;
            }
            case "Right": {
                spawnPosition = new Vector3(viewportWidth, RANDOM.nextFloat(viewportHeight), 0);
                break;
            }
            default: {
                spawnPosition = null;
                break;
            }
        }
        return spawnPosition;
    }

    /**
     * Spawns enemies and add them to the list of active enemies.
     *
     * @param viewportWidth the width of the viewport as a float
     * @param viewportHeight the height of the viewport as a float
     * @param enemiesToSpawn the number of enemies to spawn
     * */
    public void spawnEnemies(float viewportWidth, float viewportHeight, int enemiesToSpawn) {
        for (int i = 0; i < enemiesToSpawn; i++) {
            spawnEnemy(viewportWidth, viewportHeight);
        }
    }

    /*
     * Adds a new enemy to the list of active enemies.
     *
     * @param viewportWidth the width of the viewport as a float
     * @param viewportHeight the height of the viewport as a float
     * */
    private void spawnEnemy(float viewportWidth, float viewportHeight) {

        activeEnemies.add(new Goblin(
            3,
            3.0F,
            assetManager,
            determineSpawnPosition(viewportWidth, viewportHeight)));
        System.out.println("Enemy added");
        System.out.println("Active enemies: " + activeEnemies.size());
    }
}
