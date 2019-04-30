package levels;

import geometry.Velocity;
import sprites.blocks.BaseBlock;
import sprites.Sprite;

import java.io.InputStreamReader;
import java.util.List;

/**
 * Level information class.
 */
public interface LevelInformation {

    /**
     * Gives the name of the level.
     *
     * @return the name of the level.
     */
    String levelName();

    /**
     * Gives the width of the paddle.
     *
     * @return the width of the paddle.
     */
    int paddleWidth();

    /**
     * Gives the speed of the paddle.
     *
     * @return the speed of the paddle.
     */
    int paddleSpeed();

    /**
     * Gives background sprite.
     *
     * @return background sprite.
     */
    Sprite getBackground();

    /**
     * Gives the regular blocks of the level as a list.
     *
     * @return the regular blocks of the level as a list.
     */
    List<BaseBlock> blocks();

    /**
     * Gives the number of the block to remove.
     *
     * @return the number of the block to remove.
     */
    int numberOfBlocksToRemove();

    /**
     * Gives the number of the balls.
     *
     * @return the number of the balls.
     */
    int numberOfBalls();

    /**
     * Gives a list of initial velocities.
     *
     * @return a list of initial velocities.
     */
    List<Velocity> initialBallVelocities();

    /**
     * Gives the blocks definitions file.
     *
     * @return the blocks definitions file.
     */
    InputStreamReader blockDefinitionsFile();

    /**
     * Gives the x position of the blocks.
     *
     * @return the x position of the blocks.
     */
    int xPosBlocks();

    /**
     * Gives the y position of the blocks.
     *
     * @return the y position of the blocks.
     */
    int yPosBlocks();
}