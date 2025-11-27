package com.masonyoung24.bulwark.objects;

import com.masonyoung24.bulwark.objects.spells.Ability;

import java.util.ArrayList;
import java.util.List;

public class AbilityManager {

    private List<Ability> activeAbilities;

    public AbilityManager() {
        this.activeAbilities = new ArrayList<>();
    }

    /**
     * Updates each active ability.
     * */
    public void update() {
        if (activeAbilities != null && !activeAbilities.isEmpty()) {
            for (Ability ability : activeAbilities) {
                ability.update();
            }
        }
    }
}
