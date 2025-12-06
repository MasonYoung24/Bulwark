package com.masonyoung24.bulwark.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}
