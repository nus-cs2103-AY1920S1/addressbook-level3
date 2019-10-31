package mams.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import mams.commons.core.LogsCenter;
import mams.logic.InputOutput;

/**
 * Panel displaying command history in list form.
 */
public class HistoryListPanel extends UiPart<Region> {
    private static final String FXML = "ItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AppealListPanel.class);

    @FXML
    private ListView<InputOutput> itemListView;

    private boolean isHideOutput;

    private ObservableList<InputOutput> commandHistoryList;

    public HistoryListPanel(ObservableList<InputOutput> commandHistory) {
        super(FXML);
        this.commandHistoryList = commandHistory;
        itemListView.setItems(commandHistory);
        itemListView.setCellFactory(listView -> new HistoryListViewCell());
    }

    /**
     * Sets boolean flag for hiding output when updating all existing ListCells
     * in {@code itemListView}
     * @param isHideOutput
     */
    public void hideOutput(boolean isHideOutput) {
        this.isHideOutput = isHideOutput;
    }

    /**
     * Scroll {@code commandHistoryListAdapter} to the bottom, and selects the last
     * cell.
     */
    public void scrollToBottom() {
        int lastIndex = commandHistoryList.size() - 1;
        itemListView.scrollTo(lastIndex);
        itemListView.getSelectionModel().select(lastIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code InputOutput} using an {@code InputOutputCard}.
     */
    class HistoryListViewCell extends ListCell<InputOutput> {
        @Override
        protected void updateItem(InputOutput inputOutput, boolean empty) {
            super.updateItem(inputOutput, empty);

            if (empty || inputOutput == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new InputOutputCard(inputOutput, getIndex() + 1, isHideOutput).getRoot());
            }
        }
    }
}
