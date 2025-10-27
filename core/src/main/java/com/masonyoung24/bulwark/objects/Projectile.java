package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.resources.Assets;

public class Projectile {
    public static final int SPRITESHEET_ROWS = 26;
    public static final int SPRITE_SHEET_COLUMNS = 11;
    public static final int SPRITESHEET_TARGET_ROW = 12;
    public static final int SPRITE_SHEET_TARGET_COLUMN = 6;
    private final Sprite sprite;
    private final float speed;
    private final Vector3 direction;

    public Projectile(float speed, Vector3 direction, AssetManager assetManager) {
        this.sprite = SpriteUtils.getSprite(assetManager.get(Assets.ITEMS_SPRITE_SHEET),
            SPRITESHEET_TARGET_ROW,
            SPRITE_SHEET_TARGET_COLUMN,
            SPRITESHEET_ROWS,
            SPRITE_SHEET_COLUMNS);
        this.speed = speed;
        this.direction = direction;
    }
}
