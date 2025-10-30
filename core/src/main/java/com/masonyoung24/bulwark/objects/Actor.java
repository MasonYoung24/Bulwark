package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.enums.Direction;

public class Actor {
    private static final int SPRITE_SHEET_ROWS = 7;
    private static final int SPRITE_SHEET_COLUMNS = 7;
    private static final int SINGLE_FRAME_TEXTURE_REGION_LENGTH = 1;
    private static final int STARTING_X_POSITION = 100;
    private static final int STARTING_Y_POSITION = 100;

    private final int healthPoints;
    protected final float speed;
    protected final Sprite sprite;
    private Direction directionFacing;
    private float positionX;
    private float positionY;

    public Actor(int healthPoints, float speed, Sprite sprite) {
        this.healthPoints = healthPoints;
        this.speed = speed;
        this.sprite = sprite;
        this.directionFacing = Direction.LEFT;
        this.sprite.setPosition(STARTING_X_POSITION, STARTING_Y_POSITION);
        this.sprite.setSize(64, 64);
    }

    /**
     * Returns the coordinate X and Y position of Actor as an array of floats.
     *
     * @return an array containing the X and Y position as floats.
     * */
    public Vector3 getPosition() {
        return new Vector3(sprite.getX(), sprite.getY(), 0);
    }

    /*
     * Creates a sprite from the passed sprite sheet.
     *
     * @param spriteSheet the sprite sheet that holds the Actor frame
     * @return the sprite of the Actor as a Sprite;
     * */
    protected static Sprite getSprite(final Texture spriteSheet, final int targetRow, final int targetColumn) {
        return SpriteUtils.getSprite(spriteSheet, targetRow, targetColumn, SPRITE_SHEET_COLUMNS, SPRITE_SHEET_ROWS);
    }

    /**
     * Draws the Actor's sprite.
     *
     * @param batch the game's sprite batch as a SpriteBatch
     * */
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void faceLeft() {
        if (directionFacing != Direction.LEFT) {
            this.directionFacing = Direction.LEFT;
            this.sprite.flip(true, false);
        }
    }

    public void faceRight() {
        if (directionFacing != Direction.RIGHT) {
            this.directionFacing = Direction.RIGHT;
            this.sprite.flip(true, false);
        }
    }

    /**
     * Moves the actor left.
     * */
    public void moveLeft() {
        float delta = Gdx.graphics.getDeltaTime();
        sprite.translateX(-speed * delta);
    }

    /**
     * Moves the actor right.
     * */
    public void moveRight() {
        float delta = Gdx.graphics.getDeltaTime();
        sprite.translateX(speed * delta);
    }

    /**
     * Moves the actor up.
     * */
    public void moveUp() {
        float delta = Gdx.graphics.getDeltaTime();
        sprite.translateY(speed * delta);
    }

    /**
     * Moves the actor down.
     * */
    public void moveDown() {
        float delta = Gdx.graphics.getDeltaTime();
        sprite.translateY(-speed * delta);
    }
}
