package geometry;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Cycle class.
 */
public class Cycle implements Shape {
    private Color color;
    private Point center;
    private int radius;

    /**
     * Constructs a cycle.
     *
     * @param center the center.
     * @param radius the radius.
     * @param color the color.
     */
    public Cycle(Point center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    /**
     * Constructs a cycle.
     *
     * @param x the center x position.
     * @param y the center y position.
     * @param radius the radius.
     * @param color the color.
     */
    public Cycle(int x, int y, int radius, Color color) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.color = color;
    }

    /**
     * Draws on the cycle.
     *
     * @param surface the surface it is drawn on it.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    /**
     * Gives the color.
     *
     * @return the color.
     */
    @Override
    public Color color() {
        return this.color;
    }
}
