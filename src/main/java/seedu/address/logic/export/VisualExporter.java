package seedu.address.logic.export;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;

import static javafx.embed.swing.SwingFXUtils.fromFXImage;

/**
 * Interface class to export visual representations of schedules.
 */
public interface VisualExporter {

    /**
     * Captures a snapshot of the node view and save it as fileformat in filepath.
     * @param view The node that will be captured.
     * @param fileformat The file format that will exported. Examples: PNG, JPG, ETC.
     * @param filepath The file path where the export will be placed.
     */
    public static void exportTo(Node view, String fileformat, String filepath) throws IOException {
        Image image = view.snapshot(null, null);
        RenderedImage renderedImage = fromFXImage(image, null);
        ImageIO.write(renderedImage, fileformat, new File(filepath));
    };

}

