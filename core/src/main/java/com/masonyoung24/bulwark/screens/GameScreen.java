package com.masonyoung24.bulwark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.masonyoung24.bulwark.Bulwark;
import com.masonyoung24.bulwark.HUDs.GamePlayHUD;
import com.masonyoung24.bulwark.Managers.ProjectileManager;
import com.masonyoung24.bulwark.enums.Direction;
import com.masonyoung24.bulwark.inputs.Inputs;
import com.masonyoung24.bulwark.objects.CollisionManager;
import com.masonyoung24.bulwark.objects.EnemyManager;
import com.masonyoung24.bulwark.objects.PlayerCharacter;
import com.masonyoung24.bulwark.objects.Projectile;
import com.masonyoung24.bulwark.objects.spells.Ability;
import com.masonyoung24.bulwark.objects.spells.Blink;
import com.masonyoung24.bulwark.resources.Assets;

import java.util.ArrayList;

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


    public GameScreen(Bulwark game) {
        this.game = game;
        this.assetManager = game.assets.manager;
        this.projectileManager = ProjectileManager.getInstance();
        this.enemyManager = new EnemyManager(assetManager);
        playerCharacter = new PlayerCharacter(1, 100, assetManager);
        this.collisionManager = new CollisionManager(enemyManager, projectileManager, playerCharacter);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(camera.viewportWidth / 2f, camera.viewportHeight / 2f);
        camera.update();
        this.mapRenderer = new OrthogonalTiledMapRenderer(
            null,
            this.game.getSpriteBatch());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        inputs = new Inputs(camera, playerCharacter, projectileManager);
        this.gamePlayHUD = new GamePlayHUD(game.getSpriteBatch(), playerCharacter.getCurrentHP());
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
        enemyManager.handleEnemyDeath();
        gamePlayHUD.updateEnemiesSlain(EnemyManager.getEnemiesSlain());
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.getSpriteBatch().begin();
        inputs.updateDirectionPlayerFacing();
        projectileManager.render(game.getSpriteBatch());
        playerCharacter.render(game.getSpriteBatch());
        game.getSpriteBatch().end();
        enemyManager.render(game.getSpriteBatch());
    }

    private void update(float deltaTime) {
        if (inputs.getIsFiring()) {
            handlePlayerFiring();
        }
        playerCharacter.updateTimeSinceLastShot(deltaTime);
        enemyManager.spawnEnemies(camera.viewportWidth, camera.viewportHeight, gamePlayHUD.getMinutes(), deltaTime);
        enemyManager.updateEnemyFire(playerCharacter.getPosition(), deltaTime);

        // if the player character dies, switch to the game over screen
        if (!playerCharacter.getIsAlive()) {
            this.dispose();
            game.setScreen(new GameOverScreen(game));
        }
        gamePlayHUD.updateGameTimer(deltaTime);
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
