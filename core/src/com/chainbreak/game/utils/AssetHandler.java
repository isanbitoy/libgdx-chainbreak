package com.chainbreak.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetHandler
{
    private AssetManager manager = new AssetManager();

    public void load()
    {
        manager.load("sound/click.wav", Sound.class);
        manager.load("sound/hit.wav", Sound.class);
        manager.load("sound/explosion.wav", Sound.class);
        manager.load("sound/pause.wav", Sound.class);
        manager.load("sound/resume.wav", Sound.class);
        manager.load("sound/phaseJump.wav", Sound.class);
        manager.load("sound/gameOver.wav", Sound.class);
        manager.load("sound/nextLevel.wav", Sound.class);
        manager.load("sound/main.ogg", Music.class);
        manager.load("sound/background.ogg", Music.class);

        manager.load("atlas/fg.png", Texture.class);
        manager.load("atlas/bg.png", Texture.class);
        manager.load("atlas/uiButtons.pack", TextureAtlas.class);
        manager.load("atlas/textureAtlas.pack", TextureAtlas.class);

        manager.finishLoading();
    }

    public void dispose()
    {
        manager.clear();
        manager.dispose();
    }

    public AssetManager getManager()
    {
        return manager;
    }
}
