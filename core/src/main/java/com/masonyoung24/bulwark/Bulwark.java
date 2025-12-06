package com.masonyoung24.bulwark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.masonyoung24.bulwark.resources.Assets;
import com.masonyoung24.bulwark.screens.MainMenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Bulwark extends Game {
    private static final float WORLD_WIDTH = 800f;
    private static final float WORLD_HEIGHT = 600f;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    public Assets assets;
    private ShapeRenderer shapeRenderer;
    private BitmapFont bitmapFont;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assets = new Assets();
        this.shapeRenderer = new ShapeRenderer();
        this.bitmapFont = new BitmapFont();

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //font has 15pt, but we need to scale it to our viewport. Scaling seems to make it fuzzy.
        bitmapFont.setUseIntegerPositions(false);
        bitmapFont.getData().setScale((viewport.getWorldHeight() / Gdx.graphics.getHeight() * 2));

        assets.load();
        assets.manager.finishLoading();

        setScreen(new MainMenuScreen(this));
    }

    /**
     * Renders the game.
     * */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Returns the game's sprite batch.
     *
     * @return the game's sprite batch as a SpriteBatch
     * */
    public SpriteBatch getSpriteBatch() {
        return this.batch;
    }

    /**
     * Returns the game's viewport.
     *
     * @return the game's viewport as a FitViewport
     * */
    public FitViewport getViewport() {
        return this.viewport;
    }

    /**
     * Returns the game's shape renderer.
     *
     * @return the game's shape renderer as a ShapeRenderer
     * */
    public ShapeRenderer getShapeRenderer() {
        return this.shapeRenderer;
    }

    /**
     * Returns the game's bitmap font.
     *
     * @return the game's bitmap font as a BitmapFont
     * */
    public BitmapFont getBitmapFont() {
        return this.bitmapFont;
    }

    /**
     * Returns the game's camera.
     *
     * @return the game's camera as an OrthographicCamera
     * */
    public OrthographicCamera getCamera() {
        return this.camera;
    }

    /**
     * Disposes of the game's resources.
     * */
    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
    }
}
