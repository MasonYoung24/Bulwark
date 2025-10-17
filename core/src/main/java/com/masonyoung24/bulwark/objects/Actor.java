package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Actor {
    private static final int SPRITE_SHEET_ROWS = 7;
    private static final int SPRITE_SHEET_COLUMNS = 7;
    private static final int SINGLE_FRAME_TEXTURE_REGION_LENGTH = 1;

    private final int healthPoints;
    protected final float speed;
    protected final Sprite sprite;

    public Actor(int healthPoints, float speed, Sprite sprite) {
        this.healthPoints = healthPoints;
        this.speed = speed;
        this.sprite = sprite;
    }

    protected static Sprite getSprite(final Texture spriteSheet) {
        TextureRegion[][] splitSpriteSheet = TextureRegion.split(
            spriteSheet,
            spriteSheet.getWidth() / SPRITE_SHEET_COLUMNS,
            spriteSheet.getHeight() / SPRITE_SHEET_ROWS);

        TextureRegion actorFrame = splitSpriteSheet[Actor.SPRITE_SHEET_ROWS][Actor.SPRITE_SHEET_COLUMNS];

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
