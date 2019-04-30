package listeners;

import general.Counter;
import animation.GameLevel;
import geometry.Ball;
import sprites.blocks.BaseBlock;

/**
 * Block remover class.
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Constructs a block remover.
     *
     * @param gameLevel the gameLevel.
     * @param remaining the count of the remaining blocks.
     */
    public BlockRemover(GameLevel gameLevel, Counter remaining) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = new Counter(remaining.getValue());
    }

    /**
     * Constructs a BlockRemover.
     *
     * @param gameLevel the gameLevel.
     */
    public BlockRemover(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = new Counter(0);
    }

    /**
     * Removes zero hit points blocks.
     *  @param beingHit the hit block.
     * @param hitter the hitter ball.
     */
    public void hitEvent(BaseBlock beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.gameLevel);
            this.remainingBlocks.decrease(1);
        }
    }

    /**
     * Gives the remaining blocks.
     *
     * @return the remaining blocks.
     */
    public Counter getRemainingBlocks() {
        return remainingBlocks;
    }
}
