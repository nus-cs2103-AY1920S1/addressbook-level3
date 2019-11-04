package seedu.jarvis.ui.planner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jarvis.ui.UiPart;

/**
 * A UI component of the planner that displays the header of a list
 */
public class Header extends UiPart<Region> {

    private static final String FXML = "Header.fxml";

    public final String header;

    @FXML
    private HBox cardPane;
    @FXML
    private Label headerText;

    public Header(String text) {
        super(FXML);
        header = text;

    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof Header)) {
            return false;
        }

        //state check
        Header head = (Header) other;
        return header.equals(head.header);
    }

}
