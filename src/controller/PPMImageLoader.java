package controller;

import model.IImageState;
import model.ImageImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents an image loader that can load images from a PPM file.
 */

public class PPMImageLoader implements IImageLoader {
  private final String filePath;

  public PPMImageLoader(String filePath) {
    this.filePath = Objects.requireNonNull(filePath);
  }

  /**
   * A method for loading an image ppm file.
   *     @return a loaded image.
   */
  @Override
  public IImageState run() {
    try {
      Scanner sc = new Scanner(new FileInputStream(filePath));

      // Read the PPM file header.
      String magicNumber = sc.next();
      int width = sc.nextInt();
      int height = sc.nextInt();
      int maxValue = sc.nextInt();

      // Create an ImageImpl object to hold the pixel.
      ImageImpl image = new ImageImpl(width, height);

      // Read the pixel data and set the pixels in the image object.
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          image.setPixel(x, y, r, g, b);
        }
      }

      sc.close();
      return image;
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filePath + " not found!");
    }
  }
}
