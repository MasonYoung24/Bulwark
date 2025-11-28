package com.masonyoung24.bulwark.objects.spells;

import com.masonyoung24.bulwark.objects.Actor;
import com.masonyoung24.bulwark.objects.spells.FrostWave;

public final class AbilityFactory {

    private AbilityFactory() {
    }

    public static Ability createAbility(String abilityName, Actor actor) {

        switch (abilityName) {
            case "FrostWave": {
                float baseDamage = 25f;
                float baseCooldown = 3f;

                return new FrostWave(baseDamage, baseCooldown, actor.getPosition());
            }
            case "Blink": {
                float baseDamage = 25f;
                float baseCooldown = 3f;

                return new Blink(baseDamage, baseCooldown);
            }

            default:
                return null;
        }
    }
}

