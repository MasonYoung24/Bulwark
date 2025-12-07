package com.masonyoung24.bulwark.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.masonyoung24.bulwark.enums.EnemyType;
import com.masonyoung24.bulwark.enums.SlimeSize;

/**
 * Represents an EnemyFactory used to make template Enemy objects.
 *
 * @author Mason Young
 * @version 2025
 * */
public class EnemyFactory {

    private static final int FACTORY_TEMPLATE_HP = 3;
    private static final float FACTORY_TEMPLATE_SPEED = 1.2f;
    private static final float FACTORY_TEMPLATE_SCALE_FACTOR = 1.0f;
    private static final SlimeSize SLIME_DEFAULT_SIZE = SlimeSize.LARGE;
    private static final String ILLEGAL_ARGUMENT_MESSAGE = "Illegal enemy type";

    /*
     * Constructs an object of type EnemyFactory.
     * */
    private EnemyFactory() {

    }

    /**
     * Creates an Enemy based on the provided EnemyType.
     *
     * @param enemyType the type of Enemy to make as an EnemyType
     * @param assets the asset manager used to make the Enemy sprite as an AssetManager
     * @throws IllegalArgumentException if enemy type does not exist
     * */
    public static Enemy create(final EnemyType enemyType, final AssetManager assets) {
        switch (enemyType) {
            case GOBLIN:
                return new Goblin(
                    FACTORY_TEMPLATE_HP,
                    FACTORY_TEMPLATE_SPEED,
                    assets,
                    new Vector3()
                );
            case SLIME:
                return new Slime(
                    SLIME_DEFAULT_SIZE,
                    FACTORY_TEMPLATE_SCALE_FACTOR,
                    FACTORY_TEMPLATE_HP,
                    FACTORY_TEMPLATE_SPEED,
                    assets,
                    new Vector3()
                );
            case SKELETON_WIZARD:
                return new SkeletonWizard(
                    FACTORY_TEMPLATE_HP,
                    FACTORY_TEMPLATE_SPEED,
                    assets,
                    new Vector3()
                );
            default:
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
    }
}

