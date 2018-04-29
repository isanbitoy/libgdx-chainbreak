package com.chainbreak.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.chainbreak.game.screens.GameScreen;
import com.chainbreak.game.utils.Constants;

public class Portal extends Sprite
{
    private World world;
    private Body body;
    private Animation animation;
    private float x, y, stateTime;

    public Portal(GameScreen screen, float x, float y)
    {
        this.world = screen.getWorld();
        this.x = x;
        this.y = y;

        stateTime = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(screen.getGame().getWormHole(), i * 64, 0, 64, 64));
        animation = new Animation(0.1f, frames);
        frames.clear();

        defineBody();

        setBounds(getX(), getY(), 128 / Constants.PPM, 128 / Constants.PPM);
    }

    public void update(float delta)
    {
        stateTime += delta;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(animation.getKeyFrame(stateTime, true));
    }

    private void defineBody()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(16 / Constants.PPM);
        fixtureDef.filter.categoryBits = Constants.PORTAL_BIT;
        fixtureDef.filter.maskBits = Constants.ACTOR_BIT;
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }
}
