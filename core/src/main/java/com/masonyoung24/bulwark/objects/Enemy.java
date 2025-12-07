package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.Managers.EnemyManager;
import com.masonyoung24.bulwark.data.EnemySaveData;
import com.masonyoung24.bulwark.enums.EnemyType;

import java.util.Objects;

/**
 * Represents and Enemy.
 *
 * @author Mason Young
 * @version 2025
 * */
public abstract class Enemy extends Actor {
    public static final int SPRITE_SHEET_ROWS = 13;
    public static final int SPRITE_SHEET_COLUMNS = 12;
    private static final float LEFT_DIRECTION_BOUNDARY = 0f;

    private boolean isLiving;
    private final EnemyType enemyType;

    /**
     * Constructs an object of type Enemy.
     *
     * @param healthPoints the HP of the Enemy as an int
     * @param speed the movement speed of the Enemy as a float
     * @param sprite the sprite of the Enemy as a Sprite
     * @param spawnPosition the spawn position of the Enemy as a Vector3
     * @param enemyType the type of the enemy as an EnemyType
     * */
    public Enemy(final int healthPoints,
                 final float speed,
                 final Sprite sprite,
                 final Vector3 spawnPosition,
                 final EnemyType enemyType) {
        super(healthPoints, speed, sprite, spawnPosition);
        this.isLiving = true;
        this.enemyType = enemyType;
    }

    /**
     * Returns true if the enemy is alive, otherwise false.
     */
    public boolean getIsLiving() {
        return this.isLiving;
    }

    /**
     * Returns the type of the enemy as an EnemyType
     * */
    public EnemyType getEnemyType() {
        return this.enemyType;
    }

    /**
     * Renders the Enemy sprite.
     *
     * @param batch the game's sprite batch as a SpriteBatch used to render the Enemy
     * */
    public void render(final SpriteBatch batch) {
        sprite.draw(batch);
    }

    /**
     * Moves the Enemy.
     *
     * @param targetPosition the target position to move the Actor to as a Vector3
     * */
    public void move(final Vector3 targetPosition) {
        if (getIsFrozen()) {
            Vector3 direction = new Vector3(targetPosition).sub(this.getPosition()).nor();
            this.updateDirectionFacing(direction);
            this.sprite.translate(direction.x * this.getSpeed(), direction.y * this.getSpeed());
        }
    }

    /**
     * Updates the direction that the enemy is facing.
     *
     * @param direction the direction that the Enemy is moving as a Vector3
     * */
    public void updateDirectionFacing(final Vector3 direction) {
        if (direction.x < LEFT_DIRECTION_BOUNDARY) {
            this.faceLeft();
        } else {
            this.faceRight();
        }
    }

    /**
     * Handles the death of an Enemy by flagging it as dead.
     */
    @Override
    public void handleDeath() {
        this.isLiving = false;
    }

    /**
     * Handles the death of an Enemy by flagging it as dead.
     *
     * @param enemyManager an enemy manager as an EnemyManager
     */
    public abstract void handleDeath(final EnemyManager enemyManager);

    /**
     * Saves enemy data.
     *
     * @return the saved data as an EnemySaveData.
     * */
    public EnemySaveData saveData() {
        EnemySaveData data = new EnemySaveData();

        data.setXCoordinate(this.getPosition().x);
        data.setYCoordinate(this.getPosition().y);
        data.setHP(this.getCurrentHP());
        data.setMaxHP(this.getMaxHP());
        data.setEnemyType(this.getEnemyType());
        data.setSpeed(this.getSpeed());

        return data;
    }

    /**
     * Loads previously saved data into the object
     *
     * @param data the save data containing the state to be loaded
     * @throws IllegalArgumentException if the data provided is null
     * */
    public abstract <T extends EnemySaveData> void loadData(final T data);

    /**
     * Returns a String representation of this Enemy.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("Enemy{");
        stringBuilder.append(super.toString());
        stringBuilder.append(", isLiving=").append(isLiving);
        stringBuilder.append(", enemyType=").append(enemyType);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /**
     * Compares this Enemy to another Object for equality.
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
        Enemy enemy = (Enemy) object;
        return isLiving == enemy.isLiving
            && getEnemyType() == enemy.getEnemyType();
    }

    /**
     * Returns a hash code for this Enemy.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(
            isLiving,
            getEnemyType()
        );
    }
}
