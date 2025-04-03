package model;

/**
 * Represents an image with pixels and provides methods to set pixel values.
 */
public interface IImage extends IImageState {


  /**
   * Sets the RGB values of the pixel at the specified coordinates (x, y).
   *     @param x The x coordinate of the pixel.
   *     @param y The y coordinate of the pixel.
   *     @param r The red channel value.
   *     @param g The green channel value.
   *     @param b The blue channel value.
   */
  void setPixel(int x, int y, int r, int g, int b);
}
