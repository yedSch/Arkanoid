package sprites.blocks;

import biuoop.DrawSurface;
import general.Utils;
import geometry.Ball;
import geometry.Point;
import geometry.Velocity;
import listeners.BallRemover;

import java.awt.Color;

/**
 * Killing block class.
 */
public class KillingBlock extends Border {
    private boolean drawYellow;
    private Utils utils;

    /**
     * Constructs a KillingBlock.
     *
     * @param remover the ball remover.
     */
    public KillingBlock(BallRemover remover) {
        super(-1000, 596, 2800, 5, new Color(204, 0, 0));
        this.utils = new Utils();
        this.drawYellow = false;
        super.addHitListener(remover);
    }

    /**
     * Notifies the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param hitter          the hitter.
     * @param collisionPoint  the point of the collision.
     * @param currentVelocity the velocity when it collides.
     * @return the return is the new velocity expected after the hit.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.drawYellow = true;
        return super.hit(hitter, collisionPoint, currentVelocity);
    }

    /**
     * Draws this ball using given draw surface.
     *
     * @param surface use this to draw.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        if (this.drawYellow) {
            // draw in yellow when hit
            this.drawYellow = false;
            super.setColor(Color.YELLOW);
        } else {
            // draw in red
            super.setColor(new Color(204, 0, 0));
        }

        // draw the regular block
        super.drawOn(surface);
    }
}