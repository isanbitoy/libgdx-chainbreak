package com.chainbreak.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.utils.Array;
import com.chainbreak.game.screens.GameScreen;
import com.chainbreak.game.utils.Constants;

public class Actor extends Sprite
{
    private enum State { IDLE, STEER_LEFT, STEER_RIGHT, DEAD, WARP }
    private State currentState;
    private GameScreen screen;
    private Body body;
    private World world;
    private float x, y, stateTime;

    private Animation leftSwing, rightSwing, charDead, charWarp;

    private RopeJoint ropeJoint;
    private RopeJointDef ropeJointDef;

    private Vector2 linearForce;
    private boolean dead, touchdown, faceRight;

    public Actor(GameScreen screen, float x, float y)
    {
        this.screen = screen;
        this.world = screen.getWorld();
        this.x = x;
        this.y = y;
        stateTime = 0;

        setCharToDead(false);
        setTouchdown(false);
        setFaceRight(true);

        ropeJoint = new RopeJoint(world, 0);
        ropeJointDef = new RopeJointDef();

        linearForce = new Vector2(0, 0);

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getGame().getLeftSwing(), i * 48, 0, 48, 48));
        leftSwing = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getGame().getRightSwing(), i * 48, 0, 48, 48));
        rightSwing = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(screen.getGame().getPoof(), i * 48, 0, 48, 48));
        charDead = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(screen.getGame().getWarp(), i * 48, 0, 48, 48));
        charWarp = new Animation(0.1f, frames);
        frames.clear();

        createBody();

        setBounds(getX(), getY(), 48 / Constants.PPM, 48 / Constants.PPM);
    }

    public void update(float delta)
    {
        setRegion(getFrame(delta));
        setPosition(body.getPosition().x - getWidth() / 2.0f, body.getPosition().y - getHeight() / 1.5f);

        for (Chain chain : screen.getArrayChain())
        {
            ropeJointDef.bodyA = chain.getSegments();
            ropeJointDef.bodyB = body;
            ropeJointDef.collideConnected = true;
            ropeJointDef.maxLength = 16 / Constants.PPM;
            ropeJointDef.localAnchorA.set(0, 0);
            ropeJointDef.localAnchorB.set(0, 2 / Constants.PPM);

            if (isCharDead() || body.getPosition().y < 0)
                screen.initGameOver(true);
            else if(isTouchdown())
                screen.initNextLevel(true);
            else
            {
                if (!world.isLocked() && chain.isAttach())
                    ropeJoint = (RopeJoint) world.createJoint(ropeJointDef);
                if (!world.isLocked() && chain.isDetach()){
                    final Array<JointEdge> list = body.getJointList();
                    while (list.size > 0)
                        world.destroyJoint(list.first().joint);
                    list.clear();
                    ropeJoint = null;
                }
                body.applyForce(getLinearForce(), body.getWorldCenter(), true);
            }
        }
    }

    private TextureRegion getFrame(float delta)
    {
        currentState = getState();

        TextureRegion region;
        switch (currentState)
        {
            case DEAD:
                region = charDead.getKeyFrame(stateTime, false);
                break;
            case WARP:
                region = charWarp.getKeyFrame(stateTime, false);
                break;
            case STEER_LEFT:
                if (isFacingRight())
                    region = leftSwing.getKeyFrame(stateTime, true);
                else
                    region = rightSwing.getKeyFrame(stateTime, true);
                break;
            case STEER_RIGHT:
                if (isFacingRight())
                    region = rightSwing.getKeyFrame(stateTime, true);
                else
                    region = leftSwing.getKeyFrame(stateTime, true);
                break;
            default:
                region = screen.getGame().getMinerIdle();
        }

        if (!isFacingRight() && !region.isFlipX())
        {
            region.flip(true, false);
            setFaceRight(false);
        }
        else if (isFacingRight() && region.isFlipX())
        {
            region.flip(true, false);
            setFaceRight(true);
        }

        stateTime += delta;
        return region;
    }

    private State getState()
    {
        if (isCharDead())
            return State.DEAD;
        if (isTouchdown())
            return State.WARP;
        if (body.getLinearVelocity().x < 0 && currentState == State.STEER_LEFT)
            return State.STEER_LEFT;
        else if (body.getLinearVelocity().x > 0 && currentState == State.STEER_RIGHT)
            return State.STEER_RIGHT;
        else
            return State.IDLE;
    }

    public void steerRight()
    {
        currentState = State.STEER_RIGHT;
        setLinearForce(body.getMass() * 12.5f / Constants.PPM, 0);
    }

    public void steerLeft()
    {
        currentState = State.STEER_LEFT;
        setLinearForce(body.getMass() * -12.5f / Constants.PPM, 0);
    }

    private void setLinearForce(float forceX, float forceY)
    {
        linearForce.x = forceX;
        linearForce.y = forceY;
    }

    private Vector2 getLinearForce()
    {
        return linearForce;
    }

    public void setCharToDead(boolean dead)
    {
        stateTime = 0;
        this.dead = dead;
    }

    public void setTouchdown(boolean touchdown)
    {
        stateTime = 0;
        this.touchdown = touchdown;
    }
    public void setFaceRight(boolean faceRight)
    {
        this.faceRight = faceRight;
    }

    public boolean isCharDead()
    {
        return dead;
    }
    public boolean isTouchdown()
    {
        return touchdown;
    }
    public boolean isFacingRight()
    {
        return faceRight;
    }

    public void onHitByObject()
    {
        setCharToDead(true);

        Filter filter = new Filter();
        filter.maskBits = Constants.NULL_BIT;
        for(Fixture fixture : body.getFixtureList())
            fixture.setFilterData(filter);

        screen.getGame().getHitSound().play();
    }

    public void inPortal()
    {
        setTouchdown(true);

        Filter filter = new Filter();
        filter.maskBits = Constants.NULL_BIT;
        for(Fixture fixture : body.getFixtureList())
            fixture.setFilterData(filter);
    }

    protected void createBody()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shapeA = new CircleShape();
        shapeA.setRadius(15.0f / Constants.PPM);
        fixtureDef.filter.categoryBits = Constants.ACTOR_BIT;
        fixtureDef.filter.maskBits = Constants.ROPE_BIT |
                Constants.GROUND_BIT |
                Constants.TRAP_BIT |
                Constants.TRAP_BARREL_BIT |
                Constants.PORTAL_BIT |
                Constants.ARROW_A_BIT |
                Constants.ARROW_B_BIT;
        fixtureDef.shape = shapeA;
        body.createFixture(fixtureDef).setUserData(this);
        shapeA.dispose();

        PolygonShape shapeB = new PolygonShape();
        shapeB.setAsBox(15.0f / Constants.PPM, 8.0f / Constants.PPM, new Vector2(0, -23 / Constants.PPM), 0);
        fixtureDef.density = 1.5f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.friction = 0.1f;
        fixtureDef.shape = shapeB;
        body.createFixture(fixtureDef).setUserData(this);
        body.setFixedRotation(true);
        shapeB.dispose();
    }

    public Body getBody()
    {
        return body;
    }
}
