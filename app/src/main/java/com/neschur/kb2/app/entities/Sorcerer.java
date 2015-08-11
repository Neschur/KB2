package com.neschur.kb2.app.entities;

import com.neschur.kb2.app.R;
import com.neschur.kb2.app.models.MapPoint;
import com.neschur.kb2.app.models.Mover;

public class Sorcerer extends EntityImpl implements Moving {
    private final Mover mover;

    public Sorcerer(MapPoint point) {
        super(point);
        mover = new Mover(point.getGlade());
    }

    @Override
    public int getID() {
        return R.drawable.sorcerer;
    }

    @Override
    public void moveInDirection(MapPoint point) {
        mover.moveInDirection(this, point);
    }

    @Override
    public void moveInRandomDirection() {
        mover.moveInRandomDirection(this);
    }

    @Override
    public boolean canMoveTo(MapPoint point) {
        return mover.canMoveTo(point);
    }
}
