package model;

/**
 * Represents the database of images and provides methods to add and retrieve images.
 */

public interface IImageDataBase {

  /**
   * Adds an image to the database with the specified ID.
   *     @param id The ID of the image.
   *     @param image The image to add to the database.
   */
  void add(String id, IImageState image);

  /**
   * Gets the image from the database based on the ID.
   *     @param id The ID of the image to get.
   *     @return The image with the ID, or null if not found.
   */
  IImageState get(String id);
}