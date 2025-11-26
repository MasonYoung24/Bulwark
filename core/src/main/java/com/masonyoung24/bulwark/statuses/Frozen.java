package com.masonyoung24.bulwark.statuses;

public class Frozen extends StatusEffect
{
    private final StatusEffectType statusEffectType;

    public Frozen(final float duration) {
        super(duration);
        statusEffectType = StatusEffectType.FROZEN;
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
