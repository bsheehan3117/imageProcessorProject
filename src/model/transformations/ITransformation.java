package model.transformations;

import model.IImageState;

/**
 * Represents a transformation applied to an image.
 */
public interface ITransformation {
  /**
   * Runs the transformation of an image.
   *     @param sourceImage The original image.
   *     @return A new transformed image.
   */
  IImageState run(IImageState sourceImage);
}
