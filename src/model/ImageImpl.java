package model;

/**
 * Represents an image with pixels and provides methods to set pixel values.
 */

public class ImageImpl implements IImage {
  private final int width;
  private final int height;
  private final IPixel [][] data;

  /**
   * Constructs an ImageImpl with the specified width and height.
   *     @param width  The width of the image.
   *     @param height The height of the image.
   */

  public ImageImpl(int width, int height) {

    this.width = width;
    this.height = height;
    this.data = new Pixel[width][height];

  }

  /**
   * Gets the red channel value from the image.
   *     @param x The x coordinate of the pixel.
   *     @param y The y coordinate of the pixel.
   *     @return The red channel in the database.
   */
  @Override
  public int getRedChannel(int x, int y) {
    if ( x < 0 || x >= this.height || y < 0 || y > this.width) {
      throw new IllegalArgumentException("X or Y outside of bounds.");
    }
    return this.data[x][y].getR();
  }

  /**
   * Gets the blue channel value of the image.
   *     @param x The x coordinate of the pixel.
   *     @param y The y coordinate of the pixel.
   *     @return The blue channel of the image.
   */
  @Override
  public int getBlueChannel(int x, int y) {
    return this.data[x][y].getB();
  }

  /**
   * Gets the green channel value of the image.
   *     @param x The x coordinate of the pixel.
   *     @param y The y coordinate of the pixel.
   *     @return The green channel of the image.
   */
  @Override
  public int getGreenChannel(int x, int y) {
    return this.data[x][y].getG();
  }

  /**
   * Gets the width of the image.
   *     @return The width of the image.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the height of the image.
   *     @return The height of the image.
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Sets the values of the image pixels.
   *     @param x The x coordinate of the pixel.
   *     @param y The y coordinate of the pixel.
   *     @param r The red channel value.
   *     @param g The green channel value.
   *     @param b The blue channel value.
   */
  @Override
  public void setPixel(int x, int y, int r, int g, int b) {
    if ( x < 0 || x >= this.height || y < 0 || y > this.width) {
      throw new IllegalArgumentException("X or Y outside of bounds.");
    }
    this.data[x] [y] = new Pixel(r, g, b);
  }
}
