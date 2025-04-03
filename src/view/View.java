package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import controller.BMPImageLoader;
import controller.BMPImageSaver;
import controller.IImageLoader;
import controller.IImageSaver;
import controller.JPGImageLoader;
import controller.JPGImageSaver;
import controller.PNGImageLoader;
import controller.PNGImageSaver;
import controller.PPMImageLoader;
import controller.PPMImageSaver;
import model.IImageState;
import model.ImageImpl;
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
 * A class which creates a graphical user interface with which
 * a user can load and save PPM, JPG, BMP, and PPM images and transform them.
 * Extends JFrame and implements ActionListener.
 */
public class View extends JFrame implements ActionListener, IView {
  private static final Map<String, IImageState> imageDatabase = new HashMap<>();
  public JLabel imageLabel;
  private IImageState currentImage;

  private final JTextField transformationField;
  private final JLabel showText;

  /**
   * Constructs a view for the View class.
   */
  public View() {

    // Set up the title.
    setTitle("Image Editor");

    // Set up window size
    setSize(1200, 800);

    // Exit operation on window close.
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // Set layout from oracle layout options.
    setLayout(new BorderLayout());

    // Create a JLabel with the text "Image Editor" and modify the font size
    JLabel titleLabel = new JLabel("Image Editor", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 30));


    // Create a text field.
    JTextField enterText = new JTextField(30);

    // Create an area for instructions
    JTextArea instructionArea = new JTextArea();
    instructionArea.setFont(new Font("Arial", Font.PLAIN, 16));
    instructionArea.setText("How to use the Image Editor:" +
            " \n-Load a file of a specific type by choosing the" +
            " correct load button.  \n-Edit the image using the" +
            " transformation buttons on the right. \n(If Brightening" +
            " or Darkening, type an integer in the bottom right text" +
            " box before clicking the button this increments the level" +
            " or brighten or darken)\n-Save the image you are working on" +
            " to the type of file you choose by clicking the save" +
            " button of your choice.\n -Look at the bottom of the" +
            " screen for updates on operations.");

    // Ensure instructions cannot be edited and are centered
    instructionArea.setEditable(false);
    instructionArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

    // Create a panel for the top of the screen
    // For a title and instructions.
    JPanel northPanel = new JPanel(new BorderLayout());
    northPanel.add(titleLabel, BorderLayout.NORTH);
    northPanel.add(instructionArea, BorderLayout.CENTER);

    // Add the panel to the north part of the layout.
    add(northPanel, BorderLayout.NORTH);

    // Initialize message box
    this.showText = new JLabel("This is a message box.", SwingConstants.CENTER);
    Font font = this.showText.getFont();
    Font biggerFont = font.deriveFont(font.getSize() + 6f);
    this.showText.setFont(biggerFont);

    // Initialize the text field which will be where ints are added
    // for brighten/darken increments.
    this.transformationField = new JTextField(30);

    // Save and load buttons
    JButton savePPM = new JButton("Save Image as PPM");
    JButton loadPPM = new JButton("Load PPM Image");
    JButton savePNG = new JButton("Save Image as PNG");
    JButton loadPNG = new JButton("Load PNG Image");
    JButton saveBMP = new JButton("Save Image as BMP");
    JButton loadBMP = new JButton("Load BMP Image");
    JButton saveJPG = new JButton("Save Image as JPG");
    JButton loadJPG = new JButton("Load JPG Image");

    // Transformation buttons.
    JButton brighten = new JButton("Brighten");
    JButton darken = new JButton("Darken");
    JButton bGreyscale = new JButton("Greyscale Blue");
    JButton rGreyscale = new JButton("Greyscale Red");
    JButton gGreyscale = new JButton("Greyscale Green");
    JButton intensity = new JButton("Intensity");
    JButton luma = new JButton("Luma");
    JButton value = new JButton("Value");
    JButton blur = new JButton("Blur");
    JButton sharpen = new JButton("Sharpen");

    // Create a left side panel for loading and saving images
    JPanel loadSavePanel = new JPanel(new GridLayout(0, 1)); // 0 rows (any number), 1 column

    // Add Buttons for saving and loading to panel
    loadSavePanel.add(savePPM);
    loadSavePanel.add(loadPPM);
    loadSavePanel.add(loadBMP);
    loadSavePanel.add(saveBMP);
    loadSavePanel.add(loadJPG);
    loadSavePanel.add(saveJPG);
    loadSavePanel.add(loadPNG);
    loadSavePanel.add(savePNG);

    // Set Load Save Panel to Left side of Layout
    getContentPane().add(loadSavePanel, BorderLayout.WEST);

    // Create a right side panel for transforming the current image.
    JPanel transformationPanel = new JPanel(new GridLayout(0, 1));

    // Add transformations to panel.
    transformationPanel.add(brighten);
    transformationPanel.add(darken);
    transformationPanel.add(blur);
    transformationPanel.add(bGreyscale);
    transformationPanel.add(gGreyscale);
    transformationPanel.add(rGreyscale);
    transformationPanel.add(intensity);
    transformationPanel.add(value);
    transformationPanel.add(luma);
    transformationPanel.add(sharpen);

    // Add Text field for entering image name to panel
    transformationPanel.add(this.transformationField);

    // Set Transformation Panel to right side of Layout.
    getContentPane().add(transformationPanel, BorderLayout.EAST);

    // Add Show text to GUI South.
    add(this.showText, BorderLayout.SOUTH);

    // Add image to center.
    this.imageLabel = new JLabel();
    add(this.imageLabel, BorderLayout.CENTER);

    // Add a listener for an action Save/Load.
    savePPM.addActionListener(this);
    loadPPM.addActionListener(this);
    savePNG.addActionListener(this);
    loadPNG.addActionListener(this);
    saveBMP.addActionListener(this);
    loadBMP.addActionListener(this);
    saveJPG.addActionListener(this);
    loadJPG.addActionListener(this);

    // Add an action command for Save/Load
    savePPM.setActionCommand("savePPM");
    loadPPM.setActionCommand("loadPPM");
    savePNG.setActionCommand("savePNG");
    loadPNG.setActionCommand("loadPNG");
    saveBMP.setActionCommand("saveBMP");
    loadBMP.setActionCommand("loadBMP");
    saveJPG.setActionCommand("saveJPG");
    loadJPG.setActionCommand("loadJPG");

    // Add a listener for Transformations.
    blur.addActionListener(this);
    sharpen.addActionListener(this);
    brighten.addActionListener(this);
    darken.addActionListener(this);
    value.addActionListener(this);
    luma.addActionListener(this);
    intensity.addActionListener(this);
    rGreyscale.addActionListener(this);
    gGreyscale.addActionListener(this);
    bGreyscale.addActionListener(this);

    // Add an action command for Transformations.
    blur.setActionCommand("Blur");
    sharpen.setActionCommand("Sharpen");
    brighten.setActionCommand("Brighten");
    darken.setActionCommand("Darken");
    bGreyscale.setActionCommand("Greyscale Blue");
    gGreyscale.setActionCommand("Greyscale Green");
    rGreyscale.setActionCommand("Greyscale Red");
    intensity.setActionCommand("Intensity");
    value.setActionCommand("Value");
    luma.setActionCommand("Luma");
  }

  /**
   * Helper method to convert an image to a buffered image for display.
   *     @param image The image to convert to bufferedImage.
   *     @return the Buffered image.
   */
  public BufferedImage convertToBufferedImage(IImageState image) {

    // Get w and h
    int width = image.getWidth();
    int height = image.getHeight();

    // Create a buffered Image.
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    // Loop through image
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        // get channel values.
        int red = image.getRedChannel(x, y);
        int green = image.getGreenChannel(x, y);
        int blue = image.getBlueChannel(x, y);

        // Assign RGB Values to buff image.
        Color color = new Color(red, green, blue);
        int rgb = color.getRGB();
        bufferedImage.setRGB(x, y, rgb);
      }
    }
    return bufferedImage;
  }

  /**
   * Helper method to create an IImageState from a bufferedImage.
   *     @param bufferedImage The bufferedImage.
   *     @return The IImageState of the buffered image.
   */
  public IImageState getCurrentImageStateFromBufferedImage(BufferedImage bufferedImage) {

    // Get w/h of buffered image
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();

    // Create an empty image state with the same dimensions as the BufferedImage.
    ImageImpl imageState = new ImageImpl(width, height);

    // Loop through image to get rgb values.
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = bufferedImage.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Set values in new image.
        imageState.setPixel(x, y, red, green, blue);
      }
    }

    return imageState;
  }


  /** Helper method to display an image at the center after loading or editing.
   *     @param img The image to be displayed at the center.
   */
  public void displayImage(BufferedImage img) {

    // Get w/h of image and label.
    int imageWidth = img.getWidth();
    int imageHeight = img.getHeight();
    int labelWidth = imageLabel.getWidth();
    int labelHeight = imageLabel.getHeight();

    Image scaledImage;

    // If image is bigger than label
    if (imageWidth > labelWidth || imageHeight > labelHeight) {

      // Scale image to fit the label
      scaledImage = img.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
    } else {
      // If the image is smaller use its original image size
      scaledImage = img;
    }

    // Create an image icon
    ImageIcon icon = new ImageIcon(scaledImage);
    imageLabel.setIcon(icon);

    // Center the image icon
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imageLabel.setVerticalAlignment(JLabel.CENTER);
    this.repaint();

    // Get current image for use in operations.
    this.currentImage = getCurrentImageStateFromBufferedImage(img);
  }


  /**
   * Helper method for removing the file extension to have the file name.
   *     @param filename The name of the file.
   *     @return The name of the file without the type of file.
   */
  public String removeFileExtension(String filename) {

    // remove everything after the "." (PNG, etc)
    int pos = filename.lastIndexOf(".");
    if (pos > 0) {
      filename = filename.substring(0, pos);
    }
    return filename;
  }

  /**
   * Perform actions based on commands via button clicks and text input.
   * Actions include loading, saving, and transforming.
   *     @param e the event to be processed.
   */

  @Override
  public void actionPerformed(ActionEvent e) {

    // Create a file chooser
    JFileChooser fileChooser = new JFileChooser();

    // Assign command which initiates action
    String command = e.getActionCommand();

    // Variables for storing a file and return file chooser value.
    File selectedFile;
    int returnValue;

    // Switch to handle commands.
    switch ( e.getActionCommand()) {

      // Cases for loading various file types.

      case "loadPPM":

        // Use filechooser to select a file
        returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          selectedFile = fileChooser.getSelectedFile();

          // Use PPM Img loader on selected file to generate IImagestate
          IImageLoader imageLoader = new PPMImageLoader(selectedFile.getAbsolutePath());
          IImageState image = imageLoader.run();

          // Get name of image for addition to database
          String imageName = removeFileExtension(selectedFile.getName());

          // Add to database, message at bottom.
          imageDatabase.put(imageName, image);
          showText.setText("You loaded: " + selectedFile.getAbsolutePath());
          BufferedImage bufferedImage = convertToBufferedImage(image);
          displayImage(bufferedImage);
        }
        break;
      case "loadPNG":

        // Use filechooser to select a file
        returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          selectedFile = fileChooser.getSelectedFile();

          // Use PNG Img loader on selected file to generate IImagestate
          IImageLoader imageLoader = new PNGImageLoader(selectedFile.getAbsolutePath());
          IImageState image = imageLoader.run();

          // Get name of image for addition to database
          String imageName = removeFileExtension(selectedFile.getName());

          // Add to database, message at bottom.
          imageDatabase.put(imageName, image);
          showText.setText("You loaded: " + selectedFile.getAbsolutePath());
          BufferedImage bufferedImage = convertToBufferedImage(image);
          displayImage(bufferedImage);
        }
        break;
      case "loadJPG":

        // Use filechooser to select a file
        returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          selectedFile = fileChooser.getSelectedFile();

          // Use JPG Img loader on selected file to generate IImagestate
          IImageLoader imageLoader = new JPGImageLoader(selectedFile.getAbsolutePath());
          IImageState image = imageLoader.run();

          // Get name of image for addition to database
          String imageName = removeFileExtension(selectedFile.getName());

          // Add to database, message at bottom.
          imageDatabase.put(imageName, image);
          showText.setText("You loaded: " + selectedFile.getAbsolutePath());
          BufferedImage bufferedImage = convertToBufferedImage(image);
          displayImage(bufferedImage);
        }
        break;
      case "loadBMP":

        // Use filechooser to select a file
        returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          selectedFile = fileChooser.getSelectedFile();

          // Use BMP Img loader on selected file to generate IImagestate
          IImageLoader imageLoader = new BMPImageLoader(selectedFile.getAbsolutePath());
          IImageState image = imageLoader.run();

          // Get name of image for addition to database
          String imageName = removeFileExtension(selectedFile.getName());

          // Add to database, message at bottom.
          imageDatabase.put(imageName, image);
          showText.setText("You loaded: " + selectedFile.getAbsolutePath());
          BufferedImage bufferedImage = convertToBufferedImage(image);
          displayImage(bufferedImage);
        }
        break;

      // Cases for saving different types of files.
      case "savePPM":
      case "savePNG":
      case "saveJPG":
      case "saveBMP":

        // Get current image in display
        IImageState imageToSave = this.currentImage;

        // If no image in display, display message.
        if (imageToSave == null) {
          showText.setText("No image in the display, cannot save.");
          return;
        }

        // Window for saving a file.
        returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {

          // Get the file to save
          selectedFile = fileChooser.getSelectedFile();

          // Add the file type extension to the file to be saved.
          String fileExtension = "";
          switch (e.getActionCommand()) {
            case "savePPM":
              fileExtension = ".ppm";
              break;
            case "savePNG":
              fileExtension = ".png";
              break;
            case "saveJPG":
              fileExtension = ".jpg";
              break;
            case "saveBMP":
              fileExtension = ".bmp";
              break;
            default:
              showText.setText("Invalid file type.");
              return;
          }

          // Add the file extension to the file name
          String fullFilePath = selectedFile.getAbsolutePath();
          if (!fullFilePath.endsWith(fileExtension)) {
            fullFilePath += fileExtension;
          }


          // Initialize the ImageSaver with the imagePath, image, and System.out
          IImageSaver imageSaver;
          switch (e.getActionCommand()) {
            case "savePPM":
              imageSaver = new PPMImageSaver(fullFilePath, imageToSave, System.out);
              break;
            case "savePNG":
              imageSaver = new PNGImageSaver(fullFilePath, imageToSave, System.out);
              break;
            case "saveJPG":
              imageSaver = new JPGImageSaver(fullFilePath, imageToSave, System.out);
              break;
            case "saveBMP":
              imageSaver = new BMPImageSaver(fullFilePath, imageToSave, System.out);
              break;
            default:
              showText.setText("Invalid save command.");
              return;
          }

          // Run the ImageSaver and display success message.
          imageSaver.run();
          showText.setText("You saved: " + selectedFile.getAbsolutePath());
        }
        break;

      // Cases for transforming an image.
      case "Blur":
      case "Brighten":
      case "Darken":
      case "Greyscale Blue":
      case "Greyscale Green":
      case "Greyscale Red":
      case "Intensity":
      case "Value":
      case "Luma":
      case "Sharpen":

        // Get the current image in display.
        IImageState imageToTransform = this.currentImage;

        // Message if no image to transform
        if (imageToTransform == null) {
          showText.setText("No image in the display, please load an image first.");
          return;
        }

        // Brighten and darken require an int to increment amount of change.
        if (e.getActionCommand().equals("Brighten") || e.getActionCommand().equals("Darken")) {

          // Get the integer value from the text box
          String textValue = transformationField.getText().trim();
          int increment;
          try {
            increment = Integer.parseInt(textValue);
          } catch (NumberFormatException ex) {
            showText.setText("Invalid increment value. Please enter an integer.");
            return;
          }

          // Brighten or darken the image based on the increment and chosen button.
          IImageState transformedImage;
          if (e.getActionCommand().equals("Brighten")) {
            transformedImage = new BrightenTransformation(increment).run(imageToTransform);
          } else {
            transformedImage = new DarkenTransformation(increment).run(imageToTransform);
          }

          // Update the displayed image with the transformed image
          if (transformedImage != null) {
            BufferedImage bufferedImage = convertToBufferedImage(transformedImage);
            displayImage(bufferedImage);

            // Message if success or failure.
            showText.setText("Transformation '" + e.getActionCommand() + "' applied.");
          } else {
            showText.setText("Transformation '" + e.getActionCommand() + "' failed.");
          }
        } else {

          // Switch for transformations that do not require an increment.
          IImageState transformedImage = null;
          switch (e.getActionCommand()) {
            case "Blur":
              transformedImage = new BlurTransformation().run(imageToTransform);
              break;
            case "Sharpen":
              transformedImage = new SharpenTransformation().run(imageToTransform);
              break;
            case "Luma":
              transformedImage = new LumaTransformation().run(imageToTransform);
              break;
            case "Value":
              transformedImage = new ValueComponentTransformation().run(imageToTransform);
              break;
            case "Intensity":
              transformedImage = new IntensityTransformation().run(imageToTransform);
              break;
            case "Greyscale Blue":
              transformedImage = new BTransformation().run(imageToTransform);
              break;
            case "Greyscale Red":
              transformedImage = new RTransformation().run(imageToTransform);
              break;
            case "Greyscale Green":
              transformedImage = new GTransformation().run(imageToTransform);
              break;
            default:
              showText.setText("Invalid action command.");
              return;
          }
          // Update the displayed image with the transformed image
          if (transformedImage != null) {
            BufferedImage bufferedImage = convertToBufferedImage(transformedImage);
            displayImage(bufferedImage);

            // Message if successful or failure.
            showText.setText("Transformation '" + e.getActionCommand() + "' applied.");
          } else {
            showText.setText("Transformation '" + e.getActionCommand() + "' failed.");
          }
        }
        break;
      default:
        throw new IllegalStateException("Unknown action command.");
    }
  }
}

