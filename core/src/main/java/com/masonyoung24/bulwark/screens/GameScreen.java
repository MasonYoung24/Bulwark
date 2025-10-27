package com.masonyoung24.bulwark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.masonyoung24.bulwark.Bulwark;
import com.masonyoung24.bulwark.Managers.ProjectileManager;
import com.masonyoung24.bulwark.enums.Direction;
import com.masonyoung24.bulwark.inputs.Inputs;
import com.masonyoung24.bulwark.objects.PlayerCharacter;
import com.masonyoung24.bulwark.objects.Projectile;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private final Bulwark game;
    private final OrthographicCamera camera;
    private final PlayerCharacter playerCharacter;
    private final Inputs inputs;
    private final ProjectileManager projectileManager;

    public GameScreen(Bulwark game) {
        this.game = game;
        this.projectileManager = new ProjectileManager();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        AssetManager manager = game.assets.manager;
        playerCharacter = new PlayerCharacter(1, 100, manager);
        inputs = new Inputs(camera, playerCharacter, projectileManager);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputs);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        inputs.handleMovementInput();
        projectileManager.updateProjectiles(0, camera.viewportWidth, 0, camera.viewportHeight);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        inputs.updateDirectionPlayerFacing();
        projectileManager.render(game.batch);
        playerCharacter.render(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

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
