package model.transformations;

import model.IImageState;
import model.ImageImpl;
import model.IImage;

/**
 * Represents a transformation to adjust the value component of an image.
 */
public class ValueComponentTransformation implements ITransformation {

  /**
   * Method which transforms an image based on value.
   *     @param sourceImage The original image.
   *     @return A new value transformed image.
   */
  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    // Iterate through each pixel of the sourceImage
    // and find the maximum value of RGB.
    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int newR = sourceImage.getRedChannel(row, col);
        int newG = sourceImage.getGreenChannel(row, col);
        int newB = sourceImage.getBlueChannel(row, col);
        int maxValue = Math.max(newR, Math.max(newG, newB));

        // Set the new RGB values for the pixel in the newImage.
        newImage.setPixel(row, col, maxValue, maxValue, maxValue);
      }
    }
    return newImage;
  }
}
