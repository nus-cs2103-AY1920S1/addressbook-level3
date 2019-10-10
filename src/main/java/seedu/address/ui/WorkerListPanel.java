package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.worker.Worker;

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
            }
        }
    }

}
