package com.mowitnow;

import com.mowitnow.helpers.MowerDataHelper;
import com.mowitnow.mow2d.LawnLimit;
import com.mowitnow.mower.Mower;
import com.mowitnow.mower.MowerState;

import java.io.FileReader;
import java.io.LineNumberReader;

/**
 * Main Application
 *
 * @author didier
 * @version 1.0
 */
public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) throw new Exception("Must provide a file in command line");
        System.out.println("Reading file " + args[0]);
        try (LineNumberReader lnr = new LineNumberReader(new FileReader(args[0]))) {
            System.out.println("Init lawnLimit");
            LawnLimit lawnLimit = MowerDataHelper.readLawnLimit(lnr.readLine());
            System.out.println(lawnLimit);
            System.out.println("Reading Mowers...");
            String line;
            while ((line = lnr.readLine()) != null) {
                Mower mower = new Mower(lawnLimit);
                MowerState state = MowerDataHelper.readMowerState(line);
                mower.setState(state);
                System.out.println("Initial State: " + state);
                line = lnr.readLine();
                if (line == null) {
                    System.out.println("End of file");
                    break;
                }
                System.out.println("Executing " + line);
                state = mower.execute(MowerDataHelper.readCommandList(line));
                System.out.println("End state " + state);
            }
        }
    }
}
