package com.masonyoung24.bulwark.Managers;

import com.masonyoung24.bulwark.objects.Projectile;

import java.util.ArrayList;

public class ProjectileManager {
    private ArrayList<Projectile> activeProjectiles;

    public ProjectileManager() {
        activeProjectiles = new ArrayList<>();
    }

    /**
     * Adds a new Projectile to an array of active projectiles.
     *
     * @param projectile a new projectile of type Projectile
     * */
    public void addProjectile(Projectile projectile) {
        activeProjectiles.add(projectile);
    }

}
