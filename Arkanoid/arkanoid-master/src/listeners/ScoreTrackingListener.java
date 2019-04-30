package listeners;

import general.Counter;
import geometry.Ball;
import sprites.blocks.BaseBlock;

/**
 * Score tracking listener class.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructs a ScoreTrackingListener by init a counter.
     */
    public ScoreTrackingListener() {
        currentScore = new Counter();
    }

    /**
     * Constructs a ScoreTrackingListener by given score.
     *
     * @param scoreCounter the given score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Increases score when hit and remove block.
     *  @param beingHit the hit block.
     * @param hitter the hitter ball.
     */
    public void hitEvent(BaseBlock beingHit, Ball hitter) {
        // 5 points for hit
        this.currentScore.increase(5);

        // more 10 points for removing block
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(10);
        }
    }

    /**
     * Gives the current score.
     *
     * @return the current score.
     */
    public Counter getCurrentScore() {
        return currentScore;
    }
}