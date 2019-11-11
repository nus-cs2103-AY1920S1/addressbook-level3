package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.model.file.ViewableFile;

/**
 * A Ui for previewing encrypted files when the preview command is called.
 */
public class FilePreviewPanel extends UiPart<Region> {
    private static final String FXML = "FilePreviewPanel.fxml";

    @FXML
    private VBox placeHolder;
    @FXML
    private Label filenameLabel;
    @FXML
    private Label metadataLabel;
    @FXML
    private TextArea previewTextArea;
    @FXML
    private ImageView previewImage;

    public FilePreviewPanel() {
        super(FXML);
    }

    public void setFeedbackToUser(ViewableFile file) {
        requireNonNull(file);
        filenameLabel.setText(file.getFileName());
        switch (file.getFileType()) {
        case TEXT:
            placeHolder.getChildren().removeAll(previewImage);
            previewTextArea.setText((String) file.getFileContent());
            metadataLabel.setText("Plain Text");
            break;
        case IMAGE:
            previewImage.setPreserveRatio(true);
            placeHolder.getChildren().removeAll(previewTextArea);
            BufferedImage image = (BufferedImage) file.getFileContent();
            previewImage.setImage(SwingFXUtils.toFXImage(image, null));
            metadataLabel.setText("Image | " + image.getWidth() + " x " + image.getHeight());
            break;
        case PDF:
            previewImage.setPreserveRatio(true);
            placeHolder.getChildren().removeAll(previewTextArea);
            PDDocument pdf = (PDDocument) file.getFileContent();
            PDFRenderer renderer = new PDFRenderer(pdf);
            try {
                previewImage.setImage(SwingFXUtils.toFXImage(renderer.renderImage(0), null));
            } catch (IOException e) {
                filenameLabel.setText("Fails to render PDF");
            }
            metadataLabel.setText("PDF | " + pdf.getNumberOfPages() + " Page(s)");
            break;
        case WORD:
            placeHolder.getChildren().removeAll(previewImage);
            XWPFWordExtractor extractor = new XWPFWordExtractor((XWPFDocument) file.getFileContent());
            previewTextArea.setText(extractor.getText());
            metadataLabel.setText("MS Word Document");
            break;
        default:
            break;
        }
    }

}
