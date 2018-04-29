package com.chainbreak.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.chainbreak.game.ChainBreak;
import com.chainbreak.game.screens.GameScreen;

public abstract class Trap extends Sprite
{
    protected ChainBreak game;
    protected GameScreen screen;
    protected World world;
    protected Body body;
    protected float x, y;

    public Trap(GameScreen screen, float x, float y)
    {
        this.screen = screen;
        this.game = screen.getGame();
        this.world = screen.getWorld();

        setPosition(x, y);
        defineBody();
    }

    public abstract void update(float delta);
    protected abstract void defineBody();
}
