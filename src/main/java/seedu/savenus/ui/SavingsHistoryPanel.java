package seedu.savenus.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.model.savings.Savings;

/**
 * Panel containing the list of purchase.
 */
public class SavingsHistoryPanel extends UiPart<Region> {
    private static final String FXML = "SavingsHistoryPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SavingsHistoryPanel.class);

    @FXML
    private ListView<Savings> savingsHistoryView;

    public SavingsHistoryPanel(ObservableList<Savings> savingsHistory) {
        super(FXML);
        savingsHistoryView.setItems(savingsHistory);
        savingsHistoryView.setCellFactory(listView -> new SavingsHistoryViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Savings} using a {@code SavingsCard}.
     */
    class SavingsHistoryViewCell extends ListCell<Savings> {
        @Override
        protected void updateItem(Savings savings, boolean empty) {
            super.updateItem(savings, empty);

            if (empty || savings == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SavingsCard(savings).getRoot());
            }
        }
    }

    public void updateSavingsHistory(ObservableList<Savings> savingsHistory) {
        savingsHistoryView.setItems(savingsHistory);
    }

}
