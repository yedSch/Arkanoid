package geometry;

import animation.GameLevel;
import biuoop.DrawSurface;
import collisions.CollisionInfo;
import general.GameEnvironment;
import general.Utils;
import sprites.Sprite;

import java.awt.Color;

/**
 * Ball class.
 *
 * @author Ori.
 */
public class Ball implements Sprite {
    private Color color;
    private Circle circle;
    private Velocity velocity;
    private GameEnvironment environment;

    /**
     * Constructs a ball by given center, radius and color.
     *
     * @param center the center of the ball.
     * @param radius the radius of the ball.
     * @param color  the color of the ball.
     */
    public Ball(Point center, int radius, Color color) {
        this.circle = new Circle(center, radius);
        this.color = color;
    }

    /**
     * Constructs a ball.
     *
     * @param circle the circle.
     */
    public Ball(Circle circle) {
        this.color = Color.WHITE;
        this.circle = circle;
    }

    /**
     * Constructs a ball.
     *
     * @param center      the center.
     * @param radius      the radius.
     * @param velocity    the velocity.
     * @param environment the environment.
     */
    public Ball(Point center, int radius, Velocity velocity, GameEnvironment environment) {
        this(center, radius, Color.WHITE);
        this.environment = environment;
        this.velocity = new Velocity(velocity);
    }

    /**
     * Constructs a ball.
     *
     * @param velocity    the velocity.
     * @param environment the environment.
     */
    public Ball(Velocity velocity, GameEnvironment environment) {
        this.color = Color.WHITE;
        this.circle = new Circle(Utils.STARTING_POINT, Utils.BALL_SIZE);
        this.velocity = new Velocity(velocity);
        this.environment = environment;
    }

    /**
     * Gives the ball's data as string.
     *
     * @return ball's data as string.
     */
    public String toString() {
        return this.circle.getCenter().toString() + " r: " + this.circle.getRadius();
    }

    /**
     * Draws this ball using given draw surface.
     *
     * @param surface use this to draw.
     */
    public void drawOn(DrawSurface surface) {
        // fill circle with color
        surface.setColor(this.color);
        surface.fillCircle((int) circle.getCenter().getX(), (int) circle.getCenter().getY(), circle.getRadius());
        // draw circle with black
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) circle.getCenter().getX(), (int) circle.getCenter().getY(), circle.getRadius());
        // draw red point in the center
        surface.setColor(Color.RED);
        surface.drawCircle((int) circle.getCenter().getX(), (int) this.circle.getCenter().getY(), 1);
    }

    /**
     * Adds this ball to given game.
     *
     * @param gameLevel the GameLevel that this added to.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

    /**
     * Handles balls behavior when time passed.
     *
     * @param dt keeps the speed to be according seconds.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * Handles the movement of the ball.
     *
     * @param dt keeps the speed to be according to seconds.
     */
    private void moveOneStep(double dt) {
        // solves edge case that ball on paddle
        this.checkOnPaddle();

        // ball trajectory
        Line trajectory = new Line(circle.getCenter(), velocity.applyToPoint(circle.getCenter(), dt));
        // collision information
        CollisionInfo collisionInfo = this.environment.getClosestCollision(trajectory);

        // case no collision move to end of trajectory
        if (collisionInfo == null) {
            this.circle.setCenter(trajectory.end());
            // case collision
        } else {
            // move very close the collision point
            double xMove = (collisionInfo.collisionPoint().getX() - this.circle.getCenter().getX()) * 0.9999D;
            double yMove = (collisionInfo.collisionPoint().getY() - this.circle.getCenter().getY()) * 0.9999D;
            this.circle.setCenter(new Point(circle.getCenter().getX() + xMove, circle.getCenter().getY() + yMove));
            this.velocity = collisionInfo.collisionObject().hit(this, collisionInfo.collisionPoint(), velocity);
        }
    }

    /**
     * Solves case ball is on paddle.
     */
    private void checkOnPaddle() {
        // sizes, ball's position and epsilon for fix
        int height = this.environment.getHeight();
        int border = this.environment.getBorder();
        double xPos = this.circle.getCenter().getX();
        int epsilon = this.circle.getRadius();

        // if on paddle move it up
        Rectangle paddleRec = environment.getPaddleRectangle();
        if (paddleRec.isPointInside(this.circle.getCenter())) {
            double pdlHeight = paddleRec.getHeight();
            this.circle.setCenter(new Point(xPos, height - 0.3 * border - pdlHeight - epsilon));
        }
    }

    /**
     * Removes from game.
     *
     * @param gameLevel that removing from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }
}