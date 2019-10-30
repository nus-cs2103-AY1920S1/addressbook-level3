package dukecooks.model;

import static dukecooks.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Represents an Item's image.
 * Guarantees: immutable; is valid as declared in {@link #isValidImage(String)}
 */
public class Image {

    public static final String MESSAGE_CONSTRAINTS = "Image file path should exist and it should end with .png or .jpg";

    /*
     * The image filePath must not be a whitespace
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String filePath;

    /**
     * Constructs an {@code Image}.
     *
     * @param filePath A valid file path.
     */
    public Image(String filePath) {
        requireNonNull(filePath);
        checkArgument(isValidImage(filePath), MESSAGE_CONSTRAINTS);

        this.filePath = filePath;
    }

    /**
     * Returns the filePath of a valid Image
     */
    public String getFilePath() {
        return filePath;
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
                return (Image.class.getResource(test)) != null && (test.endsWith(".png") || test.endsWith(".jpg"));
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

