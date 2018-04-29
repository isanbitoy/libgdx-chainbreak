package com.chainbreak.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chainbreak.game.screens.GameScreen;
import com.chainbreak.game.screens.ScreenEnum;
import com.chainbreak.game.screens.ScreenManager;
import com.chainbreak.game.utils.Constants;

public class Hud
{
    private GameScreen screen;
    private Viewport viewport;
    private Stage hudStage, gameOverStage, nxtLevelStage;
    private ImageButton.ImageButtonStyle restartButtonStyle,
            nextButtonStyle,
            menuButtonStyle;

    public Hud(GameScreen screen)
    {
        this.screen = screen;

        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());

        restartButtonStyle = new ImageButton.ImageButtonStyle();
        restartButtonStyle.up = screen.getGame().getSkin().getDrawable("restart_unpressed");
        restartButtonStyle.down = screen.getGame().getSkin().getDrawable("restart_pressed");
        restartButtonStyle.checked = screen.getGame().getSkin().getDrawable("restart_pressed");

        nextButtonStyle = new ImageButton.ImageButtonStyle();
        nextButtonStyle.up = screen.getGame().getSkin().getDrawable("next_unpressed");
        nextButtonStyle.down = screen.getGame().getSkin().getDrawable("next_pressed");
        nextButtonStyle.checked = screen.getGame().getSkin().getDrawable("next_pressed");

        menuButtonStyle = new ImageButton.ImageButtonStyle();
        menuButtonStyle.up = screen.getGame().getSkin().getDrawable("menu_unpressed");
        menuButtonStyle.down = screen.getGame().getSkin().getDrawable("menu_pressed");
        menuButtonStyle.checked = screen.getGame().getSkin().getDrawable("menu_pressed");

        initHudStage();
        initNxtLevelStage();
        initGameOverStage();
    }

    public void initHudStage()
    {
        hudStage = new Stage(viewport, screen.getGame().getSpriteBatch());
        hudStage.clear();

        Label levelLabel = new Label(String.format("level " + "%1d", screen.getCurrentLevel()), new Label.LabelStyle(screen.getGame().initMediumFont(), Color.WHITE));
        levelLabel.setPosition(Constants.V_WIDTH/2 - levelLabel.getWidth()/2, Constants.V_HEIGHT - (levelLabel.getHeight() + 16));

        hudStage.addActor(levelLabel);
    }

    public void initGameOverStage()
    {
        gameOverStage = new Stage(viewport, screen.getGame().getSpriteBatch());

        Label text = new Label("Game Over", new Label.LabelStyle(screen.getGame().initLargeFont(), Color.WHITE));
        text.setPosition(Constants.V_WIDTH/2.0f - text.getWidth()/2.0f, Constants.V_HEIGHT/1.5f - text.getHeight()/1.5f);

        final ImageButton restart = new ImageButton(restartButtonStyle);
        restart.setBounds(Constants.V_WIDTH/2.0f - (restart.getWidth() + 32), Constants.V_HEIGHT/2.0f - (restart.getHeight() + 48), 64, 64);
        restart.setDisabled(false);
        restart.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                restart.setChecked(true);
                screen.getGame().getClickSound().play();
                Timer.schedule(new Timer.Task()
                {
                    @Override
                    public void run() {
                        ScreenManager.getInstance().showScreen(ScreenEnum.GAME, screen.getCurrentLevel());
                    }
                }, 0.5f);
                return false;
            }
        });

        final ImageButton menu = new ImageButton(menuButtonStyle);
        menu.setBounds(restart.getX() + (menu.getWidth() + 40), Constants.V_HEIGHT/2.0f - (menu.getHeight() + 48), 64, 64);
        menu.setDisabled(false);
        menu.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                menu.setChecked(true);
                screen.getGame().getClickSound().play();
                Timer.schedule(new Timer.Task()
                {
                    @Override
                    public void run() {
                        ScreenManager.getInstance().showScreen(ScreenEnum.LEVEL);
                    }
                }, 0.5f);
                return false;
            }
        });

        Group group = new Group();

        group.addActor(text);
        group.addActor(restart);
        group.addActor(menu);

        gameOverStage.addActor(group);
    }

    public void initNxtLevelStage()
    {
        nxtLevelStage = new Stage(viewport, screen.getGame().getSpriteBatch());

        Label text = new Label("Next Level!", new Label.LabelStyle(screen.getGame().initLargeFont(), Color.WHITE));
        text.setPosition(Constants.V_WIDTH/2.0f - text.getWidth()/2.0f, Constants.V_HEIGHT/1.5f - text.getHeight()/1.5f);

        final ImageButton next = new ImageButton(nextButtonStyle);
        next.setBounds(Constants.V_WIDTH/2.0f - (next.getWidth() + 64), Constants.V_HEIGHT/2.0f - (next.getHeight() + 48), 64, 64);
        next.setDisabled(false);
        next.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                next.setChecked(true);
                screen.getGame().getClickSound().play();
                Timer.schedule(new Timer.Task()
                {
                    @Override
                    public void run() {
                        ScreenManager.getInstance().showScreen(ScreenEnum.GAME, screen.getCurrentLevel() + 1);
                    }
                }, 0.5f);
                return true;
            }
        });

        final ImageButton restart = new ImageButton(restartButtonStyle);
        restart.setBounds(next.getX() + (restart.getWidth() + 32), Constants.V_HEIGHT/2.0f - (restart.getHeight() + 48), 64, 64);
        restart.setDisabled(false);
        restart.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                restart.setChecked(true);
                screen.getGame().getClickSound().play();
                Timer.schedule(new Timer.Task()
                {
                    @Override
                    public void run() {
                        ScreenManager.getInstance().showScreen(ScreenEnum.GAME, screen.getCurrentLevel());
                    }
                }, 0.5f);
                return true;
            }
        });

        final ImageButton menu = new ImageButton(menuButtonStyle);
        menu.setBounds(restart.getX() + (menu.getWidth() + 32), Constants.V_HEIGHT/2.0f - (menu.getWidth() + 48), 64, 64);
        menu.setDisabled(false);
        menu.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                menu.setChecked(true);
                screen.getGame().getClickSound().play();
                Timer.schedule(new Timer.Task()
                {
                    @Override
                    public void run() {
                        ScreenManager.getInstance().showScreen(ScreenEnum.LEVEL);
                    }
                }, 0.5f);
                return true;
            }
        });

        Group group = new Group();

        group.addActor(text);
        group.addActor(next);
        group.addActor(restart);
        group.addActor(menu);

        nxtLevelStage.addActor(group);
    }

    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }

    public void dispose()
    {
        hudStage.dispose();
        gameOverStage.dispose();
        nxtLevelStage.dispose();
    }

    public Stage getHudStage()
    {
        return hudStage;
    }
    public Stage getGameOverStage()
    {
        return gameOverStage;
    }
    public Stage getNxtLevelStage()
    {
        return nxtLevelStage;
    }
}
