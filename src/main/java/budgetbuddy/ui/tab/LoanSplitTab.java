package budgetbuddy.ui.tab;

import budgetbuddy.model.loan.Debtor;
import budgetbuddy.ui.panel.LoanSplitPanel;
import javafx.collections.transformation.SortedList;

/**
 * Represents a tab component that displays the loan split panel when selected.
 */
public class LoanSplitTab extends PanelTab {

    public LoanSplitTab(SortedList<Debtor> loanSplitList) {
        super(new LoanSplitPanel(loanSplitList), "/images/loanSplitTab.png");
    }
}
