package model;

/**
 * Represents a pixel in an image with red, green, and blue channels.
 */
public interface IPixel extends IPixelState {

  /**
  * Retrieves the red channel value of the pixel.
  *     @return The red channel value.
  */

  int getR();

  /**
  * Retrieves the green channel value of the pixel.
  *     @return The green channel value.
  */

  int getG();

  /**
  * Retrieves the blue channel value of the pixel.
  *     @return The blue channel value.
  */

  int getB();

  /**
  * Sets the red channel value of the pixel.
  *     @param val The red channel value to set.
  */
  void setR(int val);

  /**
  * Sets the green channel value of the pixel.
  *     @param val The green channel value to set.
  */
  void setG(int val);

  /**
  * Sets the blue channel value of the pixel.
  *     @param val The blue channel value to set.
  */
  void setB(int val);
}
