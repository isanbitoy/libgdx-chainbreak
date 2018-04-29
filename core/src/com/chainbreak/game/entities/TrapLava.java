package com.chainbreak.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.chainbreak.game.screens.GameScreen;
import com.chainbreak.game.utils.Constants;

public class TrapLava extends Trap
{
    private Animation animation;
    private float stateTime;

    public TrapLava(GameScreen screen, float x, float y)
    {
        super(screen, x, y);

        stateTime = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getGame().getLava(), i * 32, 0, 32, 32));
        animation = new Animation(0.1f, frames);
        frames.clear();

        setBounds(getX(), getY(), 32 / Constants.PPM, 32 / Constants.PPM);
    }

    @Override
    public void update(float delta)
    {
        stateTime += delta;
        setRegion(animation.getKeyFrame(stateTime, true));
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    @Override
    protected void defineBody()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16.0f / Constants.PPM, 16.0f / Constants.PPM);
        fixtureDef.filter.categoryBits = Constants.TRAP_BIT;
        fixtureDef.filter.maskBits = Constants.ACTOR_BIT;
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }
}
