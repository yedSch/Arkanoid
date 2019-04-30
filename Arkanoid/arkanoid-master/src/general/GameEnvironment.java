package general;

import collisions.Collidable;
import collisions.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import sprites.Paddle;

import java.util.ArrayList;
import java.util.List;

/**
 * GameEnvironment class.
 *
 * @author Ori.
 */
public class GameEnvironment {
    private int width;
    private int height;
    private int border;
    private List<Collidable> collidables;

    /**
     * Constructs a GameLevel environment by given width, height and border width.
     * It generates also an array list of collidables.
     *
     * @param width  the width of the GameLevel window.
     * @param height the height of the GameLevel window.
     * @param border the border width of the GameLevel window.
     */
    public GameEnvironment(int width, int height, int border) {
        this.width = width;
        this.height = height;
        this.border = border;
        this.collidables = new ArrayList<>();
    }

    /**
     * Gives the width of the GameLevel window.
     *
     * @return the width of the GameLevel window.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gives the height of the GameLevel window.
     *
     * @return the height of the GameLevel window.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Gives the border of the GameLevel window.
     *
     * @return the border of the GameLevel window.
     */
    public int getBorder() {
        return this.border;
    }

    /**
     * Adds the given collidable to the environment.
     *
     * @param collidable a collidable object.
     */
    public void addCollidable(Collidable collidable) {
        this.collidables.add(collidable);
    }

    /**
     * Adds the given collidable to the environment.
     *
     * @param collidable a collidable object.
     */
    public void removeCollidable(Collidable collidable) {
        this.collidables.remove(collidable);
    }

    /**
     * Gets the rectangle of the paddle.
     *
     * @return the rectangle ot the paddle.
     */
    public Rectangle getPaddleRectangle() {

        List<Collidable> copy = new ArrayList<>(this.collidables);

        for (Collidable collidable : copy) {
            // if collidable is Paddle return it
            if (collidable instanceof Paddle) {
                return collidable.getCollisionRectangle();
            }
        }
        // case there is no paddle return null
        return null;
    }

    /**
     * Gets the upper border.
     *
     * @return the upper border.
     */
    public Rectangle getTop() {
        Point upperLeft = new Point(this.border, 0);
        return new Rectangle(upperLeft, width - 2 * border, border);
    }

    /**
     * Gets the right border.
     *
     * @return the right border.
     */
    public Rectangle getRight() {
        Point upperLeft = new Point(width - border, border);
        return new Rectangle(upperLeft, border, height - 2 * border);
    }

    /**
     * Gets the bottom border.
     *
     * @return the bottom border.
     */
    public Rectangle getBottom() {
        Point topLeft = new Point(border, height - border);
        return new Rectangle(topLeft, width - 2 * border, border);
    }

    /**
     * Gets the left border.
     *
     * @return the left border.
     */
    public Rectangle getLeft() {
        Point topLeft = new Point(0, border);
        return new Rectangle(topLeft, border, height - 2 * border);
    }

    /**
     * Returns the information about the closest collision that is going to
     * accur if object collides with any collidable.
     * If there isn't collision return null.
     *
     * @param trajectory a line to check if collides with collidable.
     * @return CollisionInfo of collision if collides, else return null.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestOnCollidable;
        Rectangle collisionRectangle;

        // case no collision return null
        if (!this.isCollide(trajectory)) {
            return null;
        }

        // list of the closest collide points on all the collided collidables
        List<Point> closestCollidePoints = new ArrayList<>();
        List<Collidable> copy = new ArrayList<>(this.collidables);
        for (Collidable collidable : copy) {
            // find closest on collidable by the collision rectangle
            collisionRectangle = collidable.getCollisionRectangle();
            closestOnCollidable = trajectory.closestIntersectionToStartOfLine(
                    collisionRectangle);
            // add the closest collide point to the list
            if (closestOnCollidable != null) {
                closestCollidePoints.add(closestOnCollidable);
            }
        }

        // create the closest collision point
        Point closest = trajectory.start().closestPoint(closestCollidePoints);
        // create collisionInfo with collision point and collided collidable
        return new CollisionInfo(closest, this.collidableByPoint(closest));
    }

    /**
     * Checks if given trajectory collides with at least one collidable.
     *
     * @param trajectory to check if intersects with at least one collidable.
     * @return true if finds intersection point, else false.
     */
    public Boolean isCollide(Line trajectory) {
        Rectangle collisionRectangle;


        List<Collidable> copy = new ArrayList<>(this.collidables);
        // passes over collidables and check if intersect with someone
        for (Collidable collidable : copy) {
            collisionRectangle = collidable.getCollisionRectangle();
            // case at least one intersection point with collidable return true
            if (collisionRectangle.intersectionPoints(trajectory).size() > 0) {
                return true;
            }
        }
        // case no collision found return false
        return false;
    }

    /**
     * Finds a collidable by given point on frame and returns it.
     *
     * @param point a point to find the collidable that its on it's frame.
     * @return collidanle that the given point is on it's frame.
     */
    public Collidable collidableByPoint(Point point) {
        List<Collidable> copy = new ArrayList<>(this.collidables);

        // passes over collidable and check if point on frame
        for (Collidable collidable : copy) {
            if (collidable.getCollisionRectangle().isPointOnFrame(point)) {
                return collidable;
            }
        }
        return null;
    }

    /**
     * Gets the collidables list.
     *
     * @return collidables list.
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }

    /**
     * Checks if point is inside GameLevel window.
     *
     * @param point the point to check if inside window.
     * @return true if the point is inside window, else false.
     */
    public boolean isInWindow(Point point) {
        double xPos = point.getX();
        double yPos = point.getY();
        return (0 <= xPos && xPos <= width && 0 <= yPos && yPos <= height);
    }
}
