package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.show.Show;

/**
 * An UI component that displays information of a {@code Show}.
 */
public class ShowCard extends UiPart<Region> {

    private static final String FXML = "ShowCard.fxml";

    public final Show show;

    @FXML
    private Label id;
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;

    public ShowCard(Show show, int displayedIndex) {
        super(FXML);
        this.show = show;
        id.setText(displayedIndex + ". ");
        name.setText(show.getName().toString());
    }
}
