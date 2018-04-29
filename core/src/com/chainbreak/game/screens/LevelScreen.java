package com.chainbreak.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chainbreak.game.ChainBreak;
import com.chainbreak.game.enums.TrapFireType;
import com.chainbreak.game.utils.Constants;
import com.chainbreak.game.utils.CustomAnimation;
import com.chainbreak.game.utils.DataManager;
import com.chainbreak.game.utils.RandomUtils;

public class LevelScreen extends AbstractScreen
{
    private int state;
    private final int PAGE_1 = 0, PAGE_2 = 1;

    private final ChainBreak game;
    private SpriteBatch spriteBatch;
    private Viewport viewport;
    private Stage mainStage;
    private ImageButton rightArrow, leftArrow;
    private CustomAnimation fireAnimation;

    private LevelScreenPage levelScreenPage;

    private InputMultiplexer inputA, inputB;

    public LevelScreen(final ChainBreak game)
    {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();

        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        mainStage = new Stage(viewport, spriteBatch);

        DataManager.getInstance().load();

        state = PAGE_1;

        levelScreenPage = new LevelScreenPage(game, viewport);

        final TrapFireType trapFireType = RandomUtils.getInstance().getRandomTrapFireType();

        fireAnimation = new CustomAnimation(game.getFire(trapFireType.getAssetID()), 8, 0.5f);

        ImageButton.ImageButtonStyle rightArrowButtonStyle = new ImageButton.ImageButtonStyle();
        rightArrowButtonStyle.up = game.getSkin().getDrawable("right_up");
        rightArrowButtonStyle.down = game.getSkin().getDrawable("right_down");
        rightArrowButtonStyle.checked = game.getSkin().getDrawable("right_down");
        ImageButton.ImageButtonStyle leftArrowButtonStyle = new ImageButton.ImageButtonStyle();
        leftArrowButtonStyle.up = game.getSkin().getDrawable("left_up");
        leftArrowButtonStyle.down = game.getSkin().getDrawable("left_down");
        leftArrowButtonStyle.checked = game.getSkin().getDrawable("left_down");

        rightArrow = new ImageButton(rightArrowButtonStyle);
        rightArrow.setPosition(560, Constants.V_HEIGHT/2 - rightArrow.getWidth()/2);
        rightArrow.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightArrow.setChecked(true);
                state = PAGE_2;
                game.getClickSound().play();
                return false;
            }
        });

        leftArrow = new ImageButton(leftArrowButtonStyle);
        leftArrow.setPosition(32, Constants.V_HEIGHT/2 - leftArrow.getWidth()/2);
        leftArrow.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftArrow.setChecked(true);
                state = PAGE_1;
                game.getClickSound().play();
                return false;
            }
        });

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

        group.addActor(rightArrow);
        group.addActor(leftArrow);
        group.addActor(back);

        mainStage.addActor(group);

        inputA = new InputMultiplexer(mainStage, levelScreenPage.getStagePage1());
        inputB = new InputMultiplexer(mainStage, levelScreenPage.getStagePage2());
    }

    @Override
    public void show() {
        super.show();
    }

    private void update()
    {
        switch (state)
        {
            case PAGE_1:
                Gdx.input.setInputProcessor(inputA);
                levelScreenPage.getStagePage2().cancelTouchFocus();
                break;
            case PAGE_2:
                Gdx.input.setInputProcessor(inputB);
                levelScreenPage.getStagePage1().cancelTouchFocus();
                break;
        }
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);

        fireAnimation.update(delta);
        update();

        spriteBatch.begin();
        spriteBatch.draw(game.getBackground(), Constants.V_WIDTH/2 - game.getBackground().getWidth()/2, Constants.V_HEIGHT/2 - game.getBackground().getHeight()/2);
        spriteBatch.draw(fireAnimation.getFrame(), 192, 260, 32, 32);
        spriteBatch.draw(fireAnimation.getFrame(), 416, 260, 32, 32);
        spriteBatch.end();

        switch (state)
        {
            case PAGE_1:
                levelScreenPage.getStagePage1().draw();
                break;
            case PAGE_2:
                levelScreenPage.getStagePage2().draw();
                break;
        }

        mainStage.draw();
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
        mainStage.dispose();
        levelScreenPage.getStagePage1().dispose();
        levelScreenPage.getStagePage2().dispose();
    }
}
