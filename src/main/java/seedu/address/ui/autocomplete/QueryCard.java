package seedu.address.ui.autocomplete;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Query}.
 */
public class QueryCard extends UiPart<Region> {

    private static final String FXML = "QueryCard.fxml";

    @FXML
    private HBox apCard;
    @FXML
    private Label query;

    public QueryCard(String word) {
        super(FXML);
        query.setText(word);
    }
}
