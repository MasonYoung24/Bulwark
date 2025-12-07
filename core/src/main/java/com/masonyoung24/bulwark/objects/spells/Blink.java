package com.masonyoung24.bulwark.objects.spells;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.objects.Actor;

import java.util.Objects;

/**
 * Represents the teleportation ability Blink.
 *
 * @author Mason Young
 * @version 2025
 * */
public class Blink extends Ability {

    private static final float BASE_BLINK_DISTANCE = 150.0f;

    private final float distance;

    /**
     * Creates an object of type Blink.
     *
     * @param damage the damage done by the ability as a float
     * @param cooldown the cooldown of the ability as a float
     * */
    public Blink(final float damage,
                 final float cooldown) {
        super(damage, cooldown);
        this.distance = BASE_BLINK_DISTANCE;
    }

    /**
     * Executes the ability.
     *
     * @param actor the actor that is executing the ability
     */
    @Override
    void execute(final Actor actor) {
        Vector3 movementDirectionNormalized = actor.getMovementDirection().nor();
        actor.sprite.translate(
            movementDirectionNormalized.x * distance,
            movementDirectionNormalized.y * distance);
    }

    /**
     * Updates the state of the Ability.
     *
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void update(final float deltaTime) {

    }

    /**
     * Renders the ability.
     *
     * @param batch the game's sprite batch as a SpriteBatch
     *
     */
    @Override
    public void render(final SpriteBatch batch) {

    }

    /**
     * Disposes of the Ability.
     */
    @Override
    public void dispose() {

    }

    /**
     * Returns a String representation of this Blink.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("Blink{");
        stringBuilder.append(super.toString());
        stringBuilder.append(", distance=").append(distance);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /**
     * Compares this Blink to another Object for equality.
     *
     * @param object an Object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) return false;
        Blink blink = (Blink) object;
        return Float.compare(distance, blink.distance) == 0;
    }

    /**
     * Returns a hash code for this Blink.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(
            super.hashCode(),
            distance
        );
    }
}
