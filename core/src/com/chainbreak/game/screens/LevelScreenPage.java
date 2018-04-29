package com.chainbreak.game.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chainbreak.game.ChainBreak;
import com.chainbreak.game.utils.DataManager;

public class LevelScreenPage
{
    private ChainBreak game;
    private Stage stagePage1, stagePage2;
    private TextButton.TextButtonStyle levelButtonStyle;

    public LevelScreenPage(ChainBreak game, Viewport viewport)
    {
        this.game = game;
        stagePage1 = new Stage(viewport, game.getSpriteBatch());
        stagePage2 = new Stage(viewport, game.getSpriteBatch());

        levelButtonStyle = new TextButton.TextButtonStyle();
        levelButtonStyle.font = game.initMediumFont();
        levelButtonStyle.up = game.getSkin().getDrawable("button_unpressed");
        levelButtonStyle.down = game.getSkin().getDrawable("button_pressed");
        levelButtonStyle.checked = game.getSkin().getDrawable("button_pressed");
        levelButtonStyle.disabled = game.getSkin().getDrawable("level_locked");

        initPage1();
        initPage2();
    }

    //Page 1
    public void initPage1()
    {
        final TextButton buttonLvl1 = new TextButton("1", levelButtonStyle);
        buttonLvl1.setDisabled(false);
        buttonLvl1.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                buttonLvl1.setChecked(true);
                game.getClickSound().play();
                Timer.schedule(new Timer.Task()
                {
                    @Override
                    public void run() {
                        ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 1);
                    }
                }, 0.5f);
                return true;
            }
        });

        final TextButton buttonLvl2 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl2())
        {
            buttonLvl2.setDisabled(false);
            buttonLvl2.setText("2");
            buttonLvl2.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl2.setChecked(true);
                    game.getClickSound().play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 2);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl2.setDisabled(true);

        final TextButton buttonLvl3 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl3())
        {
            buttonLvl3.setDisabled(false);
            buttonLvl3.setText("3");
            buttonLvl3.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl3.setChecked(true);
                    game.getClickSound().play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 3);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl3.setDisabled(true);

        final TextButton buttonLvl4 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl4())
        {
            buttonLvl4.setDisabled(false);
            buttonLvl4.setText("4");
            buttonLvl4.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl4.setChecked(true);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 4);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl4.setDisabled(true);

        final TextButton buttonLvl5 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl5())
        {
            buttonLvl5.setDisabled(false);
            buttonLvl5.setText("5");
            buttonLvl5.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl5.setChecked(true);
                    game.getClickSound().play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 5);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl5.setDisabled(true);

        final TextButton buttonLvl6 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl6())
        {
            buttonLvl6.setDisabled(false);
            buttonLvl6.setText("6");
            buttonLvl6.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl6.setChecked(true);
                    game.getClickSound().play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 6);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl6.setDisabled(true);

        final TextButton buttonLvl7 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl7())
        {
            buttonLvl7.setDisabled(false);
            buttonLvl7.setText("7");
            buttonLvl7.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl7.setChecked(true);
                    game.getClickSound().play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 7);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl7.setDisabled(true);

        final TextButton buttonLvl8 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl8())
        {
            buttonLvl8.setDisabled(false);
            buttonLvl8.setText("8");
            buttonLvl8.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl8.setChecked(true);
                    game.getClickSound().play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 8);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl8.setDisabled(true);

        final TextButton buttonLvl9 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl9())
        {
            buttonLvl9.setDisabled(false);
            buttonLvl9.setText("9");
            buttonLvl9.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl9.setChecked(true);
                    game.getClickSound().play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 9);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl9.setDisabled(true);

        final TextButton buttonLvl10 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl10())
        {
            buttonLvl10.setDisabled(false);
            buttonLvl10.setText("10");
            buttonLvl10.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl10.setChecked(true);
                    game.getClickSound().play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 10);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl10.setDisabled(true);

        final TextButton buttonLvl11 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl11())
        {
            buttonLvl11.setDisabled(false);
            buttonLvl11.setText("11");
            buttonLvl11.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl11.setChecked(true);
                    game.getClickSound().play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 11);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl11.setDisabled(true);

        final TextButton buttonLvl12 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl12())
        {
            buttonLvl12.setDisabled(false);
            buttonLvl12.setText("12");
            buttonLvl12.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLvl12.setChecked(true);
                    game.getClickSound().play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 12);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl12.setDisabled(true);

        Table table = new Table();
        table.setFillParent(true);

        table.add(buttonLvl1).size(64, 64).pad(16);
        table.add(buttonLvl2).size(64, 64).pad(16);
        table.add(buttonLvl3).size(64, 64).pad(16);
        table.add(buttonLvl4).size(64, 64).pad(16);
        table.row();
        table.add(buttonLvl5).size(64, 64).pad(16);
        table.add(buttonLvl6).size(64, 64).pad(16);
        table.add(buttonLvl7).size(64, 64).pad(16);
        table.add(buttonLvl8).size(64, 64).pad(16);
        table.row();
        table.add(buttonLvl9).size(64, 64).pad(16);
        table.add(buttonLvl10).size(64, 64).pad(16);
        table.add(buttonLvl11).size(64, 64).pad(16);
        table.add(buttonLvl12).size(64, 64).pad(16);

        stagePage1.addActor(table);
    }

    //Page 2
    public void initPage2()
    {
        final TextButton buttonLvl13 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl13())
        {
            buttonLvl13.setDisabled(false);
            buttonLvl13.setText("13");
            buttonLvl13.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    buttonLvl13.setChecked(true);
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 13);
                        }
                    }, 0.5f);
                    return true;

                }
            });
        }
        else buttonLvl13.setDisabled(true);

        final TextButton buttonLvl14 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl14())
        {
            buttonLvl14.setDisabled(false);
            buttonLvl14.setText("14");
            buttonLvl14.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    buttonLvl14.setChecked(true);
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 14);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl14.setDisabled(true);

        final TextButton buttonLvl15 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl15())
        {
            buttonLvl15.setDisabled(false);
            buttonLvl15.setText("15");
            buttonLvl15.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    buttonLvl15.setChecked(true);
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 15);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl15.setDisabled(true);

        final TextButton buttonLvl16 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl16())
        {
            buttonLvl16.setDisabled(false);
            buttonLvl16.setText("16");
            buttonLvl16.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    buttonLvl16.setChecked(true);
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 16);
                        }
                    }, 0.5f);
                    return true;
                }
            });
        }
        else buttonLvl16.setDisabled(true);

        final TextButton buttonLvl17 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl17())
        {
            buttonLvl17.setDisabled(false);
            buttonLvl17.setText("17");
            buttonLvl17.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    buttonLvl17.setChecked(true);
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 17);
                        }
                    }, 0.5f);
                    return true;

                }
            });
        }
        else buttonLvl17.setDisabled(true);

        final TextButton buttonLvl18 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl18())
        {
            buttonLvl18.setDisabled(false);
            buttonLvl18.setText("18");
            buttonLvl18.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    buttonLvl18.setChecked(true);
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 18);
                        }
                    }, 0.5f);
                    return true;

                }
            });
        }
        else buttonLvl18.setDisabled(true);

        final TextButton buttonLvl19 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl19())
        {
            buttonLvl19.setDisabled(false);
            buttonLvl19.setText("19");
            buttonLvl19.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    buttonLvl19.setChecked(true);
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 19);
                        }
                    }, 0.5f);
                    return true;

                }
            });
        }
        else buttonLvl19.setDisabled(true);

        final TextButton buttonLvl20 = new TextButton("", levelButtonStyle);
        if (DataManager.getInstance().getUnlockLvl20())
        {
            buttonLvl20.setDisabled(false);
            buttonLvl20.setText("20");
            buttonLvl20.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    buttonLvl20.setChecked(true);
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 20);
                        }
                    }, 0.5f);
                    return true;

                }
            });
        }
        else buttonLvl20.setDisabled(true);

        Table table = new Table();
        table.setFillParent(true);

        table.add(buttonLvl13).size(64, 64).pad(16);
        table.add(buttonLvl14).size(64, 64).pad(16);
        table.add(buttonLvl15).size(64, 64).pad(16);
        table.add(buttonLvl16).size(64, 64).pad(16);
        table.row();
        table.add(buttonLvl17).size(64, 64).pad(16);
        table.add(buttonLvl18).size(64, 64).pad(16);
        table.add(buttonLvl19).size(64, 64).pad(16);
        table.add(buttonLvl20).size(64, 64).pad(16);

        stagePage2.addActor(table);
    }

    public Stage getStagePage1()
    {
        return stagePage1;
    }
    public Stage getStagePage2()
    {
        return stagePage2;
    }
}
