package com.masonyoung24.bulwark.objects.spells;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.masonyoung24.bulwark.objects.Actor;
import com.masonyoung24.bulwark.statuses.Frozen;
import com.masonyoung24.bulwark.statuses.StatusEffect;

public class FrostWave extends Ability implements Collidable {
    private static final float MAX_RADIUS = 300f;
    private static final float SPEED = 150f;
    private static final float START_RADIUS = 0.0f;

    private float radius;
    private float maxRadius;
    private float speed;
    private boolean expired;
    private Texture texture;

    public FrostWave(final float damage,
                     final float cooldown) {
        super(damage, cooldown);
        this.radius = START_RADIUS;
        this.maxRadius = MAX_RADIUS;
        this.speed = SPEED;
        this.expired = false;
        texture = createTexture();
    }

    /**
     * Creates the FrostWave texture to be drawn.
     *
     * @return the texture of the FrostWave to be drawn as a Texture
     * */
    private Texture createTexture() {
        Pixmap pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pixmap.setColor(0f, 0.5f, 1f, 0.7f);
        pixmap.fillCircle(32, 32, 32);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    /**
     * Executes the ability.
     */
    @Override
    void execute(Actor actor) {

    }

    /**
     * Updates the state of the Ability.
     *
     */
    @Override
    public void update(float deltaTime) {
        this.radius += speed * deltaTime;
        if (radius >= maxRadius) {
            expired = true;
        }
    }

    /**
     * Returns true of the FrostWave has expired, otherwise, false
     * */
    public boolean getExpired() {
        return this.expired;
    }

    /**
     * Affects an Actor on collision
     *
     * @param actor the Actor that is being effected by the collision as an Actor
     *
     */
    @Override
    public void affect(final Actor actor) {
        StatusEffect frozenStatus = new Frozen(50.0f);
        actor.addStatusEffect(frozenStatus);
        actor.takeDamage();
    }

    public void render(SpriteBatch batch) {

    }
}
