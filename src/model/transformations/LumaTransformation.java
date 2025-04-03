package model.transformations;

import model.IImageState;
import model.ImageImpl;
import model.IImage;

/**
 * Represents a transformation to convert an image to luma.
 */
public class LumaTransformation implements ITransformation {

  /**
   * Runs the luma transformation on an image.
   *     @param sourceImage The original image.
   *     @return A new Luma transformed image.
   */
  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    // Iterate through the sourceImage and caclulate luma values.
    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        // Calculate new RGB values based on weighted sum formula.
        int newR = (int) (sourceImage.getRedChannel(row, col) * 0.2126);
        int newG = (int) (sourceImage.getGreenChannel(row, col) * 0.7152);
        int newB = (int) (sourceImage.getBlueChannel(row, col) * 0.0722);

        // Set the new RGB values for the pixel in the newImage.
        newImage.setPixel(row, col, newR, newG, newB);
      }
    }
    return newImage;
  }
}