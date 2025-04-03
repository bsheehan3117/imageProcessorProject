package controller;

import model.IImageState;

/**
 * An interface for the image loader which loads an
 * image from a file.
 */
public interface IImageLoader {

  /**
   * Method to load an image from a file.
   *     @return a loaded image state.
   */
  IImageState run();

}
