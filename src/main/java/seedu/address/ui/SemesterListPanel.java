package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;

/**
 * Panel containing the list of semesters.
 */
public class SemesterListPanel extends UiPart<Region> {
    private static final String FXML = "SemesterListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SemesterListPanel.class);

    @FXML
    private ListView<Semester> semesterListView;

    public SemesterListPanel(ObservableList<Semester> semesters, SemesterName currentSem) {
        super(FXML);
        semesterListView.setItems(semesters);
        semesterListView.setCellFactory(listView -> new SemesterListViewCell(currentSem));
    }

    public void refresh() {
        semesterListView.refresh();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Semester} using a {@code SemesterCard}.
     */
    class SemesterListViewCell extends ListCell<Semester> {
        private SemesterName currentSem;

        public SemesterListViewCell(SemesterName currentSem) {
            this.currentSem = currentSem;
        }

        @Override
        protected void updateItem(Semester semester, boolean empty) {
            super.updateItem(semester, empty);

            if (empty || semester == null) {
                setGraphic(null);
                setText(null);
            } else if (semester.isBlocked()) {
                setGraphic(new BlockedSemesterCard(semester).getRoot());
            } else if (semester.getSemesterName() == this.currentSem) {
                setGraphic(new CurrentSemesterCard(semester).getRoot());
            } else {
                setGraphic(new SemesterCard(semester).getRoot());
            }
        }
    }

}
