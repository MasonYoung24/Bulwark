package com.masonyoung24.bulwark.objects.spells;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.objects.Actor;

public class Blink extends Ability {

    private static final float BASE_BLINK_DISTANCE = 150.0f;

    private final float distance;

    public Blink(final float damage,
                 final float cooldown) {
        super(damage, cooldown);
        this.distance = BASE_BLINK_DISTANCE;
    }

    /**
     * Executes the ability.
     *
     */
    @Override
    void execute(Actor actor) {
        Vector3 movementDirectionNormalized = actor.getMovementDirection().nor();
        actor.sprite.translate(
            movementDirectionNormalized.x * distance,
            movementDirectionNormalized.y * distance);
    }

    /**
     * Updates the state of the Ability.
     *
     * @param deltaTime
     *
     */
    @Override
    public void update(final float deltaTime) {

    }

    /**
     * Renders the ability.
     *
     * @param batch
     *
     */
    @Override
    public void render(final SpriteBatch batch) {

    }

    /**
     * Disposes of the Ability.
     *
     */
    @Override
    public void dispose() {

    }
}
