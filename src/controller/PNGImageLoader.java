package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import controller.IImageLoader;
import model.IImageState;
import model.ImageImpl;

/**
 * A class for loading PNG images which implements the IImageloader interface
 * and its methods.
 */
public class PNGImageLoader implements IImageLoader {
  private final String filePath;

  /**
   * Constructor for PNGImageloader which takes filepath as a param.
   *     @param filePath the filepath to be loaded.
   */
  public PNGImageLoader(String filePath) {
    this.filePath = Objects.requireNonNull(filePath);
  }

  /**
   * Runs the PNGImageLoader.
   *     @return an IImageState of the file.
   */
  @Override
  public IImageState run() {
    try {

      // Read the filepath image.
      BufferedImage image = ImageIO.read(new File(filePath));

      int width = image.getWidth();
      int height = image.getHeight();

      // Create a new imageimpl with the width and height.
      ImageImpl ppmImage = new ImageImpl(width, height);

      // Loop through pixels.
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {

          // Get values for rgb.
          int rgb = image.getRGB(x, y);

          // Assign values to variables.
          int r = (rgb >> 16) & 0xFF;
          int g = (rgb >> 8) & 0xFF;
          int b = rgb & 0xFF;

          // Set the values in ppm format.
          ppmImage.setPixel(x, y, r, g, b);
        }
      }

      // Return image in ppm format.
      return ppmImage;

      // Throw exception if file not found.
    } catch (IOException e) {
      throw new IllegalArgumentException("File " + filePath + " not found!");
    }
  }
}