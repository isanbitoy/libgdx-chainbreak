package com.chainbreak.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.chainbreak.game.screens.GameScreen;
import com.chainbreak.game.utils.Constants;

public class BouncePad extends Sprite
{
    private GameScreen screen;
    private World world;
    private Body body1, body2;
    private float x, y;

    private final BodyDef bodyDef = new BodyDef();
    private final FixtureDef fixtureDef = new FixtureDef();
    private final DistanceJointDef distanceJointDef = new DistanceJointDef();

    private Sprite crate;

    public BouncePad(GameScreen screen, float x, float y)
    {
        this.screen = screen;
        this.world = screen.getWorld();
        this.x = x;
        this.y = y;

        defineBodyA();
        defineBodyB();
        defineJoint();

        crate = new Sprite(screen.getGame().getCrate());

        setBounds(getX(), getY(), 32 / Constants.PPM, 32 / Constants.PPM);
    }

    public void update()
    {
        setRegion(screen.getGame().getSpring());
        setPosition(body2.getPosition().x - getWidth() / 2.0f, body2.getPosition().y - getHeight() / 2.0f);
    }

    public void renderObject(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(crate, body1.getPosition().x - (crate.getWidth() / 2) / Constants.PPM, body1.getPosition().y - (crate.getHeight() / 2) / Constants.PPM, 32 / Constants.PPM, 32 / Constants.PPM);
    }

    //static crate
    private void defineBodyA()
    {
        PolygonShape shape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(x, y);

        body1 = world.createBody(bodyDef);
        Body body3 = world.createBody(bodyDef);

        fixtureDef.shape = shape;

        shape.setAsBox(16.0f / Constants.PPM, 16.0f / Constants.PPM);
        body1.createFixture(fixtureDef);

        shape.setAsBox(1.5f / Constants.PPM, 18.0f / Constants.PPM, new Vector2(14.5f / Constants.PPM, 8.0f / Constants.PPM), 0);
        body3.createFixture(fixtureDef);

        shape.setAsBox(1.5f / Constants.PPM, 18.0f / Constants.PPM, new Vector2(-14.0f / Constants.PPM, 8.0f / Constants.PPM), 0);
        body3.createFixture(fixtureDef);

        shape.dispose();
    }

    //spring board
    private void defineBodyB()
    {
        PolygonShape shape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(x, y);

        body2 = world.createBody(bodyDef);

        shape.setAsBox(12.0f / Constants.PPM, 16.0f / Constants.PPM);
        fixtureDef.density = 7.0f;
        fixtureDef.restitution = 2.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.shape = shape;

        body2.createFixture(fixtureDef);
        body2.setFixedRotation(true);

        shape.dispose();
    }

    private void defineJoint()
    {
        distanceJointDef.bodyA = body1;
        distanceJointDef.bodyB = body2;
        distanceJointDef.collideConnected = false;
        distanceJointDef.length = 24 / Constants.PPM;
        distanceJointDef.frequencyHz = 1.0f;
        distanceJointDef.dampingRatio = 0.1f;

        world.createJoint(distanceJointDef);
    }
}
