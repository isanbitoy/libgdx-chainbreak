package com.chainbreak.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.chainbreak.game.screens.GameScreen;
import com.chainbreak.game.utils.Constants;

public class Chain
{
    private GameScreen screen;
    private World world;
    private Body body;
    private Body[] segments;

    private float x, y;
    private boolean attachable, detachable;

    public Chain(GameScreen screen, float x, float y)
    {
        this.screen = screen;
        this.world = screen.getWorld();
        this.x = x;
        this.y = y;

        setAttach(false);
        setDetach(false);

        createBody();
        createChain(17);
    }

    public void renderObject(SpriteBatch spriteBatch)
    {
        for (int i = 0; i <segments.length; i++)
        {
            spriteBatch.draw(screen.getGame().getChain(), segments[i].getPosition().x - 2.5f / Constants.PPM, segments[i].getPosition().y - 2.5f / Constants.PPM, 6 / Constants.PPM, 6 / Constants.PPM);
        }
    }

    protected void createBody()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16.0f / Constants.PPM, 16.0f / Constants.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    private Body[] createChain(int length)
    {
        segments = new Body[length];
        RevoluteJoint[] revoluteJoints = new RevoluteJoint[length - 1];
        RopeJoint[] ropeJoints = new RopeJoint[length - 1];

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y-32 / Constants.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        float radius = 3.5f, circumference = 2.0f;

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Constants.PPM);
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = Constants.ROPE_BIT;
        fixtureDef.filter.maskBits = Constants.ACTOR_BIT;
        fixtureDef.shape = shape;

        for(int i=0; i < segments.length; i++)
        {
            segments[i] = world.createBody(bodyDef);
            segments[i].createFixture(fixtureDef).setUserData(this);
        }

        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.localAnchorA.y = -circumference / Constants.PPM;
        revoluteJointDef.localAnchorB.y = circumference / Constants.PPM;

        for(int i = 0; i < revoluteJoints.length; i++)
        {
            revoluteJointDef.bodyA = segments[i];
            revoluteJointDef.bodyB = segments[i + 1];
            revoluteJoints[i] = (RevoluteJoint) world.createJoint(revoluteJointDef);
        }

        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.localAnchorA.set(0, -circumference / Constants.PPM);
        ropeJointDef.localAnchorB.set(0, circumference / Constants.PPM);

        for(int i = 0; i < ropeJoints.length; i++)
        {
            ropeJointDef.bodyA = segments[i];
            ropeJointDef.bodyB = segments[i + 1];
            ropeJoints[i] = (RopeJoint) world.createJoint(ropeJointDef);
        }

        RopeJointDef ropeJointA = new RopeJointDef();
        ropeJointA .bodyA = body;
        ropeJointA .bodyB = segments[0];
        ropeJointA .collideConnected = true;
        ropeJointA .maxLength = 2 / Constants.PPM;
        ropeJointA .localAnchorA.set(0, -16 / Constants.PPM);
        ropeJointA .localAnchorB.set(0, 0);

        world.createJoint(ropeJointA);

        return segments;
    }

    public void onAttach()
    {
        setAttach(true);
        setDetach(false);
    }
    public void setAttach(boolean attachable)
    {
        this.attachable = attachable;
    }
    public boolean isAttach()
    {
        return attachable;
    }

    public void onDetach()
    {
        setDetach(true);
        setAttach(false);
    }
    public void setDetach(boolean detachable)
    {
        this.detachable = detachable;
    }
    public boolean isDetach()
    {
        return detachable;
    }
    public Body getSegments()
    {
        return segments[16];
    }
}
