package levels;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * LevelSets class.
 */
public class LevelSets {
    private List<LevelSet> levelSetList;

    /**
     *  Constructs a levelSets.
     */
    public LevelSets() {
        this.levelSetList = new ArrayList();
    }

    /**
     * Reads levelSets from file.
     *
     * @param reader what to read.
     * @return the levelSets.
     * @throws IOException exception if needed.
     */
    public static LevelSets fromReader(Reader reader) throws IOException {
        LevelSets levelSets = new LevelSets();
        LevelSet currentSet = null;
        try (LineNumberReader lineReader = new LineNumberReader(reader)) {
            String line;
            while ((line = lineReader.readLine()) != null) {
                if (lineReader.getLineNumber() % 2 != 0) {
                    // odd lines format example: "e:Easy"
                    currentSet = new LevelSet();
                    String[] lineParts = line.trim().split(":");
                    currentSet.setKey(lineParts[0]);
                    currentSet.setMessage(lineParts[1]);
                } else {
                    // even lines format example: "definitions/easy_level_definitions.txt"
                    currentSet.setPath(line.trim());
                    levelSets.addLevelSet(currentSet);
                    currentSet = null;
                }
            }
        }
        return levelSets;
    }

    /**
     * Adds a levelSet to levelSets list.
     *
     * @param levelSet a levelSet to add.
     */
    public void addLevelSet(LevelSet levelSet) {
        this.levelSetList.add(levelSet);
    }

    /**
     * Gives the levelSets list.
     *
     * @return the levelSets list.
     */
    public List<LevelSet> getLevelSetList() {
        return this.levelSetList;
    }

    /**
     * LevelSet inner class.
     */
    public static class LevelSet {
        private String key;
        private String message;
        private String path;

        /**
         * Sets the message.
         *
         * @param newMessage the new message.
         */
        public void setMessage(String newMessage) {
            this.message = newMessage;
        }

        /**
         * Sets the key.
         *
         * @param newKey the new key.
         */
        public void setKey(String newKey) {
            this.key = newKey;
        }

        /**
         * Sets the path.
         *
         * @param newPath the new path.
         */
        public void setPath(String newPath) {
            this.path = newPath;
        }

        /**
         * Gives the message.
         *
         * @return the message.
         */
        public String getMessage() {
            return this.message;
        }

        /**
         * Gives the key.
         *
         * @return the key.
         */
        public String getKey() {
            return this.key;
        }

        /**
         * Gives the path.
         *
         * @return the path.
         */
        public String getPath() {
            return this.path;
        }
    }
}