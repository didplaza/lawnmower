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
    private MowerListener listener;

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
        listener = null;
    }

    public void setListener(MowerListener listener) {
        this.listener = listener;
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
    public void execute(List<Command> commands) {
        commands.forEach(this::runCommand);
        if(listener != null)
            listener.commandsExecuted(new MowerState(orientation, position));
    }

    /**
     * execute a single command and return his position and orientation.
     *
     * @param command the Command to execute
     * @return the state after the command execution
     */
    private void runCommand(Command command) {
        switch (command) {
            case D:
            case G:
                rotate( command == Command.D);
                break;
            case A:
                forward();
                break;
            default:
                break;
        }
    }

    /**
     * Rotate mower by 90° (clockwize=true) or -90° (clockwize=false)
     *
     * @param clockwise rotation orientation
     */
    private void rotate(boolean clockwise) {
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
                break;
        }
    }

    /**
     * One step forward depending of the orientation. If the mower go outsize the lawn, position is unchanged
     *
     */
    private void forward() {
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
                return;
        }

        if (lawnLimit.contains(nextPosition))
            position = nextPosition;
    }
}
