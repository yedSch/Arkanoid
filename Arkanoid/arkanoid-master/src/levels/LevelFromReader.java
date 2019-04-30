package levels;

import geometry.Velocity;
import sprites.Sprite;
import sprites.blocks.BaseBlock;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * LevelFromReader class.
 */
public class LevelFromReader implements LevelInformation {
    private List<Velocity> velocities;
    private String levelName;
    private int paddleSpeed;
    private int paddleWidth;
    private int numBlocks;
    private int numBalls;
    private Sprite background;
    private int xPosBlocks;
    private int yPosBlocks;
    private int rowHeight;
    private InputStreamReader blocksDefinitionsFile;
    private List<BaseBlock> blocks;

    /**
     * Constructs a LevelFromReader.
     */
    public LevelFromReader() {
        this.velocities = new ArrayList<>();
        this.blocks = new ArrayList<>();
    }

    /**
     * Adds a block.
     *
     * @param block the block to add.
     */
    public void addBlock(BaseBlock block) {
        this.blocks.add(block);
    }

    /**
     * Sets level name.
     *
     * @param newLevelName the new level name.
     */
    public void setLevelName(String newLevelName) {
        this.levelName = newLevelName;
    }

    /**
     * Sets background.
     *
     * @param newBackground the new background.
     */
    public void setBackground(Sprite newBackground) {
        this.background = newBackground;
    }

    /**
     * Sets paddles speed.
     *
     * @param newPaddleSpeed the new paddles speed.
     */
    public void setPaddleSpeed(int newPaddleSpeed) {
        this.paddleSpeed = newPaddleSpeed;
    }

    /**
     * Sets xPos.
     *
     * @param xPos the new xPos.
     */
    public void setxPosBlocks(int xPos) {
        this.xPosBlocks = xPos;
    }

    /**
     * Sets yPos.
     *
     * @param yPos the new yPos.
     */
    public void setyPosBlocks(int yPos) {
        this.yPosBlocks = yPos;
    }

    /**
     * Sets the blocks list.
     *
     * @param newBlocks the new blocks list.
     */
    public void setBlocks(List<BaseBlock> newBlocks) {
        this.blocks = newBlocks;
    }

    /**
     * Sets the num of the blocks.
     *
     * @param newNumBlocks the new num of the blocks.
     */
    public void setNumBlocks(int newNumBlocks) {
        this.numBlocks = newNumBlocks;
    }

    /**
     * Sets paddles width.
     *
     * @param newPaddleWidth the new paddles width.
     */
    public void setPaddleWidth(int newPaddleWidth) {
        this.paddleWidth = newPaddleWidth;
    }

    /**
     * Sets the row height.
     *
     * @param newRowHeight the new row height.
     */
    public void setRowHeight(int newRowHeight) {
        this.rowHeight = newRowHeight;
    }

    /**
     * Sets velocities.
     *
     * @param newVelocities the new velocities.
     */
    public void setVelocities(List<Velocity> newVelocities) {
        this.numBalls = newVelocities.size();
        this.velocities = newVelocities;
    }

    /**
     * Sets setBlocksDefinitionsFile.
     *
     * @param newBlocksDefinitionsFile the new blocks definitions file.
     */
    public void setBlocksDefinitionsFile(InputStreamReader newBlocksDefinitionsFile) {
        this.blocksDefinitionsFile = newBlocksDefinitionsFile;
    }

    /**
     * Gives row height.
     *
     * @return the row height.
     */
    public int getRowHeight() {
        return rowHeight;
    }

    /**
     * Gives the level name.
     *
     * @return the level name.
     */
    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     * Gives the blocks list.
     *
     * @return the blocks list.
     */
    @Override
    public List<BaseBlock> blocks() {
        return this.blocks;
    }

    /**
     * Gives the paddles width.
     *
     * @return the paddles width.
     */
    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Gives the paddles speed.
     *
     * @return the paddles speed.
     */
    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Gives the number of the blocks to remove.
     *
     * @return the number of the blocks to remove.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.numBlocks;
    }

    /**
     * Gives the velocities.
     *
     * @return the velocities.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    /**
     * Gives the number of the balls.
     *
     * @return the number of the balls.
     */
    @Override
    public int numberOfBalls() {
        return this.numBalls;
    }

    /**
     * Gives background.
     *
     * @return background.
     */
    @Override
    public Sprite getBackground() {
        return background;
    }

    /**
     * Gives block definition file.
     *
     * @return block definition file.
     */
    @Override
    public InputStreamReader blockDefinitionsFile() {
        return this.blocksDefinitionsFile;
    }

    /**
     * Gives xPos of blocks.
     *
     * @return xPos of blocks.
     */
    @Override
    public int xPosBlocks() {
        return this.xPosBlocks;
    }

    /**
     * Gives yPos of blocks.
     *
     * @return yPos of blocks.
     */
    @Override
    public int yPosBlocks() {
        return this.yPosBlocks;
    }
}