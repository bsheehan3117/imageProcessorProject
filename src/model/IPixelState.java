package model;

/**
 * Represents the state of a pixel with red, green, and blue channels.
 */
public interface IPixelState {

  /**
   * Retrieves the red channel value of the pixel.
   *     @return The red channel value of the pixel.
   */
  int getR();

  /**
   * Retrieves the green channel value of the pixel.
   *     @return The green channel value of the pixel.
   */
  int getG();

  /**
   * Retrieves the blue channel value of the pixel.
   *     @return The blue channel value of the pixel.
   */
  int getB();
}