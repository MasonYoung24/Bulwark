package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.masonyoung24.bulwark.enums.Direction;
import com.masonyoung24.bulwark.objects.spells.Ability;
import com.masonyoung24.bulwark.objects.spells.AbilityFactory;
import com.masonyoung24.bulwark.statuses.StatusEffect;

import java.util.*;

/**
 * Represents an Actor.
 *
 * @author Mason Young
 * @version 2025
 * */
public abstract class Actor {
    private static final int SPRITE_SHEET_ROWS = 7;
    private static final int SPRITE_SHEET_COLUMNS = 7;
    private static final float DAMAGE_TINT_TIME = 0.2f;
    private static final int HP_DECREMENT_VALUE = 1;
    private static final int MINIMUM_HP = 1;
    private static final float[] FROZEN_TINT_RGBA = {0.6f, 0.8f, 1.0f, 1.0f};

    private int currentHP;
    private int maxHP;
    protected final float speed;
    public final Sprite sprite;
    private Direction directionFacing;
    private final HashMap<String, Ability> abilities;
    private Timer.Task tintTask;
    private final Color originalSpriteColor;
    private Vector3 movementDirection;
    private float movementDirectionX;
    private float movementDirectionY;
    private List<StatusEffect> statusEffects;
    private boolean isFrozen;
    private boolean isDamageTintActive;

    /**
     * Constructs an object of type Actor.
     *
     * @param maxHP the maximum health of the Actor as an int
     * @param speed the movement speed of the Actor as a float
     * @param sprite the Sprite to use for the Actor as a Sprite
     * @param spawnPosition the spawn position of the Actor as a Vector3
     * */
    public Actor(final int maxHP, final float speed, Sprite sprite, final Vector3 spawnPosition) {
        this.currentHP = maxHP;
        this.speed = speed;
        this.sprite = sprite;
        this.directionFacing = Direction.LEFT;
        this.sprite.setPosition(spawnPosition.x, spawnPosition.y);
        this.abilities = new HashMap<>();
        this.originalSpriteColor = new Color(this.sprite.getColor());
        this.movementDirection = new Vector3();
        this.statusEffects = new ArrayList<>();
        this.isFrozen = false;
        this.isDamageTintActive = false;
    }

    /**
     * Returns the coordinate X and Y position of Actor as an array of floats.
     *
     * @return an array containing the X and Y position as floats.
     * */
    public Vector3 getPosition() {
        return new Vector3(sprite.getX(), sprite.getY(), 0);
    }

    /**
     * Returns the isFrozen status of the Actor.
     * */
    public boolean getIsFrozen() {
        return !this.isFrozen;
    }

    /**
     * Sets the frozen status of the Actor.
     * */
    public void setIsFrozen(boolean frozenStatus) {
        this.isFrozen = frozenStatus;
    }

    /**
     * Returns the bounding box of the Actor as a Rectangle.
     * */
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

    /**
     * Faces the Actor in the left direction.
     * */
    public void faceLeft() {
        if (directionFacing != Direction.LEFT) {
            this.directionFacing = Direction.LEFT;
            this.sprite.flip(true, false);
        }
    }

    /**
     * Faces the Actor in the right direction.
     * */
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
        movementDirectionX = -speed;
        movementDirection.set(movementDirectionX, movementDirectionY, 0).nor();
        sprite.translateX(-speed * delta);
    }

    /**
     * Moves the actor right.
     * */
    public void moveRight() {
        float delta = Gdx.graphics.getDeltaTime();
        movementDirectionX = speed;
        movementDirection.set(movementDirectionX, movementDirectionY, 0).nor();
        sprite.translateX(speed * delta);
    }

    /**
     * Moves the actor up.
     * */
    public void moveUp() {
        float delta = Gdx.graphics.getDeltaTime();
        movementDirectionY = speed;
        movementDirection.set(movementDirectionX, movementDirectionY, 0).nor();
        sprite.translateY(speed * delta);
    }

    /**
     * Moves the actor down.
     * */
    public void moveDown() {
        float delta = Gdx.graphics.getDeltaTime();
        movementDirectionY = -speed;
        this.movementDirection.set(movementDirectionX, movementDirectionY, 0).nor();
        sprite.translateY(-speed * delta);
    }

    /**
     * Translates the Actor's sprite by the given x and y amounts.
     *
     * @param xAmount the x amount to translate as a float
     * @param yAmount the y amount to translate as a float
     * */
    public void translate(float xAmount, float yAmount) {
        this.sprite.translate(xAmount, yAmount);
    }

    /**
     * Returns the movement direction of the Actor.
     *
     * @return the movement direction of the Actor as a Vector3
     * */
    public Vector3 getMovementDirection() {
        return movementDirection;
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
     * Returns the current HP of the Actor.
     *
     * @return the current HP of the Actor as an int
     * */
    public int getCurrentHP() {
        return this.currentHP;
    }

    /**
     * Adds an ability that the Actor can use.
     *
     * @param abilityName the name of the Ability to execute
     * @param ability the Ability to add
     * @throws IllegalArgumentException if provided ability name or Ability object are null
     * */
    public void addAbility(String abilityName, Ability ability) {
        if (ability == null) {
            throw new IllegalArgumentException("Ability cannot be null");
        }
        if (abilityName == null) {
            throw new IllegalArgumentException("Ability name cannot be null");
        }
        abilities.put(abilityName, ability);
    }

    /**
     * Executes an ability known to the actor.
     *
     * @param abilityName the Ability to execute
     * */
    public void executeAbility(String abilityName, AbilityManager abilityManager) {
        Ability abilityToExecute = abilities.get(abilityName);
        if (abilityToExecute != null) {
            abilityToExecute.cast(this);
            Ability newInstance = AbilityFactory.createAbility(abilityName, this);
            if (newInstance == null) {
                System.out.println("empty");
            }
            abilityManager.add(newInstance);
        }
    }

    /**
     * Applies a status effect to the enemy.
     * */
    public void addStatusEffect(StatusEffect statusEffect) {
        this.statusEffects.add(statusEffect);
        statusEffect.applyEffect(this);
    }

    /**
     * Updates any status effects applied to the enemy. If the duration
     * of the effect is finished, the effect will be removed.
     * */
    public void updateStatusEffect(float deltaTime) {
        if (statusEffects.isEmpty()) {
            return;
        }
        Iterator<StatusEffect> statusEffectIterator = statusEffects.iterator();
        while (statusEffectIterator.hasNext()) {
            StatusEffect statusEffect = statusEffectIterator.next();
            statusEffect.updateDuration(deltaTime);
            if (statusEffect.isFinished()) {
                statusEffectIterator.remove();
            }
        }
    }

    /**
     * Reduces the current HP of the Actor by one.
     * */
    public void takeDamage() {
        applyDamageTint();
        this.currentHP = currentHP - HP_DECREMENT_VALUE;
        if (currentHP < MINIMUM_HP) {
            handleDeath();
        }

        if (tintTask != null) {
            tintTask.cancel();
        }

        tintTask = new Timer.Task() {
            @Override
            public void run() {
                removeTint();
            }
        };

        Timer.schedule(tintTask, DAMAGE_TINT_TIME);
    }

    /*
     * Tints the Actor's sprite red to indicate that damage has been taken.
     * */
    private void applyDamageTint() {
        isDamageTintActive = true;
        updateSpriteColor();
    }

    /*
     * Removes and tint that has been applied to the Actor's sprite.
     * */
    private void removeTint() {
        isDamageTintActive = false;
        updateSpriteColor();
    }

    /*
     * Updates the colour of the Actor's sprite depending on the Actor's condition.
     * */
    private void updateSpriteColor() {
        if (isDamageTintActive) {
            this.sprite.setColor(Color.RED);
        } else if (isFrozen) {
            this.sprite.setColor(
                FROZEN_TINT_RGBA[0],
                FROZEN_TINT_RGBA[1],
                FROZEN_TINT_RGBA[2],
                FROZEN_TINT_RGBA[3]);
        } else {
            this.sprite.setColor(originalSpriteColor);
        }
    }

    /**
     * Handles the death of the Actor.
     * */
    public abstract void handleDeath();
}
