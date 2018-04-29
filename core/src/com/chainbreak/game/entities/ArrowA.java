package com.chainbreak.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.chainbreak.game.screens.GameScreen;
import com.chainbreak.game.utils.Constants;

public class ArrowA extends Arrow
{
    private Animation animation;
    private float stateTime, rotation;

    public ArrowA(GameScreen screen, float rotation, float x, float y)
    {
        super(screen, x, y);
        this.rotation = rotation;
        stateTime = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(screen.getGame().getArrowA(), i * 32, 0, 32, 32));
        animation = new Animation(0.1f, frames);
        frames.clear();

        setBounds(getX(), getY(), 32 / Constants.PPM, 32 / Constants.PPM);
    }

    @Override
    public void update(float delta)
    {
        stateTime += delta;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(animation.getKeyFrame(stateTime, true));

        setRotation(rotation);
        setOriginCenter();
    }

    @Override
    protected void defineBody()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX(), getY());
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(32 / Constants.PPM);
        fixtureDef.filter.categoryBits = Constants.ARROW_A_BIT;
        fixtureDef.filter.maskBits = Constants.ACTOR_BIT;
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }
}
