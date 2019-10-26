package thrift.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A UI for displaying the meaning of the list.
 */
public class FilteredBar extends UiPart<Region> {
    private static final String FXML = "FilteredBar.fxml";

    @FXML
    private Label filterLabel;

    public FilteredBar(String filteredName) {
        super(FXML);
        setFiltered(filteredName);
    }

    public void setFiltered(String filteredName) {
        filterLabel.setText("Filter: " + filteredName);
    }

}
