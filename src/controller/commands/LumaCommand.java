package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.LumaTransformation;
import model.transformations.ITransformation;

/**
 * Represents a command to transform an image using the Luma transformation.
 */

public class LumaCommand implements ICommand {

  /**
   * Runs the Luma command.
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

    // Get the source image with the specified id from the model.
    IImageState sourceImage = model.get(sourceImageId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id doesn't exist.");
    }

    // Create a LumaTransformation object and
    // apply the transformation to the source image.
    ITransformation lumaTransformation = new LumaTransformation();
    IImageState lumaImage = lumaTransformation.run(sourceImage);

    // Replace the source image with the transformed image in the model.
    model.add(sourceImageId, lumaImage);
  }
}