package seedu.scheduler.ui;

import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
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
            initialiseCellData(column, finalIdx);
            editColumn(column);
            this.tableView.getColumns().add(column);
        }
        editTable();
        this.tableView.setItems(this.schedule);
    }

    /**
     * Initialise cell data to color the available time slots of interviewer or the allocated time slots of interviewee
     * @param column data from each column.
     * @param index index of column selected.
     */
    private void initialiseCellData(TableColumn column, int index) {
        if (index != 0) {
            column.setCellFactory(param ->
                    new TableCell<ObservableList<String>, String>() {
                    @Override
                    public void updateItem(String cell, boolean empty) {
                        super.updateItem(cell, empty);
                        if (cell == null || empty) {
                            setText(null);
                        } else if (cell.equals("0")) {
                            setText(cell);
                            setStyle("-fx-background-color: transparent");
                        } else if (cell.equals("1")) {
                            setText(cell);
                            setStyle("-fx-background-color: #b8b8b882");
                        } else {
                            setText(cell);
                            setStyle("-fx-background-color: #14542179");
                        }
                    }
            });
        }
    }

    /**
     * Edit the characteristics of each column
     * @param column Column selected.
     */
    private void editColumn(TableColumn column) {
        column.setSortable(false);
        column.setReorderable(false);
        column.setMinWidth(300);
    }

    /**
     * Edit the characteristics of the table.
     */
    private void editTable() {
        if (this.titles.size() < 5) {
            this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } else {
            this.tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        }
    }
}

