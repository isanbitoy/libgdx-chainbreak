package com.chainbreak.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chainbreak.game.ChainBreak;
import com.chainbreak.game.utils.Constants;

public class LoadingScreen extends AbstractScreen
{
    private ChainBreak game;
    private SpriteBatch spriteBatch;
    private Viewport viewport;
    private Stage stage;
    private Label text;
    private NinePatch empty, full;
    private float progress, fadeTime, elapseTime;
    private float position, maxWidth;

    public LoadingScreen(ChainBreak game)
    {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());

        elapseTime = 0;
        fadeTime = 1.0f;
        position = Constants.V_HEIGHT/2 - 32;
        maxWidth = 400;

        empty = new NinePatch(new TextureRegion(game.getLoadBackground(), 19, 26), 8, 8, 8, 8);
        full = new NinePatch(new TextureRegion(game.getLoadForeground(), 19, 26), 8, 8, 8, 8);

        progress = 0;

        stage = new Stage(viewport, spriteBatch);
        stage.clear();

        text = new Label("loading...", new Label.LabelStyle(game.initSmallFont(), Color.WHITE));
        text.setPosition(120, position + 24);

        text.addAction(Actions.alpha(0));
        text.act(0);

        stage.addActor(text);
    }

    private void update(float delta)
    {
        elapseTime += delta;
        text.addAction(Actions.alpha(Interpolation.fade.apply((elapseTime / fadeTime) % 1f)));
        text.act(delta);

        progress = MathUtils.lerp(progress, game.getAssetHandler().getManager().getProgress(), 0.01f);
        if (game.getAssetHandler().getManager().update() && progress >= game.getAssetHandler().getManager().getProgress() - 0.001f)
        {
            ScreenManager.getInstance().showScreen(ScreenEnum.MENU);
            this.dispose();
        }
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);

        Gdx.gl.glClearColor(159/255.0f, 220/255.0f, 235/255.0f, 0xff/255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        spriteBatch.begin();
        empty.draw(spriteBatch, Constants.V_WIDTH/2 - maxWidth/2, position, maxWidth, 24);
        full.draw(spriteBatch, Constants.V_WIDTH/2 - maxWidth/2, position, progress * maxWidth, 24);
        spriteBatch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
