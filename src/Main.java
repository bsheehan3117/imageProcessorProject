import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import view.View;
import controller.IImageLoader;
import controller.IImageSaver;
import controller.BMPImageLoader;
import controller.BMPImageSaver;
import controller.JPGImageLoader;
import controller.JPGImageSaver;
import controller.PNGImageLoader;
import controller.PNGImageSaver;
import controller.PPMImageLoader;
import controller.PPMImageSaver;
import model.IImageState;
import model.transformations.BTransformation;
import model.transformations.BlurTransformation;
import model.transformations.BrightenTransformation;
import model.transformations.DarkenTransformation;
import model.transformations.GTransformation;
import model.transformations.IntensityTransformation;
import model.transformations.LumaTransformation;
import model.transformations.RTransformation;
import model.transformations.SharpenTransformation;
import model.transformations.ValueComponentTransformation;

/**
 * A main class used to run commands for transforming, saving, and loading file images.
 * Opens a GUI with no args, opens interactive mode with "-text", executes a script file with
 * "-file path-of-script-file"
 */
public class Main {

  // Database to store images.
  private static final Map<String, IImageState> imageDatabase = new HashMap<>();

  // Scanner for reading commands.
  private static final Scanner scanner = new Scanner(System.in);

  private static void runGUI() {
    View view = new View();
    view.setVisible(true);
  }

  /**
   * The main method used to run commands for transforming images.
   *     @param args The main command line arguments.
   */
  public static void main(String[] args) {

    // If no command-line argument is provided, open the graphical user interface.
    if (args.length == 0) {
      runGUI();
    } else {
      // Check the command-line arguments and execute accordingly.
      if (args[0].equals("-file")) {
        // If "-file" argument is provided, open and run the script file.
        if (args.length != 2) {
          System.out.println("Invalid syntax. Correct Syntax: -file path-of-script-file");
        } else {
          String scriptFileName = args[1];
          File scriptFile = new File(scriptFileName);

          if (scriptFile.exists()) {
            runScript(scriptFile);
          } else {
            System.out.println("Script file not found: " + scriptFileName);
          }
        }
      } else if (args[0].equals("-text")) {
        // If "-text" argument is provided, open interactive text mode.
        runInteractiveMode();
      } else {
        System.out.println("Invalid command-line argument. " +
                "Valid options: -file, -text, or no argument for GUI.");
      }
    }
  }

  /**
   * Helper method for running the script from a script file.
   *     @param scriptFile The script file to run.
   */
  private static void runScript(File scriptFile) {
    try {
      // Scanner for script file.
      Scanner fileScanner = new Scanner(scriptFile);

      // Loop through lines, add lines to command line and process
      // until exit.
      while (fileScanner.hasNextLine()) {
        String commandLine = fileScanner.nextLine().trim();
        if (commandLine.equalsIgnoreCase("exit")) {
          System.exit(0);
        }
        processCommand(commandLine);
      }

      // Close the scanner and throw exception if File not found.
      fileScanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("Script file not found: " + scriptFile.getName());
    }
  }

  /**
   * Helper method for running the program in interactive mode.
   */
  private static void runInteractiveMode() {
    // Loop takes commands until exit command.
    while (true) {
      try {
        System.out.print("Enter command: ");
        String commandLine = scanner.nextLine().trim();

        if (commandLine.equalsIgnoreCase("exit")) {
          break;
        }

        // Run the command.
        processCommand(commandLine);
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  /**
   * Helper method for pocessing commands.
   *     @param commandLine The command given by the user.
   */
  private static void processCommand(String commandLine) {
    String[] tokens = commandLine.split("\\s+");
    String command = tokens[0].toLowerCase();

    // Filter file command
    if (command.equals("-file")) {
      if (tokens.length != 2) {
        System.out.println("Invalid -file command syntax.");
        return;
      }

      // Run script file command.
      String scriptFileName = tokens[1];
      File scriptFile = new File(scriptFileName);
      if (scriptFile.exists()) {
        runScript(scriptFile);
      } else {
        System.out.println("Error: Script file not found: " + scriptFileName);
      }
      return;
    }

    switch (command) {
      case "loadppm":
      case "loadbmp":
      case "loadjpg":
      case "loadpng":
        // Process load command.
        handleLoadCommand(tokens);
        break;
      case "saveppm":
      case "savebmp":
      case "savejpg":
      case "savepng":
        // Process save command.
        handleSaveCommand(tokens);
        break;
      case "brighten":
      case "darken":
      case "value":
      case "intensity":
      case "luma":
      case "rcomponent":
      case "gcomponent":
      case "bcomponent":
      case "blur":
      case "sharpen":
        // Process transformation commands.
        handleTransformationCommand(command, tokens);
        break;
      default:
        System.out.println("Invalid command: " + command);
    }
  }


  /**
   * Helper method for processing the load command.
   *     @param tokens The number of tokens of the command.
   */
  private static void handleLoadCommand(String[] tokens) {
    if (tokens.length != 3) {
      System.out.println("Invalid load command syntax.");
      return;
    }

    String imagePath = tokens[1];
    String imageName = tokens[2];

    // Load the image.
    IImageLoader imageLoader;
    switch (tokens[0].toLowerCase()) {
      case "loadppm":
        imageLoader = new PPMImageLoader(imagePath);
        break;
      case "loadbmp":
        imageLoader = new BMPImageLoader(imagePath);
        break;
      case "loadjpg":
        imageLoader = new JPGImageLoader(imagePath);
        break;

      case "loadpng":
        imageLoader = new PNGImageLoader(imagePath);
        break;
      default:
        System.out.println("Invalid load command.");
        return;

    }

    IImageState image = imageLoader.run();

    if (image == null) {
      System.out.println("Failed to load the image: " + imagePath);
      return;
    }

    // Store the loaded image in the database.
    imageDatabase.put(imageName, image);
    System.out.println("Image '" + imageName + "' loaded successfully.");
  }

  /**
   * Helper method for processing the save command.
   *     @param tokens The number of tokens of the command.
   */
  private static void handleSaveCommand(String[] tokens) {
    if (tokens.length != 3) {
      System.out.println("Invalid save command syntax.");
      return;
    }

    String imagePath = tokens[1];
    String imageName = tokens[2];

    // Retrieve the image from the database.
    IImageState image = imageDatabase.get(imageName);

    if (image == null) {
      System.out.println("Image with name '" + imageName + "' does not exist in the database.");
      return;
    }

    // Save the image using correct ImageSaver based on command given.
    IImageSaver imageSaver;

    switch (tokens[0].toLowerCase()) {

      case "saveppm":
        imageSaver = new PPMImageSaver(imagePath, image, System.out);
        break;
      case "savebmp":
        imageSaver = new BMPImageSaver(imagePath, image, System.out);
        break;
      case "savejpg":
        imageSaver = new JPGImageSaver(imagePath, image, System.out);
        break;
      case "savepng":
        imageSaver = new PNGImageSaver(imagePath, image, System.out);
        break;

      default:
        System.out.println("Invalid save command.");
        return;
    }

    imageSaver.run();
    System.out.println("Image '" + imageName + "' saved successfully to: " + imagePath);
  }


  /**
   * Helper method for processing transformation commands.
   *     @param command The command to process.
   *     @param tokens The number of tokens of the command.
   */
  private static void handleTransformationCommand(String command, String[] tokens) {
    int numExpectedTokens;

    // Determine the number of tokens based on command.
    switch (command) {
      case "intensity":
      case "value":
      case "luma":
      case "rcomponent":
      case "gcomponent":
      case "bcomponent":
      case "blur":
      case "sharpen":
        numExpectedTokens = 3;
        break;
      case "brighten":
      case "darken":
        numExpectedTokens = 4;
        break;
      default:
        System.out.println("Invalid transformation command: " + command);
        return;
    }

    if (tokens.length != numExpectedTokens) {
      System.out.println("Invalid " + command + " command syntax.");
      return;
    }

    int increment = 0;
    String sourceImageName;
    String destImageName;

    // Get process info from command tokens.
    if (numExpectedTokens == 3) {
      sourceImageName = tokens[1];
      destImageName = tokens[2];
    } else {
      try {
        increment = Integer.parseInt(tokens[1]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid increment value.");
        return;
      }
      sourceImageName = tokens[2];
      destImageName = tokens[3];
    }

    // Get source image from the database.
    IImageState sourceImage = imageDatabase.get(sourceImageName);
    if (sourceImage == null) {
      System.out.println("Image with name '" + sourceImageName + "' does " +
              "not exist in the database.");
      return;
    }

    IImageState transformedImage = null;

    // Process the transformation based on the command.
    switch (command) {
      case "brighten":
        transformedImage = new BrightenTransformation(increment).run(sourceImage);
        break;
      case "darken":
        transformedImage = new DarkenTransformation(increment).run(sourceImage);
        break;
      case "value":
        transformedImage = new ValueComponentTransformation().run(sourceImage);
        break;
      case "intensity":
        transformedImage = new IntensityTransformation().run(sourceImage);
        break;
      case "luma":
        transformedImage = new LumaTransformation().run(sourceImage);
        break;
      case "rcomponent":
        transformedImage = new RTransformation().run(sourceImage);
        break;
      case "gcomponent":
        transformedImage = new GTransformation().run(sourceImage);
        break;
      case "blur":
        transformedImage = new BlurTransformation().run(sourceImage);
        break;
      case "sharpen":
        transformedImage = new SharpenTransformation().run(sourceImage);
        break;
      case "bcomponent":
        transformedImage = new BTransformation().run(sourceImage);
        break;
      default:
        System.out.println("Invalid transformation command: " + command);
    }

    if (transformedImage != null) {

      // Store the transformed image in the database.
      imageDatabase.put(destImageName, transformedImage);
      System.out.println("Transformation '" + command + "' applied " +
              "successfully to create image: " + destImageName);
    } else {
      System.out.println("Transformation '" + command + "' failed.");
    }
  }
}
