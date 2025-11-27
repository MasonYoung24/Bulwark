package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    /**
     * Renders each active ability.
     * */
    public void render(SpriteBatch spriteBatch) {
        if (activeAbilities != null && !activeAbilities.isEmpty()) {
            for (Ability ability : activeAbilities) {
                ability.render(spriteBatch);
            }
        }
    }

    /**
     * Adds a new ability to the list of active abilities.
     * */
    public void add(Ability ability) {
        activeAbilities.add(ability);
    }
}
