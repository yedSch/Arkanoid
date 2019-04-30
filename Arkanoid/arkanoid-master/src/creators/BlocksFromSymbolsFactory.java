package creators;

import sprites.blocks.BaseBlock;

import java.util.HashMap;
import java.util.Map;

/**
 * BlocksFromSymbolsFactory class.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructs a BlocksFromSymbolsFactory.
     *
     * @param newSpacerWidths  spacer widths map.
     * @param newBlockCreators block creators map.
     */
    BlocksFromSymbolsFactory(Map<String, Integer> newSpacerWidths, Map<String, BlockCreator> newBlockCreators) {
        this.spacerWidths = new HashMap<>(newSpacerWidths);
        this.blockCreators = new HashMap<>(newBlockCreators);
    }

    /**
     * Returns true if 's' is a valid space symbol.
     *
     * @param symbol the symbol.
     * @return if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String symbol) {
        return this.spacerWidths.containsKey(symbol);
    }

    /**
     * Returns true if 's' is a valid block symbol.
     *
     * @param symbol the symbol.
     * @return if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String symbol) {
        return this.blockCreators.containsKey(symbol);
    }

    /**
     * Gives space width according to symbol.
     *
     * @param symbol the symbol.
     * @return space width according to symbol.
     */
    public int getSpaceWidth(String symbol) {
        return this.spacerWidths.get(symbol);
    }

    /**
     * Gives block according to symbol.
     *
     * @param symbol the symbol.
     * @param xPos   the x position of the block.
     * @param yPos   the y position of the block.
     * @return block according to symbol.
     */
    public BaseBlock getBlock(String symbol, int xPos, int yPos) {
        return this.blockCreators.get(symbol).create(xPos, yPos);
    }
}