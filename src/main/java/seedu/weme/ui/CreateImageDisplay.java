package seedu.weme.ui;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.weme.model.template.MemeCreation;
import seedu.weme.model.template.MemeText;

/**
 * Panel displaying the image of the current meme creation session.
 */
public class CreateImageDisplay extends UiPart<Region> {

    private static final String FXML = "CreateImageDisplay.fxml";

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


    public CreateImageDisplay(MemeCreation memeCreation) {
        super(FXML);
        ObservableList<MemeText> memeTexts = memeCreation.getMemeTextList();
        MemeTextListPanel memeTextListPanel = new MemeTextListPanel(memeTexts);
        memeTextListPlaceholder.getChildren().addAll(memeTextListPanel.getRoot());
        updateImage(memeCreation);
    }

    /**
     * Updates the image displayed.
     *
     * @param session the current meme creation session
     */
    public void updateImage(MemeCreation session) {
        if (session.getCurrentImage().isPresent()) {
            memeImage.setImage(SwingFXUtils.toFXImage(session.getCurrentImage().get(), null));
            verticalRule.setFitHeight(memeImage.getBoundsInParent().getHeight());
            horizontalRule.setFitWidth(memeImage.getBoundsInParent().getWidth());
            memeCreationBox.setVisible(true);
            memeCreationPlaceholder.setVisible(false);
        } else {
            memeImage.setImage(null);
            memeCreationBox.setVisible(false);
            memeCreationPlaceholder.setVisible(true);
        }
    }

}
