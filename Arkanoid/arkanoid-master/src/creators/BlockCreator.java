package creators;

import sprites.blocks.BaseBlock;

/**
 * BlockCreator interface.
 */
public interface BlockCreator {

    /**
     * Creates a block at the specified location.
     *
     * @param xPos the x position of the created block.
     * @param yPos the y position of the created block.
     * @return the created block.
     */
    BaseBlock create(int xPos, int yPos);
}
