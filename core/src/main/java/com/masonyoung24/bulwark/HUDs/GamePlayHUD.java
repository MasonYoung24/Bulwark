package com.masonyoung24.bulwark.HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Represents the main gameplay HUD.
 *
 * @author Mason Young
 * @version 2025
 * */
public class GamePlayHUD {
    private final static int GAME_START_TIME = 0;

    private Stage stage;
    private Viewport viewport;
    private Table table;

    private Label countdownLabel, timeLabel, linkLabel;
    private Label enemiesSlainLabel;
    private Label healthLabel;
    private Label healthValue;

    private float timeCount;
    private int seconds;
    private int minutes;
    private int hours;
    private int enemiesSlain;

    /**
     * Constructs an object of type GamePlayHUD.
     *
     * @param spriteBatch the SpriteBatch used to render the HUD
     * */
    public GamePlayHUD(final SpriteBatch spriteBatch, int playerHealth) {
        enemiesSlain = 0;
        seconds = GAME_START_TIME;
        minutes = GAME_START_TIME;
        hours = GAME_START_TIME;

        viewport = new FitViewport(
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight(),
            new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        countdownLabel = new Label(String.format("%d:%d:%03d", hours, minutes, seconds), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIMER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel = new Label("HEALTH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        linkLabel = new Label("ENEMIES SLAIN", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        enemiesSlainLabel = new Label(String.format("%d", enemiesSlain), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthValue = new Label(String.format("%d", playerHealth), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //define a table used to organize hud's labels
        this.table = new Table();
        table.top();
        table.setFillParent(true);

        //add labels to table, padding the top, and giving them all equal width with expandX
        table.add(linkLabel).expandX().padTop(10);
        table.add(healthLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(enemiesSlainLabel).expandX();
        table.add(healthValue);
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public void updateEnemiesSlain(final int enemiesSlain) {
        this.enemiesSlain = enemiesSlain;
        enemiesSlainLabel.setText(String.format("%d", this.enemiesSlain));
    }

    /**
     * Updates the time displayed on the game timer.
     *
     * @param delta time in seconds since the last frame
     * */
    public void updateGameTimer(final float delta) {
        this.timeCount += delta;
        seconds = (int) timeCount;

        if (seconds >= 60) {
            seconds = 0;
            timeCount = 0;
            minutes++;

        }
        if (minutes >= 60) {
            minutes = 0;
            hours++;
        }
        countdownLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    /**
     * Returns the game duration in minutes.
     *
     * @return the game duration in minutes as an int
     * */
    public int getMinutes() {
        return this.minutes;
    }

    /**
     * Draws the HUD.
     * */
    public void draw() {
        this.stage.draw();
    }
}
