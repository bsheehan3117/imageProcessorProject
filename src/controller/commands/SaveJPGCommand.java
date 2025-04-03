package controller.commands;

import controller.IImageSaver;
import controller.JPGImageSaver;
import model.IImageDataBase;
import model.IImageState;

import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a command to save an image as a PPM file.
 */
public class SaveJPGCommand implements ICommand {

  /**
   * Run the SavePPM command to save a ppm file.
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

    // Get the image with the specified id from the model.
    IImageState image = model.get(imageId);
    if (image == null) {
      throw new IllegalStateException("No image with the given id exists.");
    }

    // Create an image saver for BMP files and
    // save the image to the specified file path.
    IImageSaver imageSaver = new JPGImageSaver(filePath, image, System.out);
    imageSaver.run();
  }
}