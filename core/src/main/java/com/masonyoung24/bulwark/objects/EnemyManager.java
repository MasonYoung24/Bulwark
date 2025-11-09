package com.masonyoung24.bulwark.objects;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    private List<Enemy> activeEnemies;
    public static int enemiesSlain = 0;

    public EnemyManager() {
        activeEnemies = new ArrayList<>();
    }

    /**
     * Increments the number of enemies slain by the player character.
     * */
    public void incrementEnemiesSlain() {
        enemiesSlain++;
    }
}
