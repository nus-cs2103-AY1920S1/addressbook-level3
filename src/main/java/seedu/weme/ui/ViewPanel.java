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
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;

/**
 * Panel containing the meme to view.
 */
public class ViewPanel extends UiPart<Region> {

    private static final String FXML = "ViewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ViewPanel.class);

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView display;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    public ViewPanel(ObservableValue<Meme> observableMeme) {
        super(FXML);
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
