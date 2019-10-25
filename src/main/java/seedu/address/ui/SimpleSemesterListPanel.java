package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.semester.Semester;

/**
 * Panel containing the list of semesters for simple study plan view on the left side of GUI.
 */
public class SimpleSemesterListPanel extends UiPart<Region> {
    private static final String FXML = "SimpleSemesterListPanel.fxml";
    // private final Logger logger = LogsCenter.getLogger(SimpleSemesterListPanel.class);

    @FXML
    private ListView<Semester> simpleSemesterListView;

    public SimpleSemesterListPanel(ObservableList<Semester> semesters) {
        super(FXML);
        simpleSemesterListView.setItems(semesters);
        simpleSemesterListView.setCellFactory(listView -> new SimpleSemesterListViewCell());
    }

    public void refresh() {
        simpleSemesterListView.refresh();
    }

    /**
     * Custom {@code ListCell} that displays the simplified graphics
     * of a {@code Semester} using a {@code SimpleSemesterListCard}.
     */
    class SimpleSemesterListViewCell extends ListCell<Semester> {
        @Override
        protected void updateItem(Semester semester, boolean empty) {
            super.updateItem(semester, empty);

            if (empty || semester == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SimpleSemesterListCard(semester).getRoot());
            }
        }
    }

}
