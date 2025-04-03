package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the database of images and provides methods to add and retrieve images.
 */
public class ImageDataBase implements IImageDataBase {

  private final Map<String, IImageState> images;

  public ImageDataBase() {
    this.images = new HashMap<String, IImageState>();
  }


  /**
   * Adds the image to the database.
   *     @param id The ID of the image.
   *     @param image The image to add to the database.
   */
  @Override
  public void add(String id, IImageState image) {
    if ( id == null || image == null ) {
      throw new IllegalArgumentException("id or image is null");
    }

    this.images.put(id, image);
  }


  /**
   * Gets an image from the database.
   *     @param id The ID of the image to get.
   *     @return The image from the database.
   */
  @Override
  public IImageState get(String id) {
    Objects.requireNonNull(id);
    return this.images.get(id);
  }
}
