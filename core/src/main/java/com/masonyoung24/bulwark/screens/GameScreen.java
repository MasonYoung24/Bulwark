package com.masonyoung24.bulwark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.masonyoung24.bulwark.Bulwark;
import com.masonyoung24.bulwark.objects.PlayerCharacter;

public class GameScreen implements Screen {
    private final Bulwark game;
    private final OrthographicCamera camera;

    public GameScreen(Bulwark game) {
        this.game = game;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        AssetManager manager = game.assets.manager;
        PlayerCharacter playerCharacter = new PlayerCharacter(manager);
        game.batch.begin();
        game.batch.draw(playerCharacter.getFrame(), 100, 100, 64, 64); // draw at (100,100)
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
