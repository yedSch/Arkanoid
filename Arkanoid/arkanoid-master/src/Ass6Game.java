
import menu.Menu;
import animation.MenuAnimation;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import animation.AnimationRunner;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import scores.HighScoresTable;
import menu.Task;
import general.Utils;
import general.GameFlow;
import levels.LevelFromReader;
import levels.LevelSets;
import levels.LevelSpecificationReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Ass6Game class.
 */
public class Ass6Game {

    /**
     * Runs the game.
     *
     * @param args a path for level sets file.
     */
    public static void main(String[] args) {
        // init some basics
        GUI gui = new GUI("Arkanoid", Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT);
        Sleeper sleeper = new Sleeper();
        AnimationRunner runner = new AnimationRunner(sleeper, gui);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        DialogManager dialog = gui.getDialogManager();

        // the level sets (get as arg or default)
        LevelSets levelSets;

        if (args.length > 0) {
            // get level sets as arg
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(args[0]);
                levelSets = LevelSets.fromReader(new InputStreamReader(is));
            } catch (IOException e) {
                throw new RuntimeException("failed loading level sets");
            }
        } else {
            // default level sets
            levelSets = Utils.levelSets();
        }

        // manage levelSets menu
        Menu<Task<Void>> levelSetsMenu = new MenuAnimation<>("Choose Scenario", keyboard, runner);
        for (LevelSets.LevelSet levelSet : levelSets.getLevelSetList()) {
            levelSetsMenu.addSelection(levelSet.getKey(), levelSet.getMessage(), new Task() {
                @Override
                public Object run() {
                    try {
                        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSet.getPath());
                        List<LevelFromReader> levels = LevelSpecificationReader.fromReader(new InputStreamReader(is));
                        GameFlow gameFlow = new GameFlow(runner, keyboard, dialog);
                        gameFlow.runLevels(levels);
                    } catch (IOException e) {
                        throw new RuntimeException("failed to load the levels");
                    }

                    return null;
                }
            });
        }
        // manage main menu
        Menu<Task<Void>> mainMenu = new MenuAnimation<>("Main Menu", keyboard, runner);
        // add level sets menu as start game option
        mainMenu.addSubMenu("s", "Start Game", levelSetsMenu);
        // add high scores option
        mainMenu.addSelection("h", "High Scores", new Task() {
            public Void run() {
                // load scoresTable
                HighScoresTable scoresTable = Utils.scoresTable();
                runner.run(new KeyPressStoppableAnimation(keyboard, "space", new HighScoresAnimation(scoresTable)));
                return null;
            }
        });
        // add exit option
        mainMenu.addSelection("e", "Exit", new Task() {
            public Void run() {
                gui.close();
                return null;
            }
        });
        // run menu
        while (true) {
            runner.run(mainMenu);
            mainMenu.getStatus().run();
            mainMenu.reset();
        }
    }
}