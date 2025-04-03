package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * Represents a transformation for sharpening an image.
 */
public class SharpenTransformation implements ITransformation {

  /**
   * Kernel array for the sharpen transformation.
   */
  private static final double[][] SHARPEN_KERNEL = {
          {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
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
   * Runs the sharper transformation.
   *     @param sourceImage The original image.
   *     @return A new sharpened image.
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
        int kernelSize = SHARPEN_KERNEL.length;
        int halfKernelSize = kernelSize / 2;

        // Sharpen each pixel.
        for (int j = 0; j < kernelSize; j++) {
          for (int i = 0; i < kernelSize; i++) {

            // Clamp the coordinates for kernal to stay within image
            int x = Math.min(Math.max(col + i - halfKernelSize, 0), sourceImage.getWidth() - 1);
            int y = Math.min(Math.max(row + j - halfKernelSize, 0), sourceImage.getHeight() - 1);

            // Get the kernel value at ij.
            double kernelValue = SHARPEN_KERNEL[j][i];

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
