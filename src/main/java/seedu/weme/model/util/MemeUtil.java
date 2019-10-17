package seedu.weme.model.util;

import java.io.IOException;
import java.nio.file.Path;

import seedu.weme.commons.util.FileUtil;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.meme.Meme;

/**
 * A utility class for operations related to {@code Meme}.
 */
public class MemeUtil {

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
     * Generates the Path the meme image should be stored in.
     *
     * @param originalPath original Path of the meme
     * @param memeLocation meme image location specified in user preferences
     * @return the Path for the meme to be copied to
     */
    public static Path getNewImagePath(Path originalPath, Path memeLocation) {
        String extension = FileUtil.getExtension(originalPath).orElse("");
        return memeLocation.resolve(FileUtil.hash(originalPath) + "." + extension);
    }

}
