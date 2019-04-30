package collisions;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import geometry.Ball;

/**
 * Collidable interface.
 *
 * @author Ori.
 */
public interface Collidable {

    /**
     * Gets the shape of the object.
     *
     * @return the collision shape of the object
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param hitter          the hitter.
     * @param collisionPoint  the point of the collision.
     * @param currentVelocity the velocity when it collides.
     * @return the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
