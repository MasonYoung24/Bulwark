package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.resources.Assets;

/**
 * Represents a projectile that can be fired.
 *
 * @author Mason Young
 * @version 2025
 **/
public class Projectile {
    public static final int SPRITE_SHEET_ROWS = 26;
    public static final int SPRITE_SHEET_COLUMNS = 11;
    public static final int SPRITESHEET_TARGET_ROW = 1;
    public static final int SPRITE_SHEET_TARGET_COLUMN = 1;
    /**
     * The default speed of a projectile
     * */
    private static final float DEFAULT_SPEED = 300f;
    /**
     * The default refractory period of a projectile
     * */
    private static final float DEFAULT_REFRACTORY_PERIOD = 1.0f;

    private final Sprite sprite;
    private final float speed;
    private Vector3 position;
    private final Vector3 direction;
    private final float refractoryPeriod;

    public Projectile(AssetManager assetManager) {
        this(
            new Vector3(0, 0, 0),               // startPosition
            DEFAULT_SPEED,                      // speed
            new Vector3(1, 0, 0),               // direction (default right)
            assetManager,                       // assetManager
            DEFAULT_REFRACTORY_PERIOD           // refractoryPeriod
        );
    }

    /**
     * Constructs an object of type Projectile.
     *
     * @param startPosition the starting position of the projectile as a Vector3
     * @param speed the speed of the projectile as a float
     * @param direction the travel direction of the projectile as a Vector3
     * @param assetManager the asset manager as an AssetManager
     * @param refractoryPeriod the refractory period of the projectile as a float
     * */
    public Projectile(Vector3 startPosition,
                      float speed,
                      Vector3 direction,
                      AssetManager assetManager,
                      float refractoryPeriod) {
        this.sprite = SpriteUtils.getSpriteFromSpriteSheet(assetManager.get(Assets.ITEMS_SPRITE_SHEET),
            SPRITESHEET_TARGET_ROW,
            SPRITE_SHEET_TARGET_COLUMN,
            SPRITE_SHEET_COLUMNS,
            SPRITE_SHEET_ROWS);
        this.position = startPosition;
        this.speed = speed;
        this.direction = direction;
        this.refractoryPeriod = refractoryPeriod;
        this.sprite.setPosition(position.x, position.y);
    }

    /**
     * Returns the current position of the Projectile.
     * */
    public Vector3 getPosition() {
        return new Vector3(sprite.getX(), sprite.getY(), 0);
    }

    /**
     * Updates the position of the projectile by translating its sprite
     * */
    public void updatePosition() {
        sprite.translate(direction.x * speed, direction.y * speed);
    }

    /**
     * Returns the refractory period of the projectile as a float
     *
     * @return the refractory period of the projectile as a float
     *  */
    public float getRefractoryPeriod() {
        return this.refractoryPeriod;
    }

    /**
     * Returns the bounding box of the Projectile
     *
     * @return the bounding box of the Projectile as a Rectangle
     * */
    public Rectangle getBoundingBox() {
        return this.sprite.getBoundingRectangle();
    }

    /**
     * Draws the Projectile.
     *
     * @param batch the game's sprite batch as a SpriteBatch
     * */
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
