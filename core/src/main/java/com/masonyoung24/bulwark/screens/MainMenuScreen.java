package com.masonyoung24.bulwark.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.masonyoung24.bulwark.Bulwark;
import com.masonyoung24.bulwark.resources.Assets;

public class MainMenuScreen implements Screen {

    private static final float START_BUTTON_SCALE_MODIFIER = 0.2f;

    private final Bulwark game;
    private Stage stage;
    private ImageButton startButton;

    public MainMenuScreen(Bulwark game) {
        this.game = game;
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), game.getSpriteBatch());

        Texture backgroundTexture = game.assets.getTexture(Assets.MAIN_MENU_BACKGROUND);
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Texture startButtonTexture = game.assets.getTexture(Assets.START_BUTTON);
        TextureRegionDrawable drawable = new TextureRegionDrawable(startButtonTexture);

        this.startButton = new ImageButton(drawable);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.setDebug(true);

        Cell<ImageButton> startButtonCell = table.add(startButton).
            width(startButtonTexture.getWidth() * START_BUTTON_SCALE_MODIFIER)
            .height(startButtonTexture.getHeight() * START_BUTTON_SCALE_MODIFIER);
        startButtonCell.padTop(300f);
        stage.addActor(table);


        startButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                startButtonCell.width(startButtonTexture.getWidth() * 0.21f);
                startButtonCell.height(startButtonTexture.getHeight() * 0.21f);
                table.invalidateHierarchy();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                startButtonCell.width(startButtonTexture.getWidth() * START_BUTTON_SCALE_MODIFIER);
                startButtonCell.height(startButtonTexture.getHeight() * START_BUTTON_SCALE_MODIFIER);
                table.invalidateHierarchy();
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        // Enable input to be processed for this stage
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        // Update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {
    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {
    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}

