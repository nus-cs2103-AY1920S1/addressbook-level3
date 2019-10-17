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

    private List<ObservableList<ObservableList<String>>> scheduleList;

    @FXML
    private TableView tableView;

    ScheduleView(List<ObservableList<ObservableList<String>>> scheduleList) {
        super(FXML);
        this.scheduleList = scheduleList;
        initialise();
    }

    /**
     * Allow the creation of table.
     */
    private void initialise() {
        for (int i = 0; i < this.scheduleList.get(0).get(0).size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column =
                    new TableColumn<ObservableList<String>, String>(
                            this.scheduleList.get(0).get(0).get(i)
                    );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            this.tableView.getColumns().add(column);
            this.tableView.setColumnResizePolicy(this.tableView.CONSTRAINED_RESIZE_POLICY);
        }

        for (int i = 1; i < this.scheduleList.get(0).size(); i++) {
            this.tableView.getItems().add(
                    this.scheduleList.get(0).get(i)
            );
        }
    }
}

