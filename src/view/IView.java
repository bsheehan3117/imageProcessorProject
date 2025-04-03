package view;

import java.awt.image.BufferedImage;

import model.IImageState;

/**
 * An interface for the View class.
 */
public interface IView {

  /** Helper method to display an image at the center after loading or editing.
   *     @param img The image to be displayed at the center.
   */
  void displayImage(BufferedImage img);

  /**
   * Helper method to convert an image to a buffered image for display.
   *     @param image The image to convert to bufferedImage.
   *     @return the Buffered image.
   */
  BufferedImage convertToBufferedImage(IImageState image);

  /**
   * Helper method to create an IImageState from a bufferedImage.
   *     @param bufferedImage The bufferedImage.
   *     @return The IImageState of the buffered image.
   */
  IImageState getCurrentImageStateFromBufferedImage(BufferedImage bufferedImage);

}
