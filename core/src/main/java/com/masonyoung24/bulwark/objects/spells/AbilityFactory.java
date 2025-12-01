package com.masonyoung24.bulwark.objects.spells;

import com.masonyoung24.bulwark.enums.AbilityName;
import com.masonyoung24.bulwark.objects.Actor;

public final class AbilityFactory {

    private AbilityFactory() {
    }

    public static Ability createAbility(AbilityName abilityName, Actor actor) {

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

