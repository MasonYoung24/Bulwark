package com.masonyoung24.bulwark.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.masonyoung24.bulwark.Bulwark;

import java.util.Objects;

/**
 * Represents the game over screen.
 *
 * @author Mason Young
 * @version 2025
 * */
public class GameOverScreen implements Screen {

    private static final int TITLE_PAD_TOP = 50;
    private static final int LABEL_PAD_TOP = 20;
    private static final String TEXT_YOU_LOSE = "YOU LOSE";
    private static final String TEXT_TIME_SURVIVED = "TIME SURVIVED";
    private static final String TEXT_ENEMIES_SLAIN = "ENEMIES SLAIN";

    private final Bulwark game;
    private final Stage stage;

    /**
     * Constructs an object of type GameOverScreen.
     *
     * @param game the game as a Bulwark
     * @param enemiesSlain the number of enemies slain as an int
     * @param timeSurvived the time survived as a String
     * */
    public GameOverScreen(final Bulwark game, int enemiesSlain, String timeSurvived) {
        this.game = game;
        stage = new Stage(game.getViewport(), game.getSpriteBatch());

        BitmapFont bitmapFont = game.getBitmapFont();

        Label youLose = new Label(TEXT_YOU_LOSE, new Label.LabelStyle(bitmapFont, Color.WHITE));

        Label timeSurvivedLabel = new Label(
            TEXT_TIME_SURVIVED,
            new Label.LabelStyle(bitmapFont, Color.WHITE));

        Label timeSurvivedValue = new Label(
            timeSurvived,
            new Label.LabelStyle(bitmapFont, Color.WHITE));

        Label enemiesSlainLabel = new Label(
            TEXT_ENEMIES_SLAIN,
            new Label.LabelStyle(bitmapFont, Color.WHITE));

        Label enemiesSlainCountLabel = new Label(
            String.format("%d", enemiesSlain),
            new Label.LabelStyle(bitmapFont,
                Color.WHITE));

        //define a table used to organize hud's labels
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //add labels to table, padding the top, and giving them all equal width with expandX
        table.add(youLose).expandX().center().padTop(TITLE_PAD_TOP);
        table.row();

        table.add(enemiesSlainLabel).expandX().center().padTop(LABEL_PAD_TOP);
        table.row();

        table.add(enemiesSlainCountLabel).expandX().center().padTop(LABEL_PAD_TOP);
        table.row();

        table.add(timeSurvivedLabel).expandX().center().padTop(LABEL_PAD_TOP);
        table.row();

        table.add(timeSurvivedValue).expandX().center().padTop(LABEL_PAD_TOP);

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

    /**
     * Returns a String representation of this GameOverScreen.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder;
        stringBuilder = new StringBuilder("GameOverScreen{");
        stringBuilder.append("game=").append(game);
        stringBuilder.append(", stage=").append(stage);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /**
     * Compares this GameOverScreen to another Object for equality.
     *
     * @param object an Object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) return false;
        GameOverScreen that = (GameOverScreen) object;
        return Objects.equals(game, that.game)
            && Objects.equals(stage, that.stage);
    }

    /**
     * Returns a hash code for this GameOverScreen.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(
            game,
            stage
        );
    }
}
