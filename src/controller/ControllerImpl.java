package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import controller.commands.BCommand;
import controller.commands.BlurCommand;
import controller.commands.BrightenCommand;
import controller.commands.DarkenCommand;
import controller.commands.GCommand;
import controller.commands.ICommand;
import controller.commands.IntensityCommand;
import controller.commands.LoadBMPCommand;
import controller.commands.LoadJPGCommand;
import controller.commands.LoadPNGCommand;
import controller.commands.LoadPPMCommand;
import controller.commands.LumaCommand;
import controller.commands.RCommand;
import controller.commands.SaveBMPCommand;
import controller.commands.SaveJPGCommand;
import controller.commands.SavePNGCommand;
import controller.commands.SavePPMCommand;
import controller.commands.SharpenCommand;
import controller.commands.ValueComponentCommand;
import model.IImageDataBase;

/**
 * Represents the controller for taking user commands
 * and interacting with the model.
 */
public class ControllerImpl implements IController {

  private final Readable input;
  private final IImageDataBase model;
  private final Appendable appendable;
  private final Map<String, ICommand> commandMap;

  /**
   * Implements the controller.
   *     @param input The user command input.
   *     @param model The database model.
   *     @param appendable an appendable object.
   */
  public ControllerImpl(Readable input, IImageDataBase model, Appendable appendable) {
    this.input = Objects.requireNonNull(input);
    this.model = Objects.requireNonNull(model);
    this.appendable = Objects.requireNonNull(appendable);

    // Initialize the command map.
    this.commandMap = new HashMap<String, ICommand>();
    this.commandMap.put("displayImage", new DisplayImageCommand());
    this.commandMap.put("brighten", new BrightenCommand());
    this.commandMap.put("darken", new DarkenCommand());
    this.commandMap.put("value", new ValueComponentCommand());
    this.commandMap.put("intensity", new IntensityCommand());
    this.commandMap.put("luma", new LumaCommand());
    this.commandMap.put("blur", new BlurCommand());
    this.commandMap.put("sharpen", new SharpenCommand());
    this.commandMap.put("loadPPM", new LoadPPMCommand());
    this.commandMap.put("savePPM", new SavePPMCommand());
    this.commandMap.put("RComponent", new RCommand());
    this.commandMap.put("GComponent", new GCommand());
    this.commandMap.put("BComponent", new BCommand());
    this.commandMap.put("loadPNG", new LoadPNGCommand());
    this.commandMap.put("savePNG", new SavePNGCommand());
    this.commandMap.put("loadJPG", new LoadJPGCommand());
    this.commandMap.put("saveJPG", new SaveJPGCommand());
    this.commandMap.put("loadBMP", new LoadBMPCommand());
    this.commandMap.put("saveBMP", new SaveBMPCommand());

  }

  /**
  * Helper method for writing to the appendable.
  *     @param message The message to be written to the appendable.
  */
  private void write(String message) {
    try {
      this.appendable.append(message);

    } catch (IOException e) {
      throw new IllegalStateException("Writing to the appendable failed.");
    }
  }

  @Override
  public void go() {
    Scanner scanner = new Scanner(this.input);

    // Process user commands while there are commands left.
    while (scanner.hasNext()) {
      String command = scanner.next();

      ICommand commandToRun = this.commandMap.getOrDefault(command, null);
      if (command == null) {
        write("Invalid command.");
        continue;
      }

      // Run the commands based on input.
      try {
        commandToRun.run(scanner, this.model);
      } catch (IllegalStateException e) {
        write(e.getMessage());
      }
    }
  }
}


