import controller.commands.BCommand;
import controller.commands.DarkenCommand;
import controller.commands.GCommand;
import controller.commands.ICommand;
import controller.commands.LumaCommand;
import controller.commands.RCommand;
import controller.commands.ValueComponentCommand;
import model.IImage;
import model.IImageDataBase;
import model.IImageState;
import model.ImageDataBase;
import model.ImageImpl;
import model.transformations.DarkenTransformation;
import model.transformations.ITransformation;
import model.transformations.IntensityTransformation;
import model.transformations.LumaTransformation;

import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for commands and transforms.
 */
public class TestCommandTransform {

  private class MockImageDataBase implements IImageDataBase {

    @Override
    public void add(String id, IImageState image) {
      // Space comment
    }

    @Override

    public IImageState get(String id) {
      return new MockImageState();
    }
  }

  private class MockImageState implements IImageState {

    @Override
    public int getRedChannel(int x, int y) {
      return 0;
    }

    @Override
    public int getBlueChannel(int x, int y) {
      return 0;
    }

    @Override
    public int getGreenChannel(int x, int y) {
      return 0;
    }

    @Override
    public int getWidth() {
      return 0;
    }

    @Override
    public int getHeight() {
      return 0;
    }
  }

  @Test(expected = NullPointerException.class)
  public void testRun_NullScanner_ThrowsException() {
    GCommand command = new GCommand();
    command.run(null, new MockImageDataBase());
  }

  @Test(expected = NullPointerException.class)
  public void testRun_NullDataBase_ThrowsException() {
    GCommand command = new GCommand();
    command.run(new Scanner(""), null);
  }

  @Test
  public void testValueComponentCommand() {
    // Create database
    IImageDataBase model = new ImageDataBase();

    // Create img
    ImageImpl image = new ImageImpl(2, 2);
    image.setPixel(0, 0, 100, 150, 200);
    image.setPixel(0, 1, 50, 75, 100);
    image.setPixel(1, 0, 200, 100, 50);
    image.setPixel(1, 1, 0, 0, 0);

    // Add img
    model.add("image1", image);

    // Create a ValueComponentCommand
    ICommand valueComponentCommand = new ValueComponentCommand();

    Scanner scanner = new Scanner("image1 result");
    valueComponentCommand.run(scanner, model);

    // get new img
    IImageState transformedImage = model.get("result");

    // assert result
    assertEquals(200, transformedImage.getRedChannel(0, 0));
    assertEquals(200, transformedImage.getGreenChannel(0, 0));
    assertEquals(200, transformedImage.getBlueChannel(0, 0));

    assertEquals(100, transformedImage.getRedChannel(0, 1));
    assertEquals(100, transformedImage.getGreenChannel(0, 1));
    assertEquals(100, transformedImage.getBlueChannel(0, 1));

    assertEquals(200, transformedImage.getRedChannel(1, 0));
    assertEquals(200, transformedImage.getGreenChannel(1, 0));
    assertEquals(200, transformedImage.getBlueChannel(1, 0));

    assertEquals(0, transformedImage.getRedChannel(1, 1));
    assertEquals(0, transformedImage.getGreenChannel(1, 1));
    assertEquals(0, transformedImage.getBlueChannel(1, 1));
  }

  @Test
  public void testRCommand() {
    // Create database
    IImageDataBase model = new ImageDataBase();

    // Create img
    ImageImpl image = new ImageImpl(2, 2);
    image.setPixel(0, 0, 100, 150, 200);
    image.setPixel(0, 1, 50, 75, 100);
    image.setPixel(1, 0, 200, 100, 50);
    image.setPixel(1, 1, 0, 0, 0);

    // Add img
    model.add("image1", image);

    // Create an RCommand
    ICommand rCommand = new RCommand();

    Scanner scanner = new Scanner("image1 result");
    rCommand.run(scanner, model);

    // Get new img
    IImageState transformedImage = model.get("result");

    // Assert result
    assertEquals(100, transformedImage.getRedChannel(0, 0));
    assertEquals(0, transformedImage.getGreenChannel(0, 0));
    assertEquals(0, transformedImage.getBlueChannel(0, 0));

    assertEquals(50, transformedImage.getRedChannel(0, 1));
    assertEquals(0, transformedImage.getGreenChannel(0, 1));
    assertEquals(0, transformedImage.getBlueChannel(0, 1));

    assertEquals(200, transformedImage.getRedChannel(1, 0));
    assertEquals(0, transformedImage.getGreenChannel(1, 0));
    assertEquals(0, transformedImage.getBlueChannel(1, 0));

    assertEquals(0, transformedImage.getRedChannel(1, 1));
    assertEquals(0, transformedImage.getGreenChannel(1, 1));
    assertEquals(0, transformedImage.getBlueChannel(1, 1));
  }

  @Test
  public void testGCommand() {
    // Create database
    IImageDataBase model = new ImageDataBase();

    // Create img
    ImageImpl image = new ImageImpl(2, 2);
    image.setPixel(0, 0, 100, 150, 200);
    image.setPixel(0, 1, 50, 75, 100);
    image.setPixel(1, 0, 200, 100, 50);
    image.setPixel(1, 1, 0, 0, 0);

    // Add img
    model.add("image1", image);

    // Create a GCommand
    ICommand gCommand = new GCommand();

    Scanner scanner = new Scanner("image1 result");
    gCommand.run(scanner, model);

    // Get new img
    IImageState transformedImage = model.get("result");

    // Assert result
    assertEquals(0, transformedImage.getRedChannel(0, 0));
    assertEquals(150, transformedImage.getGreenChannel(0, 0));
    assertEquals(0, transformedImage.getBlueChannel(0, 0));

    assertEquals(0, transformedImage.getRedChannel(0, 1));
    assertEquals(75, transformedImage.getGreenChannel(0, 1));
    assertEquals(0, transformedImage.getBlueChannel(0, 1));

    assertEquals(0, transformedImage.getRedChannel(1, 0));
    assertEquals(100, transformedImage.getGreenChannel(1, 0));
    assertEquals(0, transformedImage.getBlueChannel(1, 0));

    assertEquals(0, transformedImage.getRedChannel(1, 1));
    assertEquals(0, transformedImage.getGreenChannel(1, 1));
    assertEquals(0, transformedImage.getBlueChannel(1, 1));
  }

  @Test
  public void testBCommand() {
    // Create database
    IImageDataBase model = new ImageDataBase();

    // Create img
    ImageImpl image = new ImageImpl(2, 2);
    image.setPixel(0, 0, 100, 150, 200);
    image.setPixel(0, 1, 50, 75, 100);
    image.setPixel(1, 0, 200, 100, 50);
    image.setPixel(1, 1, 0, 0, 0);

    // Add img
    model.add("image1", image);

    // Create a BCommand
    ICommand bCommand = new BCommand();

    Scanner scanner = new Scanner("image1 result");
    bCommand.run(scanner, model);

    // Get new img
    IImageState transformedImage = model.get("result");

    // Assert result
    assertEquals(0, transformedImage.getRedChannel(0, 0));
    assertEquals(0, transformedImage.getGreenChannel(0, 0));
    assertEquals(200, transformedImage.getBlueChannel(0, 0));

    assertEquals(0, transformedImage.getRedChannel(0, 1));
    assertEquals(0, transformedImage.getGreenChannel(0, 1));
    assertEquals(100, transformedImage.getBlueChannel(0, 1));

    assertEquals(0, transformedImage.getRedChannel(1, 0));
    assertEquals(0, transformedImage.getGreenChannel(1, 0));
    assertEquals(50, transformedImage.getBlueChannel(1, 0));

    assertEquals(0, transformedImage.getRedChannel(1, 1));
    assertEquals(0, transformedImage.getGreenChannel(1, 1));
    assertEquals(0, transformedImage.getBlueChannel(1, 1));
  }


  private IImageDataBase mockDatabase;
  private IImageState sourceImage;
  private IImageState expectedDarkenedImage;
  private final String sourceId = "source";
  private final String destId = "dest";
  private final int darkenValue = 20;

  @Before
  public void setUp() {
    mockDatabase = new IImageDataBase() {
      @Override
      public void add(String id, IImageState image) {
        if (id.equals(destId)) {
          expectedDarkenedImage = image;
        }
      }



      @Override
      public IImageState get(String id) {
        if (id.equals(sourceId)) {
          return sourceImage;
        }
        return null;
      }
    };

    sourceImage = new ImageImpl(2, 2);
    ((IImage) sourceImage).setPixel(0, 0, 50, 100, 150);
    ((IImage) sourceImage).setPixel(0, 1, 60, 110, 160);
    ((IImage) sourceImage).setPixel(1, 0, 70, 120, 170);
    ((IImage) sourceImage).setPixel(1, 1, 80, 130, 180);

    mockDatabase.add(sourceId, sourceImage);
  }

  @Test
  public void testDarkenCommand() {
    DarkenCommand darkenCommand = new DarkenCommand();
    String commandArgs = darkenValue + " " + sourceId + " " + destId;
    Scanner scanner = new Scanner(commandArgs);

    darkenCommand.run(scanner, mockDatabase);

    assertTrue(expectedDarkenedImage != null);
    assertEquals(30, expectedDarkenedImage.getRedChannel(0, 0));
    assertEquals(80, expectedDarkenedImage.getGreenChannel(0, 0));
    assertEquals(130, expectedDarkenedImage.getBlueChannel(0, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testDarkenCommandInvalidInput() {
    DarkenCommand darkenCommand = new DarkenCommand();
    String commandArgs = "not an int" + " " + sourceId + " " + destId;
    Scanner scanner = new Scanner(commandArgs);

    darkenCommand.run(scanner, mockDatabase);
  }

  @Test
  public void testDarkenTransformation() {
    DarkenTransformation darkenTransformation = new DarkenTransformation(darkenValue);

    IImageState darkenedImage = darkenTransformation.run(sourceImage);

    assertEquals(30, darkenedImage.getRedChannel(0, 0));
    assertEquals(80, darkenedImage.getGreenChannel(0, 0));
    assertEquals(130, darkenedImage.getBlueChannel(0, 0));
  }

  @Test
  public void testLumaTransformation() {
    ImageImpl sourceImage = new ImageImpl(3, 3);
    sourceImage.setPixel(0, 0, 255, 100, 100);
    sourceImage.setPixel(0, 1, 0, 255, 100);
    sourceImage.setPixel(0, 2, 100, 100, 255);

    ITransformation lumaTransform = new LumaTransformation();
    IImageState resultImage = lumaTransform.run(sourceImage);

    assertEquals(resultImage.getRedChannel(0, 0), 54);
    assertEquals(resultImage.getGreenChannel(0, 0), 54);
    assertEquals(resultImage.getBlueChannel(0, 0), 54);

    assertEquals(resultImage.getRedChannel(0, 1), 182);
    assertEquals(resultImage.getGreenChannel(0, 1), 182);
    assertEquals(resultImage.getBlueChannel(0, 1), 182);

    assertEquals(resultImage.getRedChannel(0, 2), 114);
    assertEquals(resultImage.getGreenChannel(0, 2), 114);
    assertEquals(resultImage.getBlueChannel(0, 2), 114);
  }

  @Test
  public void testLumaCommand() {
    IImageDataBase database = new ImageDataBase();
    String id = "test_image";
    IImageState image = new ImageImpl(3, 3);
    database.add(id, image);

    LumaCommand command = new LumaCommand();
    command.run(new Scanner("test_image"), database);

    IImageState updatedImage = database.get(id);
    assertNotNull(updatedImage);

    for (int i = 0; i < updatedImage.getWidth(); i++) {
      for (int j = 0; j < updatedImage.getHeight(); j++) {
        assertEquals(updatedImage.getRedChannel(i, j), 0);
        assertEquals(updatedImage.getGreenChannel(i, j), 0);
        assertEquals(updatedImage.getBlueChannel(i, j), 0);
      }
    }
  }


  @Test
  public void testIntensityTransformation() {
    IntensityTransformation intensityTransformation = new IntensityTransformation();

    IImageState intensityImage = intensityTransformation.run(sourceImage);

    assertTrue(intensityImage != null);
    int expectedIntensityValue = (50 + 110 + 150) / 3;
    assertEquals(expectedIntensityValue, intensityImage.getRedChannel(0, 0));
    assertEquals(expectedIntensityValue, intensityImage.getGreenChannel(0, 0));
    assertEquals(expectedIntensityValue, intensityImage.getBlueChannel(0, 0));

    expectedIntensityValue = (60 + 110 + 150) / 3;
    assertEquals(expectedIntensityValue, intensityImage.getRedChannel(0, 1));
    assertEquals(expectedIntensityValue, intensityImage.getGreenChannel(0, 1));
    assertEquals(expectedIntensityValue, intensityImage.getBlueChannel(0, 1));

    expectedIntensityValue = (70 + 120 + 170) / 3;
    assertEquals(expectedIntensityValue, intensityImage.getRedChannel(1, 0));
    assertEquals(expectedIntensityValue, intensityImage.getGreenChannel(1, 0));
    assertEquals(expectedIntensityValue, intensityImage.getBlueChannel(1, 0));

    expectedIntensityValue = (80 + 130 + 180) / 3;
    assertEquals(expectedIntensityValue, intensityImage.getRedChannel(1, 1));
    assertEquals(expectedIntensityValue, intensityImage.getGreenChannel(1, 1));
    assertEquals(expectedIntensityValue, intensityImage.getBlueChannel(1, 1));
  }

}
