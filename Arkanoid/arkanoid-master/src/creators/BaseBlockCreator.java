package creators;

import sprites.blocks.BaseBlock;

/**
 * BaseBlockCreator class.
 */
public abstract class BaseBlockCreator implements BlockCreator {
    private int width;
    private int height;
    private int hitPoints;

    /**
     * Constructs a BaseBlockCreator.
     *
     * @param width     the width of the block to create.
     * @param height    the height of the block to create.
     * @param hitPoints the hit points of the block to create.
     */
    BaseBlockCreator(int width, int height, int hitPoints) {
        this.width = width;
        this.height = height;
        this.hitPoints = hitPoints;
    }

    /**
     * Sets the width of the block to create.
     *
     * @param newWidth the new width.
     */
    public void setWidth(int newWidth) {
        this.width = newWidth;
    }

    /**
     * Sets the height of the block to create.
     *
     * @param newHeight the new width.
     */
    public void setHeight(int newHeight) {
        this.height = newHeight;
    }

    /**
     * Sets the hitPoints of the block to create.
     *
     * @param newHitPoints the new width.
     */
    public void setHitPoints(int newHitPoints) {
        this.hitPoints = newHitPoints;
    }

    /**
     * Gives the width of the block to create.
     *
     * @return the width of the block to create.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gives the height of the block to create.
     *
     * @return the height of the block to create.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gives the hit points of the block to create.
     *
     * @return the hit points of the block to create.
     */
    int getHitPoints() {
        return hitPoints;
    }

    /**
     * Creates a block according to creator.
     *
     * @param xPos the x position of the block to create.
     * @param yPos the y position of the block to create.
     * @return the created block.
     */
    @Override
    public abstract BaseBlock create(int xPos, int yPos);
}
