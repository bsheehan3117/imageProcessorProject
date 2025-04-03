package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * Represents a transformation to adjust the brightness of an image.
 */

public class BrightenTransformation implements ITransformation {
  final private int brightenValue;

  public BrightenTransformation(int brightenValue) {
    this.brightenValue = brightenValue;
  }

  /**
   * Helper method for clamping a value between limits.
   *     @param value The original value.
   *     @return The clamped value.
   */
  private int clamp(int value) {
    if (value < 0) {
      return 0;
    } else if (value > 255) {
      return 255;
    } else {
      return value;
    }
  }


  /**
   * Runs the Brighten transformation.
   *     @param sourceImage The original image.
   *     @return A new brightened image.
   */
  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    // Iterate through each pixel of the sourceImage and brighten.
    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {

        // Calculate new RGB values for the pixel by adding brightenValue.
        int newR = clamp(sourceImage.getRedChannel(row, col) + brightenValue);
        int newG = clamp(sourceImage.getGreenChannel(row, col) + brightenValue);
        int newB = clamp(sourceImage.getBlueChannel(row, col) + brightenValue);

        // Set the new RGB values for the pixel in the newImage.
        newImage.setPixel(row, col, newR, newG, newB);
      }
    }
    return newImage;
  }
}