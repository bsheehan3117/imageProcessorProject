package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.BrightenTransformation;
import model.transformations.ITransformation;

/**
 * Represents a command to transform an image using the Brighten transformation.
 */
public class BrightenCommand implements ICommand {
  private IImageDataBase model;

  /**
   * Constructor for the brighten command.
   */
  public BrightenCommand() {
    //Empty Constructor.
  }

  /**
   * Run the brighten command for the brighten transformation.
   *     @param scanner Scans input.
   *     @param model   The image database model.
   */
  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);

    // Check if the second argument is an integer brightness value.
    if (!scanner.hasNextInt()) {
      throw new IllegalStateException("Second argument must be an int.");
    }

    int value = scanner.nextInt();

    // Check if the source image id is present.
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Third argument must be the image id.");
    }
    String sourceImageId = scanner.next();

    // Check if the destination image id is present.
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Fourth argument must be the image id.");
    }
    String destId = scanner.next();

    // Get the source image with the specified id from the model.
    IImageState sourceImage = model.get(sourceImageId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id doesn't exist.");
    }

    // Create a BrightenTransformation object with the
    // specified brightness value and transform the source image.
    ITransformation brightenTransformation = new BrightenTransformation(value);
    IImageState brightenedImage = brightenTransformation.run(sourceImage);

    // Add the transformed image to the model with the specified destination id.
    this.model.add(destId, brightenedImage);
  }
}