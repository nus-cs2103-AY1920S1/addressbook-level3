package seedu.guilttrip.ui.gui.guihandles;

import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.guilttrip.model.entry.Budget;

/**
 * Provides a handle to a budget card in the budget list panel.
 */
public class BudgetCardHandle extends EntryCardHandle {
    private static final String SPENT_AMOUNT_FIELD_ID = "#spent";
    private static final String TOTAL_AMOUNT_FIELD_ID = "#total";

    private final Label amountSpentLabel;
    private final Label totalAmountLabel;

    public BudgetCardHandle(Node cardNode) {
        super(cardNode);

        amountSpentLabel = getChildNode(SPENT_AMOUNT_FIELD_ID);
        totalAmountLabel = getChildNode(TOTAL_AMOUNT_FIELD_ID);

    }

    public String getAmountSpent() {
        return amountSpentLabel.getText();
    }

    public String getTotalAmount() {
        return totalAmountLabel.getText();
    }

    @Override
    public String getAmount() {
        return getTotalAmount();
    }

    /**
     * Returns true if this handle contains {@code budget}.
     */
    public boolean equals(Budget budget) {
        return super.getDesc().equals(budget.getDesc().fullDesc)
                && getAmountSpent().equals(budget.getSpent().toString())
                && getAmount().equals(budget.getAmount().toString())
                && super.getDate().equals(budget.getDate().toString())
                && super.getCategory().equals(budget.getCategory().toString())
                && super.getTags().equals(budget.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
