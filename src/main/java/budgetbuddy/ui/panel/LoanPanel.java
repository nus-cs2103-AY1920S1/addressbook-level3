package budgetbuddy.ui.panel;

import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.ui.card.LoanCard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * Panel containing the list of loans.
 */
public class LoanPanel extends DisplayPanel {
    private static final String FXML = "LoanPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LoanPanel.class);

    @FXML
    private ListView<Loan> loanListView;

    public LoanPanel(ObservableList<Loan> sortedLoanList) {
        super(FXML);
        loanListView.setItems(sortedLoanList);
        loanListView.setCellFactory(loanListView -> new LoanListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Loan} using a {@code LoanCard}.
     */
    class LoanListViewCell extends ListCell<Loan> {
        @Override
        protected void updateItem(Loan loan, boolean empty) {
            super.updateItem(loan, empty);

            if (empty || loan == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LoanCard(loan, getIndex() + 1).getRoot());
                setMouseTransparent(true);
                setFocusTraversable(false);
            }
        }
    }
}
