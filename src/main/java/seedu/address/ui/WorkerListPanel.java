package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.worker.Worker;

//@@author shaoyi1997
/**
 * Panel containing the list of workers.
 */
public class WorkerListPanel extends UiPart<Region> {
    private static final String FXML = "WorkerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WorkerListPanel.class);

    @FXML
    private ListView<Worker> workerListView;

    public WorkerListPanel(ObservableList<Worker> workerList) {
        super(FXML);
        workerListView.setItems(workerList);
        workerListView.setCellFactory(listView -> new WorkerListViewCell());
        workerListView.setSelectionModel(new NoSelectionModel<>());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Worker} using a {@code WorkerCard}.
     */
    class WorkerListViewCell extends ListCell<Worker> {
        @Override
        protected void updateItem(Worker worker, boolean empty) {
            super.updateItem(worker, empty);

            if (empty || worker == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WorkerCard(worker, getIndex() + 1).getRoot());
                setMaxHeight(Double.MAX_VALUE);
            }
        }
    }

    //@@author shaoyi1997-reused
    //Reused from https://stackoverflow.com/questions/20621752/javafx-make-listview-not-selectable-via-mouse
    /**
     * Null selection model that prevents item selection in the list.
     */
    public class NoSelectionModel<T> extends MultipleSelectionModel<T> {

        @Override
        public ObservableList<Integer> getSelectedIndices() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public ObservableList<T> getSelectedItems() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public void selectIndices(int index, int... indices) {
        }

        @Override
        public void selectAll() {
        }

        @Override
        public void selectFirst() {
        }

        @Override
        public void selectLast() {
        }

        @Override
        public void clearAndSelect(int index) {
        }

        @Override
        public void select(int index) {
        }

        @Override
        public void select(T obj) {
        }

        @Override
        public void clearSelection(int index) {
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public boolean isSelected(int index) {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void selectPrevious() {
        }

        @Override
        public void selectNext() {
        }
    }
    //@@author
}
//@@author
