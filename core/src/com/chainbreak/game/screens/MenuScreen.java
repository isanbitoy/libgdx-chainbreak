package com.chainbreak.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chainbreak.game.ChainBreak;
import com.chainbreak.game.enums.TrapFireType;
import com.chainbreak.game.utils.Constants;
import com.chainbreak.game.utils.CustomAnimation;
import com.chainbreak.game.utils.RandomUtils;

public class MenuScreen extends AbstractScreen
{
    private int state;
    private final int MENU_SCREEN = 1, INFO_SCREEN = 2;

    private final ChainBreak game;
    private SpriteBatch spriteBatch;
    private Viewport viewport;
    private Stage menuStage;
    private Label text;
    private float fadeTime, elapseTime;
    private CustomAnimation fireAnimation;
    private InfoPage infoPage;

    private Vector3 touchPoint;
    private Rectangle soundBound;
    private boolean soundEnable;

    public MenuScreen(ChainBreak game)
    {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();

        state = MENU_SCREEN;

        elapseTime = 0;
        fadeTime = 1.0f;

        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());

        touchPoint = new Vector3();
        soundBound = new Rectangle(Constants.V_WIDTH - 72, Constants.V_HEIGHT - 72, 64, 64);

        setSoundEnable(true);
        game.getMainMusic().play();

        TrapFireType trapFireType = RandomUtils.getInstance().getRandomTrapFireType();
        fireAnimation = new CustomAnimation(game.getFire(trapFireType.getAssetID()), 8, 0.5f);

        initMenuStage();

        infoPage = new InfoPage(game, viewport);
    }

    @Override
    public void show()
    {
        super.show();
    }

    private void initMenuStage()
    {
        menuStage = new Stage(viewport, spriteBatch);
        menuStage.clear();

        Image title = new Image(game.getTitle());
        title.setBounds(Constants.V_WIDTH/2 - title.getWidth()/2, Constants.V_HEIGHT/1.5f - title.getHeight(), title.getWidth(), title.getHeight());

        text = new Label("tap here to start", new Label.LabelStyle(game.initSmallFont(), Color.WHITE));
        text.setPosition(Constants.V_WIDTH/2 - text.getWidth()/2, 100);

        text.addAction(Actions.alpha(0));
        text.act(0);

        text.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenManager.getInstance().showScreen(ScreenEnum.LEVEL);
                return false;
            }
        });

        Image info = new Image(game.getInfo());
        info.setBounds(Constants.V_WIDTH - 128, Constants.V_HEIGHT - 70, 64, 64);
        info.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                state = INFO_SCREEN;
                dispose();
                return false;
            }
        });

        Group group = new Group();

        group.addActor(title);
        group.addActor(text);
        group.addActor(info);

        menuStage.addActor(group);
    }

    private void menuStageUpdate(float delta)
    {
        Gdx.input.setInputProcessor(menuStage);

        elapseTime += delta;
        text.addAction(Actions.alpha(Interpolation.fade.apply((elapseTime / fadeTime) % 1f)));
        text.act(delta);

        if (Gdx.input.justTouched())
        {
            viewport.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (soundBound.contains(touchPoint.x, touchPoint.y))
            {
                soundEnable = !soundEnable;
                if (isSoundEnable())
                    game.getMainMusic().play();
                else
                    game.getMainMusic().pause();
            }
        }
    }

    private void update(float delta)
    {
        switch (state)
        {
            case MENU_SCREEN:
                menuStageUpdate(delta);
                break;
            case INFO_SCREEN:
                Gdx.input.setInputProcessor(infoPage.getInfoStage());
                break;
        }
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);

        update(delta);
        fireAnimation.update(delta);

        spriteBatch.begin();
        spriteBatch.draw(game.getBackground(), Constants.V_WIDTH/2 - game.getBackground().getWidth()/2, Constants.V_HEIGHT/2 - game.getBackground().getHeight()/2);
        spriteBatch.draw(fireAnimation.getFrame(), 192, 260, 32, 32);
        spriteBatch.draw(fireAnimation.getFrame(), 416, 260, 32, 32);
        spriteBatch.draw(isSoundEnable() ? game.getMusicOn() : game.getMusicOff(), soundBound.getX(), soundBound.getY(), soundBound.getWidth(), soundBound.getHeight());
        spriteBatch.end();

        switch (state)
        {
            case MENU_SCREEN:
                menuStage.draw();
                break;
            case INFO_SCREEN:
                infoPage.getInfoStage().draw();
                break;
        }

    }

    private void setSoundEnable(boolean soundEnable)
    {
        this.soundEnable = soundEnable;
    }

    private boolean isSoundEnable()
    {
        return soundEnable;
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        menuStage.dispose();
    }
}
