package com.chainbreak.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chainbreak.game.ChainBreak;
import com.chainbreak.game.utils.Constants;

public class InfoPage
{
    private final ChainBreak game;
    private Stage infoStage;

    public InfoPage(ChainBreak game, Viewport viewport)
    {
        this.game = game;
        infoStage = new Stage(viewport, game.getSpriteBatch());
        infoStage.clear();

        initInfoStage();
    }

    public void initInfoStage()
    {
        Label story = new Label("    Guide the explorer out of the dungeon\n" + "and use the chains to maneuver your escape.", new Label.LabelStyle(game.initSmallFont(), Color.WHITE));
        story.setPosition(Constants.V_WIDTH/2 - story.getWidth()/2, 215);

        Label instruction = new Label("Pan the screen in swing motion,\n" + "and tap the screen to release.", new Label.LabelStyle(game.initSmallFont(), Color.WHITE));
        instruction.setPosition(Constants.V_WIDTH/2 - instruction.getWidth()/2, 150);

        Label info1 = new Label("developed by : Lisandro A. Bitoy", new Label.LabelStyle(game.initSmallFont(), Color.WHITE));
        info1.setPosition(Constants.V_WIDTH/2 - info1.getWidth()/2, 85);

        Label info2 = new Label("credits : www.kenney.nl", new Label.LabelStyle(game.initSmallFont(), Color.WHITE));
        info2.setPosition(Constants.V_WIDTH/2 - info2.getWidth()/2, 50);

        final Image back = new Image(game.getBack());
        back.setBounds(8, Constants.V_HEIGHT - 72, 64, 64);
        back.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenManager.getInstance().showScreen(ScreenEnum.MENU);
                dispose();
                return false;
            }
        });

        Group group = new Group();

        group.addActor(story);
        group.addActor(instruction);
        group.addActor(info1);
        group.addActor(info2);
        group.addActor(back);

        infoStage.addActor(group);
    }

    public void dispose()
    {
        infoStage.dispose();
    }
    public Stage getInfoStage()
    {
        return infoStage;
    }
}
