package animation;

import biuoop.DrawSurface;

/**
 * Animation interface.
 */
public interface Animation {

    /**
     * Do one frame during the game.
     *
     * @param surface drawSurface to do the frame with.
     * @param dt      keeps the speed according to seconds.
     */
    void doOneFrame(DrawSurface surface, double dt);

    /**
     * Tells if animation needs to stop.
     *
     * @return if needs to stop.
     */
    boolean shouldStop();
}