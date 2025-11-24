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

    //HUD
    public static final String HEALTH_HEART = "sprites/health.png";
//    public final Texture health = new Texture(Gdx.files.internal("image.jpg"));

    // Tilesets
    public static final String MAIN_TILESET = "maps/objects/main.tmx";

    /**
     * Loads all the game assets into the asset manager.
     * */
    public void load() {
        loadGameplayAssets();
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
