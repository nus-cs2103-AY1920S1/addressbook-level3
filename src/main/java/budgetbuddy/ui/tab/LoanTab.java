package budgetbuddy.ui.tab;

import budgetbuddy.model.loan.Debtor;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.ui.panel.LoanPanel;
import budgetbuddy.ui.panel.LoanSplitPanel;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 * Represents a tab component that displays the loan panel when selected.
 */
public class LoanTab extends PanelTab {

    public LoanTab(ObservableList<Loan> loanList, SortedList<Debtor> debtors) {
        super(new LoanPanel(loanList), new LoanSplitPanel(debtors), "Loan");
    }
}
