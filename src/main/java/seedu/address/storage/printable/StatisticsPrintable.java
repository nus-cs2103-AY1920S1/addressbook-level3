package seedu.address.storage.printable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_SAVE_STATS_FILE_ERROR;
import static seedu.address.commons.util.FileUtil.isValidFileName;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

/**
 * Represents a .png printable task of the statistics report.
 */
public class StatisticsPrintable implements NjoyPrintable {

    private final WritableImage image;
    private final String fileName;

    public StatisticsPrintable(WritableImage image, String fileName) {
        requireNonNull(image, fileName);
        this.image = image;
        this.fileName = fileName;
    }

    /**
     * Saves the statistics report image to the fileName specified in the Constructor.
     * @throws IOException if error occurs while saving the file.
     */
    public void savePrintable() throws IOException {
        try {
            File tempDir = new File(PRINTABLE_DIRECTORY_PATH);
            if (!tempDir.exists()) {
                new File(PRINTABLE_DIRECTORY_PATH).mkdir();
            }
            if (isValidFileName(fileName)) {
                File file = new File(PRINTABLE_DIRECTORY_PATH + fileName);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } else {
                throw new IOException();
            }
        } catch (IOException ex) {
            throw new IOException(MESSAGE_SAVE_STATS_FILE_ERROR);
        }
    }

}
