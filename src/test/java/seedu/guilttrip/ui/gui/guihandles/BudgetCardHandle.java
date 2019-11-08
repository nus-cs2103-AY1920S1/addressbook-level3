package seedu.guilttrip.ui.gui.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.guilttrip.model.entry.Budget;

/**
 * Provides a handle to a budget card in the budget list panel.
 */
public class BudgetCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String DESC_FIELD_ID = "#desc";
    private static final String SPENT_AMOUNT_FIELD_ID = "#spent";
    private static final String TOTAL_AMOUNT_FIELD_ID = "#total";
    private static final String DATE_FIELD_ID = "#date";
    private static final String CATEGORY_FIELD_ID = "#category";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label descLabel;
    private final Label amountSpentLabel;
    private final Label totalAmountLabel;
    private final Label dateLabel;
    private final Label categoryLabel;
    private final List<Label> tagLabels;

    public BudgetCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        descLabel = getChildNode(DESC_FIELD_ID);
        amountSpentLabel = getChildNode(SPENT_AMOUNT_FIELD_ID);
        totalAmountLabel = getChildNode(TOTAL_AMOUNT_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
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

    public String getAmountSpent() {
        return amountSpentLabel.getText();
    }

    public String getTotalAmount() {
        return totalAmountLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
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
     * Returns true if this handle contains {@code budget}.
     */
    public boolean equals(Budget budget) {
        return getDesc().equals(budget.getDesc().fullDesc)
                && getAmountSpent().equals(budget.getSpent().toString())
                && getTotalAmount().equals(budget.getAmount().toString())
                && getDate().equals(budget.getDate().toString())
                && getCategory().equals(budget.getCategory().toString())
                && getTags().equals(budget.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
