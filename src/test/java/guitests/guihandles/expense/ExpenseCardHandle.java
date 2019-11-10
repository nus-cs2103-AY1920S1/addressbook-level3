package guitests.guihandles.expense;

import static java.time.format.DateTimeFormatter.ofPattern;
import static seedu.moolah.ui.expense.ExpenseCard.CURRENCY_SYMBOL;
import static seedu.moolah.ui.expense.ExpenseCard.DATE_PATTERN;
import static seedu.moolah.ui.expense.ExpenseCard.PRICE_TEMPLATE;
import static seedu.moolah.ui.expense.ExpenseCard.TIME_PATTERN;

import guitests.guihandles.CardHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.moolah.model.expense.Expense;

/**
 * Provides a handle for {@code ExpenseListPanel} containing the list of {@code Expense}.
 *
 * Adapted from with modifications:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/guitests/guihandles/PersonCardHandle.java
 */
public class ExpenseCardHandle extends CardHandle<Expense> {

    private static final String UUID_FX_ID = "#uniqueId";
    private static final String INDEX_FX_ID = "#index";
    private static final String DESCRIPTION_FX_ID = "#description";
    private static final String PRICE_FX_ID = "#price";
    private static final String CATEGORY_FX_ID = "#categories";
    private static final String DATE_FX_ID = "#date";
    private static final String TIME_FX_ID = "#time";

    private final Label uniqueId;
    private final Label index;
    private final Label description;
    private final Label date;
    private final Label time;
    private final Label price;
    private final Label category;

    public ExpenseCardHandle(Node cardNode) {
        super(cardNode);

        uniqueId = getChildNode(UUID_FX_ID);
        index = getChildNode(INDEX_FX_ID);
        date = getChildNode(DATE_FX_ID);
        time = getChildNode(TIME_FX_ID);
        price = getChildNode(PRICE_FX_ID);
        description = getChildNode(DESCRIPTION_FX_ID);
        FlowPane categories = getChildNode(CATEGORY_FX_ID);
        category = (Label) categories.getChildren().get(0);
    }

    public String getIndex() {
        return index.getText();
    }

    // not visible
    public String getUniqueId() {
        return uniqueId.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public String getDate() {
        return date.getText();
    }

    public String getTime() {
        return time.getText();
    }

    public String getPrice() {
        return price.getText();
    }

    public String getCategory() {
        return category.getText();
    }

    @Override
    public boolean wraps(Expense expense) {
        return getUniqueId().equals(expense.getUniqueIdentifier().toString())
                && getDescription().equals(expense.getDescription().fullDescription)
                && getCategory().equals(expense.getCategory().getCategoryName())
                && getDate().equals(expense.getTimestamp().getFullTimestamp().format(ofPattern(DATE_PATTERN)))
                && getTime().equals(expense.getTimestamp().getFullTimestamp().format(ofPattern(TIME_PATTERN)))
                && getPrice().equals(String.format(PRICE_TEMPLATE, CURRENCY_SYMBOL, expense.getPrice().getAsDouble()));
    }
}
