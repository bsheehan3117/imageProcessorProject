package model.transformations;

import model.IImageState;
import model.ImageImpl;
import model.IImage;

/**
 * Represents a transformation to adjust the blue component of an image.
 */
public class BTransformation implements ITransformation {

  /**
   * Constructor for the B transformation.
   */
  public BTransformation() {
    // Empty Constructor.

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
   * Transforms the image to greyscale using the blue values.
   *     @param sourceImage The original image.
   *     @return A new transformed image.
   */
  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    // Iterate through each pixel of the sourceImage and
    // apply the blue component transformation.
    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        // Calculate new RGB values for the pixel, keeping only the original blue component.
        int newR = clamp(sourceImage.getBlueChannel(row, col));
        int newG = clamp(sourceImage.getBlueChannel(row, col));
        int newB = clamp(sourceImage.getBlueChannel(row, col));

        // Set the new RGB values for the pixel in the newImage.
        newImage.setPixel(row, col, newR, newG, newB);
      }
    }
    return newImage;
  }
}