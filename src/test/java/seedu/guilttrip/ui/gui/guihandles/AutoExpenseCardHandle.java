package seedu.guilttrip.ui.gui.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.guilttrip.model.entry.AutoExpense;

/**
 * Provides a handle to an AutoExpense card in the AutoExpense list panel.
 */
public class AutoExpenseCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String DESC_FIELD_ID = "#desc";
    private static final String AMOUNT_FIELD_ID = "#amt";
    private static final String NEXT_TIME_FIELD_ID = "#nextTime";
    private static final String FREQ_FIELD_ID = "#freq";
    private static final String CATEGORY_FIELD_ID = "#category";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label descLabel;
    private final Label amountLabel;
    private final Label nextTimeLabel;
    private final Label freqLabel;
    private final Label categoryLabel;
    private final List<Label> tagLabels;

    public AutoExpenseCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        descLabel = getChildNode(DESC_FIELD_ID);
        amountLabel = getChildNode(AMOUNT_FIELD_ID);
        nextTimeLabel = getChildNode(NEXT_TIME_FIELD_ID);
        freqLabel = getChildNode(FREQ_FIELD_ID);
        categoryLabel = getChildNode(CATEGORY_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDesc() {
        return descLabel.getText();
    }

    public String getAmount() {
        return amountLabel.getText();
    }

    public String getNextTime() {
        return nextTimeLabel.getText();
    }

    public String getFrequency() {
        return freqLabel.getText();
    }

    public String getCategory() {
        return categoryLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code autoExpense}.
     */
    public boolean equals(AutoExpense autoExpense) {
        return getDesc().equals(autoExpense.getDesc().fullDesc)
                && getAmount().equals(autoExpense.getAmount().toString())
                && getNextTime().equals(autoExpense.getNextTime().toString())
                && getFrequency().equals(autoExpense.getFrequency().toString())
                && getCategory().equals(autoExpense.getCategory().toString())
                && getTags().equals(autoExpense.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
