package com.dukeacademy.logic.notes;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import com.dukeacademy.commons.core.LogsCenter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

/**
 * Encapsulates all CRUD operations required by the application with regards to the sketch image files of Notes. It
 * is used to convert, store and load the user's sketches as PNG image files. All sketches will be loaded/saved from
 * the given storage folder path given in the constructor.
 */
public class SketchManager {
    private final Path sketchStorageFolderPath;
    private final Logger logger;
    private final boolean isStorageFolderAvailable;

    /**
     * Constructor, the SketchManager instance is initialized with a root path to store all the sketch image files to.
     * @param sketchStorageFolderPath the path of the folder to save sketch image files to
     */
    public SketchManager(Path sketchStorageFolderPath) {
        this.logger = LogsCenter.getLogger(SketchManager.class);

        this.sketchStorageFolderPath = sketchStorageFolderPath;
        isStorageFolderAvailable = this.createStorageFolder(sketchStorageFolderPath);
    }

    /**
     * Searches for a sketch image in the storage folder and returns it as a JavaFX WritableImage class to be used
     * by the application's Ui components. Returns null if storage folder was  not initialized properly.
     * @param sketchId the id of the sketch to be loaded
     * @return the JavaFX WritableImage representation of the sketch
     * @throws IOException if the sketch cannot be loaded.
     */
    public WritableImage loadSketch(UUID sketchId) throws IOException {
        // If the storage folder was not initialized properly, simply return null
        if (!isStorageFolderAvailable) {
            logger.warning("Sketch storage file was not initialized, returning null...");
            return null;
        }

        File sketchImageFile = this.getFileFromSketchId(sketchId);

        logger.info("Loading sketch from : " + sketchImageFile.getPath());

        if (!sketchImageFile.exists()) {
            logger.warning("Sketch not found at : " + sketchImageFile.getPath());
            throw new FileNotFoundException();
        }

        // First load the original image as a BufferedImage instance before converting it to a JavaFX WritableImage
        BufferedImage originalImage = ImageIO.read(sketchImageFile);
        WritableImage writableImage = SwingFXUtils.toFXImage(originalImage, null);
        logger.info("Successfully loaded sketch from : " + sketchImageFile.getPath());

        return writableImage;
    }

    /**
     * Saves a sketch given in the form of a JavaFX WritableImage instance as a PNG image file whose name is the unique
     * sketch id that's given. If the storage folder was not initialized properly, the method will fail silently.
     * @param sketchId the unique sketch id of the sketch to be saved
     * @param sketch the sketch to be saved
     * @throws IOException if the sketch fails to be saved
     */
    public void saveSketch(UUID sketchId, WritableImage sketch) throws IOException {
        // If the storage folder was not initialized properly, abort the operation
        if (!isStorageFolderAvailable) {
            logger.warning("Sketch storage file was not initialized, sketch will not be saved...");
            return;
        }

        File sketchImageFile = this.getFileFromSketchId(sketchId);

        logger.info("Saving sketch at : " + sketchImageFile.getPath());

        // Attempts to create a new file in the file system if it did not exist previously
        if (!sketchImageFile.exists() && !sketchImageFile.createNewFile()) {
            logger.warning("Unable to create sketch image file at : " + sketchImageFile.getPath());
            throw new IOException("Unable to create image file : " + sketchImageFile.toPath());
        }

        // Convert and write the sketch contents to the newly created file
        RenderedImage drawing = SwingFXUtils.fromFXImage(sketch, null);
        ImageIO.write(drawing, "png", sketchImageFile);

        logger.info("Sketch  saved successfully at : " + sketchImageFile.getPath());
    }

    /**
     * Deletes the sketch file corresponding to the give unique sketch id. If the storage folder was not initialized
     * properly or the sketch image file is not found, the method will fail silently.
     * @param sketchId the unique sketch id of the sketch to be deleted
     */
    public void deleteSketch(UUID sketchId) {
        // If the storage folder was not initialized properly, abort the operation
        if (!isStorageFolderAvailable) {
            logger.warning("Sketch storage file was not initialized, sketch will not be deleted...");
            return;
        }

        File sketchImageFile = this.getFileFromSketchId(sketchId);

        logger.info("Deleting sketch at : " + sketchImageFile.getPath());

        // If the sketch image file does not exist, abort the operation
        if (!sketchImageFile.exists()) {
            logger.warning("Sketch not found at : " + sketchImageFile.getPath() + ", skipping delete");
            return;
        }

        // Attempt to delete the image file
        if (!sketchImageFile.delete()) {
            logger.warning("Failed to delete sketch at : " + sketchImageFile.getPath());
        }

        logger.info("Successfully deleted sketch at : " + sketchImageFile.getPath());
    }

    /**
     * Helper method used to create the sketch storage folder if it does not already exist.
     * @param sketchStorageFolderPath the path of the storage folder
     * @return true if the folder exists by the end of the method call
     */
    private boolean createStorageFolder(Path sketchStorageFolderPath) {
        if (!sketchStorageFolderPath.toFile().isDirectory()) {
            logger.info("No sketch storage folder found at : " + sketchStorageFolderPath);
            logger.info("Creating new folder...");

            if (!sketchStorageFolderPath.toFile().mkdirs()) {
                logger.warning("Unable to make sketch storage directory, sketches will not be saved");
                return false;
            }

            logger.info("Sketch storage folder successfully created at " + sketchStorageFolderPath);
        }

        return true;
    }

    /**
     * Helper method used to create Java File instance corresponding to a sketch from a given sketch id.
     * @param sketchId the sketch id of the sketch file to be created
     * @return the File instance corresponding to the sketch
     */
    private File getFileFromSketchId(UUID sketchId) {
        String sketchFileName = sketchId.getMostSignificantBits() + sketchId.getLeastSignificantBits() + ".png";
        return sketchStorageFolderPath.resolve(sketchFileName).toFile();
    }
}
