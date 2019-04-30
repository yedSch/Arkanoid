package general;

import animation.HighScoresAnimation;
import animation.GameLevel;
import animation.KeyPressStoppableAnimation;
import animation.AnimationRunner;
import animation.EndScreen;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import levels.LevelFromReader;
import levels.LevelInformation;
import scores.HighScoresTable;
import scores.ScoreInfo;

import java.io.File;
import java.util.List;

/**
 * Game flow class.
 */
public class GameFlow {
    private KeyboardSensor keyboardSensor;
    private DialogManager dialogManager;
    private AnimationRunner runner;
    private int score;
    private int lives;

    /**
     * Constructs a GameFlow.
     *
     * @param runner the animation runner.
     * @param keyboardSensor the keyboard sensor.
     * @param dialogManager the dialogManager.
     */
    public GameFlow(AnimationRunner runner, KeyboardSensor keyboardSensor, DialogManager dialogManager) {
        this.runner = runner;
        this.keyboardSensor = keyboardSensor;
        this.dialogManager = dialogManager;
        this.score = 0;
        this.lives = Utils.LIVES;
    }

    /**
     * Runs game levels.
     *
     * @param levels the levels.
     */
    public void runLevels(List<LevelFromReader> levels) {
        for (LevelInformation levelInfo : levels) {
            // adds 100 to score each level
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor,
                    this.runner, this.score, this.lives);
            level.initialize();

            // level has more blocks and player has more lives
            while (level.getNumBlocks() > 0 && this.lives > 0) {
                level.playOneTurn();

                this.lives = level.getNumLives();
                this.score = level.getScore();
            }

            if (level.getNumBlocks() < 1) {
                continue;
            }

            // if no more lives
            if (this.lives < 1) {
                break;
            }
        }

        this.runner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space",
                new EndScreen(this.lives, this.score)));

        this.manageHighScore();
    }

    /**
     * Manages high scores.
     */
    private void manageHighScore() {
        HighScoresTable scoresTable = Utils.scoresTable();
        int curRank = scoresTable.getRank(this.score);

        if (curRank < scoresTable.size()) {
            String name = dialogManager.showQuestionDialog("Name", "What is your name?", "");
            scoresTable.add(new ScoreInfo(name, this.score));
            scoresTable.save(new File(Utils.SCORESTABLE_PATH));
        }

        this.runner.run(new KeyPressStoppableAnimation(keyboardSensor, "space",
                new HighScoresAnimation(scoresTable, curRank)));
    }
}
