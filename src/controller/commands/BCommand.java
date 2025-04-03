package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.BTransformation;
import model.transformations.ITransformation;

/**
 * Represents a command to transform an image using the B transformation.
 */

public class BCommand implements ICommand {
  private IImageDataBase model;

  /**
   * Constructor for the BCommand Class.
   */
  public BCommand() {
    // Empty constructor.
  }

  /**
   * Run the BCommand for the Blue transformation.
   *     @param scanner Scans input.
   *     @param model   The image database model.
   */
  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);

    // Check if the source image id is present.
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Second argument must be the image id.");
    }
    String sourceImageId = scanner.next();

    // Check if the destination id is present.
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Third argument must be the image id.");
    }
    String destId = scanner.next();

    // Get the source image with the specified id from the model.
    IImageState sourceImage = model.get(sourceImageId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id doesn't exist.");
    }

    // Create a BTransformation object and transformation the source image.
    ITransformation bTransformation = new BTransformation();
    IImageState bImage = bTransformation.run(sourceImage);

    // Add the transformed image to the model with the specified destination id.
    this.model.add(destId, bImage);
  }
}