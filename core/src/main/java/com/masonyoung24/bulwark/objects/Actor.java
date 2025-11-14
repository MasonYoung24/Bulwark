package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.enums.Direction;
import com.masonyoung24.bulwark.objects.spells.Ability;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Actor {
    private static final int SPRITE_SHEET_ROWS = 7;
    private static final int SPRITE_SHEET_COLUMNS = 7;

    private int currentHP;
    private int maxHP;
    protected final float speed;
    protected final Sprite sprite;
    private Direction directionFacing;
    private Vector3 spawnPosition;
    private final List<Ability> abilities;

    public Actor(final int maxHP, final float speed, Sprite sprite, final Vector3 spawnPosition) {
        this.currentHP = maxHP;
        this.speed = speed;
        this.sprite = sprite;
        this.directionFacing = Direction.LEFT;
        this.sprite.setPosition(spawnPosition.x, spawnPosition.y);
        this.abilities = new ArrayList<>();
    }

    /**
     * Returns the coordinate X and Y position of Actor as an array of floats.
     *
     * @return an array containing the X and Y position as floats.
     * */
    public Vector3 getPosition() {
        return new Vector3(sprite.getX(), sprite.getY(), 0);
    }

    public Rectangle getSpriteBoundingBox() {
        return this.sprite.getBoundingRectangle();
    }

    /*
     * Creates a sprite from the passed sprite sheet.
     *
     * @param spriteSheet the sprite sheet that holds the Actor frame
     * @return the sprite of the Actor as a Sprite;
     * */
    protected static Sprite getSprite(final Texture spriteSheet, final int targetRow, final int targetColumn) {
        return SpriteUtils.getSpriteFromSpriteSheet(spriteSheet, targetRow, targetColumn, SPRITE_SHEET_COLUMNS, SPRITE_SHEET_ROWS);
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

    /**
     * Moves the Actor to a target position.
     *
     * @param targetPosition the target position to move the Actor to as a Vector3
     * */
    public void moveToTarget(Vector3 targetPosition) {
        Vector3 direction = new Vector3(targetPosition).sub(this.getPosition()).nor();
        this.sprite.translate(direction.x, direction.y);
    }

    /**
     * Removes an ability known to the Actor.
     *
     * @param ability the Ability to remove
     * */
    public void removeAbility(Ability ability) {
        if (ability != null) {
            abilities.remove(ability);
        }
    }

    /**
     * Adds an ability that the Actor can use.
     *
     * @param ability the Ability to add
     * */
    public void addAbility(Ability ability) {
        if (ability != null) {
            abilities.add(ability);
        }
    }

    /**
     * Executes an ability known to the actor.
     *
     * @param ability the Ability to execute
     * */
    public void executeAbility(Ability ability) {
        if (ability != null) {
//            ability.cast();
        }
    }

    /**
     * Reduces the current HP of the Actor by one.
     * */
    public void takeDamage() {
        this.currentHP = currentHP - 1;
        if (currentHP <= 0) {
            handleDeath();
        }
    }
}
