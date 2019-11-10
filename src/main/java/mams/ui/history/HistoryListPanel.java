package mams.ui.history;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import mams.commons.core.LogsCenter;
import mams.logic.history.InputOutput;
import mams.ui.UiPart;
import mams.ui.appeal.AppealListPanel;

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
        requireNonNull(commandHistory);
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
        logger.fine("Command output in the command history window has been hidden");
        this.isHideOutput = isHideOutput;
    }

    /**
     * Scroll {@code commandHistoryListAdapter} to the bottom, and selects the last
     * cell.
     */
    public void scrollToBottom() {
        int lastIndex = commandHistoryList.size() - 1;
        itemListView.requestFocus();
        itemListView.scrollTo(lastIndex);
        itemListView.getSelectionModel().select(lastIndex);
        itemListView.getFocusModel().focus(lastIndex);
    }

    public InputOutput getCurrentlySelectedInputOutput() {
        logger.fine("Retrieving details of the currently selected InputOutput object");
        return itemListView.getSelectionModel().getSelectedItem();
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
