package com.mowitnow.mow2d;

/**
 * Represent coordinates in a 2D plannar space.
 *
 * @author didier
 * @version 1.0
 */
public class Point {
    private int x;
    private int y;

    /**
     * Return a Point with specified coordinates
     *
     * @param x the x coordinate of the Point
     * @param y the y coordinate of the Point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return a new instance of a Point from the specified Point
     *
     * @param point the point to duplicate
     */
    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    /**
     * Retrieve x coordinate of the Point
     *
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieve y coordiante of the Point
     *
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Return a new Point by adding x and y parameter to the Point.
     *
     * @param x value to add to x coordinate
     * @param y value to add to y coordinate
     *
     * @return a new Point translated by x and y
     */
    public Point translate(int x, int y) {
        return new Point(
                this.x + x,
                this.y + y
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
