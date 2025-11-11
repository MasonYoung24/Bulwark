package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class Enemy extends Actor {
    public static final int SPRITE_SHEET_ROWS = 13;
    public static final int SPRITE_SHEET_COLUMNS = 12;

    /**
     * Constructs an object of type Enemy.
     *
     * @param healthPoints the HP of the Enemy as an int
     * @param speed the movement speed of the Enemy as a float
     * @param sprite the sprite of the Enemy as a Sprite
     * @param spawnPosition the spawn position of the Enemy as a Vector3
     * */
    public Enemy(final int healthPoints,
                 final float speed,
                 Sprite sprite,
                 Vector3 spawnPosition) {
        super(healthPoints, speed, sprite, spawnPosition);
    }

    /**
     * Draws the Enemy.
     *
     * @param batch the game's sprite batch as a SpriteBatch
     * */
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
