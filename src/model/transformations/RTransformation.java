package model.transformations;

import model.IImageState;
import model.ImageImpl;
import model.IImage;

/**
 * Represents a transformation to adjust the red component of an image.
 */

public class RTransformation implements ITransformation {

  public RTransformation() {
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
   * Transforms an image into a new greyscale image using red component.
   *     @param sourceImage The original image.
   *     @return A new greyscale image based on red component.
   */
  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    // Iterate through each pixel of the sourceImage and
    // apply the red component transformation.
    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        // Calculate new RGB values for the pixel, keep original red component.
        int newR = clamp(sourceImage.getRedChannel(row, col));
        int newG = clamp(sourceImage.getRedChannel(row, col));
        int newB = clamp(sourceImage.getRedChannel(row, col));

        // Set the new RGB values for the pixel in the newImage.
        newImage.setPixel(row, col, newR, newG, newB);
      }
    }
    return newImage;
  }
}