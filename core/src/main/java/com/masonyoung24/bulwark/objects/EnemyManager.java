package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.Managers.ProjectileManager;

import java.util.*;

public class EnemyManager {
    private final List<Enemy> activeEnemies;
    public static int enemiesSlain = 0;
    public static final Random RANDOM = new Random();
    public static final float MINIMUM_SPAWN_POSITION_X_COORDINATE = 0.0f;
    public static final float MINIMUM_SPAWN_POSITION_Y_COORDINATE = 0.0f;
    private static final float HALF_WIDTH_DIVISOR = 2.0f;
    private static final float OVERLAP_DIVISOR = 2.0f;
    private static final int MINIMUM_NUMBER_OF_ENEMIES_TO_SPAWN = 1;
    private static final int SPAWN_MULTIPLIER = 2;
    private static final float SPAWN_TIMER_START = 0.0f;
    private static final float BASE_SPAWN_INTERVAL = 2.0f;

    private final AssetManager assetManager;
    private float spawnTimer;

    public EnemyManager(final AssetManager assetManager) {
        activeEnemies = new ArrayList<>();
        this.assetManager = assetManager;
        this.spawnTimer = SPAWN_TIMER_START;
    }

    public List<Enemy> getActiveEnemies() {
        return activeEnemies;
    }

    /**
     * Returns the number of enemies that have been slain.
     *
     * @return the number of enemies that have been slain as an int
     * */
    public static int getEnemiesSlain() {
        return enemiesSlain;
    }

    /**
     * Handles the death of an enemy.
     * */
    public void handleEnemyDeath() {
        Iterator<Enemy> iterator = activeEnemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (!enemy.getIsLiving()) {
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
            enemy.move(playerPosition);
        }
    }

    /**
     * Fires a projectile as the target projectile if the Enemy can fire.
     *
     * @param targetPosition the target position to fire at as a Vector3
     * */
    public void updateEnemyFire(Vector3 targetPosition, float deltaTime) {
        for (Enemy enemy : activeEnemies) {
            if (enemy instanceof RangedEnemy) {
                RangedEnemy rangedEnemy = (RangedEnemy) enemy;
                ((RangedEnemy) enemy).updateTimeSinceLastShot(deltaTime);
                if (rangedEnemy.canFire()) {
                    Projectile projectile = rangedEnemy.fireProjectile(targetPosition);
                    ProjectileManager.getInstance().addProjectile(projectile);
                }
            }
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
     * */
    public void spawnEnemies(float viewportWidth, float viewportHeight, int minutesSurvived, float deltaTime) {
        spawnTimer += deltaTime;
        if (spawnTimer > BASE_SPAWN_INTERVAL) {
            int numberToSpawn = generateNumberOfEnemiesToSpawn(minutesSurvived);
            for (int i = 0; i < numberToSpawn; i++) {
                spawnEnemy(viewportWidth, viewportHeight);
            }
            spawnTimer = SPAWN_TIMER_START;
        }

    }

    /**
     * Randomly generates a number of enemies.
     *
     * @return the number of enemies randomly generated as an int
     * */
    private int generateNumberOfEnemiesToSpawn(int minutesSurvived) {
        Random random = new Random();
        int maximumNumberOfEnemiesToSpawn = MINIMUM_NUMBER_OF_ENEMIES_TO_SPAWN
            + (minutesSurvived * SPAWN_MULTIPLIER);
        return random.nextInt(
            maximumNumberOfEnemiesToSpawn
                - MINIMUM_NUMBER_OF_ENEMIES_TO_SPAWN + 1) + MINIMUM_NUMBER_OF_ENEMIES_TO_SPAWN;
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
        activeEnemies.add(new SkeletonWizard(
            3,
            3.0F,
            assetManager,
            determineSpawnPosition(viewportWidth, viewportHeight)));
    }

    /**
     * Detects and handles collisions between two balls.
     * <p>
     * On collision, the velocities of the balls are swapped, and
     * they are separated slightly to avoid sticking and ghost
     * collisions.
     *</p>
     *
     *
     *
     * */
    public void checkEnemyCollision() {
        for (Enemy enemy : activeEnemies) {
            for (Enemy otherEnemy : activeEnemies) {
                if (!otherEnemy.equals(enemy) && otherEnemy.getSpriteBoundingBox()
                    .overlaps(enemy.getSpriteBoundingBox())) {
                    handleEnemyCollision(enemy, otherEnemy);
                }
            }
        }
    }

    /*
     * Ensures that colliding enemies do not overlap each-other.
     *
     * @param enemy the first sprite as an Enemy
     * @param otherEnemy the second sprite as an Enemy
     * */
    private void handleEnemyCollision(Enemy enemy, Enemy otherEnemy) {
        // compute overlaps
        float overlap = getOverlap(enemy, otherEnemy);
        // Determines the distance between the sprites
        double midpointXSprite1 = enemy.getPosition().x + enemy.sprite.getWidth() / HALF_WIDTH_DIVISOR;
        double midpointYSprite1 = enemy.getPosition().y + enemy.sprite.getHeight() / HALF_WIDTH_DIVISOR;
        double midpointXSprite2 = otherEnemy.getPosition().x + otherEnemy.sprite.getWidth() / HALF_WIDTH_DIVISOR;
        double midpointYSprite2 = otherEnemy.getPosition().y + otherEnemy.sprite.getHeight() / HALF_WIDTH_DIVISOR;

        double midpointXDifference = midpointXSprite1 - midpointXSprite2;
        double midpointYDifference = midpointYSprite1 - midpointYSprite2;
        double distanceSquared = midpointXDifference * midpointXDifference
            + midpointYDifference * midpointYDifference;

        // Optional: Separate the balls slightly to prevent sticking/ghost collisions
        double distance = Math.sqrt(distanceSquared);
        // Normalize the collision vector
        double normalX = midpointXDifference / distance;
        double normalY = midpointYDifference / distance;

        // Move each ball half the overlap amount away from each other
        enemy.translate((float) (overlap / OVERLAP_DIVISOR * normalX),
            (float) (overlap / OVERLAP_DIVISOR * normalY));
        otherEnemy.translate((float) (-overlap / OVERLAP_DIVISOR * normalX),
            (float) (-overlap / OVERLAP_DIVISOR * normalY));
    }

    /*
     * Gets the smallest overlap between two enemies.
     *
     * @param enemy the first sprite as an Enemy
     * @param otherEnemy the second sprite as an Enemy
     * @return the smallest of the horizontal or vertical overlaps as a double
     * */
    private float getOverlap(final Enemy enemy, final Enemy otherEnemy) {
        float overlapLeft = (enemy.getPosition().x + enemy.getSpriteBoundingBox().getWidth())
            - otherEnemy.getPosition().x;
        float overlapRight = (otherEnemy.getPosition().x + otherEnemy.getSpriteBoundingBox().getWidth())
            - enemy.getPosition().x;
        float overlapTop = (enemy.getPosition().y + enemy.getSpriteBoundingBox().getHeight())
            - otherEnemy.getPosition().y;
        float overlapBottom = (otherEnemy.getPosition().y + otherEnemy.getSpriteBoundingBox().getHeight())
            - enemy.getPosition().y;

        // find the smallest overlap
        return Math.min(
            Math.min(overlapLeft, overlapRight),
            Math.min(overlapTop, overlapBottom)
        );
    }
}
