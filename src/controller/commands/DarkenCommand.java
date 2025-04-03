package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.DarkenTransformation;
import model.transformations.ITransformation;

/**
 * Represents a command to transform an image using the Darken transformation.
 */
public class DarkenCommand implements ICommand {

  /**
   * Run the darken command for the darken transformation.
   *     @param scanner Scans input.
   *     @param model   The image database model.
   */
  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);

    // Check if the second argument is an integer darken value.
    if (!scanner.hasNextInt()) {
      throw new IllegalStateException("Second argument must be an int.");
    }

    int value = scanner.nextInt();

    // Check if the source image id is present.
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Third argument must be the image id.");
    }
    String sourceImageId = scanner.next();

    // Check if the destination id is present.
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Fourth argument must be the image id.");
    }
    String destId = scanner.next();

    // Get the source image with the specified id from the model.
    IImageState sourceImage = model.get(sourceImageId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id doesn't exist.");
    }

    // Create a DarkenTransformation object with the darken value
    // and apply the transformation to the source image.
    ITransformation darkenTransformation = new DarkenTransformation(value);
    IImageState darkenedImage = darkenTransformation.run(sourceImage);

    // Add the transformed image to the model with the specified destination id.
    model.add(destId, darkenedImage);
  }
}
