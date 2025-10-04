package com.masonyoung24.bulwark.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.awt.*;

public class Assets {
    // asset manager
    public final AssetManager manager = new AssetManager();

    // gameplay assets
    public static final String ANIMALS_SPRITE_SHEET = "sprites/AnimalsSpriteSheet.png";
    public static final String MONSTERS_SPRITE_SHEET = "sprites/MonstersSpriteSheet.png";
    public static final String HUMANS_SPRITE_SHEET = "sprites/HumansSpriteSheet.png";
    public static final String ITEMS_SPRITE_SHEET = "sprites/ItemsSpriteSheet.png";

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
    }

    /**
     * Disposes of the assets loaded into the asset manager.
     * */
    public void dispose() {
        manager.dispose();
    }
}
