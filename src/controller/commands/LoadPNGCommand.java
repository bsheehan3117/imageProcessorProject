package controller.commands;

import controller.IImageLoader;
import controller.PNGImageLoader;
import model.IImageDataBase;
import model.IImageState;

import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a command to load an image from a PPM file.
 */
public class LoadPNGCommand implements ICommand {

  /**
   * Command for loading the ppm file.
   *     @param scanner Scans input.
   *     @param model   The image database model.
   */

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);

    // Check if the file path is present.
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Expected a file path.");
    }

    String filePath = scanner.next();
    // Check if the image id is present.
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Expected an image id.");
    }
    String imageId = scanner.next();

    // Create an image loader for PPM files and
    // load the image from the specified file path.
    IImageLoader imageLoader = new PNGImageLoader(filePath);
    IImageState image = imageLoader.run();

    // Add the loaded image to the model with the specified image id.
    model.add(imageId, image);
  }
}