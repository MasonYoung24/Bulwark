package com.masonyoung24.bulwark.HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.masonyoung24.bulwark.enums.AbilityName;
import com.masonyoung24.bulwark.objects.SpriteUtils;
import com.masonyoung24.bulwark.resources.Assets;

import java.util.HashMap;

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

    // Abilities
    private Image frostWaveIcon;
    private HashMap<AbilityName, Image> abilityIcons;

    /**
     * Constructs an object of type GamePlayHUD.
     *
     * @param spriteBatch the SpriteBatch used to render the HUD
     * @param playerHealth the current health of the player character as an int
     * @param assetManager the asset manager of the game as an AssetManager
     * */
    public GamePlayHUD(final SpriteBatch spriteBatch, final int playerHealth, final AssetManager assetManager) {
        enemiesSlain = 0;
        seconds = GAME_START_TIME;
        minutes = GAME_START_TIME;
        hours = GAME_START_TIME;

        abilityIcons = new HashMap<>();

        viewport = new FitViewport(
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight(),
            new OrthographicCamera());

        SpriteBatch hudBatch = new SpriteBatch();
        stage = new Stage(viewport, hudBatch);

        countdownLabel = new Label(String.format("%d:%d:%03d", hours, minutes, seconds), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIMER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel = new Label("HEALTH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        linkLabel = new Label("ENEMIES SLAIN", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        enemiesSlainLabel = new Label(String.format("%d", enemiesSlain), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthValue = new Label(String.format("%d", playerHealth), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Sprite frostWaveSprite = SpriteUtils.getSpriteFromSpriteSheet(
            assetManager.get(Assets.FROST_ABILITIES_SPRITE_SHEET), 1, 0, 5, 5);
        Sprite blinkSprite = SpriteUtils.getSpriteFromSpriteSheet(
            assetManager.get(Assets.BLINK_ABILITY_SPRITE), 0, 0, 1, 1);


        this.frostWaveIcon = new Image(new TextureRegionDrawable(frostWaveSprite));
        abilityIcons.put(AbilityName.FROST_WAVE, frostWaveIcon);
        abilityIcons.put(AbilityName.BLINK, new Image(new TextureRegionDrawable(blinkSprite)));

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

        // New table for abilities
        Table abilitiesTable = new Table();
        abilitiesTable.bottom();
        abilitiesTable.setFillParent(true);

        abilitiesTable.add(abilityIcons.get(AbilityName.BLINK)).size(50, 50).pad(10);;
        abilitiesTable.add(frostWaveIcon).size(50, 50).pad(10);

        stage.addActor(abilitiesTable);
    }

    /**
     * Updates the displayed number of enemies slain.
     *
     * @param enemiesSlain the current number of enemies slain
     * */
    public void updateEnemiesSlain(final int enemiesSlain) {
        this.enemiesSlain = enemiesSlain;
        enemiesSlainLabel.setText(String.format("%d", this.enemiesSlain));
    }

    /**
     * Sets the FrostWave icons to indicate if the ability is ready or not.
     *
     * @param ready true if the ability is ready to be cast, otherwise false
     * */
    public void setAbilityReady(AbilityName abilityName, boolean ready) {
        if (ready) {
            abilityIcons.get(abilityName).setColor(1f, 1f, 1f, 1f);
        } else {
            abilityIcons.get(abilityName).setColor(0.3f, 0.3f, 0.3f, 1f);
        }
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
     * Updates the HUD to display the player's health.
     *
     * @param playerHealth the current health of the player as an int
     * */
    public void updatePlayerHealth(final int playerHealth) {
        healthValue.setText(String.format("%d", playerHealth));
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
