package seedu.address.ui;

import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

/**
 * Table consisting of timetables of scheduling.
 */
public class ScheduleView extends UiPart<Region> {

    private static final String FXML = "ScheduleView.fxml";

    private List<List<String>> titles;
    private List<ObservableList<ObservableList<String>>> scheduleList; // Excluding titles

    @FXML
    private TableView tableView;

    ScheduleView(List<List<String>> titles, List<ObservableList<ObservableList<String>>> scheduleList) {
        super(FXML);
        this.titles = titles;
        this.scheduleList = scheduleList;
        initialise();
    }

    /**
     * Allow the creation of table.
     */
    private void initialise() {
        // Currently the code here will only retrieve the first list of titles.
        for (int i = 0; i < titles.get(0).size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column =
                    new TableColumn<ObservableList<String>, String>(
                            titles.get(0).get(i)
                    );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            this.tableView.getColumns().add(column);
            this.tableView.setColumnResizePolicy(this.tableView.CONSTRAINED_RESIZE_POLICY);
        }

        // the data of the schedule now excludes the titles, so titles is not in the first row of data returned anymore
        for (int i = 0; i < this.scheduleList.get(0).size(); i++) {
            this.tableView.getItems().add(
                    this.scheduleList.get(0).get(i)
            );
        }
    }
}

