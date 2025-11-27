package com.masonyoung24.bulwark.objects.spells;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.masonyoung24.bulwark.objects.Actor;

public abstract class Ability {
    private float damage;
    private float cooldown;
    private float timeSinceCast;
    private float sprite;

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
    }

    /**
     * Casts the ability if it is executable.
     *
     * @param actor the Actor casting the Ability
     * */
    public void cast(Actor actor) {
        if (checkIfExecutable()) {
            execute(actor);
        }
    }

    /**
     * Checks if the ability can be cast.
     * */
    public boolean checkIfExecutable() {
        return timeSinceCast >= cooldown;
    }

    /**
     * Returns the damage done by the ability
     * */
    public float getDamage() {
        return this.damage;
    }

    /**
     * Executes the ability.
     * */
    abstract void execute(Actor actor);

    /**
     * Updates the state of the Ability.
     * */
    public abstract void update(float deltaTime);
}
