import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import controller.commands.BlurCommand;
import controller.commands.SaveBMPCommand;
import controller.commands.SaveJPGCommand;
import controller.commands.SavePNGCommand;
import controller.commands.SharpenCommand;
import model.IImageState;
import model.ImageDataBase;
import model.transformations.BlurTransformation;
import model.transformations.SharpenTransformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for HW9.
 */
public class TestHW9 {
  private ImageDataBase model;
  private IImageState mockImage;

  @Before
  public void setup() {
    model = new ImageDataBase();
    mockImage = new MockImageState(3, 3);
    model.add("mockImage", mockImage);
  }

  /**
   * Mock Image for the HW 9 methods.
   */
  static class MockImageState implements IImageState {

    private final int width;
    private final int height;
    private final int[][] redChannel;
    private final int[][] greenChannel;
    private final int[][] blueChannel;

    public MockImageState(int width, int height) {
      this.width = width;
      this.height = height;

      redChannel = new int[height][width];
      greenChannel = new int[height][width];
      blueChannel = new int[height][width];
    }

    public void setPixel(int row, int col, int red, int green, int blue) {
      redChannel[row][col] = red;
      greenChannel[row][col] = green;
      blueChannel[row][col] = blue;
    }

    @Override
    public int getRedChannel(int row, int col) {
      return redChannel[row][col];
    }

    @Override
    public int getGreenChannel(int row, int col) {
      return greenChannel[row][col];
    }

    @Override
    public int getBlueChannel(int row, int col) {
      return blueChannel[row][col];
    }

    @Override
    public int getWidth() {
      return width;
    }

    @Override
    public int getHeight() {
      return height;
    }
  }

  @Test
  public void testBlurTransformation() {
    BlurTransformation blur = new BlurTransformation();

    MockImageState testImage = new MockImageState(3, 3);

    testImage.setPixel(0, 0, 255, 0, 0);
    testImage.setPixel(0, 1, 0, 255, 0);
    testImage.setPixel(0, 2, 0, 0, 255);
    testImage.setPixel(1, 0, 255, 255, 0);
    testImage.setPixel(1, 1, 255, 0, 255);
    testImage.setPixel(1, 2, 0, 255, 255);
    testImage.setPixel(2, 0, 128, 0, 0);
    testImage.setPixel(2, 1, 0, 128, 0);
    testImage.setPixel(2, 2, 0, 0, 128);

    IImageState resultImage = blur.run(testImage);

    MockImageState expectedImage = new MockImageState(3, 3);

    expectedImage.setPixel(0, 0, 201, 92, 15);
    expectedImage.setPixel(0, 1, 92, 124, 92);
    expectedImage.setPixel(0, 2, 15, 92, 201);
    expectedImage.setPixel(1, 0, 195, 117, 31);
    expectedImage.setPixel(1, 1, 117, 109, 117);
    expectedImage.setPixel(1, 2, 31, 117, 195);
    expectedImage.setPixel(2, 0, 133, 70, 15);
    expectedImage.setPixel(2, 1, 70, 78, 70);
    expectedImage.setPixel(2, 2, 15, 70, 133);

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedImage.getRedChannel(i, j), resultImage.getRedChannel(i, j));
        assertEquals(expectedImage.getGreenChannel(i, j), resultImage.getGreenChannel(i, j));
        assertEquals(expectedImage.getBlueChannel(i, j), resultImage.getBlueChannel(i, j));
      }
    }

    // Run test again on result.
    IImageState newResultImage = blur.run(expectedImage);

    MockImageState newExpectedImage = new MockImageState(3,3);

    newExpectedImage.setPixel(0, 0, 171, 99, 32);
    newExpectedImage.setPixel(0, 1, 99, 105, 99);
    newExpectedImage.setPixel(0, 2, 32, 99, 171);
    newExpectedImage.setPixel(1, 0, 156, 95, 35);
    newExpectedImage.setPixel(1, 1, 95, 97, 95);
    newExpectedImage.setPixel(1, 2, 35, 95, 156);
    newExpectedImage.setPixel(2, 0, 128, 77, 28);
    newExpectedImage.setPixel(2, 1, 77, 79, 77);
    newExpectedImage.setPixel(2, 2, 28, 77, 128);

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(newExpectedImage.getRedChannel(i, j), newResultImage.getRedChannel(i, j));
        assertEquals(newExpectedImage.getGreenChannel(i, j), newResultImage.getGreenChannel(i, j));
        assertEquals(newExpectedImage.getBlueChannel(i, j), newResultImage.getBlueChannel(i, j));
      }
    }
  }

  @Test
  public void testSharpenTransformation() {
    SharpenTransformation sharpen = new SharpenTransformation();

    MockImageState testImage = new MockImageState(3, 3);

    testImage.setPixel(0, 0, 255, 0, 0);
    testImage.setPixel(0, 1, 0, 255, 0);
    testImage.setPixel(0, 2, 0, 0, 255);
    testImage.setPixel(1, 0, 255, 255, 0);
    testImage.setPixel(1, 1, 255, 0, 255);
    testImage.setPixel(1, 2, 0, 255, 255);
    testImage.setPixel(2, 0, 128, 0, 0);
    testImage.setPixel(2, 1, 0, 128, 0);
    testImage.setPixel(2, 2, 0, 0, 128);

    IImageState resultImage = sharpen.run(testImage);

    MockImageState expectedImage = new MockImageState(3, 3);

    expectedImage.setPixel(0, 0, 255, 141, 0);
    expectedImage.setPixel(0, 1, 66, 255, 63);
    expectedImage.setPixel(0, 2, 0, 141, 255);
    expectedImage.setPixel(1, 0, 255, 255, 0);
    expectedImage.setPixel(1, 1, 242, 110, 240);
    expectedImage.setPixel(1, 2, 0, 255, 255);
    expectedImage.setPixel(2, 0, 210, 80, 0);
    expectedImage.setPixel(2, 1, 34, 176, 32);
    expectedImage.setPixel(2, 2, 0, 80, 209);

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedImage.getRedChannel(i, j), resultImage.getRedChannel(i, j));
        assertEquals(expectedImage.getGreenChannel(i, j), resultImage.getGreenChannel(i, j));
        assertEquals(expectedImage.getBlueChannel(i, j), resultImage.getBlueChannel(i, j));
      }
    }

    // Run test again on result.
    IImageState newResultImage = sharpen.run(expectedImage);

    MockImageState newExpectedImage = new MockImageState(3,3);

    newExpectedImage.setPixel(0, 0, 255, 232, 0);
    newExpectedImage.setPixel(0, 1, 110, 255, 106);
    newExpectedImage.setPixel(0, 2, 0, 232, 255);
    newExpectedImage.setPixel(1, 0, 255, 255, 0);
    newExpectedImage.setPixel(1, 1, 228, 176, 225);
    newExpectedImage.setPixel(1, 2, 0, 255, 255);
    newExpectedImage.setPixel(2, 0, 255, 120, 0);
    newExpectedImage.setPixel(2, 1, 57, 192, 54);
    newExpectedImage.setPixel(2, 2, 0, 120, 255);

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(newExpectedImage.getRedChannel(i, j), newResultImage.getRedChannel(i, j));
        assertEquals(newExpectedImage.getGreenChannel(i, j), newResultImage.getGreenChannel(i, j));
        assertEquals(newExpectedImage.getBlueChannel(i, j), newResultImage.getBlueChannel(i, j));
      }
    }
  }


  @Test
  public void testBlurTransformationSinglePixelImage() {
    BlurTransformation blur = new BlurTransformation();

    MockImageState testImage = new MockImageState(1, 1);
    testImage.setPixel(0, 0, 255, 255, 255);

    IImageState resultImage = blur.run(testImage);

    assertEquals(247, resultImage.getRedChannel(0, 0));
    assertEquals(247, resultImage.getGreenChannel(0, 0));
    assertEquals(247, resultImage.getBlueChannel(0, 0));
  }

  @Test
  public void testSharpenTransformationSinglePixelImage() {
    SharpenTransformation sharpen = new SharpenTransformation();

    MockImageState testImage = new MockImageState(1, 1);
    testImage.setPixel(0, 0, 255, 255, 255);

    IImageState resultImage = sharpen.run(testImage);

    assertEquals(255, resultImage.getRedChannel(0, 0));
    assertEquals(255, resultImage.getGreenChannel(0, 0));
    assertEquals(255, resultImage.getBlueChannel(0, 0));
  }
  
  @Test
  public void testBlurCommandNoSourceId() {
    Scanner scanner = new Scanner("");
    BlurCommand command = new BlurCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Second argument must be the image id.", e.getMessage());
    }
  }

  @Test
  public void testBlurCommandNoDestId() {
    Scanner scanner = new Scanner("mockImage");
    BlurCommand command = new BlurCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Third argument must be the image id.", e.getMessage());
    }
  }

  @Test
  public void testBlurCommandImageDoesntExist() {
    Scanner scanner = new Scanner("nonexistentImage destImage");
    BlurCommand command = new BlurCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Image with specified id doesn't exist.", e.getMessage());
    }
  }

  @Test
  public void testSharpenCommandNoSourceId() {
    Scanner scanner = new Scanner("");
    SharpenCommand command = new SharpenCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Second argument must be the image id.", e.getMessage());
    }
  }

  @Test
  public void testSharpenCommandNoDestId() {
    Scanner scanner = new Scanner("mockImage");
    SharpenCommand command = new SharpenCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Third argument must be the image id.", e.getMessage());
    }
  }

  @Test
  public void testSharpenCommandImageDoesntExist() {
    Scanner scanner = new Scanner("nonexistentImage destImage");
    SharpenCommand command = new SharpenCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Image with specified id doesn't exist.", e.getMessage());
    }
  }

  @Test
  public void testSaveBMPCommandNoFilePath() {
    Scanner scanner = new Scanner("");
    SaveBMPCommand command = new SaveBMPCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Expected a file path.", e.getMessage());
    }
  }

  @Test
  public void testSaveBMPCommandNoImageId() {
    Scanner scanner = new Scanner("test.bmp");
    SaveBMPCommand command = new SaveBMPCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Expected an image id.", e.getMessage());
    }
  }

  @Test
  public void testSaveBMPCommandNoImage() {
    model.add("mockImage", mockImage);
    Scanner scanner = new Scanner("test.bmp nonExistingImage");
    SaveBMPCommand command = new SaveBMPCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("No image with the given id exists.", e.getMessage());
    }
  }

  @Test
  public void testSaveBMPCommand() {
    model.add("mockImage", mockImage);
    Scanner scanner = new Scanner("test.bmp mockImage");
    SaveBMPCommand command = new SaveBMPCommand();

    try {
      command.run(scanner, model);
    } catch (Exception e) {
      fail("Exception should not have been thrown");
    }
  }

  @Test
  public void testSaveJPGCommandNoFilePath() {
    Scanner scanner = new Scanner("");
    SaveJPGCommand command = new SaveJPGCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Expected a file path.", e.getMessage());
    }
  }

  @Test
  public void testSaveJPGCommandNoImageId() {
    Scanner scanner = new Scanner("test.bmp");
    SaveJPGCommand command = new SaveJPGCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Expected an image id.", e.getMessage());
    }
  }

  @Test
  public void testSaveJPGCommandNoImage() {
    model.add("mockImage", mockImage);
    Scanner scanner = new Scanner("test.bmp nonExistingImage");
    SaveJPGCommand command = new SaveJPGCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("No image with the given id exists.", e.getMessage());
    }
  }

  @Test
  public void testSaveJPGCommand() {
    model.add("mockImage", mockImage);
    Scanner scanner = new Scanner("test.bmp mockImage");
    SaveJPGCommand command = new SaveJPGCommand();

    try {
      command.run(scanner, model);
    } catch (Exception e) {
      fail("Exception should not have been thrown");
    }
  }

  @Test
  public void testSavePNGCommandNoFilePath() {
    Scanner scanner = new Scanner("");
    SavePNGCommand command = new SavePNGCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Expected a file path.", e.getMessage());
    }
  }

  @Test
  public void testSavePNGCommandNoImageId() {
    Scanner scanner = new Scanner("test.bmp");
    SavePNGCommand command = new SavePNGCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("Expected an image id.", e.getMessage());
    }
  }

  @Test
  public void testSavePNGCommandNoImage() {
    model.add("mockImage", mockImage);
    Scanner scanner = new Scanner("test.bmp nonExistingImage");
    SavePNGCommand command = new SavePNGCommand();

    try {
      command.run(scanner, model);
      fail("Exception should have been thrown");
    } catch (IllegalStateException e) {
      assertEquals("No image with the given id exists.", e.getMessage());
    }
  }

  @Test
  public void testSavePNGCommand() {
    model.add("mockImage", mockImage);
    Scanner scanner = new Scanner("test.bmp mockImage");
    SavePNGCommand command = new SavePNGCommand();

    try {
      command.run(scanner, model);
    } catch (Exception e) {
      fail("Exception should not have been thrown");
    }
  }
}
