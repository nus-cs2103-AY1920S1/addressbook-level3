package seedu.weme.model.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.file.Path;

import seedu.weme.commons.util.FileUtil;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.ImagePath;
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
        Path originalPath = toCopy.getImagePath().getFilePath();
        Path newPath = copyImageFile(originalPath, memeLocation);
        return new Meme(new ImagePath(newPath.toString()), toCopy.getDescription(), toCopy.getTags());
    }

    /**
     * Copies the image of {@code toCopy} to {@code ImagePath}, and returns a new {@code Template} whose
     * {@code ImagePath} points to that location.
     *
     * @param toCopy           the {@code Template} to copy
     * @param templateLocation the template image location
     * @return a new {@code Template} with the new {@code ImagePath}.
     */
    public static Template copyTemplate(Template toCopy, Path templateLocation) throws IOException {
        Path originalPath = toCopy.getImagePath().getFilePath();
        Path newPath = copyImageFile(originalPath, templateLocation);
        return new Template(toCopy.getName(), new ImagePath(newPath.toString()));
    }

    /**
     * Copies the image file {@code originalPath} to {@code destinationDir}, with a new file name that will be
     * generated using {@link ImageUtil#getNewImagePath(Path, String)}
     *
     * @param originalPath   the path of the original image file
     * @param destinationDir the destination directory
     * @return the new {@code Path} of the copied image
     * @throws IOException if an error occurred during IO
     */
    public static Path copyImageFile(Path originalPath, Path destinationDir) throws IOException {
        String extension = FileUtil.getExtension(originalPath).orElse("");
        Path newPath = getNewImagePath(destinationDir, extension);
        FileUtil.copy(originalPath, newPath);
        return newPath;
    }

    /**
     * Generates a random image path in the destination directory.
     *
     * @param destination destination directory
     * @param extension   file extension of the image
     * @return the Path for the image to be copied to
     */
    public static Path getNewImagePath(Path destination, String extension) {
        Path newPath;
        do {
            newPath = destination.resolve(FileUtil.generateUuidString() + "." + extension);
        } while (FileUtil.isFileExists(newPath));
        return newPath;
    }

    /**
     * Returns a copy of {@code toCopy}.
     *
     * @param toCopy the {@code BufferedImage} to copy
     * @return the copied {@code BufferedImage}
     */
    public static BufferedImage copyBufferedImage(BufferedImage toCopy) {
        if (toCopy == null) {
            return null;
        } else {
            ColorModel cm = toCopy.getColorModel();
            WritableRaster raster = toCopy.copyData(toCopy.getRaster().createCompatibleWritableRaster());
            boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
            return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        }
    }

}
