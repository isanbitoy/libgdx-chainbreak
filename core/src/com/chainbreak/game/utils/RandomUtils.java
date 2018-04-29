package com.chainbreak.game.utils;

import com.chainbreak.game.enums.TrapBarrelType;
import com.chainbreak.game.enums.TrapFireType;

import java.util.Random;

public class RandomUtils
{
    private static RandomUtils instance;

    public RandomUtils()
    {
        super();
    }

    public static RandomUtils getInstance()
    {
        if(instance == null)
            instance = new RandomUtils();

        return instance;
    }

    public class RandomEnum<E extends Enum>
    {
        private final Random RND = new Random();
        private final E[] values;

        public RandomEnum(Class<E> token)
        {
            values = token.getEnumConstants();
        }

        public E random()
        {
            return values[RND.nextInt(values.length)];
        }
    }

    public TrapFireType getRandomTrapFireType()
    {
        RandomEnum<TrapFireType> randomEnum = new RandomEnum<TrapFireType>(TrapFireType.class);
        return randomEnum.random();
    }

    public TrapBarrelType getRandomTrapBarrelType()
    {
        RandomEnum<TrapBarrelType> randomEnum = new RandomEnum<TrapBarrelType>(TrapBarrelType.class);
        return randomEnum.random();
    }
}
