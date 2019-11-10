package budgetbuddy.ui.panel;

import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.model.loan.Debtor;
import budgetbuddy.ui.card.LoanSplitCard;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * Panel containing the list of debtors.
 */
public class LoanSplitPanel extends DisplayPanel {
    private static final String FXML = "LoanSplitPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LoanSplitPanel.class);

    @FXML
    private ListView<Debtor> debtorListView;

    public LoanSplitPanel(SortedList<Debtor> sortedDebtorList) {
        super(FXML);
        debtorListView.setItems(sortedDebtorList);
        debtorListView.setCellFactory(debtorListView -> new DebtorListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Debtor} using a {@code LoanSplitCard}.
     */
    class DebtorListViewCell extends ListCell<Debtor> {
        @Override
        protected void updateItem(Debtor debtor, boolean empty) {
            super.updateItem(debtor, empty);

            if (empty || debtor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LoanSplitCard(debtor).getRoot());
            }
        }
    }
}
