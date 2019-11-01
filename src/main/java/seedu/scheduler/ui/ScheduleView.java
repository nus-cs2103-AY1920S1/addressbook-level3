package seedu.scheduler.ui;

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

    private List<String> titles;
    private ObservableList<ObservableList<String>> schedule; // Excluding titles

    @FXML
    private TableView tableView;

    ScheduleView(List<String> titles, ObservableList<ObservableList<String>> schedule) {
        super(FXML);
        this.titles = titles;
        this.schedule = schedule;
        initialise();
    }

    /**
     * Allow the creation of table.
     */
    private void initialise() {
        for (int i = 0; i < this.titles.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column =
                    new TableColumn<ObservableList<String>, String>(
                            this.titles.get(i)
                    );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            column.setSortable(false);
            column.setReorderable(false);
            column.setMinWidth(300);
            this.tableView.getColumns().add(column);
            this.tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        }
        this.tableView.setItems(this.schedule);
    }
}

