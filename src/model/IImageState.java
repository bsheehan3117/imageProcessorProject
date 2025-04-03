package model;

/**
 * Represents an image with pixels and provides methods to access pixel values.
 */
public interface IImageState {

  /**
   * Gets the red channel value of the pixel.
   *     @param x The x coordinate of the pixel.
   *     @param y The y coordinate of the pixel.
   *     @return The red channel value.
   */
  int getRedChannel(int x, int y);

  /**
   * Gets the blue channel value of the pixel.
   *     @param x The x coordinate of the pixel.
   *     @param y The y coordinate of the pixel.
   *     @return The blue channel value..
   */
  int getBlueChannel(int x, int y);

  /**
   * Gets the green channel value of the pixel.
   *     @param x The x coordinate of the pixel.
   *     @param y The y coordinate of the pixel.
   *     @return The green channel value..
   */
  int getGreenChannel(int x, int y);

  /**
   *     Gets the width of the image.
   *     @return The width of the image.
   */
  int getWidth();

  /**
   *     Gets the height of the image.
   *     @return The height of the image.
   */
  int getHeight();
}
