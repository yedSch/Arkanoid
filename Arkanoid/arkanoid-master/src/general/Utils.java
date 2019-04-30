package general;

import geometry.Point;
import geometry.Velocity;
import levels.LevelSets;
import scores.HighScoresTable;
import sprites.ColorBackground;
import sprites.ImageBackground;
import sprites.Sprite;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utils class.
 */
public class Utils {
    /**
     * The constant WINDOW_WIDTH.
     */
    public static final int WINDOW_WIDTH = 800;
    /**
     * The constant WINDOW_HEIGHT.
     */
    public static final int WINDOW_HEIGHT = 600;
    /**
     * The constant BORDER_SIZE.
     */
    public static final int BORDER_SIZE = 25;
    /**
     * The constant BALL_SIZE.
     */
    public static final int BALL_SIZE = 5;
    /**
     * The Lives.
     */
    static final int LIVES = 7;
    /**
     * The constant FRAMES_PER_SECOND.
     */
    public static final int FRAMES_PER_SECOND = 60;
    /**
     * The constant SCORES_TABLE_SIZE.
     */
    public static final int SCORES_TABLE_SIZE = 5;
    /**
     * The constant PADDLE_HEIGHT.
     */
    public static final int PADDLE_HEIGHT = 15;
    /**
     * The constant PADDLE_COLOR.
     */
    public static final Color PADDLE_COLOR = Color.ORANGE;
    /**
     * The constant INDICATOR_COLOR.
     */
    public static final Color INDICATOR_COLOR = new Color(230, 230, 230);
    /**
     * The constant STARTING_POINT.
     */
    public static final Point STARTING_POINT = new Point(400, 500);
    /**
     * The Scorestable path.
     */
    static final String SCORESTABLE_PATH = "highscores.ser";
    private static final String LEVEL_SETS_PATH = "level_sets.txt";
    private static final String WIN_IMAGE_PATH = "win.png";
    private static final String LOSE_IMAGE_PATH = "lose.jpg";
    private static final String PAUSE_IMAGE_PATH = "pause.png";

    /**
     * Gives random borders color.
     *
     * @return random borders color.
     */
    public static Color getRandomStdBorderColor() {
        List<Color> bordersColors = new ArrayList<>();
        bordersColors.add(new Color(153, 76, 0));
        bordersColors.add(new Color(76, 0, 153));
        bordersColors.add(new Color(153, 0, 76));
        bordersColors.add(new Color(0, 102, 102));
        bordersColors.add(new Color(102, 102, 0));
        Random random = new Random();
        return bordersColors.get(random.nextInt(bordersColors.size()));
    }

    /**
     * Gives the winning image.
     *
     * @return the winning image.
     */
    public static Image winImage() {
        BufferedImage image;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(WIN_IMAGE_PATH);
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("failed loading winning image");
        }
        return image;
    }

    /**
     * Gives the winning image.
     *
     * @return the winning image.
     */
    public static Image loseImage() {
        BufferedImage image;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(LOSE_IMAGE_PATH);
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("failed loading losing image");
        }
        return image;
    }

    /**
     * Gives the winning image.
     *
     * @return the winning image.
     */
    public static Image pauseImage() {
        BufferedImage image;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(PAUSE_IMAGE_PATH);
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("failed loading pause image");
        }
        return image;
    }

    /**
     * Gives the scores table.
     *
     * @return the scores table.
     */
    public static HighScoresTable scoresTable() {
        return HighScoresTable.loadFromFile(new File(SCORESTABLE_PATH));
    }

    /**
     * Gives the default level sets.
     *
     * @return gives the default level sets.
     */
    public static LevelSets levelSets() {
        LevelSets levelSets;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(LEVEL_SETS_PATH);
            levelSets = LevelSets.fromReader(new InputStreamReader(is));
        } catch (IOException e) {
            throw new RuntimeException("failed loading level sets");
        }
        return levelSets;
    }

    /**
     * Generates a fine random velocity with given speed.
     *
     * @param speed to determine the velocity with.
     * @return a random velocity.
     */
    public static Velocity genRandVelocity(double speed) {
        Random rand = new Random();
        // generate an angle between 300 and 60 (= 420)
        int num = rand.nextInt(120);
        int randAngle = 300 + num;
        return Velocity.fromAngleAndSpeed(randAngle, speed);
    }

    /**
     * Checks if given numbers are equal.
     * Allows small mistake in comparison.
     *
     * @param num1 num to check if equal to num2.
     * @param num2 num to check if equal to num1.
     * @return true if numbers are equal, else false.
     */
    public static boolean doublesEqual(double num1, double num2) {
        double mistake = 0.001;
        return Math.abs(num1 - num2) < mistake;
    }

    /**
     * Extracts sub string from string according to pre string and post string.
     *
     * @param str     the whole string.
     * @param preStr  the pre string.
     * @param postStr the post string.
     * @return the sub string between the per and the post strings.
     */
    public static String extract(String str, String preStr, String postStr) {
        return str.substring(preStr.length(), str.length() - postStr.length());
    }

    /**
     * Parse background sprite.
     *
     * @param str the str
     * @return the sprite
     */
    public static Sprite parseBackground(String str) {
        // case color
        if ((str.startsWith("color(")) && (str.endsWith(")"))) {
            return new ColorBackground(new ColorsParser().colorFromString(str));
            // case image
        } else if ((str.startsWith("image(")) && (str.endsWith(")"))) {
            String arg = extract(str, "image(", ")");
            InputStream inputStream = null;
            try {
                inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(arg);
                BufferedImage image = ImageIO.read(inputStream);
                return new ImageBackground(image);
            } catch (IOException e) {
                throw new RuntimeException("failed to load image");
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException("failed to load image");
                    }
                }
            }
        }
        // no use
        return new ColorBackground();
    }
}