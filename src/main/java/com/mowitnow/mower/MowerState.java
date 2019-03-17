package com.mowitnow.mower;

import com.mowitnow.mow2d.Point;

/**
 * Represent the state of a Mower: position and orientation.
 *
 * @author didier
 * @version 1.0
 */
public class MowerState {
    private Orientation orientation;
    private Point position;

    public MowerState(Orientation orientation, Point position) {
        this.orientation = orientation;
        this.position = position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Point getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "MowerState{" +
                "orientation=" + orientation +
                ", position=" + position +
                '}';
    }
}
