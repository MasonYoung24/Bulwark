package com.masonyoung24.bulwark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.masonyoung24.bulwark.Bulwark;
import com.masonyoung24.bulwark.HUDs.GamePlayHUD;
import com.masonyoung24.bulwark.Managers.AbilityManager;
import com.masonyoung24.bulwark.Managers.CollisionManager;
import com.masonyoung24.bulwark.Managers.EnemyManager;
import com.masonyoung24.bulwark.Managers.ProjectileManager;
import com.masonyoung24.bulwark.data.EnemySaveData;
import com.masonyoung24.bulwark.data.GameSaveData;
import com.masonyoung24.bulwark.data.SaveLoadManager;
import com.masonyoung24.bulwark.enums.AbilityName;
import com.masonyoung24.bulwark.objects.*;
import com.masonyoung24.bulwark.resources.Assets;

/**
 * Represents the main game screen.
 *
 * @author Mason Young
 * @version 2025
 * */
public class GameScreen implements Screen, InputProcessor {
    public static final float UNIT_SCALE = 1f / 32f;
    private static final float HALF_MODIFIER = 2f;

    private final Bulwark game;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final GamePlayHUD gamePlayHUD;
    private final PlayerCharacter playerCharacter;
    private final ProjectileManager projectileManager;
    private final EnemyManager enemyManager;
    private final CollisionManager collisionManager;
    private final AssetManager assetManager;
    private final AbilityManager abilityManager;
    private float autosaveTimer = 0;
    private boolean moveLeftKeyPressed = false;
    private boolean moveRightKeyPressed = false;
    private boolean isFiring = false;
    private boolean isPaused;
    private Sprite continueButton;
    private Sprite saveButton;

    /**
     * Creates an object of type GameScreen.
     *
     * @param game the main game class as a Bulwark
     * @param loadSave true if the game is loaded from a save, otherwise false as a boolean
     * */
    public GameScreen(Bulwark game, boolean loadSave) {
        this.game = game;
        this.assetManager = game.assets.manager;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(camera.viewportWidth / 2f, camera.viewportHeight / 2f);
        camera.update();
        this.mapRenderer = new OrthogonalTiledMapRenderer(
            null,
            this.game.getSpriteBatch());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        this.projectileManager = ProjectileManager.getInstance();
        this.enemyManager = new EnemyManager(assetManager);
        this.abilityManager = new AbilityManager();
        this.isPaused = false;

        if (loadSave && SaveLoadManager.saveExists()) {
            GameSaveData save = SaveLoadManager.loadGame();
            if (save != null) {
                this.playerCharacter = SaveLoadManager.restorePlayer(
                    save.getPlayerSaveData(),
                    assetManager,
                    getPlayerSpawnPosition()
                );
                System.out.println("Player created from save!");

                for (EnemySaveData enemySave : save.getEnemySaveData()) {
                    Enemy restoredEnemy = SaveLoadManager.restoreEnemy(
                        enemySave,
                        assetManager);
                    enemyManager.getActiveEnemies().add(restoredEnemy);
                }
            } else {
                this.playerCharacter = new PlayerCharacter(3, 100, assetManager, getPlayerSpawnPosition());
            }
        } else {
            this.playerCharacter = new PlayerCharacter(3, 100, assetManager, getPlayerSpawnPosition());
        }

        this.collisionManager = new CollisionManager(enemyManager, projectileManager, playerCharacter);

        createPauseButtons(viewport.getWorldWidth(), viewport.getWorldHeight());
        this.gamePlayHUD = new GamePlayHUD(playerCharacter.getCurrentHP(), assetManager);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        this.mapRenderer.setMap(assetManager.get(Assets.MAIN_TILESET));
    }

    @Override
    public void render(float deltaTime) {
        if (!this.isPaused) {
            update(deltaTime);
        }
        ScreenUtils.clear(Color.BLACK);
        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();

        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.getSpriteBatch().begin();
        this.updateDirectionPlayerFacing();
        projectileManager.render(game.getSpriteBatch());
        playerCharacter.render(game.getSpriteBatch());
        abilityManager.render(game.getSpriteBatch());
        enemyManager.render(game.getSpriteBatch());
        gamePlayHUD.draw();
        game.getSpriteBatch().end();

        if (this.isPaused) {
            renderGamePausedOverlay();
        }
    }

    private void update(float deltaTime) {
        autoSave(deltaTime);

        playerCharacter.setPreviousPosition(playerCharacter.getPosition());
        this.handleMovementInput();
        projectileManager.updateProjectiles(0, camera.viewportWidth, 0, camera.viewportHeight, deltaTime);
        enemyManager.updateEnemyPosition(playerCharacter.getPosition());
        enemyManager.checkEnemyCollision();
        collisionManager.detectProjectileCollisionWithEnemy();
        collisionManager.handleProjectileCollisionWithPlayerCharacter();
        collisionManager.detectAbilityCollisionWithEnemy(abilityManager.activeAbilities, enemyManager.getActiveEnemies());
        enemyManager.handleEnemyDeath();
        gamePlayHUD.updateEnemiesSlain(EnemyManager.getEnemiesSlain());

        if (this.getIsFiring()) {
            handlePlayerFiring();
        }
        playerCharacter.updateTimeSinceLastShot(deltaTime);
        playerCharacter.updateTimeSinceCast(deltaTime);
        enemyManager.spawnEnemies(camera.viewportWidth, camera.viewportHeight, gamePlayHUD.getMinutes(), deltaTime);
        enemyManager.addEnemyToActiveEnemies();
        enemyManager.updateEnemyFire(playerCharacter.getPosition(), deltaTime);
        enemyManager.updateEnemyStatusEffects(deltaTime);
        abilityManager.update(deltaTime);

        // if the player character dies, switch to the game over screen
        if (!playerCharacter.getIsAlive()) {
            game.setScreen(new GameOverScreen(game, EnemyManager.getEnemiesSlain(), gamePlayHUD.getCurrentTimeSurvived()));
            this.dispose();
        }
        gamePlayHUD.updateGameTimer(deltaTime);
        gamePlayHUD.updatePlayerHealth(playerCharacter.getCurrentHP());
        updateAbilityIconReadyState();
    }

    /*
     * Gets the spawn position for the player character.
     * */
    private Vector3 getPlayerSpawnPosition() {
        return new Vector3(
            (this.viewport.getWorldWidth() / HALF_MODIFIER),
            (this.viewport.getWorldHeight() / HALF_MODIFIER),
            0
        );
    }

    /**
     * Handles firing a projectile from the player character.
     * */
    private void handlePlayerFiring() {
        if (!playerCharacter.canFire()) return;

        Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPoint);

        projectileManager.addProjectile(playerCharacter.fireProjectile(touchPoint));
    }

    /**
     * Saves the player character's game data.
     * */
    public void save() {
        GameSaveData save = new GameSaveData();
        save.setPlayerSaveData(SaveLoadManager.createPlayerSave(playerCharacter));

        // Save active enemies
        for (Enemy enemy : enemyManager.getActiveEnemies()) {
            save.addEnemySaveData(enemy.saveData());
        }

        SaveLoadManager.saveGame(save);
        System.out.println("Game saved");
    }

    /**
     * Automatically saves the game at a set time interval.
     * */
    private void autoSave(float deltaTime) {
        autosaveTimer += deltaTime;
        if (autosaveTimer > 15) {
            save();
            autosaveTimer = 0;
        }
    }

    /**
     * Pause the game.
     * */
    private void pauseGame() {
        this.isPaused = true;
    }

    private void createPauseButtons(final float worldWidth, final float worldHeight) {

        // Get the buttons as Sprites
        continueButton = SpriteUtils.getSpriteFromSpriteSheet(
            assetManager.get(Assets.CONTINUE_BUTTON), 0, 0, 1, 1);
        continueButton.setSize(
            continueButton.getWidth() * 0.3f,
            continueButton.getHeight() * 0.3f
        );
        saveButton = SpriteUtils.getSpriteFromSpriteSheet(
            assetManager.get(Assets.SAVE_BUTTON), 0, 0, 1, 1);
        saveButton.setSize(
            saveButton.getWidth() * 0.3f,
            saveButton.getHeight() * 0.3f
        );

        // Use scaled sizes directly
        float continueHeight = continueButton.getHeight();
        float saveHeight = saveButton.getHeight();

        float totalHeight = continueHeight + 10f + saveHeight;
        float startY = (worldHeight - totalHeight) / 2f;

        // Set the correct positions
        continueButton.setPosition(
            worldWidth / 2f - continueButton.getWidth() / 2f,
            startY + saveHeight + 10f
        );
        saveButton.setPosition(
            worldWidth / 2f - saveButton.getWidth() / 2f,
            startY
        );
    }

    /**
     * Renders the game paused overlay.
     **/
    private void renderGamePausedOverlay() {
        tintScreen();

        SpriteBatch spriteBatch = game.getSpriteBatch();
        spriteBatch.begin();
        continueButton.draw(spriteBatch);
        saveButton.draw(spriteBatch);
        spriteBatch.end();
    }

    private void tintScreen() {
        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /**
     * Updates the ability icons to reflect if they are ready for execution.
     * */
    private void updateAbilityIconReadyState() {
        gamePlayHUD.setAbilityReady(
            AbilityName.FROST_WAVE,
            playerCharacter.checkIfAbilityIsExecutable(AbilityName.FROST_WAVE)
        );
        gamePlayHUD.setAbilityReady(
            AbilityName.BLINK,
            playerCharacter.checkIfAbilityIsExecutable(AbilityName.BLINK)
        );
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.mapRenderer.dispose();
        this.projectileManager.dispose();
    }

    /**
     * Returns the coordinates of the touch point as an array of floats
     * */
    public boolean getIsFiring() {
        return isFiring;
    }

    public void handleMovementInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerCharacter.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerCharacter.moveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerCharacter.moveUp();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerCharacter.moveDown();
        }
    }

    public void updateDirectionPlayerFacing() {
        if (moveLeftKeyPressed && !moveRightKeyPressed) {
            playerCharacter.faceLeft();
        } else if (moveRightKeyPressed && !moveLeftKeyPressed) {
            playerCharacter.faceRight();
        }
    }

    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            playerCharacter.executeAbility(AbilityName.BLINK, abilityManager);
        }
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            this.moveLeftKeyPressed = true;
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            this.moveRightKeyPressed = true;
        }
        if (keycode == Input.Keys.ESCAPE) {
            if (!this.isPaused) {
                pauseGame();
            } else {
                isPaused = false;
            }
        }
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            this.moveLeftKeyPressed = false;
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            this.moveRightKeyPressed = false;
        }
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 position = new Vector3(screenX, screenY, 0);
        viewport.unproject(position);
        if (button == Input.Buttons.RIGHT) {
            playerCharacter.executeAbility(AbilityName.FROST_WAVE, abilityManager);
        }
        if (isPaused && continueButton.getBoundingRectangle().contains(position.x, position.y)) {
            isPaused = false;
        }
        if (isPaused && saveButton.getBoundingRectangle().contains(position.x, position.y)) {
            save();
        }
        if (button != Input.Buttons.LEFT || pointer > 0) {
            return false;
        }

        isFiring = true;
        return true;
    }

    /**
     * Called when a finger was lifted or a mouse button was released.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isFiring = false;
        return false;
    }

    /**
     * Called when the touch gesture is cancelled. Reason may be from OS interruption to touch becoming a large surface such as
     * the user cheek. Relevant on Android and iOS only.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amountX the horizontal scroll amount, negative or positive depending on the direction the wheel was scrolled.
     * @param amountY the vertical scroll amount, negative or positive depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
