package com.mowitnow.mower;

import com.mowitnow.mow2d.LawnLimit;
import com.mowitnow.mow2d.Point;

import java.util.List;

/**
 * Automatic mower object.
 *
 * A Mower execute Command or a List of Command and can not go outside the Lawn.
 * Default Mower position is 0,0 and orientation is N
 *
 * @author didier
 * @version 1.0
 */
public class Mower {
    private Point position;
    private Orientation orientation;
    private LawnLimit lawnLimit;

    /**
     * Constructor.
     *
     * @param lawnLimit the limit of the lawn
     * @throws MowerException if lawn limit is null
     */
    public Mower(LawnLimit lawnLimit) throws MowerException {
        if (lawnLimit == null) throw new MowerException("LawnLimit can't be null");
        this.lawnLimit = lawnLimit;
        position = new Point(0,0);
        orientation = Orientation.N;
    }

    /**
     * Retieve position and orientation
     *
     * @return the state of the mower
     */
    public MowerState getState() {
        return new MowerState(orientation, position);
    }

    /**
     * Set position and orientation
     *
     * @param state the position en orientation to set
     * @throws MowerException if state is invalid
     */
    public void setState(MowerState state) throws MowerException {
        if (state == null) throw new MowerException(("state cannot be null"));
        if (state.getOrientation() == null) throw new MowerException("MowerState orientation can't be null");
        this.orientation = state.getOrientation();
        Point p = state.getPosition();
        if (p == null) throw new MowerException("MowerState position can't be null");
        if (!lawnLimit.contains(p)) throw new MowerException(("position is outside the lawnLimit"));
        this.position = p;
    }

    /**
     * execute a list of command and return his position and orientation.
     *
     * @param commands list of commands to execute
     * @return the state after commands execution
     * @throws MowerException if something went wrong
     */
    public MowerState execute(List<Command> commands) throws MowerException {
        for (Command cmd : commands) {
            execute(cmd);
        }
        return getState();
    }

    /**
     * execute a single command and return his position and orientation.
     *
     * @param command the Command to execute
     * @return the state after the command execution
     * @throws MowerException if command is unknown
     */
    public MowerState execute(Command command) throws MowerException {
        if(position == null || orientation == null) throw new MowerException("Mower have not been initialized");
        switch (command) {
            case D:
                rotate(true);
                break;
            case G:
                rotate(false);
                break;
            case A:
                forward();
                break;
            default:
                throw new MowerException("Invalid command " + command.toString());
        }
        return getState();
    }

    /**
     * Rotate mower by 90° (clockwize=true) or -90° (clockwize=false)
     *
     * @param clockwise rotation orientation
     * @throws MowerException if orientation is unknown
     */
    private void rotate(boolean clockwise) throws MowerException {
        switch (orientation) {
            case N:
                orientation = clockwise ? Orientation.E : Orientation.W;
                break;
            case E:
                orientation = clockwise ? Orientation.S : Orientation.N;
                break;
            case S:
                orientation = clockwise ? Orientation.W : Orientation.E;
                break;
            case W:
                orientation = clockwise ? Orientation.N : Orientation.S;
                break;
            default:
                throw new MowerException("Invalid orientation " + orientation.toString());
        }
    }

    /**
     * One step forward depending of the orientation. If the mower go outsize the lawn, position is unchanged
     *
     * @throws MowerException if orientation is unknown
     */
    private void forward() throws MowerException {
        Point nextPosition;

        switch (orientation) {
            case N:
                nextPosition = position.translate(0, 1);
                break;
            case E:
                nextPosition = position.translate(1, 0);
                break;
            case W:
                nextPosition = position.translate(-1, 0);
                break;
            case S:
                nextPosition = position.translate(0, -1);
                break;
            default:
                throw new MowerException("Invalid orientation" + orientation.toString());
        }

        if (lawnLimit.contains(nextPosition))
            position = nextPosition;
    }
}
