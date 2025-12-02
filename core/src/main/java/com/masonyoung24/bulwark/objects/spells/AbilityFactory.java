package com.masonyoung24.bulwark.objects.spells;

import com.masonyoung24.bulwark.enums.AbilityName;
import com.masonyoung24.bulwark.objects.Actor;

/**
 * Represents a factory to create abilities.
 *
 * @author Mason Young
 * @version 2025
 * */
public final class AbilityFactory {

    /*
     * Constructs an object of type AbilityFactory.
     * */
    private AbilityFactory() {
    }

    public static Ability createAbility(final AbilityName abilityName, final Actor actor) {

        switch (abilityName) {
            case FROSTWAVE: {
                float baseDamage = 25f;
                float baseCooldown = 3f;

                return new FrostWave(baseDamage, baseCooldown, actor.getPosition());
            }
            case BLINK: {
                float baseDamage = 25f;
                float baseCooldown = 3f;

                return new Blink(baseDamage, baseCooldown);
            }

            default:
                return null;
        }
    }
}

