package com.mowitnow.mow2d;

import com.mowitnow.mower.MowerException;

/**
 * Represent the limit of the lawn. Limit is a rectangle starting at 0,0
 *
 * @author didier
 * @version 1.0
 */
public class LawnLimit {
    private int width;
    private int height;

    /**
     * Create a new Lawn Limit
     * @param width the with of the lawn
     * @param height the height of the lawn
     * @throws MowerException if width or height are negative
     */
    public LawnLimit(int width, int height) throws MowerException {
        if (width < 0 || height < 0) throw new MowerException("width or height can't be negative");
        this.width = width;
        this.height = height;
    }

    /**
     * Get the with of the lawn
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the lawn
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Check if a Point is inside the limit
     *
     * @param p the Point to check
     * @return true if Point is inside the limit
     */
    public boolean contains(Point p) {
        return p.getX() >= 0 && p.getX() <= this.width && p.getY() >= 0 && p.getY() <= this.height;
    }

    @Override
    public String toString() {
        return "LawnLimit{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
