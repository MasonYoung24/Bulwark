package com.masonyoung24.bulwark;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.masonyoung24.bulwark.resources.Assets;
import com.masonyoung24.bulwark.screens.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Bulwark extends Game {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture image;
    public Assets assets;
    public GameScreen gameScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assets = new Assets();
        image = new Texture("libgdx.png");

        assets.load();
        assets.manager.finishLoading();

        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }

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

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        assets.dispose();
    }
}
