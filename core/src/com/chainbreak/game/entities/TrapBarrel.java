package com.chainbreak.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.chainbreak.game.enums.TrapBarrelType;
import com.chainbreak.game.screens.GameScreen;
import com.chainbreak.game.utils.Constants;
import com.chainbreak.game.utils.RandomUtils;

public class TrapBarrel extends Trap
{
    private enum State { IDLE, EXPLODE }
    //private State currentState;
    private Animation poofAnimation;
    private float stateTime;
    private boolean explode;
    private  TrapBarrelType trapBarrelType;

    public TrapBarrel(GameScreen screen, float x, float y)
    {
        super(screen, x, y);
        stateTime = 0;

        setObjectExplode(false);

        trapBarrelType = RandomUtils.getInstance().getRandomTrapBarrelType();

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getGame().getBlast(), i * 32, 0, 32, 32));
        poofAnimation = new Animation(0.08f, frames);
        frames.clear();

        setBounds(getX(), getY(), 32 / Constants.PPM, 32 / Constants.PPM);
    }

    @Override
    public void update(float delta)
    {
        setRegion(getFrame(delta));
        setPosition(body.getPosition().x - getWidth() / 2.0f, body.getPosition().y - getHeight() / 2.0f);
    }

    private TextureRegion getFrame(float delta)
    {
        State currentState = getState();

        TextureRegion region;
        switch (currentState)
        {
            case EXPLODE:
                region = poofAnimation.getKeyFrame(stateTime, false);
                setSize(48 / Constants.PPM, 48 / Constants.PPM);
                break;
            default:
                region = screen.getGame().getBarrel(trapBarrelType.getAssetID());
        }

        stateTime += delta;
        return region;
    }

    private State getState()
    {
        if (isObjectExplode())
            return State.EXPLODE;
        else
            return State.IDLE;
    }

    public void setObjectExplode(boolean explode)
    {
        stateTime = 0;
        this.explode = explode;
    }

    public boolean isObjectExplode()
    {
        return explode;
    }

    public void onHit()
    {
        setObjectExplode(true);
        game.getExplosionSound().play(0.5f);
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
        fixtureDef.filter.categoryBits = Constants.TRAP_BARREL_BIT;
        fixtureDef.filter.maskBits = Constants.ACTOR_BIT;
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }
}
