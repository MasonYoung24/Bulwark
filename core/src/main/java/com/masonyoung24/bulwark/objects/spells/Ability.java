package com.masonyoung24.bulwark.objects.spells;

public abstract class Ability {
    private float damage;
    private float cooldown;
    private float timeSinceCast;
    private String name;

    public Ability(final String name, final float damage, final float cooldown) {
        this.name = name;
        this.damage = damage;
        this.cooldown = cooldown;
        this.timeSinceCast = cooldown;
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
