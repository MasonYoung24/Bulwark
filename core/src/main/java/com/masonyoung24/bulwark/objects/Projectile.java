package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.resources.Assets;

public class Projectile {
    public static final int SPRITE_SHEET_ROWS = 26;
    public static final int SPRITE_SHEET_COLUMNS = 11;
    public static final int SPRITESHEET_TARGET_ROW = 1;
    public static final int SPRITE_SHEET_TARGET_COLUMN = 1;
    private final Sprite sprite;
    private final float speed;
    private Vector3 position;
    private final Vector3 direction;

    public Projectile(Vector3 startPosition, float speed, Vector3 direction, AssetManager assetManager) {
        this.sprite = SpriteUtils.getSprite(assetManager.get(Assets.ITEMS_SPRITE_SHEET),
            SPRITESHEET_TARGET_ROW,
            SPRITE_SHEET_TARGET_COLUMN,
            SPRITE_SHEET_COLUMNS,
            SPRITE_SHEET_ROWS);
        this.position = startPosition;
        this.speed = speed;
        this.direction = direction;
        this.sprite.setPosition(position.x, position.y);
    }

    public void updatePosition() {
        sprite.translate(direction.x, direction.y);
    }

    /**
     * Draws the Projectile.
     *
     * @param batch the game's sprite batch as a SpriteBatch
     * */
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    /**
     * Returns the current position of the Projectile.
     * */
    public Vector3 getPosition() {
        return new Vector3(sprite.getX(), sprite.getY(), 0);
    }
}
