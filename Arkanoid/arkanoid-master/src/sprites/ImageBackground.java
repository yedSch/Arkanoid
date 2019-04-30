package sprites;

import animation.GameLevel;
import biuoop.DrawSurface;

import java.awt.Image;

/**
 * ImageBackground class.
 */
public class ImageBackground implements Sprite {
    private Image image;

    /**
     * Constructs an ImageBackground.
     *
     * @param image the image.
     */
    public ImageBackground(Image image) {
        this.image = image;
    }

    /**
     * Draws the background on the surface.
     *
     * @param surface the surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.drawImage(0, 0, this.image);
    }

    /**
     * No use.
     *
     * @param dt keeps the speed to be according to seconds.
     */
    public void timePassed(double dt) {
    }

    /**
     * Adds this to sprites.
     *
     * @param gameLevel the GameLevel that
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
}
