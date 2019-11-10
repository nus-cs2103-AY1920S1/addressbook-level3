package seedu.address.ui.cap;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.cap.module.Semester;

/**
 * Panel containing the list of modules for the semester.
 */
public class SemesterListPanel extends UiPart<Region> {
    private static final String FXML = "SemesterListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    @FXML
    private ListView<Semester> semesterListView;

    public SemesterListPanel(ObservableList<Semester> semesterList) {
        super(FXML);
        semesterListView.setItems(semesterList);
        semesterListView.setCellFactory(listView -> new SemesterListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class SemesterListViewCell extends ListCell<Semester> {
        @Override
        protected void updateItem(Semester semester, boolean empty) {
            super.updateItem(semester, empty);

            if (empty || semester == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SemesterCard(semester).getRoot());
            }
        }
    }
}
