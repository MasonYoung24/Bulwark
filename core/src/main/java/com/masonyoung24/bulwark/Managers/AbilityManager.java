package com.masonyoung24.bulwark.Managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.masonyoung24.bulwark.objects.spells.Ability;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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

    /**
     * Returns a String representation of this AbilityManager.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("AbilityManager{");
        stringBuilder.append("activeAbilities=").append(activeAbilities);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /**
     * Compares this AbilityManager to another Object for equality.
     *
     * @param object an Object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AbilityManager that = (AbilityManager) object;
        return Objects.equals(activeAbilities, that.activeAbilities);
    }

    /**
     * Returns a hash code for this AbilityManager.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(activeAbilities);
    }
}
