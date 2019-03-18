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
            mower.setState(new MowerState(Orientation.N, new Point(1, 1)));

            mower.setListener(state -> assertEquals(state.getOrientation(), Orientation.S));
            mower.execute(Arrays.asList(Command.D,Command.D));

            mower.setListener( state -> assertEquals(state.getOrientation(), Orientation.W));
            mower.execute(Arrays.asList(Command.G,Command.G,Command.G));

            mower.setListener(state -> assertEquals(state.getPosition(), new Point(0, 1)));
            mower.execute(Arrays.asList(Command.A));

            mower.setListener(state -> assertEquals(state.getPosition(), new Point(0, 1)));
            mower.execute(Arrays.asList(Command.A));

            mower.setListener(state -> assertEquals(state.getPosition(), new Point(0, 2)));
            mower.execute(Arrays.asList(Command.D, Command.A, Command.A));
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
            mower.setListener(s -> {
                assertSame(s.getOrientation(), Orientation.N);
                assertEquals(s.getPosition(), new Point(1, 3));
            });
            mower.execute(commands);
        } catch (MowerException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFullScenario() {
        try {
            LawnLimit lawnLimit = MowerDataHelper.readLawnLimit("5 5");
            Mower mower1 = new Mower(lawnLimit);
            mower1.setState(MowerDataHelper.readMowerState("1 2 N"));
            mower1.setListener(state -> {
                assertEquals(state.getPosition(), new Point(1, 3));
                assertSame(state.getOrientation(), Orientation.N);
            });
            mower1.execute(MowerDataHelper.readCommandList("GAGAGAGAA"));

            Mower mower2 = new Mower(lawnLimit);
            mower2.setState(MowerDataHelper.readMowerState("3 3 E"));
            mower2.setListener(state -> {
                assertEquals(state.getPosition(), new Point(5, 1));
                assertSame(state.getOrientation(), Orientation.E);
            });
            mower2.execute(MowerDataHelper.readCommandList("AADAADADDA"));
        } catch (MowerException e) {
            fail(e.getMessage());
        }
    }
}
