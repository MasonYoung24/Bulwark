package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.resources.Assets;

/**
 * Represents a projectile that can be fired.
 *
 * @author Mason Young
 * @version 2025
 **/
public class Projectile {
    public static final int SPRITE_SHEET_ROWS = 26;
    public static final int SPRITE_SHEET_COLUMNS = 11;
    public static final int SPRITESHEET_TARGET_ROW = 1;
    public static final int SPRITE_SHEET_TARGET_COLUMN = 1;
    private static final int FRAME_COLUMNS = 5;
    private static final int FRAME_ROWS = 1;
    private static final float FRAME_DURATION = 0.1f;
    private static final int FIRST_FRAME_INDEX = 0;
    private static final int DESTINATION_POSITION = 0;

    /**
     * The default speed of a projectile
     * */
    private static final float DEFAULT_SPEED = 300f;
    /**
     * The default refractory period of a projectile
     * */
    private static final float DEFAULT_REFRACTORY_PERIOD = 2.0f;
    private static final float START_ANIMATION_TIME = 0.0f;

    // animation states
    private Animation<TextureRegion> animation;
    private TextureRegion currentFrame;
    private float animationTime;


    private final Sprite sprite;
    private final float speed;
    private Vector3 position;
    private final Vector3 direction;
    private final float refractoryPeriod;
    private boolean isFriendly;

    public Projectile(AssetManager assetManager) {
        this(
            new Vector3(0, 0, 0),
            DEFAULT_SPEED,
            new Vector3(1, 0, 0),
            assetManager,
            DEFAULT_REFRACTORY_PERIOD
        );
        this.animationTime = START_ANIMATION_TIME;
        createAnimation(assetManager);
    }

    /**
     * Constructs an object of type Projectile.
     *
     * @param startPosition the starting position of the projectile as a Vector3
     * @param speed the speed of the projectile as a float
     * @param direction the travel direction of the projectile as a Vector3
     * @param assetManager the asset manager as an AssetManager
     * @param refractoryPeriod the refractory period of the projectile as a float
     * */
    public Projectile(Vector3 startPosition,
                      float speed,
                      Vector3 direction,
                      AssetManager assetManager,
                      float refractoryPeriod) {
        this.sprite = SpriteUtils.getSpriteFromSpriteSheet(assetManager.get(Assets.ITEMS_SPRITE_SHEET),
            SPRITESHEET_TARGET_ROW,
            SPRITE_SHEET_TARGET_COLUMN,
            SPRITE_SHEET_COLUMNS,
            SPRITE_SHEET_ROWS);
        this.position = startPosition;
        this.speed = speed;
        this.direction = direction;
        this.refractoryPeriod = refractoryPeriod;
        this.sprite.setPosition(position.x, position.y);
        this.isFriendly = true;
        createAnimation(assetManager);
        this.rotateProjectile();
    }

    /**
     * Returns the current position of the Projectile.
     * */
    public Vector3 getPosition() {
        return new Vector3(sprite.getX(), sprite.getY(), 0);
    }

    /**
     * Updates the position of the projectile by translating its sprite
     * */
    public void updatePosition(float deltaTime) {
        // update position
        sprite.translate(direction.x * speed, direction.y * speed);

        // update animation
        animationTime += deltaTime;
        currentFrame = animation.getKeyFrame(animationTime);
        sprite.setRegion(currentFrame);
    }

    /**
     * Rotates the projectile depending on its fired direction.
     * */
    public void rotateProjectile() {
        float angleDeg = (float) Math.toDegrees(Math.atan2(direction.y, direction.x));
        this.sprite.setRotation(angleDeg);
    }

    /**
     * Returns the refractory period of the projectile as a float
     *
     * @return the refractory period of the projectile as a float
     *  */
    public float getRefractoryPeriod() {
        return this.refractoryPeriod;
    }

    /**
     * Returns the bounding box of the Projectile.
     *
     * @return the bounding box of the Projectile as a Rectangle
     * */
    public Rectangle getBoundingBox() {
        return this.sprite.getBoundingRectangle();
    }

    /**
     * Returns true if the Projectile is friendly to the player character, otherwise false.
     *
     * @return true if the Projectile is friendly to the player character, otherwise false as a boolean
     */
    public boolean getIsFriendly() {
        return this.isFriendly;
    }

    /**
     * Sets the IsFriendly state of the Projectile.
     * */
    public void setIsFriendly(boolean isFriendly) {
        this.isFriendly = isFriendly;
    }

    /**
     * Creates the projectile animation.
     *
     * @param assetManager the game asset manager
     * */
    public void createAnimation(AssetManager assetManager) {

        Texture projectileTexture = assetManager.get(Assets.FIRE_BALL_PROJECTILE);

        int frameWidth = projectileTexture.getWidth() / FRAME_COLUMNS;
        int frameHeight = projectileTexture.getHeight() / FRAME_ROWS;

        // Split into individual frames
        TextureRegion[][] splitFrames = TextureRegion.split(projectileTexture, frameWidth, frameHeight);

        // Extract first row frames
        TextureRegion[] animationFrames = new TextureRegion[FRAME_COLUMNS];
        System.arraycopy( // compiler suggestion
            splitFrames[FIRST_FRAME_INDEX],
            FIRST_FRAME_INDEX,
            animationFrames,
            DESTINATION_POSITION,
            FRAME_COLUMNS);

        // Create animation
        animation = new Animation<>(FRAME_DURATION, animationFrames);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        // Initial frame
        currentFrame = animationFrames[FIRST_FRAME_INDEX];
        sprite.setRegion(currentFrame);

        sprite.setSize(
            currentFrame.getRegionWidth(),
            currentFrame.getRegionHeight()
        );
    }

    /**
     * Draws the Projectile.
     *
     * @param batch the game's sprite batch as a SpriteBatch
     * */
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
