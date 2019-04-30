package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * SpriteCollection class.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Constructs a new SpriteCollection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds given sprite to sprites.
     *
     * @param sprite a sprite to add to sprites.
     */
    public void removeSprite(Sprite sprite) {
        this.sprites.remove(sprite);
    }

    /**
     * Adds given sprite to sprites.
     *
     * @param sprite a sprite to add to sprites.
     */
    public void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
    }

    /**
     * Calls timePassed() on all sprites.
     *
     * @param dt keeps the speed to be according to seconds.
     */
     public void notifyAllTimePassed(double dt) {
        List<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite sprite : copy) {
            sprite.timePassed(dt);
        }
    }

    /**
     * Calls drawOn(surface) on all sprites.
     *
     * @param surface the surface to draw on.
     */
    public void drawAllOn(DrawSurface surface) {
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(surface);
        }
    }
}