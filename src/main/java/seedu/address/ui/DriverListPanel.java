package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Driver;

/**
 * Panel containing the list of persons.
 */
public class DriverListPanel extends UiPart<Region> {
    private static final String FXML = "DriverListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DriverListPanel.class);

    @FXML
    private ListView<Driver> driverListView;

    public DriverListPanel(ObservableList<Driver> driverList) {
        super(FXML);
        driverListView.setItems(driverList);
        driverListView.setCellFactory(listView -> new DriverListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Driver} using a {@code PersonCard}.
     */
    class DriverListViewCell extends ListCell<Driver> {
        @Override
        protected void updateItem(Driver driver, boolean empty) {
            super.updateItem(driver, empty);

            if (empty || driver == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DriverCard(driver, getIndex() + 1).getRoot());
            }
        }
    }
}
