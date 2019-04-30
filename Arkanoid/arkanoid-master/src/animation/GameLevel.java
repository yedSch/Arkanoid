package animation;

import biuoop.KeyboardSensor;
import biuoop.DrawSurface;
import collisions.Collidable;
import geometry.Circle;
import levels.LevelInformation;
import general.Counter;
import general.GameEnvironment;
import general.Utils;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import listeners.LivesTrackingListener;
import sprites.blocks.Border;
import sprites.Paddle;
import geometry.Ball;
import sprites.SpriteCollection;
import sprites.blocks.KillingBlock;
import sprites.ScoreIndicator;
import sprites.LivesIndicator;
import sprites.NameIndicator;
import sprites.blocks.BaseBlock;
import sprites.Sprite;
import sprites.StartingPoint;

import java.awt.Color;
import java.util.List;

/**
 * Game level class.
 */
public class GameLevel implements Animation {
    // general
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private LevelInformation info;
    private KeyboardSensor keyboard;
    private AnimationRunner runner;
    // listeners
    private BlockRemover blockRemover;
    private BallRemover ballRemover;
    private ScoreTrackingListener scoreUpdater;
    private LivesTrackingListener livesUpdater;
    // animation
    private boolean running;
    // paddle
    private Paddle paddle;

    /**
     * Constructs a gameLevel.
     *
     * @param info            the info of the level.
     * @param keyboard        the keyboard.
     * @param runner the animation runner.
     * @param score           the score.
     * @param lives           the lives.
     */
    public GameLevel(LevelInformation info, KeyboardSensor keyboard, AnimationRunner runner, int score, int lives) {
        this.info = info;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment(Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT, Utils.BORDER_SIZE);
        this.keyboard = keyboard;
        this.runner = runner;
        this.paddle = new Paddle(info.paddleWidth(), Utils.PADDLE_HEIGHT, info.paddleSpeed(), environment, keyboard);
        this.scoreUpdater = new ScoreTrackingListener(new Counter(score));
        this.livesUpdater = new LivesTrackingListener(new Counter(lives));
        this.blockRemover = new BlockRemover(this, new Counter(info.numberOfBlocksToRemove()));
        this.ballRemover = new BallRemover(this, new Counter(info.numberOfBalls()));
    }

    /**
     * Initializes a new game, creates the blocks, ball, paddle and add them
     * to the game.
     */
    public void initialize() {
        // background with shapes on it
        this.info.getBackground().addToGame(this);

        Color borderColor = Utils.getRandomStdBorderColor();
        // borders up, right and left (numbers clockwise 1 - up, 2 - right etc.)
        new Border(0, Utils.BORDER_SIZE, Utils.WINDOW_WIDTH, Utils.BORDER_SIZE, borderColor).addToGame(this);
        new Border(0, Utils.BORDER_SIZE, Utils.BORDER_SIZE, Utils.WINDOW_HEIGHT, borderColor).addToGame(this);
        new Border(Utils.WINDOW_WIDTH - Utils.BORDER_SIZE, Utils.BORDER_SIZE, Utils.BORDER_SIZE,
                Utils.WINDOW_HEIGHT, borderColor).addToGame(this);

        // killing block instead of down border (small height)
        new KillingBlock(this.ballRemover).addToGame(this);

        // starting point for the balls
        new StartingPoint(new Circle(Utils.STARTING_POINT, Utils.BALL_SIZE)).addToGame(this);

        // score indicator, lives and name at the top
        new ScoreIndicator(this).addToGame(this);
        new LivesIndicator(this).addToGame();
        new NameIndicator(this).addToGame();

        // the blocks with their listeners
        List<BaseBlock> blocks = this.info.blocks();
        for (BaseBlock block : blocks) {
            block.addHitListener(this.blockRemover);
            block.addHitListener(this.scoreUpdater);
            block.addToGame(this);
        }
    }

    /**
     * Handles one frame of animation.
     *
     * @param surface drawSurface to do the frame with.
     * @param dt      keeps the speed according to seconds.
     */
    @Override
    public void doOneFrame(DrawSurface surface, double dt) {
        // run pause if press p
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard,
                    "space", new PauseScreen()));
        }

        // stopping condition no balls or no blocks
        if (this.ballRemover.getRemainingBalls().getValue() == 0) {
            this.running = false;
            // decrease life
            this.livesUpdater.getCurrentLives().decrease(1);
            // init remaining balls
            this.ballRemover.getRemainingBalls().increase(this.info.numberOfBalls());
        }
        if (this.blockRemover.getRemainingBlocks().getValue() == 0) {
            this.running = false;
            // increase score
            this.scoreUpdater.getCurrentScore().increase(100);
        }

        // draw sprites and notify time passed
        this.sprites.drawAllOn(surface);
        this.sprites.notifyAllTimePassed(dt);
    }

    /**
     * Tells if animation should stop.
     *
     * @return if animation should stop.
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Plays one turn.
     */
    public void playOneTurn() {
        // first create paddle and balls
        this.createPaddleAndBalls();
        // run countdown from 3 (3, 2, 1 and GO)
        this.runner.run(new CountDownAnimation(500, 3, this.sprites));
        // is running
        this.running = true;
        // run this
        this.runner.run(this);
    }

    /**
     * Creates a new paddle and balls.
     */
    private void createPaddleAndBalls() {
        // create and add paddle
        this.paddle.removeFromGame(this);
        this.paddle = new Paddle(info.paddleWidth(), Utils.PADDLE_HEIGHT, info.paddleSpeed(),
                environment, keyboard);
        this.paddle.addToGame(this);

        // create and add balls according to numBalls
        for (int i = 0; i < this.info.numberOfBalls(); i++) {
            new Ball(this.info.initialBallVelocities().get(i), this.environment).addToGame(this);
        }
    }

    /**
     * Adds a collidable object.
     *
     * @param collidable a collidable object.
     */
    public void addCollidable(Collidable collidable) {
        this.environment.addCollidable(collidable);
    }

    /**
     * Adds a sprite object.
     *
     * @param sprite a sprite object.
     */
    public void addSprite(Sprite sprite) {
        this.sprites.addSprite(sprite);
    }

    /**
     * Removes collidable.
     *
     * @param collidable the collidable to remove.
     */
    public void removeCollidable(Collidable collidable) {
        this.environment.removeCollidable(collidable);
    }

    /**
     * Removes sprite.
     *
     * @param sprite the sprite to remove.
     */
    public void removeSprite(Sprite sprite) {
        this.sprites.removeSprite(sprite);
    }

    /**
     * Gives the score updater.
     *
     * @return the score updater.
     */
    public ScoreTrackingListener getScoreUpdater() {
        return this.scoreUpdater;
    }

    /**
     * Gives the lives updater.
     *
     * @return the lives updater.
     */
    public LivesTrackingListener getLivesUpdater() {
        return this.livesUpdater;
    }

    /**
     * Gives the num of the blocks.
     *
     * @return the num of the blocks.
     */
    public int getNumBlocks() {
        return this.blockRemover.getRemainingBlocks().getValue();
    }

    /**
     * Gives the num of the lives.
     *
     * @return the num of the lives.
     */
    public int getNumLives() {
        return this.livesUpdater.getCurrentLives().getValue();
    }

    /**
     * Gives the score.
     *
     * @return the score.
     */
    public int getScore() {
        return this.scoreUpdater.getCurrentScore().getValue();
    }

    /**
     * Gives the name of the level.
     *
     * @return the name of the level.
     */
    public String getLevelName() {
        return this.info.levelName();
    }
}