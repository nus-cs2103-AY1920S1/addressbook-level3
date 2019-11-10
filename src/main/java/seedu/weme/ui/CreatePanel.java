package seedu.weme.ui;

import java.awt.image.BufferedImage;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.weme.model.template.MemeText;

/**
 * Panel displaying the image of the current meme creation session.
 */
public class CreatePanel extends UiPart<Region> {

    private static final String FXML = "CreatePanel.fxml";

    @FXML
    private HBox memeCreationBox;
    @FXML
    private ImageView memeImage;
    @FXML
    private ImageView verticalRule;
    @FXML
    private ImageView horizontalRule;
    @FXML
    private VBox memeCreationPlaceholder;
    @FXML
    private StackPane memeTextListPlaceholder;


    public CreatePanel(ObservableList<MemeText> memeTextList, BufferedImage image) {
        super(FXML);
        MemeTextListPanel memeTextListPanel = new MemeTextListPanel(memeTextList);
        memeTextListPlaceholder.getChildren().addAll(memeTextListPanel.getRoot());
        updateImage(image);
    }

    /**
     * Updates the image displayed.
     *
     * @param image the current image of the meme creation session
     */
    public void updateImage(BufferedImage image) {
        if (image != null) {
            memeImage.setImage(SwingFXUtils.toFXImage(image, null));
            verticalRule.setFitHeight(memeImage.getBoundsInParent().getHeight());
            horizontalRule.setFitWidth(memeImage.getBoundsInParent().getWidth());
            memeCreationBox.setVisible(true);
            memeCreationPlaceholder.setVisible(false);
        } else {
            memeImage.setImage(null);
            verticalRule.setFitHeight(memeImage.getBoundsInParent().getHeight());
            horizontalRule.setFitWidth(memeImage.getBoundsInParent().getWidth());
            memeCreationBox.setVisible(false);
            memeCreationPlaceholder.setVisible(true);
        }
    }

}
