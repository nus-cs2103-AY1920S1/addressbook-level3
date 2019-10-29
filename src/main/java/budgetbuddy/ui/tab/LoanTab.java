package budgetbuddy.ui.tab;

import budgetbuddy.model.loan.Loan;
import budgetbuddy.ui.panel.LoanPanel;
import javafx.collections.ObservableList;

/**
 * Represents a tab component that displays the loan panel when selected.
 */
public class LoanTab extends PanelTab {

    public LoanTab(ObservableList<Loan> loanList) {
        super(new LoanPanel(loanList), "/images/loanTab.png");
    }
}
