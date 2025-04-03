package model.transformations;

import model.IImageState;
import model.ImageImpl;
import model.IImage;

/**
 * Represents a transformation to adjust the intensity of an image.
 */
public class IntensityTransformation implements ITransformation {

  public IntensityTransformation(){
    // Empty Constructor.
  }

  /**
   * Method for an intensity transformation of an image.
   *     @param sourceImage The original image.
   *     @return A new intensity image.
   */
  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    // Iterate through each pixel of the sourceImage and calculate the average intensity.
    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        // Calculate the average intensity by averaging the RGB channels.
        int avg = (sourceImage.getRedChannel(row, col) +
                sourceImage.getGreenChannel(row, col) +
                sourceImage.getBlueChannel(row, col)) / 3;

        // Set the new RGB values for the new image with the average.
        newImage.setPixel(row, col, avg, avg, avg);
      }
    }
    return newImage;
  }
}
