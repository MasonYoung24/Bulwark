package com.masonyoung24.bulwark.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.awt.*;



public class Assets {
    // asset manager
    public final AssetManager manager = new AssetManager();

    // Sprites
    public static final String ANIMALS_SPRITE_SHEET = "sprites/AnimalsSpriteSheet.png";
    public static final String MONSTERS_SPRITE_SHEET = "sprites/MonstersSpriteSheet.png";
    public static final String HUMANS_SPRITE_SHEET = "sprites/HumansSpriteSheet.png";
    public static final String ITEMS_SPRITE_SHEET = "sprites/ItemsSpriteSheet.png";

    // Abilities
    public static final String FROST_ABILITIES_SPRITE_SHEET = "sprites/abilities/frostAbilities.png";

    // HUD
    public static final String HEALTH_HEART = "sprites/health.png";
//    public final Texture health = new Texture(Gdx.files.internal("image.jpg"));

    // Main Menu
    public static final String START_BUTTON = "MainMenu/startbutton.png";
    public static final String MAIN_MENU_BACKGROUND = "MainMenu/MainMenuBackground.png";

    // Tilesets
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
        manager.load(ANIMALS_SPRITE_SHEET, Texture.class);
        manager.load(MONSTERS_SPRITE_SHEET, Texture.class);
        manager.load(HUMANS_SPRITE_SHEET, Texture.class);
        manager.load(ITEMS_SPRITE_SHEET, Texture.class);
        manager.load(HEALTH_HEART, Texture.class);
        manager.load(START_BUTTON, Texture.class);
        manager.load(MAIN_MENU_BACKGROUND, Texture.class);
        manager.load(FROST_ABILITIES_SPRITE_SHEET, Texture.class);

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
