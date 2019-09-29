package seedu.address.logic.export;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * Interface class to export visual representations of schedules.
 */
public interface ExportVisual {

    /**
     * Captures a snapshot of the node view and save it as fileformat in filepath.
     * @param view The node that will be captured.
     * @param fileformat The file format that will exported. Examples: PNG, JPG, ETC.
     * @param filepath The file path where the export will be placed.
     */
    public static void exportToPng(Node view, String fileformat, String filepath) throws IOException {
        Image image = view.snapshot(null, null);

        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(renderedImage, fileformat, new File(filepath));
    };

}
