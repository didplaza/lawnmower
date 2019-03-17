package com.mowitnow;

import com.mowitnow.mow2d.LawnLimit;
import com.mowitnow.mower.*;
import com.mowitnow.helpers.MowerDataHelper;
import com.mowitnow.mow2d.Point;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Mower App.
 */
@FixMethodOrder(MethodSorters.JVM)
public class AppTest {

    @Test
    public void testPoint() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 2);
        assertNotEquals(p1, p2);
        Point p3 = p1.translate(0, 1);
        assertEquals(p2, p3);
        Point p4 = new Point(p1);
        assertEquals(p4,p1);
    }

    @Test
    public void testLawn() {
        try {
            LawnLimit lawnLimit = new LawnLimit(1, 1);
            assertTrue(lawnLimit.contains(new Point(0, 0)));
            assertTrue(lawnLimit.contains(new Point(0, 1)));
            assertTrue(lawnLimit.contains(new Point(1, 0)));
            assertTrue(lawnLimit.contains(new Point(1, 1)));
            assertFalse(lawnLimit.contains(new Point(-1, 0)));
            assertFalse(lawnLimit.contains(new Point(0, -1)));
            assertFalse(lawnLimit.contains(new Point(-1, -1)));
            assertFalse(lawnLimit.contains(new Point(2, 0)));
            assertFalse(lawnLimit.contains(new Point(0, 2)));
            assertFalse(lawnLimit.contains(new Point(2, 2)));

        } catch (MowerException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testMower() {
        try {
            LawnLimit lawnLimit = new LawnLimit(2, 2);
            Mower mower = new Mower(lawnLimit);
            assertEquals(mower.getState().getPosition(), new Point(0,0));
            assertEquals(mower.getState().getOrientation(), Orientation.N);

            mower.setState(new MowerState(Orientation.N, new Point(1, 1)));
            MowerState state;

            state = mower.execute(Arrays.asList(Command.D,Command.D));
            assertEquals(state.getOrientation(), Orientation.S);

            state = mower.execute(Arrays.asList(Command.G,Command.G,Command.G));
            assertEquals(state.getOrientation(), Orientation.W);

            state = mower.execute(Command.A);
            assertEquals(state.getPosition(), new Point(0, 1));

            state = mower.execute(Command.A);
            assertEquals(state.getPosition(), new Point(0, 1));

            state = mower.execute(Arrays.asList(Command.D, Command.A, Command.A));
            assertEquals(state.getPosition(), new Point(0, 2));
        } catch (MowerException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void testDataHelperLawn() {
        try {
            LawnLimit lawnLimit = MowerDataHelper.readLawnLimit("3 3");
            assertTrue(lawnLimit.getWidth() == 3 && lawnLimit.getHeight() == 3);
        } catch (MowerException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testDataHelperMowerInit() {
        try {
            MowerState state = MowerDataHelper.readMowerState("0 1 E");
            assertSame(state.getOrientation(), Orientation.E);
            assertEquals(state.getPosition(), new Point(0,1));
        } catch (MowerException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testDataHelperCommands() {
        try {
            LawnLimit lawnLimit = new LawnLimit(3, 3);
            Mower mower = new Mower(lawnLimit);
            MowerState state = MowerDataHelper.readMowerState("1 2 N");
            mower.setState(state);
            List<Command> commands = MowerDataHelper.readCommandList("GAGAGAGAA");
            state = mower.execute(commands);
            assertSame(state.getOrientation(), Orientation.N);
            assertEquals(state.getPosition(), new Point(1, 3));
        } catch (MowerException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFullScenario() {
        try {
            LawnLimit lawnLimit = MowerDataHelper.readLawnLimit("5 5");
            Mower mower1 = new Mower(lawnLimit);
            MowerState state1 = MowerDataHelper.readMowerState("1 2 N");
            mower1.setState(state1);
            state1 = mower1.execute(MowerDataHelper.readCommandList("GAGAGAGAA"));

            Mower mower2 = new Mower(lawnLimit);
            MowerState state2 = MowerDataHelper.readMowerState("3 3 E");
            mower2.setState(state2);
            state2 = mower2.execute(MowerDataHelper.readCommandList("AADAADADDA"));

            assertEquals(state1.getPosition(), new Point(1, 3));
            assertSame(state1.getOrientation(), Orientation.N);

            assertEquals(state2.getPosition(), new Point(5, 1));
            assertSame(state2.getOrientation(), Orientation.E);

        } catch (MowerException e) {
            fail(e.getMessage());
        }
    }
}
