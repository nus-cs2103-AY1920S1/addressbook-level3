package seedu.address.storage.printable;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;


/**
 * Represents a .png printable task of the statistics report.
 */
public class StatisticsPrintable implements NjoyPrintable {

    private static final String SAVE_PNG_ERROR = "Error saving statistics report as .png file.";

    private final WritableImage image;
    private final String fileName;

    public StatisticsPrintable(WritableImage image, String fileName) {
        this.image = image;
        this.fileName = fileName;
    }

    /**
     * Saves the statistics report image to the fileName specified by user.
     * @throws IOException if error occurs while saving the file.
     */
    public void savePrintable() throws IOException {
        try {
            File tempDir = new File(PRINTABLE_DIRECTORY_PATH);
            if (!tempDir.exists()) {
                new File(PRINTABLE_DIRECTORY_PATH).mkdir();
            }
            File file = new File(PRINTABLE_DIRECTORY_PATH + fileName);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException ex) {
            throw new IOException(SAVE_PNG_ERROR);
        }
    }

}
