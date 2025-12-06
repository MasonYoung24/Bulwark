package com.masonyoung24.bulwark.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.masonyoung24.bulwark.Bulwark;

public class GameOverScreen implements Screen {

    private final Bulwark game;
    private final Stage stage;
    private Label enemiesSlainCountLabel;

    public GameOverScreen(final Bulwark game, int enemiesSlain) {
        this.game = game;
        stage = new Stage(game.getViewport(), game.getSpriteBatch());

        BitmapFont bitmapFont = game.getBitmapFont();

        Label youLose = new Label("YOU LOSE", new Label.LabelStyle(bitmapFont, Color.WHITE));
        Label timeSurvivedLabel = new Label(
            "TIME SURVIVED",
            new Label.LabelStyle(bitmapFont, Color.WHITE));

        Label enemiesSlainLabel = new Label(
            "ENEMIES SLAIN",
            new Label.LabelStyle(bitmapFont, Color.WHITE));

        enemiesSlainCountLabel = new Label(
            String.format("%d", enemiesSlain),
            new Label.LabelStyle(bitmapFont,
                Color.WHITE));

        //define a table used to organize hud's labels
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //add labels to table, padding the top, and giving them all equal width with expandX
        table.add(youLose).expandX().center().padTop(50);
        table.row();

        table.add(enemiesSlainLabel).expandX().center().padTop(20);
        table.row();

        table.add(timeSurvivedLabel).expandX().center().padTop(20);
        table.row();

        table.add(enemiesSlainCountLabel).expandX().center().padTop(20);
        table.row();

        table.add(enemiesSlainCountLabel).expandX();

        stage.addActor(table);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
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
        game.getViewport().update(width, height, true);
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

    }
}
