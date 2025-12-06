package com.masonyoung24.bulwark.data;

import com.masonyoung24.bulwark.enums.SlimeSize;

/**
 * Represents the save data for a Slime Enemy.
 *
 * @author Mason Young
 * @version 2025
 * */
public class SlimeSaveData extends EnemySaveData {

    protected float scaleFactor;
    protected SlimeSize slimeSize;

    /**
     * Constructs an object of type SlimeSaveData.
     * */
    public SlimeSaveData() {
        super();
    }

    /**
     * Returns the scale factor.
     *
     * @return the scale factor as a float.
     * */
    public float getScaleFactor() {
        return this.scaleFactor;
    }

    /**
     * Sets the scale factor of the slime save data.
     *
     * @param scaleFactor the scale factor to set for the slime dave data as a float
     * */
    public void setScaleFactor(final float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    /**
     * Returns the stored size of the slime.
     *
     * @return the stored size of the slime as a SlimeSize
     * */
    public SlimeSize getSlimeSize() {
        return this.slimeSize;
    }

    /**
     * Sets the stored size of the slime.
     *
     * @param slimeSize the size of the slime to set as a SlimeSize
     * */
    public void setSlimeSize(final SlimeSize slimeSize) {
        this.slimeSize = slimeSize;
    }
}
