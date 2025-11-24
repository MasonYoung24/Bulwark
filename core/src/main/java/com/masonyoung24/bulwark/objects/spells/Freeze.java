package com.masonyoung24.bulwark.objects.spells;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.masonyoung24.bulwark.objects.Actor;
import com.masonyoung24.bulwark.objects.Enemy;
import com.masonyoung24.bulwark.objects.EnemyManager;

public class Freeze extends Ability {
    private final float distance;

    public Freeze(final float damage,
                  final float cooldown) {
        super(damage, cooldown);
        this.distance = 100;

    }

    /**
     * Executes the ability.
     */
    @Override
    void execute(Actor actor) {
        Circle freezeArea = new Circle(actor.getPosition().x, actor.getPosition().y, distance);

        for (Enemy enemy : EnemyManager.getActiveEnemies()) {
            if (freezeArea.contains(enemy.getPosition())) {
                enemy.takeDamage();
                enemy.addStatus(new FrozenStatus(2f)); // Freeze for 2 seconds
            }
        }

    }
}
