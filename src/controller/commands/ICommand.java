package controller.commands;

import java.util.Scanner;

import model.IImageDataBase;

/**
 * Represents a command that can be executed on an image database.
 */
public interface ICommand {

  /**
   * Method for running the command.
   *     @param scanner Scans input.
   *     @param model The image database model.
   */
  void run(Scanner scanner, IImageDataBase model);
}
