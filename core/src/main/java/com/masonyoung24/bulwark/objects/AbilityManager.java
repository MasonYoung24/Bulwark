package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.masonyoung24.bulwark.objects.spells.Ability;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents and ability manager.
 *
 * @author Mason Young
 * @version 2025
 * */
public class AbilityManager {

    public List<Ability> activeAbilities;

    /**
     * Creates an object of type AbilityManager.
     * */
    public AbilityManager() {
        this.activeAbilities = new ArrayList<>();
    }

    /**
     * Updates each active ability.
     * */
    public void update(float deltaTime) {
        Iterator<Ability> iterator = activeAbilities.iterator();

        while (iterator.hasNext()) {
            Ability ability = iterator.next();
            ability.update(deltaTime);
            if (ability.getIsExpired()) {
                ability.dispose();
                iterator.remove();
            }
        }
    }

    /**
     * Renders each active ability.
     * */
    public void render(final SpriteBatch spriteBatch) {
        if (activeAbilities != null && !activeAbilities.isEmpty()) {
            for (Ability ability : activeAbilities) {
                ability.render(spriteBatch);
            }
        }
    }

    /**
     * Adds a new ability to the list of active abilities.
     * */
    public void add(final Ability ability) {
        activeAbilities.add(ability);
    }
}
