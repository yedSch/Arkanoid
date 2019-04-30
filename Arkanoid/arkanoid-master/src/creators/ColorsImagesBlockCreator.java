package creators;

import sprites.blocks.BaseBlock;
import sprites.blocks.ColorsImagesBlock;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * ColorsImagesBlockCreator class.
 */
public class ColorsImagesBlockCreator extends BaseBlockCreator {
    private Color strokeColor;
    private Map<Integer, Color> fillColors;
    private Map<Integer, BufferedImage> fillImages;

    /**
     * Constructs a ColorsImagesBlockCreator.
     *
     * @param width the width of the block.
     * @param height the height of the block.
     * @param hitPoints the hit points of the block.
     * @param strokeColor the stroke color of the block.
     * @param fillColors the fill colors of the block.
     * @param fillImages the fill resources of the block.
     */
    public ColorsImagesBlockCreator(int width, int height, int hitPoints, Color strokeColor,
                                    Map<Integer, Color> fillColors, Map<Integer, BufferedImage> fillImages) {
        super(width, height, hitPoints);
        this.strokeColor = strokeColor;
        this.fillColors = new HashMap<>(fillColors);
        this.fillImages = new HashMap<>(fillImages);
    }

    /**
     * Creates a block according to the block creator.
     *
     * @param xPos the x position of the block to create.
     * @param yPos the y position of the block to create.
     * @return a block according to the block creator.
     */
    @Override
    public BaseBlock create(int xPos, int yPos) {
        ColorsImagesBlock colorsImagesBlock = new ColorsImagesBlock(xPos, yPos,
                super.getWidth(), super.getHeight(), super.getHitPoints());
        colorsImagesBlock.setStrokeColor(this.strokeColor);
        colorsImagesBlock.setFillcolors(this.fillColors);
        colorsImagesBlock.setFillImages(this.fillImages);
        return colorsImagesBlock;
    }
}
