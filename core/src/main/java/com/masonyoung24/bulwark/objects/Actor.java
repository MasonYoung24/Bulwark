package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Actor {
    private final int healthPoints;
    protected final float speed;
    protected final Sprite sprite;

    public Actor(int healthPoints, float speed, Sprite sprite) {
        this.healthPoints = healthPoints;
        this.speed = speed;
        this.sprite = sprite;
    }
}
