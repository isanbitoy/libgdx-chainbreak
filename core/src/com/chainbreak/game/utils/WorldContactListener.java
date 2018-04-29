package com.chainbreak.game.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.chainbreak.game.entities.Actor;
import com.chainbreak.game.entities.Chain;
import com.chainbreak.game.entities.TrapBarrel;

public class WorldContactListener implements ContactListener
{
    public WorldContactListener()
    {
        super();
    }

    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int charDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (charDef)
        {
            case Constants.ACTOR_BIT | Constants.ROPE_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ACTOR_BIT)
                    ((Chain) fixB.getUserData()).onAttach();
                else
                    ((Chain) fixA.getUserData()).onAttach();
                break;
            case Constants.TRAP_BIT | Constants.ACTOR_BIT:
                if (fixA.getFilterData().categoryBits == Constants.TRAP_BIT)
                    ((Actor) fixB.getUserData()).onHitByObject();
                else
                    ((Actor) fixA.getUserData()).onHitByObject();
                break;
            case Constants.TRAP_BARREL_BIT | Constants.ACTOR_BIT:
                if (fixA.getFilterData().categoryBits == Constants.TRAP_BARREL_BIT)
                {
                    ((Actor) fixB.getUserData()).onHitByObject();
                    ((TrapBarrel) fixA.getUserData()).onHit();
                }
                else
                {
                    ((Actor) fixA.getUserData()).onHitByObject();
                    ((TrapBarrel) fixB.getUserData()).onHit();
                }
                break;
            case Constants.PORTAL_BIT | Constants.ACTOR_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PORTAL_BIT)
                    ((Actor) fixB.getUserData()).inPortal();
                else
                    ((Actor) fixA.getUserData()).inPortal();
                break;
            case Constants.ARROW_A_BIT | Constants.ACTOR_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ARROW_A_BIT)
                    ((Actor) fixB.getUserData()).setFaceRight(false);
                else
                    ((Actor) fixA.getUserData()).setFaceRight(false);
                break;
            case Constants.ARROW_B_BIT | Constants.ACTOR_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ARROW_B_BIT)
                    ((Actor) fixB.getUserData()).setFaceRight(true);
                else
                    ((Actor) fixA.getUserData()).setFaceRight(true);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
