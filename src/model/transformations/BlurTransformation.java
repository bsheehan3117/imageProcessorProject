package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * Implements the ITransformation interface and
 * performs the blur transformation on an image.
 */

public class BlurTransformation implements ITransformation {

  /**
   * Kernel array for the blur transformation.
   */
  private static final double[][] BLUR_KERNEL = {
          {1.0 / 16, 1.0 / 8, 1.0 / 16},
          {1.0 / 8, 1.0 / 4, 1.0 / 8},
          {1.0 / 16, 1.0 / 8, 1.0 / 16}
  };

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
   * Runs th blur transformation.
   *     @param sourceImage The original image.
   *     @return A new blurred image.
   */
  @Override
  public IImageState run(IImageState sourceImage) {

    // Create a new image the same size as source.
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    // Loop through pixels in source image.
    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {

        // Create variable for sums of channels.
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        // Determine kernel size and halve it to center kernel on pixel.
        int kernelSize = BLUR_KERNEL.length;
        int halfKernelSize = kernelSize / 2;

        // Blur each pixel.
        for (int i = 0; i < kernelSize; i++) {
          for (int j = 0; j < kernelSize; j++) {

            // Clamp the coordinates for kernal to stay within image.
            int x = Math.min(Math.max(col + j - halfKernelSize, 0), sourceImage.getWidth() - 1);
            int y = Math.min(Math.max(row + i - halfKernelSize, 0), sourceImage.getHeight() - 1);

            // Get the kernel value at ij.
            double kernelValue = BLUR_KERNEL[i][j];

            // Add sums for each channel with values multiplied by kernal value.
            redSum += sourceImage.getRedChannel(y, x) * kernelValue;
            greenSum += sourceImage.getGreenChannel(y, x) * kernelValue;
            blueSum += sourceImage.getBlueChannel(y, x) * kernelValue;
          }
        }

        // Clamp and round sums.
        int red = clamp((int)Math.round(redSum));
        int green = clamp((int)Math.round(greenSum));
        int blue = clamp((int)Math.round(blueSum));

        // Set new image pixels.
        newImage.setPixel(row, col, red, green, blue);
      }
    }

    return newImage;
  }
}
