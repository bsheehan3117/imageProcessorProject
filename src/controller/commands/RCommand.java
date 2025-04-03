package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.RTransformation;
import model.transformations.ITransformation;

/**
 * Represents a command to transform an image using the R transformation.
 */
public class RCommand implements ICommand {
  private IImageDataBase model;

  /**
   * Constructor for the RCommand.
   */
  public RCommand() {
    // Empty Constructor.
  }

  /**
   * Run the RCommand.
   *     @param scanner Scans input.
   *     @param model The image database model.
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

    // Check if the destination image id is present.
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Third argument must be the image id.");
    }
    String destId = scanner.next();

    // Get the source image with the specified id from the model.
    IImageState sourceImage = model.get(sourceImageId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id doesn't exist.");
    }

    // Create an RTransformation object and
    // apply the transformation to the source image.
    ITransformation rTransformation = new RTransformation();
    IImageState rImage = rTransformation.run(sourceImage);

    // Add the transformed image to the model with the specified destination id.
    this.model.add(destId, rImage);
  }
}