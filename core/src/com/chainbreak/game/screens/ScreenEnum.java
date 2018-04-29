package com.chainbreak.game.screens;

import com.chainbreak.game.ChainBreak;

public enum  ScreenEnum
{
    LOAD {
        public AbstractScreen getScreen(ChainBreak game, Object... params)
        {
            return new LoadingScreen(game);
        }
    },

    MENU {
        public AbstractScreen getScreen(ChainBreak game, Object... params)
        {
            return new MenuScreen(game);
        }
    },

    LEVEL {
        public AbstractScreen getScreen(ChainBreak game, Object... params)
        {
            return new LevelScreen(game);
        }
    },

    GAME {
        public AbstractScreen getScreen(ChainBreak game, Object... params)
        {
            return new GameScreen(game, (Integer) params[0]);
        }
    };

    public abstract AbstractScreen getScreen(ChainBreak game, Object... params);
}
