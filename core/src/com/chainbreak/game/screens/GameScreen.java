package com.chainbreak.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chainbreak.game.ChainBreak;
import com.chainbreak.game.entities.*;
import com.chainbreak.game.scenes.Hud;
import com.chainbreak.game.utils.Constants;
import com.chainbreak.game.utils.DataManager;
import com.chainbreak.game.utils.DirectionGesture;
import com.chainbreak.game.utils.WorldContactListener;
import com.chainbreak.game.world.WorldMap;

public class GameScreen extends AbstractScreen
{
    private int state;
    private final int GAME_RUNNING = 1, GAME_PAUSED = 2, GAME_LEVEL = 3, GAME_OVER = 4;

    private final ChainBreak game;
    private SpriteBatch spriteBatch;
    private World world;
    //private Box2DDebugRenderer box2DDebugRenderer;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private Vector3 touchPoint;
    private Rectangle pauseBound;

    private Integer level;
    private Hud hud;
    private WorldMap worldMap;

    private boolean onScreenTap, pauseEnable, nextLevel, gameOver;

    private InputMultiplexer multiplexer;

    public GameScreen(ChainBreak game, Integer level)
    {
        this.game = game;
        this.level = level;
        this.spriteBatch = game.getSpriteBatch();

        setPause(true);

        gameCam = new OrthographicCamera();
        gameCam.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        gamePort = new FitViewport(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM, gameCam);

        world = new World(new Vector2(0, -10), true);
        //box2DDebugRenderer = new Box2DDebugRenderer();

        setScreenOnTap(false);
        initNextLevel(false);
        initGameOver(false);

        state = GAME_RUNNING;

        touchPoint = new Vector3();
        pauseBound = new Rectangle();

        multiplexer = new InputMultiplexer();

        hud = new Hud(this);

        worldMap = new WorldMap(this, level);

        WorldContactListener contactListener = new WorldContactListener();
        world.setContactListener(contactListener);

        inputHandler();

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        Gdx.input.setInputProcessor(multiplexer);

        game.getMainMusic().stop();
        game.getBackgroundMusic().play();
    }

    private void inputHandler()
    {
        for (final Actor actor : worldMap.getActor())
        {
            multiplexer.addProcessor(new DirectionGesture(new DirectionGesture.DirectionListener() {
                @Override
                public void swingLeft() {
                    actor.steerLeft();
                }

                @Override
                public void swingRight() {
                    actor.steerRight();
                }

                @Override
                public void onRelease()
                {
                    setScreenOnTap(true);
                    game.getPhaseJumpSound().play();
                }
            }));
        }
    }

    private void setScreenOnTap(boolean onScreenTap)
    {
        this.onScreenTap = onScreenTap;
    }
    private boolean isScreenOnTap()
    {
        return onScreenTap;
    }

    private void gameUpdate(float delta)
    {
        gameCam.update();

        worldMap.update(gameCam);

        pauseBound.setSize(64 / Constants.PPM, 64 / Constants.PPM);
        pauseBound.setPosition(gameCam.position.x + ((Constants.V_WIDTH/2 - 64) / Constants.PPM), gameCam.position.y + ((Constants.V_HEIGHT/2 - 64) / Constants.PPM));

        switch (state)
        {
            case GAME_RUNNING:
                updateRunning(delta);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_LEVEL:
                updateSprite(delta);
                multiplexer.addProcessor(hud.getNxtLevelStage());
                DataManager.getInstance().save(getCurrentLevel() + 1, true);
                break;
            case GAME_OVER:
                updateSprite(delta);
                multiplexer.addProcessor(hud.getGameOverStage());
                break;
        }
    }

    private void updateRunning(float delta)
    {
        if (Gdx.input.justTouched())
        {
            gamePort.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (pauseBound.contains(touchPoint.x, touchPoint.y))
            {
                state = GAME_PAUSED;
                setPause(false);
                game.getPauseSound().play(0.2f);
                game.getBackgroundMusic().pause();
            }
        }

        world.step(1 / 60.0f, 6, 2);

        for (Trap trap : worldMap.getTrapObjects())
        {
            trap.update(delta);
        }

        for (Portal portal : worldMap.getPortal())
        {
            portal.update(delta);
        }

        for (Arrow arrow : worldMap.getArrows())
        {
            arrow.update(delta);
        }

        for (Actor actor : worldMap.getActor())
        {
            actor.update(delta);

            gameCam.position.x = actor.getBody().getPosition().x;
            gameCam.position.y = actor.getBody().getPosition().y;
        }

        for (Chain chain : worldMap.getChains())
        {
            if (isScreenOnTap() && chain.isAttach() && !pauseBound.contains(touchPoint.x, touchPoint.y)) {
                chain.onDetach();
                setScreenOnTap(false);
            }
        }

        for (BouncePad bouncePad : worldMap.getBouncePads())
        {
            bouncePad.update();
        }

        if (isNextLevel()) {
            state = GAME_LEVEL;
            game.getNextLevelSound().play();
            game.getBackgroundMusic().stop();
        }
        if (isGameOver()) {
            state = GAME_OVER;
            game.getGameOverSound().play();
            game.getBackgroundMusic().stop();
        }
    }

    private void updatePaused()
    {
        if (Gdx.input.justTouched())
        {
            gamePort.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (pauseBound.contains(touchPoint.x, touchPoint.y))
            {
                state = GAME_RUNNING;
                setPause(true);
                game.getResumeSound().play(0.2f);
                game.getBackgroundMusic().play();
            }
        }
    }

    private void updateSprite(float delta)
    {
        for (Trap trap : worldMap.getTrapObjects())
        {
            trap.update(delta);
        }

        for (Portal portal : worldMap.getPortal())
        {
            portal.update(delta);
        }

        for (Arrow arrow : worldMap.getArrows())
        {
            arrow.update(delta);
        }

        for (Actor actor : worldMap.getActor()) {
            actor.update(delta);
        }
    }

    private void gameState()
    {
        spriteBatch.setProjectionMatrix(gameCam.combined);

        worldMap.render();

        spriteBatch.begin(); //begin render

        for (Trap trap : worldMap.getTrapObjects())
        {
            trap.draw(spriteBatch);
        }

        for (Portal portal : worldMap.getPortal())
        {
            portal.draw(spriteBatch);
        }

        for (Arrow arrow : worldMap.getArrows())
        {
            arrow.draw(spriteBatch);
        }

        for (Actor actor : worldMap.getActor())
        {
            actor.draw(spriteBatch);
        }

        for (Chain chain : worldMap.getChains())
        {
            chain.renderObject(spriteBatch);
        }

        for (BouncePad bouncePad : worldMap.getBouncePads())
        {
            bouncePad.draw(spriteBatch);
            bouncePad.renderObject(spriteBatch);
        }

        spriteBatch.draw(isPauseEnable() ? game.getPause() : game.getPlay(), pauseBound.getX(), pauseBound.getY(), pauseBound.getWidth(), pauseBound.getHeight());

        spriteBatch.end(); //end render

        hud.getHudStage().draw();

        switch (state)
        {
            case GAME_RUNNING:
                break;
            case GAME_PAUSED:
                break;
            case GAME_LEVEL:
                hud.getNxtLevelStage().draw();
                hud.getGameOverStage().cancelTouchFocus();
                break;
            case GAME_OVER:
                hud.getGameOverStage().draw();
                hud.getNxtLevelStage().cancelTouchFocus();
                break;
        }

        //box2DDebugRenderer.render(world, gameCam.combined);
    }

    private void setPause(boolean pauseEnable)
    {
        this.pauseEnable = pauseEnable;
    }
    private boolean isPauseEnable()
    {
        return pauseEnable;
    }
    public boolean isNextLevel()
    {
        return nextLevel;
    }
    public void initNextLevel(boolean nextLevel)
    {
        this.nextLevel = nextLevel;
    }
    public boolean isGameOver()
    {
        return gameOver;
    }
    public void initGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);

        gameUpdate(delta);
        gameState();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        gamePort.update(width, height);
        hud.resize(width, height);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        world.dispose();
        //box2DDebugRenderer.dispose();
        worldMap.dispose();
        hud.dispose();
    }

    public ChainBreak getGame()
    {
        return game;
    }
    public SpriteBatch getSpriteBatch()
    {
        return spriteBatch;
    }
    public World getWorld()
    {
        return world;
    }
    public Integer getCurrentLevel()
    {
        return level;
    }
    public Array<Chain> getArrayChain()
    {
        return worldMap.getChains();
    }
}
