package seedu.guilttrip.ui.gui.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.guilttrip.model.entry.Expense;

/**
 * Provides a handle to a expense card in the expense list panel.
 */
public class ExpenseCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String DESC_FIELD_ID = "#desc";
    private static final String AMOUNT_FIELD_ID = "#amount";
    private static final String DATE_FIELD_ID = "#date";
    private static final String CATEGORY_FIELD_ID = "#category";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label descLabel;
    private final Label amountLabel;
    private final Label dateLabel;
    private final Label categoryLabel;
    private final List<Label> tagLabels;

    public ExpenseCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        descLabel = getChildNode(DESC_FIELD_ID);
        amountLabel = getChildNode(AMOUNT_FIELD_ID);
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

    public String getAmount() {
        return amountLabel.getText();
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
     * Returns true if this handle contains {@code expense}.
     */
    public boolean equals(Expense expense) {
        return getDesc().equals(expense.getDesc().fullDesc)
                && getAmount().equals(expense.getAmount().toString())
                && getDate().equals(expense.getDate().toString())
                && getCategory().equals(expense.getCategory().toString())
                && getTags().equals(expense.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
