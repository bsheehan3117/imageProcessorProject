package model;

/**
 * Represents a pixel in an image with red, green, and blue channels.
 */
public class Pixel implements IPixel {
  private int r;
  private int g;
  private int b;


  /**
   * Constructs a new Pixel with the specified red, green, and blue channel values.
   *     @param r The red channel value of the pixel.
   *     @param g The green channel value of the pixel.
   *     @param b The blue channel value of the pixel.
   */
  public Pixel(int r, int g, int b) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("Pixel values out of bounds.");
    }

    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Gets the red value.
   * @return The red value.
   */
  @Override
  public int getR() {
    return r;
  }

  /**
   * Gets the green value.
   * @return The green value.
   */
  @Override
  public int getG() {
    return g;
  }

  /**
   * Gets the blue value.
   * @return The blue value.
   */
  @Override
  public int getB() {
    return b;
  }

  /**
   * Sets the red value.
   * @param val The red channel value to set.
   */
  @Override
  public void setR(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid.");
    }
    this.r = val;
  }

  /**
   * Sets the green value.
   * @param val The green channel value to set.
   */
  @Override
  public void setG(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid.");
    }
    this.g = val;
  }

  /**
   * Sets the blue value.
   * @param val The blue channel value to set.
   */
  @Override
  public void setB(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid.");
    }
    this.b = val;
  }

  /**
   * Generates a string representation of the file data.
   *     @return A string representaion of the file data.
   */
  @Override
  public String toString() {
    return this.r + " " + this.g + " " + this.b;
  }
}