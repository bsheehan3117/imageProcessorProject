Overview
This program is designed to load, transform, and save PPM, JPG, BMP, and PNG file images.
The program uses a model view controller design and a strategy pattern.
Transformations included are Brighten, Darken, Value, Intensity, Luma, Greyscale (red), Greyscale (green), Greyscale (blue), Sharpen, and Blur.
The program is capable of interaction via script files, interactive mode at the command line, and a Graphical User Interface.

Classes and Interfaces

Main: This class contains the main method which allows the user to enter commands for image saving, loading, and transforming.

IImageDataBase:  An interface for a database that stores image states.

ImageDataBase: A class that implements the IImageDataBase interface. Uses a HashMap to store and retrieve image states.

IImageLoader: An interface representing the loader for image files. Loads an image file and returns its state.

PPMImageLoader: A class that implements the IImageLoader interface. Used to load PPM image files.

IImageSaver: An interface representing the saver for image files. Saves an image state to a file.

PPMImageSaver: A class that implements the IImageSaver interface.  Saves images in the PPM format.

IImageState: An interface representing the state of an image.

ImageImpl: A class that implements the IImageState interface with state and pixel data.

Pixel: A class representing a pixel in an image with RGB values.

ITransformation: An interface representing a transformation that can be used on an image.

BrightenTransformation: A class implementing ITransformation to brighten an image by adding a value to each RGB channel.

DarkenTransformation: A class implementing ITransformation to darken an image by subtracting a value from each RGB channel.

ValueComponentTransformation: A class implementing ITransformation to adjust the value of an image using the max RGB value.

IntensityTransformation: A class implementing ITransformation to transform an image to greyscale based on the average intensity of RGB.

LumaTransformation: A class implementing ITransformation to transform an image using the luma formula.

R/G/BTransformation: Classes implement ITransformation to transform an image to greyscale based on RBG values.

ICommand: An interface representing a command that can be executed in the program.

LoadPPMCommand: A class implementing ICommand to load a PPM image file and store its state in the database.

SavePPMCommand: A class implementing ICommand to save a PPM image to a file.

BrightenCommand: A class implementing ICommand to process the brighten transformation.

DarkenCommand: A class implementing ICommand to process the darken transformation.

ValueCommand: A class implementing ICommand to process the value component transformation.

IntensityCommand: A class implementing ICommand to process the intensity transformation.

LumaCommand: A class implementing ICommand to process the luma transformation.

R/G/BComponentCommand: Classes implementing ICommand to process the R/G/B greyscale transformations.

BlurCommand: A class implementing ICommand to process the blur Transformation.

SharpenCommand: A class implementing ICommand to process the sharpen Transformation.

BlurTransformation: A class implementing ITransformation to transform an image with a kernel into a blurred image.

SharpenTransformation:  A class implementing ITransformation to transform an image with a kernel into a sharper image.

LoadBMPCommand:  A class implementing ICommand to load a BMP image file and store its state in the database.

SaveBMPCommand:  A class implementing ICommand to save a BMP image to a file.

BMPImageLoader:  A class that implements the IImageLoader interface. Used to load BMP image files.

BMPImageSaver:  A class that implements the IImageSaver interface.  Saves images in the BMP format.

LoadPNGCommand: A class implementing ICommand to load a PNG image file and store its state in the database.

SavePNGCommand:  A class implementing ICommand to save a PNG image to a file.

PNGImageLoader:  A class that implements the IImageLoader interface. Used to load PNG image files.

PNGImageSaver:  A class that implements the IImageSaver interface.  Saves images in the PNG format.

LoadJPGCommand: A class implementing ICommand to load a JPG image file and store its state in the database.

SaveJPGCommand:  A class implementing ICommand to save a JPG image to a file.

JPGImageLoader:  A class that implements the IImageLoader interface. Used to load JPG image files.

JPGImageSaver:  A class that implements the IImageSaver interface.  Saves images in the JPG format.

New:

IView:  An interface for the View Class and its methods.

View:  A Class Implementing the IView interface which extends JFrame and implements ActionListener to create a graphical user interface with Swing.

Design Changes and Justifications:

The changes implemented are the creation of the IView interface and View class which generate a Graphical user interface for the Image Editor using swing.
The main file was the only code changed, which was done in order to interact with the View when run.
The main file now offers the ability to run a gui with no command line args, interactive mode with -text as an arg, and executes a script file with -file path-of-script-file as an arg.


How to run the GUI:

Run the program with no command line arguments.

How to run interactive mode:

Run the program with -text as an argument.

How to execute a script file:

Run the program with -file path-of-script-file as an argument.



Commands to run the program:

-Load an image PPM file:

loadppm path/to/image.ppm ImageName

-Save an image to a PPM file:

saveppm path/to/save/image.ppm ImageName

-Load an image PNG file:

loadpng path/to/image.png ImageName

-Save an image to a PNG file:

-savepng path/to/save/image.png ImageName

-Load an image JPG file:

loadjpg path/to/image.jpg ImageName

-Save an image to a PPM file:

savejpg path/to/save/image.jpg ImageName

-Load an image BMP file:

loadbmp path/to/image.bmp ImageName

-Save an image to a BMP file:

savebmp path/to/save/image.bmp ImageName

-Transform with Brighten:

brighten IncrementValue SourceImageName DestImageName

-Transform with darken:

darken DecrementValue SourceImageName DestImageName

-Transform with value:

value SourceImageName DestImageName

-Transform with intensity:

intensity SourceImageName DestImageName

-Transform with Luma:

luma SourceImageName DestImageName

-Convert to greyscale with red:

rcomponent SourceImageName DestImageName

-Convert to greyscale with green:

gcomponent SourceImageName DestImageName

-Convert to greyscale with Blue:

bcomponent SourceImageName DestImageName

-Transform with Sharpen:

sharpen SourceImageName DestImageName

-Transform with blur:

blur SourceImageName DestImageName

- Run a script file

-file fileName
(eg. -file res/script.txt)

-Exit:

exit



An example script for loading a sample image, creating a brightened image from
the sample image, saving the brightened image, and exiting the program:


Enter command: load res\sampleimage.ppm sampleimage

Image 'sampleimage' loaded successfully.

Enter command: brighten 25 sampleimage brighterimage

Transformation 'brighten' applied successfully to create image: brighterimage

Enter command: save res\brighterimage.ppm brighterimage

Image 'brighterimage' saved successfully to: res\brighterimage.ppm

Enter command: exit


Image Citation for sampleimage file and Snail image file.

CS35: Lab 1 - PPM Image Format. (n.d.). Www.cs.swarthmore.edu. Retrieved July 21, 2023, from https://www.cs.swarthmore.edu/~brody/cs35/f14/Labs/extras/01/ppm_info.html

PPMA Files. (n.d.). People.sc.fsu.edu. https://people.sc.fsu.edu/~jburkardt/data/ppma/ppma.html

