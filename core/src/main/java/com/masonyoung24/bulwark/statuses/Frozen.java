package com.masonyoung24.bulwark.statuses;

import com.masonyoung24.bulwark.objects.Actor;

public class Frozen extends StatusEffect {
    private final StatusEffectType statusEffectType;

    public Frozen(final float duration) {
        super(duration);
        statusEffectType = StatusEffectType.FROZEN;
    }

    /**
     * Modifies the actor according to the status effect.
     *
     * @param actor the actor to be modified
     *
     */
    @Override
    public void applyEffect(final Actor actor) {
        actor.setIsFrozen(true);
    }

    /**
     * Clears the effect from the Actor.
     *
     * @param actor the Actor to clear the effect on
     * */
    @Override
    public void clearEffect(final Actor actor) {
        actor.setIsFrozen(false);
    }

    /**
     * Returns the type of the status effect.
     *
     */
    @Override
    public StatusEffectType getType() {
        return this.statusEffectType;
    }
}
