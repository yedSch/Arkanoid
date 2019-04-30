package sprites.blocks;

import animation.GameLevel;
import biuoop.DrawSurface;
import collisions.Collidable;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import geometry.Ball;
import geometry.Line;
import listeners.HitListener;
import listeners.HitNotifier;
import sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseBlock class.
 */
public abstract class BaseBlock implements Collidable, Sprite, HitNotifier {
    private Rectangle shape;
    private int hitPoints;
    private List<HitListener> hitListeners;

    /**
     * Constructs a base block.
     *
     * @param xPos      x position of the block.
     * @param yPos      y position of the block.
     * @param width     width of the block.
     * @param height    height of the block.
     * @param hitPoints hit points of the block.
     */
    public BaseBlock(int xPos, int yPos, int width, int height, int hitPoints) {
        this.shape = new Rectangle(new Point(xPos, yPos), width, height);
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Sets blocks width.
     *
     * @param newWidth the new width.
     */
    public void setWidth(int newWidth) {
        this.shape.setWidth(newWidth);
    }

    /**
     * Sets blocks height.
     *
     * @param newHeight the new height.
     */
    public void setHeight(int newHeight) {
        this.shape.setHeight(newHeight);
    }

    /**
     * Gives blocks width.
     *
     * @return the width
     */
    public int getWidth() {
        return (int) this.shape.getWidth();
    }

    /**
     * Gives blocks height.
     *
     * @return the height
     */
    public int getHeight() {
        return (int) this.shape.getHeight();
    }

    /**
     * Gives the hit points.
     *
     * @return the hit points.
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Sets the hit points.
     *
     * @param newHitPoints the new hit points.
     */
    public void setHitPoints(int newHitPoints) {
        this.hitPoints = newHitPoints;
    }

    /**
     * Gives the shape.
     *
     * @return the shape.
     */
    Rectangle getShape() {
        return shape;
    }

    /**
     * Sets the shape.
     *
     * @param newShape the new shape.
     */
    public void setShape(Rectangle newShape) {
        this.shape = newShape;
    }

    /**
     * Gives the collision shape of the object.
     *
     * @return the collision shape of the object.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * Gives the block's data as string.
     *
     * @return block's data as string.
     */
    public String toString() {
        return this.shape.toString();
    }

    /**
     * Notifies the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param hitter          the hitter ball.
     * @param collisionPoint  the point of the collision.
     * @param currentVelocity the velocity when it collides.
     * @return the return is the new velocity expected after the hit.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // update numHits
        if (hitPoints > 0) {
            this.hitPoints--;
        }
        // dx and dy
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // take the lines clockwise
        Line top = this.shape.getLines().get(0);
        Line right = this.shape.getLines().get(1);
        Line bottom = this.shape.getLines().get(2);
        Line left = this.shape.getLines().get(3);

        // booleans if hit the upper, lower, left or right side
        boolean onTop = collisionPoint.onSegment(top.start(), top.end());
        boolean onRight = collisionPoint.onSegment(right.start(), right.end());
        boolean onBtm = collisionPoint.onSegment(bottom.start(), bottom.end());
        boolean onLeft = collisionPoint.onSegment(left.start(), left.end());

        // update dx and dy according the side of the hit
        if (onTop) {
            currentVelocity.setDy(-1 * Math.abs(dy));
        }
        if (onRight) {
            currentVelocity.setDx(Math.abs(dx));
        }
        if (onBtm) {
            currentVelocity.setDy(Math.abs(dy));
        }
        if (onLeft) {
            currentVelocity.setDx(-1 * Math.abs(dx));
        }

        this.notifyHit(hitter);

        // return updated velocity
        return currentVelocity;
    }

    /**
     * Draws this ball using given draw surface.
     *
     * @param surface use this to draw.
     */
    public abstract void drawOn(DrawSurface surface);

    /**
     * Adds this to sprites and collidables.
     *
     * @param gameLevel the GameLevel that
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }

    /**
     * Removes from game.
     *
     * @param gameLevel the gameLevel to remove from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * No use.
     *
     * @param dt keeps the speed to be according to seconds.
     */
    public void timePassed(double dt) {
    }

    /**
     * Notifies hit.
     *
     * @param hitter the hitter ball.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Removes hit listener.
     *
     * @param hl the hit listener to be removed.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Adds hit listener.
     *
     * @param hl the hit listener to be added.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Gives hit listeners.
     *
     * @return hit listeners.
     */
    public List<HitListener> getHitListeners() {
        return hitListeners;
    }
}