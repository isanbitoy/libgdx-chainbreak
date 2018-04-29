package com.chainbreak.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.chainbreak.game.screens.ScreenEnum;
import com.chainbreak.game.screens.ScreenManager;
import com.chainbreak.game.utils.AssetHandler;

public class ChainBreak extends Game
{
	private SpriteBatch spriteBatch;
	private AssetHandler assetHandler;
	private FreeTypeFontGenerator generator;
	private BitmapFont smallFont, mediumFont, largeFont;
	private static Skin skin;

	private TextureAtlas uiSkinAtlas, textureAtlas;
	private Sprite minerIdle, crate, chain, spring, play, pause,
            back, background, title, musicOn, musicOff, info;
    private Texture loadForeground, loadBackground;
    private TextureRegion leftSwing, rightSwing, poof, warp,
            blast, spike, lava, wormHole, arrowA, arrowB;

	private Sound clickSound, explosionSound, hitSound,
            pauseSound, resumeSound, phaseJumpSound, gameOverSound, nextLevelSound;
	private Music mainMusic, backgroundMusic;

	@Override
	public void create ()
	{
		spriteBatch = new SpriteBatch();
		assetHandler = new AssetHandler();
		assetHandler.load();

        getFont();
        getUISkin();
		getTextureAtlas();
        getSound();

		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen(ScreenEnum.LOAD);
	}

    private void getFont()
    {
        generator = new FreeTypeFontGenerator(Gdx.files.getFileHandle("fonts/smart.ttf", Files.FileType.Internal));
        FreeTypeFontGenerator.FreeTypeFontParameter smallParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter mediumParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter largeParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        smallParameter.borderColor = Color.CLEAR;
        smallParameter.size = 32;
        mediumParameter.borderColor = Color.CLEAR;
        mediumParameter.size = 48;
        largeParameter.borderColor = Color.CLEAR;
        largeParameter.size = 72;

        smallFont = generator.generateFont(smallParameter);
        mediumFont = generator.generateFont(mediumParameter);
        largeFont = generator.generateFont(largeParameter);
    }

    private void getUISkin()
    {
        if (uiSkinAtlas == null)
            uiSkinAtlas = assetHandler.getManager().get("atlas/uiButtons.pack", TextureAtlas.class);
        skin = new Skin();
        skin.addRegions(uiSkinAtlas);
    }

	private void getTextureAtlas()
	{
		if (textureAtlas == null)
            textureAtlas = assetHandler.getManager().get("atlas/textureAtlas.pack", TextureAtlas.class);
        minerIdle = textureAtlas.createSprite("idleExplorer");
		chain = textureAtlas.createSprite("chain");
		spring = textureAtlas.createSprite("spring");
		crate = textureAtlas.createSprite("crate");
		play = textureAtlas.createSprite("playIcon");
		pause = textureAtlas.createSprite("pauseIcon");
        back = textureAtlas.createSprite("backIcon");
        background = textureAtlas.createSprite("background");
        title = textureAtlas.createSprite("title");
        musicOn = textureAtlas.createSprite("musicOn");
        musicOff = textureAtlas.createSprite("musicOff");
        info = textureAtlas.createSprite("infoIcon");

        leftSwing = textureAtlas.findRegion("leftSwing");
        rightSwing = textureAtlas.findRegion("rightSwing");
        poof = textureAtlas.findRegion("deadSprite");
        warp = textureAtlas.findRegion("warpSprite");
        blast = textureAtlas.findRegion("blastSprite");
        spike = textureAtlas.findRegion("spikeSprite");
        lava = textureAtlas.findRegion("lavaSprite");
        wormHole = textureAtlas.findRegion("wormHoleSprite");
        arrowA = textureAtlas.findRegion("arrowA");
        arrowB = textureAtlas.findRegion("arrowB");

        loadBackground = assetHandler.getManager().get("atlas/bg.png", Texture.class);
        loadForeground = assetHandler.getManager().get("atlas/fg.png", Texture.class);
	}

	private void getSound()
	{
		mainMusic = assetHandler.getManager().get("sound/main.ogg", Music.class);
		mainMusic.setLooping(true);
        mainMusic.setVolume(1.0f);
        backgroundMusic = assetHandler.getManager().get("sound/background.ogg", Music.class);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        clickSound = assetHandler.getManager().get("sound/click.wav", Sound.class);
        explosionSound = assetHandler.getManager().get("sound/explosion.wav", Sound.class);
        hitSound = assetHandler.getManager().get("sound/hit.wav", Sound.class);
        pauseSound = assetHandler.getManager().get("sound/pause.wav", Sound.class);
        resumeSound = assetHandler.getManager().get("sound/resume.wav", Sound.class);
        phaseJumpSound = assetHandler.getManager().get("sound/phaseJump.wav", Sound.class);
        gameOverSound = assetHandler.getManager().get("sound/gameOver.wav", Sound.class);
        nextLevelSound = assetHandler.getManager().get("sound/nextLevel.wav", Sound.class);
	}

	@Override
	public void render ()
	{
		super.render();

		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
	}

    @Override
	public void dispose ()
	{
		super.dispose();
		spriteBatch.dispose();
		assetHandler.dispose();
		generator.dispose();
		skin.dispose();
	}

	public SpriteBatch getSpriteBatch()
	{
		return spriteBatch;
	}
	public BitmapFont initSmallFont()
	{
		return smallFont;
	}
	public BitmapFont initMediumFont()
	{
		return mediumFont;
	}
	public BitmapFont initLargeFont()
	{
		return largeFont;
	}
	public Skin getSkin()
	{
		return skin;
	}

    public TextureRegion getFire(String assetID)
    {
        return textureAtlas.findRegion(assetID);
    }
    public TextureRegion getLeftSwing()
    {
        return leftSwing;
    }
    public TextureRegion getRightSwing()
    {
        return rightSwing;
    }
    public TextureRegion getPoof()
    {
        return poof;
    }
    public TextureRegion getWarp()
    {
        return warp;
    }
    public TextureRegion getBlast()
    {
        return blast;
    }
    public TextureRegion getSpike()
    {
        return spike;
    }
    public TextureRegion getLava()
    {
        return lava;
    }
    public TextureRegion getWormHole()
    {
        return wormHole;
    }
    public TextureRegion getArrowA()
    {
        return arrowA;
    }
    public TextureRegion getArrowB()
    {
        return arrowB;
    }

    public Sprite getBarrel(String assetID)
    {
        return textureAtlas.createSprite(assetID);
    }
    public Sprite getMinerIdle()
    {
        return minerIdle;
    }
	public Sprite getChain()
	{
		return chain;
	}
	public Sprite getSpring()
	{
		return spring;
	}
	public Sprite getCrate()
	{
		return crate;
	}
	public Sprite getPlay()
	{
		return play;
	}
	public Sprite getPause()
	{
		return pause;
	}
    public Sprite getBack()
    {
        return back;
    }
    public Sprite getBackground()
    {
        return background;
    }
    public Sprite getTitle()
    {
        return title;
    }
    public Sprite getMusicOn()
    {
        return musicOn;
    }
    public Sprite getMusicOff()
    {
        return musicOff;
    }
    public Sprite getInfo()
    {
        return info;
    }

    public Texture getLoadBackground()
    {
        return loadBackground;
    }
    public Texture getLoadForeground()
    {
        return loadForeground;
    }

    public Music getMainMusic()
    {
        return mainMusic;
    }
    public Music getBackgroundMusic()
    {
        return backgroundMusic;
    }
    public Sound getGameOverSound()
    {
        return gameOverSound;
    }
    public Sound getNextLevelSound()
    {
        return nextLevelSound;
    }
    public Sound getClickSound()
    {
        return clickSound;
    }
    public Sound getExplosionSound()
    {
        return explosionSound;
    }
    public Sound getHitSound()
    {
        return hitSound;
    }
    public Sound getPauseSound()
    {
        return pauseSound;
    }
    public Sound getResumeSound()
    {
        return resumeSound;
    }
    public Sound getPhaseJumpSound()
    {
        return phaseJumpSound;
    }

    public AssetHandler getAssetHandler()
    {
        return assetHandler;
    }
}
