package com.chainbreak.game.utils;

import com.badlogic.gdx.input.GestureDetector;

public class DirectionGesture extends GestureDetector
{
    public interface DirectionListener
    {
        void swingLeft();

        void swingRight();

        void onRelease();
    }

    public DirectionGesture(DirectionListener directionListener)
    {
        super(new DirectionGestureListener(directionListener));
    }

    private static class DirectionGestureListener extends GestureAdapter
    {
        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener){
            this.directionListener = directionListener;
        }

        //single tap
        @Override
        public boolean tap(float x, float y, int count, int button) {
            if (count == 1){
                directionListener.onRelease();
                return true;
            }
            else
                return false;
        }

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY)
        {
            if (Math.abs(deltaX) > Math.abs(deltaY))
            {
                if (deltaX > 0)
                    directionListener.swingRight();
                else
                    directionListener.swingLeft();
            }
            return true;
        }
    }
}
