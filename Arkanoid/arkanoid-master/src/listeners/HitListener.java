package listeners;

import geometry.Ball;
import sprites.blocks.BaseBlock;

/**
 * HitListener interface.
 */
public interface HitListener {

    /**
     * Handles hit event.
     *  @param beingHit the hit block.
     * @param hitter the hitter ball.
     */
    void hitEvent(BaseBlock beingHit, Ball hitter);
}