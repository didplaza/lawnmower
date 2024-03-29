package com.mowitnow.helpers;

import com.mowitnow.mower.*;
import com.mowitnow.mow2d.LawnLimit;
import com.mowitnow.mow2d.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mower Data Helper: provide helpers to transform ASCII data.
 *
 * @author didier
 * @version 1.0
 */
public class MowerDataHelper {
    /**
     * Return a LawnLimit instance from data String.
     *
     * @param data the string containing data
     * @return a LawnLimit instance with data specified dimensions
     * @throws MowerException if data line is malformed
     */
    public static LawnLimit readLawnLimit(String data) throws MowerException {
        if (!data.matches("^[0-9]+ [0-9]+$"))
            throw new MowerException("Invalid lawn data line:" + data);

        String[] sCoords = data.split(" ");
        return new LawnLimit(Integer.decode(sCoords[0]), Integer.decode(sCoords[1]));

    }

    /**
     * Return MowerState object specified by data string
     *
     * @param data the String containing data
     * @return state corresponding to data line
     * @throws MowerException if data line is malformed
     */
    public static MowerState readMowerState(String data) throws MowerException {
        if (!data.matches("^[0-9]+ [0-9]+ [NESW]$"))
            throw new MowerException("Invalid mower data line:" + data);

        String[] strings = data.split(" ");

        Orientation orientation = Arrays.stream(Orientation.values())
                .filter(o -> o.name().startsWith(strings[2]))
                .findFirst()
                .get();

        return new MowerState(orientation, new Point(Integer.decode(strings[0]), Integer.decode(strings[1])));
    }

    /**
     * Retrieve a Command list from data String.
     *
     * @param data the String containing data
     * @return a List of Command
     * @throws MowerException if data line is malformed
     */
    public static List<Command> readCommandList(String data) throws MowerException {
        if (!data.matches("^[GDA]+$"))
            throw new MowerException("invalid command line:" + data);

        return data.chars()
                .mapToObj(c -> Command.valueOf("" + (char) c))
                .collect(Collectors.toCollection(ArrayList::new));

    }


}
