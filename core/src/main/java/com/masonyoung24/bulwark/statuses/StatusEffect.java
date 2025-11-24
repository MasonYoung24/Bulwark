package com.masonyoung24.bulwark.statuses;

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

    public StatusEffect(float duration) {
        if (duration > MINIMUM_DURATION) {
            this.duration = duration;
        } else {
            this.duration = DEFAULT_DURATION;
        }
    }
}
