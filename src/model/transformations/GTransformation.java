package model.transformations;

import model.IImageState;
import model.ImageImpl;
import model.IImage;

/**
 * Represents a transformation to adjust the green component of an image.
 */
public class GTransformation implements ITransformation {


  /**
   * Constructor for the G transformation.
   */
  public GTransformation() {
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
   * Method for changing an image to greyscale using the green component.
   *     @param sourceImage The original image.
   *     @return A new greyscale image.
   */
  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    // Iterate through each pixel of the sourceImage and
    // apply the green component transformation.
    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        // Calculate new RGB values for the pixel, keep the original green component.
        int newR = clamp(sourceImage.getGreenChannel(row, col));
        int newG = clamp(sourceImage.getGreenChannel(row, col));
        int newB = clamp(sourceImage.getGreenChannel(row, col));

        // Set the new RGB values for the pixel in the newImage.
        newImage.setPixel(row, col, newR, newG, newB);
      }
    }
    return newImage;
  }
}