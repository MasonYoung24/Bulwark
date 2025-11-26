package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.statuses.StatusEffect;
import jdk.jshell.Snippet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Enemy extends Actor {
    public static final int SPRITE_SHEET_ROWS = 13;
    public static final int SPRITE_SHEET_COLUMNS = 12;

    private final ShapeRenderer shapeRenderer;
    private boolean isLiving;


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
        this.isLiving = true;
        shapeRenderer = new ShapeRenderer();
    }

    /**
     * Returns true if the enemy is alive, otherwise false.
     */
    public boolean getIsLiving() {
        return this.isLiving;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        sprite.draw(batch);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // outline
        shapeRenderer.setColor(Color.RED);

        Rectangle bounds = getSpriteBoundingBox();
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);

        shapeRenderer.end();
    }

    /**
     * Moves the Enemy.
     *
     * @param targetPosition the target position to move the Actor to as a Vector3
     * */
    public void move(Vector3 targetPosition) {
        Vector3 direction = new Vector3(targetPosition).sub(this.getPosition()).nor();
        this.sprite.translate(direction.x, direction.y);
    }

    /**
     * Handles the death of an Enemy by flagging it as dead.
     */
    @Override
    public void handleDeath() {
        this.isLiving = false;
    }
}
