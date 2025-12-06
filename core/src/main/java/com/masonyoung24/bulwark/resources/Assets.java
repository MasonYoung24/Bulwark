package com.masonyoung24.bulwark.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.awt.*;

/**
 * Represents a class for loading, accessing, and disposing of game assets.
 *
 * @author Mason Young
 * @version 2025
 * */
public class Assets {

    /**
     * The global AssetManager used to load and manage game assets.
     * */
    public final AssetManager manager = new AssetManager();

    // Sprites
    /**
     * File path to the monster sprite sheet.
     * */
    public static final String MONSTERS_SPRITE_SHEET = "sprites/MonstersSpriteSheet.png";

    /**
     * File path to the humanoid sprite sheet use for the player character sprite.
     * */
    public static final String HUMANS_SPRITE_SHEET = "sprites/HumansSpriteSheet.png";

    // Abilities
    /**
     * File path to the sprite sheet containing all frost ability frames.
     * */
    public static final String FROST_ABILITIES_SPRITE_SHEET = "sprites/abilities/frost/frostAbilities.png";

    /**
     * File path to the sprite used for the Blink ability.
     * */
    public static final String BLINK_ABILITY_SPRITE = "sprites/abilities/arcane/blink.png";

    // Projectiles
    /**
     * File path to the fireball projectile animation frames.
     * */
    public static final String FIRE_BALL_PROJECTILE = "sprites/projectiles/fireballProjectileFrames.png";

    // Main Menu
    /**
     * File path to the START button image used in the main menu.
     * */
    public static final String START_BUTTON = "MainMenu/startbutton.png";

    /**
     * File path to the LOAD button image used in the main menu.
     * */
    public static final String LOAD_BUTTON = "MainMenu/loadbutton.png";

    /**
     * File path to the background image displayed on the main menu.
     * */
    public static final String MAIN_MENU_BACKGROUND = "MainMenu/MainMenuBackground.png";

    //Pause Screen
    /**
     * File path to the SAVE button image used on the pause screen.
     * */
    public static final String SAVE_BUTTON = "pause/saveButton.png";

    /**
     * File path to the CONTINUE button image used on the pause screen.
     * */
    public static final String CONTINUE_BUTTON = "pause/continueButton.png";

    // Tilesets
    /**
     * File path to the main tileset used for map and level rendering.
     * */
    public static final String MAIN_TILESET = "maps/objects/main.tmx";

    /**
     * Loads all the game assets into the asset manager.
     * */
    public void load() {
        loadGameplayAssets();
    }

    /**
     * Returns a texture stores in the AssetManager.
     *
     * @param filename the filename of the texture as a String
     * @return the texture as a Texture
     * @throws IllegalArgumentException if a null or empty argument is passed
     * */
    public Texture getTexture(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Texture filename cannot be null or empty.");
        }
        return manager.get(filename, Texture.class);
    }

    /*
     *  Loads gameplay assets into the asset manager.
     * */
    private void loadGameplayAssets() {
        manager.load(MONSTERS_SPRITE_SHEET, Texture.class);
        manager.load(HUMANS_SPRITE_SHEET, Texture.class);
        manager.load(START_BUTTON, Texture.class);
        manager.load(LOAD_BUTTON, Texture.class);
        manager.load(MAIN_MENU_BACKGROUND, Texture.class);
        manager.load(FROST_ABILITIES_SPRITE_SHEET, Texture.class);
        manager.load(BLINK_ABILITY_SPRITE, Texture.class);
        manager.load(SAVE_BUTTON, Texture.class);
        manager.load(CONTINUE_BUTTON, Texture.class);
        manager.load(FIRE_BALL_PROJECTILE, Texture.class);

        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(MAIN_TILESET, TiledMap.class);
    }

    /**
     * Disposes of the assets loaded into the asset manager.
     * */
    public void dispose() {
        manager.dispose();
    }
}
