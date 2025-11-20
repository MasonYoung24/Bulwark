package com.masonyoung24.bulwark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.masonyoung24.bulwark.objects.spells.Blink;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private final Bulwark game;
    private final OrthographicCamera camera;
    private final PlayerCharacter playerCharacter;
    private final Inputs inputs;
    private final ProjectileManager projectileManager;
    private final EnemyManager enemyManager;
    private final FitViewport viewport;
    private final CollisionManager collisionManager;
    private final GamePlayHUD gamePlayHUD;

    public GameScreen(Bulwark game) {
        this.game = game;
        AssetManager manager = game.assets.manager;
        this.projectileManager = new ProjectileManager();
        this.enemyManager = new EnemyManager(manager);
        playerCharacter = new PlayerCharacter(1, 100, manager);
        this.collisionManager = new CollisionManager(enemyManager, projectileManager, playerCharacter);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(camera.viewportWidth / 2f, camera.viewportHeight / 2f);
        camera.update();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        inputs = new Inputs(camera, playerCharacter, projectileManager);
        this.gamePlayHUD = new GamePlayHUD(game.batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputs);
        enemyManager.spawnEnemies(camera.viewportWidth, camera.viewportHeight, 5);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        playerCharacter.setPreviousPosition(playerCharacter.getPosition());
        inputs.handleMovementInput();
        update(v);
        game.batch.setProjectionMatrix(gamePlayHUD.stage.getCamera().combined);
        gamePlayHUD.stage.draw();
        projectileManager.updateProjectiles(0, camera.viewportWidth, 0, camera.viewportHeight);
        enemyManager.updateEnemyPosition(playerCharacter.getPosition());
        enemyManager.checkEnemyCollision();
        collisionManager.detectProjectileCollisionWithEnemy();
        enemyManager.handleEnemyDeath();
        gamePlayHUD.updateEnemiesSlain(EnemyManager.getEnemiesSlain());
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        inputs.updateDirectionPlayerFacing();
        projectileManager.render(game.batch);
        playerCharacter.render(game.batch);
        game.batch.end();
        enemyManager.render(game.batch);

    }

    private void update(float deltaTime) {
        if (inputs.getIsFiring()) {
            handleFiring();
        }
        playerCharacter.updateTimeSinceLastShot(deltaTime);
        gamePlayHUD.updateGameTimer(deltaTime);
    }

    /**
     * Handles firing a projectile from the player character.
     * */
    private void handleFiring() {
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

    }
}
