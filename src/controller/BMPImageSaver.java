package controller;

import controller.IImageSaver;
import model.IImageState;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;
import javax.imageio.ImageIO;


/**
 * A class which implements the IImagesaver interface to save BMP
 * images.
 */
public class BMPImageSaver implements IImageSaver {
  private final String pathToSave;
  private final IImageState image;

  /**
   * Constructor for the BMPImageSaver.
   *     @param pathToSave The path to save the image at.
   *     @param image The image to save.
   *     @param out A printstream.
   */
  public BMPImageSaver(String pathToSave, IImageState image, PrintStream out) {
    this.pathToSave = Objects.requireNonNull(pathToSave);
    this.image = Objects.requireNonNull(image);
  }

  /**
   * Runs the BMPImageSaver.
   */
  @Override
  public void run() {
    try {

      // Get the width and height of the image.
      int width = image.getWidth();
      int height = image.getHeight();

      // Creates a BufferedImage with W, H, and rgb values.
      BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

      // Loop through the pixels.
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {

          // Get the rgb values and assign to rgb variable.
          int r = image.getRedChannel(x, y);
          int g = image.getGreenChannel(x, y);
          int b = image.getBlueChannel(x, y);
          int rgb = (r << 16) | (g << 8) | b;

          // Set buffered image with rgb values.
          bufferedImage.setRGB(x, y, rgb);
        }
      }

      // Create a File with filepath to save at.
      File outputFile = new File(pathToSave);

      // Write buffered image to file.
      ImageIO.write(bufferedImage, "bmp", outputFile);

      // throw exception if file does not save.
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save the image to " + pathToSave);
    }
  }
}
