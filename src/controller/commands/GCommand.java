package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.GTransformation;
import model.transformations.ITransformation;


/**
 * Represents a command to transform an image using the G transformation.
 */
public class GCommand implements ICommand {
  private IImageDataBase model;

  /**
   * Constructor for GCommand.
   */
  public GCommand() {
    // Empty constructor.
  }

  /**
   * Run the GCommand for the green transformation.
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

    // Create a GTransformation object and apply the transformation to the source image.
    ITransformation gTransformation = new GTransformation();
    IImageState gImage = gTransformation.run(sourceImage);

    // Add the transformed image to the model with the specified destination id.
    this.model.add(destId, gImage);
  }
}