package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    public Actor(int healthPoints, float speed, Sprite sprite) {
        this.healthPoints = healthPoints;
        this.speed = speed;
        this.sprite = sprite;
        this.directionFacing = Direction.LEFT;
        this.sprite.setPosition(STARTING_X_POSITION, STARTING_Y_POSITION);
        this.sprite.setSize(64, 64);
    }

    /*
     * Creates a sprite from the passed sprite sheet.
     *
     * @param spriteSheet the sprite sheet that holds the Actor frame
     * @return the sprite of the Actor as a TextureRegion[];
     * */
    protected static Sprite getSprite(final Texture spriteSheet, final int targetRow, final int targetColumn) {
        TextureRegion[][] splitSpriteSheet = TextureRegion.split(
            spriteSheet,
            spriteSheet.getWidth() / SPRITE_SHEET_COLUMNS,
            spriteSheet.getHeight() / SPRITE_SHEET_ROWS);

        TextureRegion actorFrame = splitSpriteSheet[targetRow][targetColumn];

        return new Sprite(actorFrame);
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
