package seedu.guilttrip.ui.gui.guihandles;

import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.guilttrip.model.entry.AutoExpense;

/**
 * Provides a handle to an AutoExpense card in the AutoExpense list panel.
 */
public class AutoExpenseCardHandle extends EntryCardHandle {
    private static final String NEXT_TIME_FIELD_ID = "#nextTime";
    private static final String FREQ_FIELD_ID = "#freq";

    private final Label nextTimeLabel;
    private final Label freqLabel;

    public AutoExpenseCardHandle(Node cardNode) {
        super(cardNode);

        nextTimeLabel = getChildNode(NEXT_TIME_FIELD_ID);
        freqLabel = getChildNode(FREQ_FIELD_ID);
    }

    public String getNextTime() {
        return nextTimeLabel.getText();
    }

    public String getFrequency() {
        return freqLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code autoExpense}.
     */
    public boolean equals(AutoExpense autoExpense) {
        return super.getDesc().equals(autoExpense.getDesc().fullDesc)
                && super.getAmount().equals(autoExpense.getAmount().toString())
                && getNextTime().equals(autoExpense.getNextTime().toString())
                && getFrequency().equals(autoExpense.getFrequency().toString())
                && super.getCategory().equals(autoExpense.getCategory().toString())
                && super.getTags().equals(autoExpense.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
