package seedu.guilttrip.ui.gui.guihandles;

import java.util.stream.Collectors;

import javafx.scene.Node;
import seedu.guilttrip.model.entry.Income;

/**
 * Provides a handle to a income card in the income list panel.
 */
public class IncomeCardHandle extends EntryCardHandle {

    public IncomeCardHandle(Node cardNode) {
        super(cardNode);
    }

    /**
     * Returns true if this handle contains {@code income}.
     */
    public boolean equals(Income income) {
        return getDesc().equals(income.getDesc().fullDesc)
                && getAmount().equals(income.getAmount().toString())
                && getDate().equals(income.getDate().toString())
                && getCategory().equals(income.getCategory().toString())
                && getTags().equals(income.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
