package com.masonyoung24.bulwark.data;

import com.masonyoung24.bulwark.enums.EnemyType;


/**
 * Represents the base data to be saved for all Enemy objects.
 *
 * @author Mason Young
 * @version 2025
 * */
public class EnemySaveData {

    protected float xCoordinate;
    protected float yCoordinate;
    protected int hp;
    protected int maxHP;
    protected float speed;
    protected EnemyType enemyType;

    /**
     * Creates an object of type EnemySaveData.
     * */
    public EnemySaveData() {

    }

    /**
     * Returns the x-coordinate as a float.
     *
     * @return the x-coordinate as a float
     */
    public float getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Sets the x-coordinate.
     *
     * @param xCoordinate the x-coordinate as a float
     * */
    public void setXCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Returns the y-coordinate as a float.
     *
     * @return the y-coordinate as a float
     */
    public float getYCoordinate() {
        return yCoordinate;
    }

    /**
     * Sets the y-coordinate.
     *
     * @param yCoordinate the y-coordinate as a float
     */
    public void setYCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Returns the current HP as an int.
     *
     * @return the current HP as an int
     */
    public int getHP() {
        return hp;
    }

    /**
     * Sets the current HP.
     *
     * @param hp the current HP to set as an int
     */
    public void setHP(int hp) {
        this.hp = hp;
    }

    /**
     * Returns the movement speed as a float.
     *
     * @return the movement speed as a float
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the enemy type.
     *
     * @param enemyType the enemy type to set as an EnemyType
     */
    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }


    /**
     * Sets the maximum HP.
     *
     * @param maxHP the maximum HP to set as an int
     */
    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    /**
     * Sets the speed.
     *
     * @param speed the speed to set as a float
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
