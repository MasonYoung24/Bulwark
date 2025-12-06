package com.masonyoung24.bulwark.objects.spells;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.objects.Actor;
import com.masonyoung24.bulwark.statuses.Frozen;
import com.masonyoung24.bulwark.statuses.StatusEffect;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the FrostWave ability.
 *
 * @author Mason Young
 * @version 2025
 * */
public class FrostWave extends Ability {
    private static final float MAX_RADIUS = 300f;
    private static final float SPEED = 300f;
    private static final float START_RADIUS = 0.0f;

    //Pixmap constants
    private static final int PIXMAP_WIDTH = 64;
    private static final int PIXMAP_HEIGHT = 64;
    private static final float FROST_COLOR_RED = 0f;
    private static final float FROST_COLOR_GREEN = 0.5f;
    private static final float FROST_COLOR_BLUE = 1f;
    private static final float FROST_COLOR_ALPHA = 0.7f;

    private static final int CIRCLE_RADIUS = 32;
    private static final int CIRCLE_CENTER_X = 32;
    private static final int CIRCLE_CENTER_Y = 32;

    private float radius;
    private final float maxRadius;
    private final float speed;
    private final boolean expired;
    private final Texture texture;
    private final Vector3 pointOfExecution;
    private final Circle collisionCircle;
    private final Set<Actor> enemiesHit;

    /**
     * Creates an object of type FrostWave.
     *
     * @param damage the damage done as a float
     * @param cooldown the cooldown time a float
     * @param pointOfExecution the point where the FrostWave will commence as a Vector3
     * */
    public FrostWave(final float damage,
                     final float cooldown,
                     Vector3 pointOfExecution) {
        super(damage, cooldown);
        this.radius = START_RADIUS;
        this.maxRadius = MAX_RADIUS;
        this.speed = SPEED;
        this.expired = false;
        texture = createTexture();
        this.pointOfExecution = pointOfExecution;
        this.collisionCircle = new Circle();
        this.enemiesHit = new HashSet<>();
    }

    /**
     * Creates the FrostWave texture to be drawn.
     *
     * @return the texture of the FrostWave to be drawn as a Texture
     * */
    private Texture createTexture() {
        Pixmap pixmap = new Pixmap(PIXMAP_WIDTH, PIXMAP_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(FROST_COLOR_RED, FROST_COLOR_GREEN, FROST_COLOR_BLUE, FROST_COLOR_ALPHA);
        pixmap.drawCircle(CIRCLE_CENTER_X, CIRCLE_CENTER_Y, CIRCLE_RADIUS);
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
            this.setIsExpired(true);
        }
        collisionCircle.set(pointOfExecution.x, pointOfExecution.y, radius);
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
        StatusEffect frozenStatus = new Frozen(2.0f);
        actor.addStatusEffect(frozenStatus);
        actor.takeDamage();
    }

    /**
     * Handles the collision between the FrostWave and Actor.
     *
     * @param actor the actor to check for collision with
     * */
    @Override
    public void handleCollision(Actor actor) {
        Rectangle bounds = actor.getSpriteBoundingBox();
        if (Intersector.overlaps(this.collisionCircle, bounds)) {
            if (!enemiesHit.contains(actor)) {
                enemiesHit.add(actor);
                affect(actor);
            }
        }
    }

    /**
     * Renders the FrostWave.
     *
     * @param batch the sprite batch to use for rendering as a SpriteBatch
     * */
    public void render(SpriteBatch batch) {
        batch.draw(
            texture,
            this.pointOfExecution.x - radius,
            this.pointOfExecution.y - radius,
            radius * 2,
            radius * 2
        );
    }

    /**
     * Disposes of the Ability.
     */
    @Override
    public void dispose() {
        this.texture.dispose();
    }
}
