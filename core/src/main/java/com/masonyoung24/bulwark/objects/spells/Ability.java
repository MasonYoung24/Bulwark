package com.masonyoung24.bulwark.objects.spells;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.masonyoung24.bulwark.objects.Actor;

/**
 * Represents an ability.
 *
 * @author Mason Young
 * @version 2025
 * */
public abstract class Ability {
    private final float damage;
    private final float cooldown;
    private float timeSinceCast;
    private boolean isExpired;

    /**
     * Constructs an object of type Ability.
     *
     * @param damage the damage done by the Ability as a float
     * @param cooldown the cooldown of the Ability as a float
     * */
    public Ability(final float damage, final float cooldown) {
        this.damage = damage;
        this.cooldown = cooldown;
        this.timeSinceCast = cooldown;
        this.isExpired = false;
    }

    /**
     * Casts the ability if it is executable.
     *
     * @param actor the Actor casting the Ability
     * */
    public void cast(Actor actor) {
        if (checkIfExecutable()) {
            execute(actor);
            this.timeSinceCast = 0.0f;
        }
    }

    public void handleCollision(Actor actor) {
    }

    /**
     * Checks if the ability can be cast.
     * */
    public boolean checkIfExecutable() {
        return timeSinceCast >= cooldown;
    }

    /**
     * Sets the time since the ability has been last cast.
     *
     * @param deltaTime the time since the ability has last been cast as a float
     * */
    public void setTimeSinceLastCast(float deltaTime) {
        if (deltaTime >= 0) {
            this.timeSinceCast = timeSinceCast + deltaTime;
        }
    }

    /**
     * Executes the ability.
     * */
    abstract void execute(Actor actor);

    /**
     * Updates the state of the Ability.
     * */
    public abstract void update(float deltaTime);

    /**
     * Renders the Ability.
     * */
    public abstract void render(SpriteBatch batch);

    /**
     * Disposes of the Ability.
     * */
    public abstract void dispose();

    /**
     * Returns true of the FrostWave has expired, otherwise, false
     * */
    public boolean getIsExpired() {
        return this.isExpired;
    }

    /**
     * Returns true of the FrostWave has expired, otherwise, false
     * */
    public void setIsExpired(Boolean expiredStatus) {
        this.isExpired = expiredStatus;
    }

    public void affect(Actor actor) {

    }
}
