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
import com.masonyoung24.bulwark.Bulwark;
import com.masonyoung24.bulwark.resources.Assets;

/**
 * Represents the main menu screen.
 *
 * @author Mason Young
 * @version 2025
 * */
public class MainMenuScreen implements Screen {

    private static final float BUTTON_SCALE_MODIFIER = 0.2f;
    private static final float BUTTON_SCALE_MODIFIER_ENTER = 0.21f;

    private final Bulwark game;
    private Stage stage;

    /**
     * Constructs an object of type MainMenuScreen.
     *
     * @param game the main game as a Bulwark
     * */
    public MainMenuScreen(final Bulwark game) {
        this.game = game;
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        stage = new Stage(game.getViewport(), game.getSpriteBatch());

        Texture backgroundTexture = game.assets.getTexture(Assets.MAIN_MENU_BACKGROUND);
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Texture startButtonTexture = game.assets.getTexture(Assets.START_BUTTON);
        TextureRegionDrawable drawableStartButton = new TextureRegionDrawable(startButtonTexture);

        Texture loadButtonTexture = game.assets.getTexture(Assets.LOAD_BUTTON);
        TextureRegionDrawable drawableLoadButton = new TextureRegionDrawable(loadButtonTexture);

        ImageButton startButton = new ImageButton(drawableStartButton);
        ImageButton loadButton = new ImageButton(drawableLoadButton);

        Table table = new Table();
        table.setFillParent(true);
        table.bottom();

        Cell<ImageButton> startButtonCell = table.add(startButton).
            width(startButtonTexture.getWidth() * BUTTON_SCALE_MODIFIER)
            .height(startButtonTexture.getHeight() * BUTTON_SCALE_MODIFIER);

        Cell<ImageButton> loadButtonCell = table.add(loadButton).
            width(startButtonTexture.getWidth() * BUTTON_SCALE_MODIFIER)
            .height(startButtonTexture.getHeight() * BUTTON_SCALE_MODIFIER);

        stage.addActor(table);

        startButton.addListener(new ClickListener() {
            /**
             * Enlarges the button when entering it.
             *
             * @param event the input event as an InputEvent
             * @param xCoordinate the x-coordinate of the pointer as a float
             * @param yCoordinate the y-coordinate of the pointer as a float
             * @param pointer the pointer index for the mouse cursor as an int
             * @param fromActor the actor that the pointer was previously over as an Actor
             * */
            @Override
            public void enter(
                final InputEvent event,
                final float xCoordinate,
                final float yCoordinate,
                final int pointer,
                final Actor fromActor
            ) {
                startButtonCell.width(startButtonTexture.getWidth() * BUTTON_SCALE_MODIFIER_ENTER);
                startButtonCell.height(startButtonTexture.getHeight() * BUTTON_SCALE_MODIFIER_ENTER);
                table.invalidateHierarchy();
            }

            /**
             * Shrinks the button when exiting it.
             *
             * @param event the input event as an InputEvent
             * @param xCoordinate the x-coordinate of the pointer as a float
             * @param yCoordinate the y-coordinate of the pointer as a float
             * @param pointer the pointer index for the mouse cursor as an int
             * @param toActor the actor that the pointer is currently over as an Actor
             * */
            @Override
            public void exit(
                final InputEvent event,
                final float xCoordinate,
                final float yCoordinate,
                final int pointer,
                final Actor toActor
            ) {
                startButtonCell.width(startButtonTexture.getWidth() * BUTTON_SCALE_MODIFIER);
                startButtonCell.height(startButtonTexture.getHeight() * BUTTON_SCALE_MODIFIER);
                table.invalidateHierarchy();
            }

            /**
             * Starts a fresh game.
             *
             * @param event the input event as an InputEvent
             * @param xCoordinate the x-coordinate of the pointer as a float
             * @param yCoordinate the y-coordinate of the pointer as a float
             * */
            @Override
            public void clicked(
                final InputEvent event,
                final float xCoordinate,
                final float yCoordinate
            ) {
                game.setScreen(new GameScreen(game, false));
            }
        });

        loadButton.addListener(new ClickListener() {
            /**
             * Enlarges the button when entering it.
             *
             * @param event the input event as an InputEvent
             * @param xCoordinate the x-coordinate of the pointer as a float
             * @param yCoordinate the y-coordinate of the pointer as a float
             * @param pointer the pointer index for the mouse cursor as an int
             * @param fromActor the actor that the pointer was previously over as an Actor
             * */
            @Override
            public void enter(
                final InputEvent event,
                final float xCoordinate,
                final float yCoordinate,
                final int pointer,
                final Actor fromActor
            ) {
                loadButtonCell.width(startButtonTexture.getWidth() * BUTTON_SCALE_MODIFIER_ENTER);
                loadButtonCell.height(startButtonTexture.getHeight() * BUTTON_SCALE_MODIFIER_ENTER);
                table.invalidateHierarchy();
            }

            /**
             * Shrinks the button when exiting it.
             *
             * @param event the input event as an InputEvent
             * @param xCoordinate the x-coordinate of the pointer as a float
             * @param yCoordinate the y-coordinate of the pointer as a float
             * @param pointer the pointer index for the mouse cursor as an int
             * @param toActor the actor that the pointer is currently over as an Actor
             * */
            @Override
            public void exit(
                final InputEvent event,
                final float xCoordinate,
                final float yCoordinate,
                final int pointer,
                final Actor toActor
            ) {
                loadButtonCell.width(loadButtonTexture.getWidth() * BUTTON_SCALE_MODIFIER);
                loadButtonCell.height(loadButtonTexture.getHeight() * BUTTON_SCALE_MODIFIER);
                table.invalidateHierarchy();
            }

            /**
             * Loads and starts a saved game.
             *
             * @param event the input event as an InputEvent
             * @param xCoordinate the x-coordinate of the pointer as a float
             * @param yCoordinate the y-coordinate of the pointer as a float
             * */
            @Override
            public void clicked(
                final InputEvent event,
                final float xCoordinate,
                final float yCoordinate
            ) {
                game.setScreen(new GameScreen(game, true));
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
    public void render(final float delta) {
        ScreenUtils.clear(Color.BLACK);

        // Update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    /**
     * Resizes the game's viewport.
     *
     * @param width the new width of the viewport
     * @param height the new height of the viewport
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(final int width, final int height) {
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

