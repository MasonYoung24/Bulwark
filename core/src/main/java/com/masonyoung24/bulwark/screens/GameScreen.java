package com.masonyoung24.bulwark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.masonyoung24.bulwark.Bulwark;
import com.masonyoung24.bulwark.HUDs.GamePlayHUD;
import com.masonyoung24.bulwark.Managers.ProjectileManager;
import com.masonyoung24.bulwark.data.GameSaveData;
import com.masonyoung24.bulwark.data.SaveLoadManager;
import com.masonyoung24.bulwark.enums.AbilityName;
import com.masonyoung24.bulwark.inputs.Inputs;
import com.masonyoung24.bulwark.objects.*;
import com.masonyoung24.bulwark.resources.Assets;

public class GameScreen implements Screen {
    public static final float UNIT_SCALE = 1f / 32f;

    private final Bulwark game;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final GamePlayHUD gamePlayHUD;
    private final PlayerCharacter playerCharacter;
    private final Inputs inputs;
    private final ProjectileManager projectileManager;
    private final EnemyManager enemyManager;
    private final CollisionManager collisionManager;
    private final AssetManager assetManager;
    private final AbilityManager abilityManager;
    private float autosaveTimer = 0;


    public GameScreen(Bulwark game, boolean loadSave) {
        this.game = game;
        this.assetManager = game.assets.manager;
        this.projectileManager = ProjectileManager.getInstance();
        this.enemyManager = new EnemyManager(assetManager);
        this.abilityManager = new AbilityManager();

        if (loadSave && SaveLoadManager.saveExists()) {
            GameSaveData save = SaveLoadManager.loadGame();
            if (save != null) {
                this.playerCharacter = SaveLoadManager.restorePlayer(save.player, assetManager);
                System.out.println("Player created from save!");
            } else {
                this.playerCharacter = new PlayerCharacter(3, 100, assetManager);
            }
        } else {
            this.playerCharacter = new PlayerCharacter(3, 100, assetManager);
        }

        this.collisionManager = new CollisionManager(enemyManager, projectileManager, playerCharacter);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(camera.viewportWidth / 2f, camera.viewportHeight / 2f);
        camera.update();
        this.mapRenderer = new OrthogonalTiledMapRenderer(
            null,
            this.game.getSpriteBatch());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        inputs = new Inputs(camera, playerCharacter, projectileManager, abilityManager);
        this.gamePlayHUD = new GamePlayHUD(game.getSpriteBatch(), playerCharacter.getCurrentHP(), assetManager);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputs);
        this.mapRenderer.setMap(assetManager.get(Assets.MAIN_TILESET));
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();
        playerCharacter.setPreviousPosition(playerCharacter.getPosition());
        inputs.handleMovementInput();
        update(v);
        gamePlayHUD.draw();
        projectileManager.updateProjectiles(0, camera.viewportWidth, 0, camera.viewportHeight);
        enemyManager.updateEnemyPosition(playerCharacter.getPosition());
        enemyManager.checkEnemyCollision();
        collisionManager.detectProjectileCollisionWithEnemy();
        collisionManager.handleProjectileCollisionWithPlayerCharacter();
        collisionManager.detectAbilityCollisionWithEnemy(abilityManager.activeAbilities, enemyManager.getActiveEnemies());
        enemyManager.handleEnemyDeath();
        gamePlayHUD.updateEnemiesSlain(EnemyManager.getEnemiesSlain());
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.getSpriteBatch().begin();
        inputs.updateDirectionPlayerFacing();
        projectileManager.render(game.getSpriteBatch());
        playerCharacter.render(game.getSpriteBatch());
        abilityManager.render(game.getSpriteBatch());
        enemyManager.render(game.getSpriteBatch());
        game.getSpriteBatch().end();
    }

    private void update(float deltaTime) {
        autoSave(deltaTime);

        if (inputs.getIsFiring()) {
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
            game.setScreen(new GameOverScreen(game, EnemyManager.getEnemiesSlain()));
            this.dispose();
        }
        gamePlayHUD.updateGameTimer(deltaTime);
        gamePlayHUD.updatePlayerHealth(playerCharacter.getCurrentHP());
        updateAbilityIconReadyState();
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
        save.player = SaveLoadManager.createPlayerSave(playerCharacter);
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
     * Updates the ability icons to reflect if they are ready for execution.
     * */
    private void updateAbilityIconReadyState() {
        gamePlayHUD.setAbilityReady(
            AbilityName.FROSTWAVE,
            playerCharacter.checkIfAbilityIsExecutable(AbilityName.FROSTWAVE)
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
}
