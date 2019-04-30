package sprites.blocks;

import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * ColorsImagesBlock class.
 */
public class ColorsImagesBlock extends BaseBlock {
    private Color strokeColor;
    private Map<Integer, Color> fillcolors;
    private Map<Integer, BufferedImage> fillImages;

    /**
     * Constructs a ColorsImagesBlock.
     *
     * @param xPos the x position of the block.
     * @param yPos the y position of the block.
     * @param width the width of the block.
     * @param height the height of the block.
     * @param hitPoints the hit points of the block.
     */
    public ColorsImagesBlock(int xPos, int yPos, int width, int height, int hitPoints) {
        super(xPos, yPos, width, height, hitPoints);
        this.strokeColor = null;
        this.fillcolors = null;
        this.fillImages = null;
    }

    /**
     * Sets the stroke color.
     *
     * @param newStrokeColor the new stroke color.
     */
    public void setStrokeColor(Color newStrokeColor) {
        this.strokeColor = newStrokeColor;
    }

    /**
     * Sets the fill colors map.
     *
     * @param newFillcolors the new fill colors map.
     */
    public void setFillcolors(Map<Integer, Color> newFillcolors) {
        this.fillcolors = newFillcolors;
    }

    /**
     * Sets the fill resources map.
     *
     * @param newFillImages the new fill resources map.
     */
    public void setFillImages(Map<Integer, BufferedImage> newFillImages) {
        this.fillImages = new HashMap<>(newFillImages);
    }

    /**
     * Draws this ball using given draw surface.
     *
     * @param surface use this to draw.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        // block coordinates sizes
        int startX = (int) super.getShape().getUpperLeft().getX();
        int startY = (int) super.getShape().getUpperLeft().getY();
        int height = (int) super.getShape().getHeight();
        int width = (int) super.getShape().getWidth();

        // case no match hit points and maps
        if (!fillcolors.containsKey(super.getHitPoints()) && !fillImages.containsKey(super.getHitPoints())) {
            // use default color or image
            if (this.fillcolors.containsKey(1)) {
                surface.setColor(this.fillcolors.get(1));
                surface.fillRectangle(startX, startY, width, height);
                // use default image
            } else if (this.fillImages.containsKey(1)) {
                surface.drawImage(startX, startY, this.fillImages.get(1));
            }
        } else if (this.fillcolors.containsKey(super.getHitPoints())) {
            // color that fit the hit points
            surface.setColor(this.fillcolors.get(super.getHitPoints()));
            surface.fillRectangle(startX, startY, width, height);
        } else if (this.fillImages.containsKey(super.getHitPoints())) {
            // image that fit the hit points
            surface.drawImage(startX, startY, this.fillImages.get(super.getHitPoints()));
        }

        if (this.strokeColor != null) {
            // draw frame
            surface.setColor(this.strokeColor);
            surface.drawRectangle(startX, startY, width, height);
        }
    }
}
