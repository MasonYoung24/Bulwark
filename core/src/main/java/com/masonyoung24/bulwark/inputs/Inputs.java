package com.masonyoung24.bulwark.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.masonyoung24.bulwark.objects.PlayerCharacter;

public class Inputs {
    private final PlayerCharacter playerCharacter;

    public Inputs(final PlayerCharacter playerCharacter) {
        this.playerCharacter = playerCharacter;
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
}
