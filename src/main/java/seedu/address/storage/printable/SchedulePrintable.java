package seedu.address.storage.printable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_SCREENSHOT_SCHEDULE_FILE_ERROR;
import static seedu.address.commons.util.FileUtil.isValidFileName;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

/**
 * Provides functionality to save schedule into PNG format
 */
public class SchedulePrintable implements NjoyPrintable {
    private static final String PNG_FILE_TYPE = ".png";

    private final WritableImage image;
    private final String fileName;

    public SchedulePrintable(WritableImage image, String fileName) {
        requireNonNull(image);
        requireNonNull(fileName);
        this.image = image;
        this.fileName = fileName;
    }

    /**
     * Saves the event schedule image to the targetFilePath specified by user.
     * @throws IOException if error occurs while saving the file.
     */
    public void savePrintable() throws IOException {
        try {
            File tempDir = new File(PRINTABLE_DIRECTORY_PATH);
            if (!tempDir.exists()) {
                new File(PRINTABLE_DIRECTORY_PATH).mkdir();
            }
            if (isValidFileName(fileName)) {
                File file = new File(PRINTABLE_DIRECTORY_PATH + fileName + PNG_FILE_TYPE);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } else {
                throw new IOException();
            }
        } catch (IOException ex) {
            throw new IOException(MESSAGE_SCREENSHOT_SCHEDULE_FILE_ERROR);
        }
    }
}
