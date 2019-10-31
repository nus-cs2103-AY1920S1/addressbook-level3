package seedu.address.storage.printable;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

public class SchedulePrintable implements NjoyPrintable  {
    private static final String SAVE_PNG_ERROR = "Error saving event schedule as .png file. Please check file path" +
            "is valid";
    private static final String PNG_FILE_TYPE = ".png";

    private final WritableImage image;
    private final String fullTargetFilePath;

    public SchedulePrintable(WritableImage image, String fullTargetFilePath) {
        this.image = image;
        this.fullTargetFilePath = fullTargetFilePath;
    }

    /**
     * Saves the event schedule image to the targetFilePath specified by user.
     * @throws IOException if error occurs while saving the file.
     */
    public void savePrintable() throws IOException {
        try {
            File file = new File(fullTargetFilePath + PNG_FILE_TYPE);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException ex) {
            throw new IOException(SAVE_PNG_ERROR);
        }
    }
}
