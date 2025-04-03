import org.junit.Test;

import controller.IImageLoader;
import controller.PPMImageLoader;
import model.IImageState;
import model.ImageDataBase;
import model.ImageImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;


/**
 * Test class for HW 8 methods.
 */
public class TestHW8 {
  @Test
  public void testPixelConstructorValid() {
    int r = 100;
    int g = 150;
    int b = 200;
    Pixel pixel = new Pixel(r, g, b);

    assertEquals(r, pixel.getR());
    assertEquals(g, pixel.getG());
    assertEquals(b, pixel.getB());
  }

  @Test
  public void testPixelConstructorInvalid() {
    try {
      new Pixel(300, 150, 200);
      fail("IllegalArgumentException expected");
    } catch (IllegalArgumentException e) {
      // Exception
    }

    try {
      new Pixel(100, -50, 200);
      fail("IllegalArgumentException expected");
    } catch (IllegalArgumentException e) {
      // exception
    }

    try {
      new Pixel(100, 150, 260);
      fail("IllegalArgumentException expected");
    } catch (IllegalArgumentException e) {
      // Exception
    }
  }

  @Test
  public void testPixelSettersValid() {
    int r = 100;
    int g = 150;
    int b = 200;
    Pixel pixel = new Pixel(0, 0, 0);

    pixel.setR(r);
    pixel.setG(g);
    pixel.setB(b);

    assertEquals(r, pixel.getR());
    assertEquals(g, pixel.getG());
    assertEquals(b, pixel.getB());
  }

  @Test
  public void testPixelSettersInvalid() {
    Pixel pixel = new Pixel(100, 150, 200);

    try {
      pixel.setR(-50);
      fail("IllegalArgumentException expected");
    } catch (IllegalArgumentException e) {
      // Exception
    }

    try {
      pixel.setG(300);
      fail("IllegalArgumentException expected");
    } catch (IllegalArgumentException e) {
      // Exception
    }

    try {
      pixel.setB(260);
      fail("IllegalArgumentException expected");
    } catch (IllegalArgumentException e) {
      // Exception
    }
  }

  @Test
  public void testToString() {
    int r = 100;
    int g = 150;
    int b = 200;
    Pixel pixel = new Pixel(r, g, b);

    assertEquals(r + " " + g + " " + b, pixel.toString());
  }

  @Test
  public void testImageImplConstructorValid() {
    int width = 100;
    int height = 200;
    ImageImpl image = new ImageImpl(width, height);

    assertEquals(width, image.getWidth());
    assertEquals(height, image.getHeight());
  }

  @Test
  public void testImageImplGetterSettersValid() {
    int width = 100;
    int height = 200;
    ImageImpl image = new ImageImpl(width, height);

    int x = 50;
    int y = 100;
    int r = 150;
    int g = 200;
    int b = 250;
    image.setPixel(x, y, r, g, b);

    assertEquals(r, image.getRedChannel(x, y));
    assertEquals(g, image.getGreenChannel(x, y));
    assertEquals(b, image.getBlueChannel(x, y));
  }

  @Test
  public void testImageImplGettersInvalid() {
    int width = 100;
    int height = 200;
    ImageImpl image = new ImageImpl(width, height);

    try {
      // invalid x
      image.getRedChannel(150, 100);
      fail("ArrayIndexOutOfBoundsException expected");
    } catch (ArrayIndexOutOfBoundsException e) {
      assertEquals("Index 150 out of bounds for length 100", e.getMessage());
    }

    try {
      // invalid y
      image.getGreenChannel(50, 300);
      fail("ArrayIndexOutOfBoundsException expected");
    } catch (ArrayIndexOutOfBoundsException e) {
      assertEquals("Index 300 out of bounds for length 200", e.getMessage());
    }

    try {
      // invalid x and y
      image.getBlueChannel(-50, 200);
      fail("ArrayIndexOutOfBoundsException expected");
    } catch (ArrayIndexOutOfBoundsException e) {
      assertEquals("Index -50 out of bounds for length 100", e.getMessage());
    }
  }

  @Test
  public void testAddAndGetImage() {
    ImageDataBase imageDataBase = new ImageDataBase();
    IImageState image = new ImageImpl(5, 3);
    imageDataBase.add("image1", image);
    assertEquals(image, imageDataBase.get("image1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullImage() {
    ImageDataBase imageDataBase = new ImageDataBase();
    imageDataBase.add("image1", null);
  }

  @Test
  public void testGetNonExistentImage() {
    ImageDataBase imageDataBase = new ImageDataBase();
    assertNull(imageDataBase.get("nonexistent"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPPMImageLoaderInvalidFile() {
    String filePath = "path/to/invalid/file.ppm";
    IImageLoader imageLoader = new PPMImageLoader(filePath);
    imageLoader.run();
  }
}