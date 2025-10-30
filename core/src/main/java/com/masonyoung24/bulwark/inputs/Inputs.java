package com.masonyoung24.bulwark.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.Managers.ProjectileManager;
import com.masonyoung24.bulwark.objects.PlayerCharacter;
import com.masonyoung24.bulwark.objects.Projectile;

public class Inputs implements InputProcessor {
    private final OrthographicCamera camera;
    private final PlayerCharacter playerCharacter;
    private boolean moveLeftKeyPressed = false;
    private boolean moveRightKeyPressed = false;
    private boolean isFiring = false;
    private final Vector3 touchPoint = new Vector3();
    private final ProjectileManager projectileManager;

    public Inputs(OrthographicCamera camera,
                  final PlayerCharacter playerCharacter,
                  ProjectileManager projectileManager) {
        this.camera = camera;
        this.playerCharacter = playerCharacter;
        this.projectileManager = projectileManager;
    }

    /**
     * Returns the coordinates of the touch point as an array of floats
     * */
    public float[] getTouchPointCoordinates() {
        return new float[]{touchPoint.x, touchPoint.y};
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
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            this.moveLeftKeyPressed = true;
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            this.moveRightKeyPressed = true;
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
        if (button != Input.Buttons.LEFT || pointer > 0) {
            return false;
        }
        isFiring = true;
        camera.unproject(touchPoint.set(screenX, screenY, 0));
        projectileManager.addProjectile(playerCharacter.fireProjectile(touchPoint));
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
        if (!isFiring || pointer > 0) {
            return false;
        }
        camera.unproject(touchPoint.set(screenX, screenY, 0));
        projectileManager.addProjectile(playerCharacter.fireProjectile(touchPoint));
        return true;
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
