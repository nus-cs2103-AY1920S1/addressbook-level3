package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Panel containing a list of entries to display.
 */
public class ListPanel<T> extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private ListView<T> listView;

    public ListPanel(ObservableList<T> personList) {
        super(FXML);
        listView.setItems(personList);
        listView.setCellFactory(listView -> new ListViewCell<T>());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ListViewCell<U> extends ListCell<U> {
        @Override
        protected void updateItem(U entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else if (entry instanceof Person) {
                setGraphic(new PersonCard((Person) entry, getIndex() + 1).getRoot());
            } else if (entry instanceof Activity) {
                // Do nothing for now
            }
        }
    }

}
