package seedu.weme.model.util;

import java.io.IOException;
import java.nio.file.Path;

import seedu.weme.commons.util.FileUtil;
import seedu.weme.model.imagePath.ImagePath;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.template.Template;

/**
 * A utility class for copying {@code Meme}s and {@code Template}s.
 */
public class ImageUtil {

    /**
     * Copies the image of {@code toCopy} to {@code ImagePath}, and returns a new {@code Meme} whose {@code ImagePath}
     * points to that location.
     *
     * @param toCopy       the {@code Meme} to copy
     * @param memeLocation the meme image location
     * @return a new {@code Meme} with the new {@code ImagePath}.
     */
    public static Meme copyMeme(Meme toCopy, Path memeLocation) throws IOException {
        Path originalPath = toCopy.getFilePath().getFilePath();
        Path newPath = getNewImagePath(originalPath, memeLocation);
        FileUtil.copy(originalPath, newPath);
        return new Meme(new ImagePath(newPath.toString()), toCopy.getDescription(), toCopy.getTags());
    }

    /**
     * Copies the image of {@code toCopy} to {@code ImagePath}, and returns a new {@code Template} whose
     * {@code ImagePath} points to that location.
     *
     * @param toCopy       the {@code Template} to copy
     * @param templateLocation the template image location
     * @return a new {@code Template} with the new {@code ImagePath}.
     */
    public static Template copyTemplate(Template toCopy, Path templateLocation) throws IOException {
        Path originalPath = toCopy.getFilePath().getFilePath();
        Path newPath = getNewImagePath(originalPath, templateLocation);
        FileUtil.copy(originalPath, newPath);
        return new Template(toCopy.getName(), new ImagePath(newPath.toString()));
    }

    /**
     * Generates a random image path in the destination directory.
     *
     * @param originalPath original Path of the image
     * @param destination destination directory
     * @return the Path for the image to be copied to
     */
    public static Path getNewImagePath(Path originalPath, Path destination) {
        String extension = FileUtil.getExtension(originalPath).orElse("");
        Path newPath;
        do {
            newPath = destination.resolve(FileUtil.generateUuidString() + "." + extension);
        } while (FileUtil.isFileExists(newPath));
        return newPath;
    }
}
