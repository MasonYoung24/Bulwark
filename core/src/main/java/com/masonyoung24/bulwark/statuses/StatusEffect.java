package com.masonyoung24.bulwark.statuses;

import com.masonyoung24.bulwark.objects.Actor;

/**
 * Represents a status effect.
 *
 * @author Mason Young
 * @version 2025
 * */
public abstract class StatusEffect {
    private static final float DEFAULT_DURATION = 2.0f;
    private static final float MINIMUM_DURATION = 0.0f;

    private float duration;
    private float timeSinceApplication;

    /**
     * Creates an object of type StatusEffect.
     *
     * @param duration the duration of the status effect as a float
     * */
    public StatusEffect(float duration) {
        if (duration > MINIMUM_DURATION) {
            this.duration = duration;
        } else {
            this.duration = DEFAULT_DURATION;
        }
        this.timeSinceApplication = MINIMUM_DURATION;
    }

    /**
     * Returns true if the status effect is finished, otherwise false.
     *
     * @return true if the status effect is finished, otherwise false as a boolean
     * */
    public boolean isFinished() {
        return timeSinceApplication >= duration;
    }

    /**
     * Updates the status effect's remaining duration.
     * */
    public void updateTimeSinceApplication(float deltaTime) {
        this.timeSinceApplication += deltaTime;
    }


    /**
     * Modifies the actor according to the status effect.
     * */
    public abstract void applyEffect(Actor actor);

    /**
     * Clears the effect from the Actor.
     *
     * @param actor the Actor to clear the effect on
     * */
    public abstract void clearEffect(Actor actor);

    /**
     * Returns the type of the status effect.
     * */
    public abstract StatusEffectType getType();
}
