package com.masonyoung24.bulwark.data;

/**
 * Stores the saved state of the PlayerCharacter.
 *
 * @author Mason Young
 * @version 2025
 * */
public class PlayerSaveData {

    protected float xCoordinate;
    protected float yCoordinate;
    protected int hp;
    protected float speed;
    protected boolean isAlive;
    protected float timeSinceLastShot;
    protected float fireRateModifier;

    public PlayerSaveData() {

    }
}
