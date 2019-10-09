package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.semester.Semester;

/**
 * Panel containing the list of persons.
 */
public class SemesterListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SemesterListPanel.class);

    @FXML
    private ListView<Semester> semesterListView;

    public SemesterListPanel(ObservableList<Semester> personList) {
        super(FXML);
        semesterListView.setItems(personList);
        semesterListView.setCellFactory(listView -> new SemesterListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class SemesterListViewCell extends ListCell<Semester> {
        @Override
        protected void updateItem(Semester semester, boolean empty) {
            super.updateItem(semester, empty);

            if (empty || semester == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SemesterCard(semester, getIndex() + 1).getRoot());
            }
        }
    }

}
