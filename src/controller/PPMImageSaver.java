package controller;

import model.IImageState;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents an image saver that can save images to a PPM file.
 */

public class PPMImageSaver implements IImageSaver {
  private final String pathToSave;
  private final IImageState image;
  private FileWriter writer;

  /**
   * Saves a PPM file.
   *     @param pathToSave THe path to be saved to.
   *     @param image THe image to save.
   *     @param output The output file.
   */
  public PPMImageSaver(String pathToSave, IImageState image, Appendable output) {
    this.pathToSave = Objects.requireNonNull(pathToSave);
    this.image = Objects.requireNonNull(image);

  }

  /**
   * Helper method for writing to the file.
   *     @param message The message to be written to the file.
   */
  private void write(String message) {
    try {
      this.writer.write(message);
    } catch (IOException e) {
      throw new IllegalStateException("Writing Failed");
    }
  }

  /**
   * Runs the ppm image saver.
   */
  @Override
  public void run() {
    try {
      this.writer = new FileWriter(pathToSave);
      int width = image.getWidth();
      int height = image.getHeight();

      // Write the PPM file header.
      write("P3\n");
      write(width + " " + height + "\n");
      write("255\n");

      // Write pixel data.
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int r = image.getRedChannel(x, y);
          int g = image.getGreenChannel(x, y);
          int b = image.getBlueChannel(x, y);
          write(r + " " + g + " " + b + " ");
        }
        write("\n");
      }

      this.writer.close();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save the image to " + pathToSave);
    }
  }
}
