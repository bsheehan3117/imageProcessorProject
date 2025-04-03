package model.transformations;

import model.IImageState;
import model.ImageImpl;
import model.IImage;

/**
 * Represents a transformation to adjust the darkness of an image.
 */
public class DarkenTransformation implements ITransformation {
  private final int darkenValue;

  public DarkenTransformation(int darkenValue) {
    this.darkenValue = darkenValue;
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
   * Transformation method that darkens an image.
   *     @param sourceImage The original image.
   *     @return A new darker image.
   */
  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    // Iterate through each pixel of the sourceImage and
    // apply the darkness transformation.
    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        // Calculate new RGB values for the pixel by subtracting the darkenValue.
        int newR = clamp(sourceImage.getRedChannel(row, col) - darkenValue);
        int newG = clamp(sourceImage.getGreenChannel(row, col) - darkenValue);
        int newB = clamp(sourceImage.getBlueChannel(row, col) - darkenValue);
        newImage.setPixel(row, col, newR, newG, newB);
      }
    }
    return newImage;
  }
}
