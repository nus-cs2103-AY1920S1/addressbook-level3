package seedu.guilttrip.ui.gui.guihandles;

import java.util.stream.Collectors;

import javafx.scene.Node;
import seedu.guilttrip.model.entry.Expense;

/**
 * Provides a handle to a expense card in the expense list panel.
 */
public class ExpenseCardHandle extends EntryCardHandle {

    public ExpenseCardHandle(Node cardNode) {
        super(cardNode);
    }

    /**
     * Returns true if this handle contains {@code expense}.
     */
    public boolean equals(Expense expense) {
        return getDesc().equals(expense.getDesc().fullDesc)
                && getAmount().equals("$" + expense.getAmount().toString())
                && getDate().equals(expense.getDate().toString())
                && getCategory().equals(expense.getCategory().toString())
                && getTags().equals(expense.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
