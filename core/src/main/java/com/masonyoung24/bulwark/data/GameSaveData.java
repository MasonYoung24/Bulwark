package com.masonyoung24.bulwark.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents the entire game's saved data.
 *
 * @author Mason Young
 * @version 2025
 * */
public class GameSaveData {

    private PlayerSaveData playerSaveData;
    private final List<EnemySaveData> enemySaveData;

    /**
     * Creates an object of type GamesSaveData.
     * */
    public GameSaveData() {
        this.enemySaveData = new ArrayList<>();
    }

    /**
     * Returns the enemy save data.
     *
     * @return an unmodifiable list of enemy save data as a List of EnemySaveData
     * */
    public List<EnemySaveData> getEnemySaveData() {
        return Collections.unmodifiableList(this.enemySaveData);
    }

    /**
     * Adds a new EnemySaveData.
     *
     * @param newEnemySaveData the enemy data to save as an EnemySaveData
     * */
    public void addEnemySaveData(EnemySaveData newEnemySaveData) {
        if (newEnemySaveData != null) {
            this.enemySaveData.add(newEnemySaveData);
        }
    }

    /**
     * Returns the player character save data.
     * */
    public PlayerSaveData getPlayerSaveData() {
        return this.playerSaveData;
    }

    /**
     * Sets the player character save data.
     *
     * @param newPlayerSaveData the player character data to save as a PlayerSaveData
     * */
    public void setPlayerSaveData(PlayerSaveData newPlayerSaveData) {
        if (newPlayerSaveData != null) {
            this.playerSaveData = newPlayerSaveData;
        }
    }

    /**
     * Returns a String representation of this GameSaveData.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("GameSaveData{");
        stringBuilder.append("playerSaveData=").append(playerSaveData);
        stringBuilder.append(", enemySaveData=").append(enemySaveData);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /**
     * Compares this GameSaveData to another Object for equality.
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
        GameSaveData that = (GameSaveData) object;
        return Objects.equals(getPlayerSaveData(), that.getPlayerSaveData())
            && Objects.equals(getEnemySaveData(), that.getEnemySaveData());
    }

    /**
     * Returns a hash code for this GameSaveData.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(
            getPlayerSaveData(),
            getEnemySaveData()
        );
    }
}
