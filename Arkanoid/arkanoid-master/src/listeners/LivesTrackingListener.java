package listeners;

import general.Counter;
import geometry.Ball;
import sprites.blocks.BaseBlock;

/**
 * Score tracking listener class.
 */
public class LivesTrackingListener implements HitListener {
    private Counter currentLives;

    /**
     * Constructs a ScoreTrackingListener by init a counter.
     */
    public LivesTrackingListener() {
        this.currentLives = new Counter();
    }

    /**
     * Constructs a LivesTrackingListener.
     *
     * @param livesCounter the init count of lives.
     */
    public LivesTrackingListener(Counter livesCounter) {
        this.currentLives = livesCounter;
    }

    /**
     * No use.
     *  @param beingHit the hit block.
     * @param hitter the hitter ball.
     */
    public void hitEvent(BaseBlock beingHit, Ball hitter) {
    }

    /**
     * Gives the number of the lives.
     *
     * @return the number of the lives.
     */
    public Counter getCurrentLives() {
        return this.currentLives;
    }
}