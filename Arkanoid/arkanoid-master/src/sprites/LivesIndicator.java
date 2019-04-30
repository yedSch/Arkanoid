package sprites;

import biuoop.DrawSurface;
import animation.GameLevel;
import general.Utils;
import sprites.blocks.Border;

import java.awt.Color;

/**
 * Lives indicator class.
 */
public class LivesIndicator extends Border {
    private GameLevel gameLevel;

    /**
     * Constructs a livesIndicator.
     *
     * @param gameLevel the gameLevel to add this to.
     */
    public LivesIndicator(GameLevel gameLevel) {
        super(0, 0, 300, Utils.BORDER_SIZE, Utils.INDICATOR_COLOR);
        this.gameLevel = gameLevel;
    }

    /**
     * Draws this ball using given draw surface.
     *
     * @param surface use this to draw.
     */
    public void drawOn(DrawSurface surface) {
        super.drawOn(surface);

        // draw text in black
        surface.setColor(Color.BLACK);
        surface.drawText(50, 18, "Lives: " + gameLevel.getLivesUpdater().getCurrentLives().getValue(), 18);

        // draw small balls as the remaining lives
        for (int i = 0; i < this.gameLevel.getLivesUpdater().getCurrentLives().getValue(); i++) {
            surface.setColor(Color.BLACK);
            surface.drawCircle(122 + i * 11, 12, 5);
            surface.setColor(Color.WHITE);
            surface.fillCircle(122 + i * 11, 12, 5);
            surface.setColor(Color.RED);
            surface.fillCircle(122 + i * 11, 12, 1);
        }
    }

    /**
     * Adds this to game.
     */
    public void addToGame() {
        this.gameLevel.addSprite(this);
    }
}