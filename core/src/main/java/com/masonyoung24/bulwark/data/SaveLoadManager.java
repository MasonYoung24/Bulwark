package com.masonyoung24.bulwark.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.masonyoung24.bulwark.objects.Enemy;
import com.masonyoung24.bulwark.objects.EnemyFactory;
import com.masonyoung24.bulwark.objects.PlayerCharacter;

/**
 * Manages saving and loading the game's data states.
 *
 * @author Mason Young
 * @version 2025
 * */
public class SaveLoadManager {

    private static final String SAVE_FILE = "savegame.json";

    /**
     * Creates a PlayerSaveData object to store the player's saved state.
     *
     * @param player the player that the state will be stored for as a PlaterSaveData
     * @return the saved player state as a PlayerSaveData
     * */
    public static PlayerSaveData createPlayerSave(final PlayerCharacter player) {
        PlayerSaveData playerData = new PlayerSaveData();

        playerData.xCoordinate = player.getPosition().x;
        playerData.yCoordinate = player.getPosition().y;
        playerData.speed = player.getSpeed();

        playerData.hp = player.getCurrentHP();
        playerData.isAlive = player.getIsAlive();

        playerData.timeSinceLastShot = player.getTimeSinceLastShot();
        playerData.fireRateModifier = player.getFireRateModifier();

        return playerData;
    }


    /**
     * Restores the player character's state using its saved data.
     *
     * @param data the saved data to restore as an EnemySaveData
     * @param assets the games asset manager to use for creation as an AssetManager
     * @param spawnPosition the spawn position for the player character as a Vector3
     * @return the restored player character as a PlayerCharacter
     * */
    public static PlayerCharacter restorePlayer(
        final PlayerSaveData data,
        final AssetManager assets,
        final Vector3 spawnPosition
    ) {
        PlayerCharacter playerCharacter = new PlayerCharacter(
            data.hp,
            data.speed,
            assets,
            spawnPosition
        );

        playerCharacter.sprite.setPosition(data.xCoordinate, data.yCoordinate);
        playerCharacter.setCurrentHP(data.hp);

        playerCharacter.updateTimeSinceLastShot(data.timeSinceLastShot);

        return playerCharacter;
    }

    /**
     * Restores an enemies state using its saved data.
     *
     * @param data the saved data to restore as an EnemySaveData
     * @param assets the games asset manager to use for creation as an AssetManager
     * @return the restored enemy as an Enemy
     * */
    public static Enemy restoreEnemy(final EnemySaveData data, final AssetManager assets) {
        Enemy enemy = EnemyFactory.create(data.enemyType, assets);
        enemy.loadData(data);
        return enemy;
    }


    /**
     * Writes the games saved data to a json file to be later loaded in.
     *
     * @param saveData the game's saved data as a GameSaveData
     * */
    public static void saveGame(final GameSaveData saveData) {
        Json json = new Json();
        FileHandle file = Gdx.files.local(SAVE_FILE);
        file.writeString(json.prettyPrint(saveData), false);
    }

    /**
     * Loads the saved game data from the local save file if it exists.
     *
     * @return the saved game data as a GameSaveData
     * */
    public static GameSaveData loadGame() {
        FileHandle file = Gdx.files.local(SAVE_FILE);
        if (!file.exists()) {
            return null;
        }
        Json saveFileJson = new Json();
        return saveFileJson.fromJson(GameSaveData.class, file.readString());
    }

    /**
     * Checks whether a save file currently exists.
     *
     * @return true if the save file exists, otherwise false as a boolean
     * */
    public static boolean saveExists() {
        return Gdx.files.local(SAVE_FILE).exists();
    }
}
