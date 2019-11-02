package dukecooks.model;

import static dukecooks.commons.util.AppUtil.checkArgument;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents an Item's image.
 * Guarantees: immutable; is valid as declared in {@link #isValidImage(String)}
 */
public class Image {

    public static final String MESSAGE_CONSTRAINTS = "Image file path should exist and it should end with .png or .jpg";
    public static final String PATH_TO_RESOURCE =
            "src" + File.separator + "main" + File.separator + "resources" + File.separator + "images" + File.separator;
    public static final String MESSAGE_COPY_FAILURE = "There was an error in copying the file to resource";

    /*
     * The image filePath must not be a whitespace
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private String filePath;
    private String fileName;
    private URL url;

    /**
     * Constructs an {@code Image}.
     *
     * @param filePath A valid file path.
     */
    public Image(String filePath) {
        requireNonNull(filePath);

        checkArgument(isValidImage(filePath), MESSAGE_CONSTRAINTS);
        this.filePath = filePath;

        File f = new File(filePath);
        this.fileName = f.getName();
        copyToResource(Paths.get(filePath), fileName);

        this.url = null;
    }

    /**
     * Overloaded constructor for loading of images using URLs
     * @param url
     */
    public Image(URL url) {
        this.url = url;
    }

    /**
     * Returns the filePath of a valid Image
     */
    public String getFilePath() {
        return "/images/" + filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public URL getUrl() {
        return url;
    }

    /**
     * Copies the image from {@code originalPath} to resourcePath
     *
     * @throws IllegalArgumentException if unable to copy the file.
     */
    private void copyToResource(Path originalPath, String fileName) throws IllegalArgumentException {
        try {
            String resourceDirectory = PATH_TO_RESOURCE + fileName;
            Files.copy(originalPath, Paths.get(resourceDirectory), REPLACE_EXISTING);
            this.filePath = Paths.get(resourceDirectory).getFileName().toString();

        } catch (IOException e) {
            throw new IllegalArgumentException(MESSAGE_COPY_FAILURE);
        }
    }

    /**
     * Returns true if a given string is a valid image.
     */
    public static boolean isValidImage(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            File f = new File(test);

            if (f.exists()) {
                try {
                    String mimeType = Files.probeContentType(f.toPath());
                    // Check if first param of mimeType is "image"
                    return (mimeType != null && mimeType.split("/")[0].equals("image"));
                } catch (IOException e) {
                    return false;
                }
            } else {
                return (Image.class.getResourceAsStream(test)) != null
                        && (test.endsWith(".png") || test.endsWith(".jpg"));
            }
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        return filePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Image // instanceof handles nulls
                && filePath.equals(((Image) other).filePath)); // state check
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }
}

