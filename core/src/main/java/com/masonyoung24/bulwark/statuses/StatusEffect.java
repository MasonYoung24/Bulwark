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

    private final float duration;

    public StatusEffect(float duration) {
        if (duration > MINIMUM_DURATION) {
            this.duration = duration;
        } else {
            this.duration = DEFAULT_DURATION;
        }
    }

    /**
     * Returns true if the status effect is finished, otherwise false.
     *
     * @return true if the status effect is finished, otherwise false as a boolean
     * */
    public boolean isFinished() {
        return duration <= MINIMUM_DURATION;
    }
}
