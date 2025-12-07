package com.masonyoung24.bulwark.data;

import java.util.Objects;

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

    /**
     * Returns a String representation of this PlayerSaveData.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("PlayerSaveData{");
        stringBuilder.append("xCoordinate=").append(xCoordinate);
        stringBuilder.append(", yCoordinate=").append(yCoordinate);
        stringBuilder.append(", hp=").append(hp);
        stringBuilder.append(", speed=").append(speed);
        stringBuilder.append(", isAlive=").append(isAlive);
        stringBuilder.append(", timeSinceLastShot=").append(timeSinceLastShot);
        stringBuilder.append(", fireRateModifier=").append(fireRateModifier);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /**
     * Compares this PlayerSaveData to another Object for equality.
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
        PlayerSaveData that = (PlayerSaveData) object;
        return Float.compare(xCoordinate, that.xCoordinate) == 0
            && Float.compare(yCoordinate, that.yCoordinate) == 0
            && hp == that.hp
            && Float.compare(speed, that.speed) == 0
            && isAlive == that.isAlive
            && Float.compare(timeSinceLastShot, that.timeSinceLastShot) == 0
            && Float.compare(fireRateModifier, that.fireRateModifier) == 0;
    }

    /**
     * Returns a hash code for this PlayerSaveData.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(
            xCoordinate,
            yCoordinate,
            hp,
            speed,
            isAlive,
            timeSinceLastShot,
            fireRateModifier
        );
    }
}
