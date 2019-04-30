package levels;

import creators.BlocksDefinitionReader;
import creators.BlocksFromSymbolsFactory;
import general.Utils;
import geometry.Velocity;
import sprites.blocks.BaseBlock;

import java.io.Reader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * LevelSpecificationReader class.
 */
public class LevelSpecificationReader {

    /**
     * Constructs a LevelSpecificationReader.
     *
     * @param reader the reader.
     * @return list of levels from the reader.
     * @throws IOException throw if needed.
     */
    public static List<LevelFromReader> fromReader(Reader reader) throws IOException {
        // keeps data for all levels
        List<LevelFromReader> levels = new ArrayList<>();

        // keeps data for current level
        LevelFromReader curLevel;

        List<Velocity> velocities;
        String symbol;
        int xPos, yPos;

        try (LineNumberReader lineReader = new LineNumberReader(reader)) {
            String line;
            // advance while not null
            while ((line = lineReader.readLine()) != null) {
                // skip over # and blank
                while ((line.startsWith("#") || line.equals(""))) {
                    line = lineReader.readLine().trim();
                }
                if (line.equals("START_LEVEL")) {
                    curLevel = new LevelFromReader();
                    while (!line.equals("END_LEVEL")) {
                        line = lineReader.readLine().trim();
                        while ((line.startsWith("#") || line.equals(""))) {
                            line = lineReader.readLine().trim();
                        }
                        if (line.equals("START_BLOCKS")) {

                            // init yPos to the start x of blocks
                            yPos = curLevel.yPosBlocks();
                            // factory of the blocks according to blocks definitions file
                            BlocksFromSymbolsFactory blocksFactory
                                    = BlocksDefinitionReader.fromReader(curLevel.blockDefinitionsFile());

                            while (!line.equals("END_BLOCKS")) {
                                line = lineReader.readLine().trim();
                                while (line.startsWith("#") || line.equals("")) {
                                    line = lineReader.readLine().trim();
                                }

                                // init xPos to the start x of blocks
                                xPos = curLevel.xPosBlocks();
                                // translate line to row of blocks
                                for (int i = 0; i < line.length(); i++) {
                                    symbol = line.charAt(i) + "";
                                    // case block
                                    if (blocksFactory.isBlockSymbol(symbol)) {
                                        // add block according to factory
                                        BaseBlock block = blocksFactory.getBlock(symbol, xPos, yPos);
                                        curLevel.addBlock(block);
                                        // add blocks width to xPos
                                        xPos += block.getWidth();
                                        // case space
                                    } else if (blocksFactory.isSpaceSymbol(symbol)) {
                                        // add spaces width to xPos
                                        xPos += blocksFactory.getSpaceWidth(symbol);
                                    }
                                }
                                // add row height to yPos
                                yPos += curLevel.getRowHeight();
                            }
                        }
                        // rows that match the format key:value
                        if (line.split(":").length == 2) {
                            // key, value
                            String[] keyVal = line.split(":");
                            String key = keyVal[0];
                            String value = keyVal[1];
                            switch (key) {
                                case "level_name":
                                    curLevel.setLevelName(value);
                                    break;
                                case "ball_velocities":
                                    velocities = new ArrayList<>();
                                    String[] velocitiesDef = value.split(" ");
                                    for (String velDef : velocitiesDef) {
                                        String[] props = velDef.split(",");
                                        velocities.add(Velocity.fromAngleAndSpeed(Double.parseDouble(props[0]),
                                                Double.parseDouble(props[1])));
                                    }
                                    curLevel.setVelocities(velocities);
                                    break;
                                case "background":
                                    curLevel.setBackground(Utils.parseBackground(value));
                                    break;
                                case "paddle_speed":
                                    curLevel.setPaddleSpeed(Integer.parseInt(value));
                                    break;
                                case "paddle_width":
                                    curLevel.setPaddleWidth(Integer.parseInt(value));
                                    break;
                                case "block_definitions":
                                    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(value);
                                    curLevel.setBlocksDefinitionsFile(new InputStreamReader(is));
                                    break;
                                case "blocks_start_x":
                                    curLevel.setxPosBlocks(Integer.parseInt(value));
                                    break;
                                case "blocks_start_y":
                                    curLevel.setyPosBlocks(Integer.parseInt(value));
                                    break;
                                case "row_height":
                                    curLevel.setRowHeight(Integer.parseInt(value));
                                    break;
                                case "num_blocks":
                                    curLevel.setNumBlocks(Integer.parseInt(value));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    levels.add(curLevel);
                }
            }
        }
        return levels;
    }
}