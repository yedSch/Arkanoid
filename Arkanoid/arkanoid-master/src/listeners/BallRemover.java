package listeners;

import general.Counter;
import animation.GameLevel;
import geometry.Ball;
import sprites.blocks.BaseBlock;

/**
 * Ball remover class.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Constructs a ball remover.
     *
     * @param gameLevel the game level.
     * @param remaining the count of the remaining balls.
     */
    public BallRemover(GameLevel gameLevel, Counter remaining) {
        this.gameLevel = gameLevel;
        this.remainingBalls = new Counter(remaining.getValue());
    }

    /**
     * Constructs a ball remover.
     *
     * @param gameLevel the game level.
     */
    public BallRemover(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        this.remainingBalls = new Counter(0);
    }

    /**
     * Removes when hit.
     *  @param beingHit the hit block.
     * @param hitter the hitter ball.
     */
    public void hitEvent(BaseBlock beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.remainingBalls.decrease(1);
    }

    /**
     * Gives the count of the remaining balls.
     *
     * @return the count of the remaining balls.
     */
    public Counter getRemainingBalls() {
        return remainingBalls;
    }
}
