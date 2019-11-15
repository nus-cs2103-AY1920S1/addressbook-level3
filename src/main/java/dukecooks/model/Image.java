package dukecooks.model;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import dukecooks.commons.util.FileUtil;
import dukecooks.model.util.SampleImageUtil;

/**
 * Represents an Image in DukeCooks.
 */
public class Image {

    public static final String MESSAGE_CONSTRAINTS = "Image should exist and end with either .jpg or .png";
    public static final String MESSAGE_DATA_COPY_ERROR = "Failed to copy to data directory!";
    public static final String PATH_TO_DATA_DIRECTORY = "data" + File.separator;

    private static final ArrayList<String> sampleImages =
            new ArrayList<>(Arrays.asList(SampleImageUtil.getSampleImages()));

    private String originalPath;
    private String dataPath;

    public Image(String imagePath) throws IllegalArgumentException {
        requireNonNull(imagePath);

        // Check if it's a pre-loaded image in resource folder
        if (sampleImages.contains(imagePath)) {
            this.originalPath = imagePath;
            this.dataPath = imagePath;
        } else {
            // Make a copy in the data directory
            this.originalPath = imagePath;
            File file = new File(originalPath);
            this.dataPath = PATH_TO_DATA_DIRECTORY + file.getName();
            copyToDataPath(Paths.get(originalPath), Paths.get(dataPath));
        }
    }

    /**
     * Copies image from the {@code imagePath} to {@Code dataPath}.
     *
     * @throws IllegalArgumentException if copy fails
     */
    private void copyToDataPath(Path imagePath, Path dataPath) throws IllegalArgumentException {
        try {
            Files.copy(imagePath, dataPath, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalArgumentException(MESSAGE_DATA_COPY_ERROR);
        }
    }


    /**
     * Returns true is the given {@code imagePath} is a valid image.
     * An image is valid if it exists in the user directory or in the resource folder.
     */
    public static boolean isValidImage(String imagePath) {
        if (FileUtil.isFileExists(Paths.get(imagePath))) {
            return (imagePath.endsWith(".jpg") || imagePath.endsWith(".png"));
        } else {
            return Image.class.getResourceAsStream(imagePath) != null
                    && (imagePath.endsWith(".jpg") || imagePath.endsWith(".png"));
        }
    }

    /**
     * Returns the file path from the data directory, for passing into ImageView
     */
    public String getImageViewDataPath() {
        return "file://" + Paths.get(dataPath).toAbsolutePath().toUri().getPath();
    }

    public String getDataPath() {
        if (originalPath.equals(dataPath)) {
            return getResourceImagePath();
        } else {
            String path = Paths.get(dataPath).toAbsolutePath().toUri().getPath();
            int indexOfColon = path.indexOf(":");
            if (indexOfColon == -1) {
                return path;
            } else {
                return path.substring(indexOfColon + 1);
            }
        }
    }

    public String getResourceImagePath() {
        return originalPath;
    }


    @Override
    public String toString() {
        return "Image can be found in: " + originalPath + " and " + dataPath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Image // instanceof handles nulls
                && (this.dataPath.equals(((Image) other).dataPath)
                && (this.originalPath.equals(((Image) other).originalPath)))); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalPath, dataPath);
    }
}
