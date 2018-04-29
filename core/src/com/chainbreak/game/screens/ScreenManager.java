package com.chainbreak.game.screens;

import com.badlogic.gdx.Screen;
import com.chainbreak.game.ChainBreak;

public class ScreenManager
{
    private ChainBreak game;
    private static ScreenManager instance;

    private ScreenManager()
    {
        super();
    }

    public static ScreenManager getInstance()
    {
        if(instance == null){
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(ChainBreak game)
    {
        this.game = game;
    }

    public void showScreen(ScreenEnum screenEnum, Object... params)
    {
        Screen currentScreen = game.getScreen();

        AbstractScreen newScreen = screenEnum.getScreen(game, params);
        game.setScreen(newScreen);

        if(currentScreen != null)
            currentScreen.dispose();
    }
}
