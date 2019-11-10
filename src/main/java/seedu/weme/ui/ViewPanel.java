package seedu.weme.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;

/**
 * Panel containing the meme to view.
 */
public class ViewPanel extends UiPart<Region> {

    private static final String FXML = "ViewPanel.fxml";
    private static final double IMAGE_PADDING = 100;
    private final Logger logger = LogsCenter.getLogger(ViewPanel.class);

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView display;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private StackPane stackPane;

    public ViewPanel(ObservableValue<Meme> observableMeme) {
        super(FXML);
        stackPane.widthProperty().addListener(((observable, oldValue, newValue) -> {
            display.setFitWidth((double) newValue - IMAGE_PADDING * 2); // 20 padding on each side
        }));
        observableMeme.addListener((observable, oldValue, newValue) -> {
            display.setImage(new Image(newValue.getImagePath().toUrl().toString(), true));
            description.setText(newValue.getDescription().value);
            tags.getChildren().clear();
            newValue.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

}
