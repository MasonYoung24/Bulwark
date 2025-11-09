package com.masonyoung24.bulwark.objects.spells;

public abstract class Ability {
    private float damage;
    private float cooldown;
    private float timeSinceCast;
    private String name;

    public Ability() {

    }

    /**
     * Checks if the ability can be cast.
     * */
    public boolean checkIfExecutable() {
        return timeSinceCast >= cooldown;
    }

    /**
     * Executes the ability.
     * */
    abstract void execute();
}
