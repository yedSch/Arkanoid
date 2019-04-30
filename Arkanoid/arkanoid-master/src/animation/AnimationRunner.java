package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import general.Utils;

/**
 * Animation runner class.
 */
public class AnimationRunner {
    private GUI gui;
    private Sleeper sleeper;
    private int framesPerSecond;

    /**
     * Constructs an AnimationRunner by given sleeper and gui.
     *
     * @param sleeper given sleeper for the animation.
     * @param gui     given gui for the animation.
     */
    public AnimationRunner(Sleeper sleeper, GUI gui) {
        this.gui = gui;
        this.sleeper = sleeper;
        this.framesPerSecond = Utils.FRAMES_PER_SECOND;
    }

    /**
     * Runs the animation.
     *
     * @param animation given animation to run.
     */
    public void run(Animation animation) {
        long milliSecondsPerTick = 1000 / this.framesPerSecond;
        while (true) {

            long start = System.currentTimeMillis();

            DrawSurface ds = this.gui.getDrawSurface();
            animation.doOneFrame(ds, milliSecondsPerTick / 1000.0D);
            if (animation.shouldStop()) {
                break;
            }
            this.gui.show(ds);

            long tickMilliSeconds = System.currentTimeMillis() - start;
            long milliSecondLeftToSleep = milliSecondsPerTick - tickMilliSeconds;
            if (milliSecondLeftToSleep > 0L) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}