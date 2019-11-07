package budgetbuddy.ui.card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Account}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionCard.fxml";

    public final Transaction transaction;

    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label description;
    @FXML
    private Label direction;
    @FXML
    private FlowPane categories;
    @FXML
    private Label date;


    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");
        amount.setText(transaction.getAmount().toString());
        description.setText(transaction.getDescription().toString());
        amount.setText(transaction.getAmount().toString());
        direction.setText(transaction.getDirection().toString());
        List<Category> toSort = new ArrayList<>(transaction.getCategories());
        toSort.sort(Comparator.comparing(Category::getCategory));
        for (Category category : toSort) {
            categories.getChildren().add(new Label(category.getCategory()));
        }
        date.setText(transaction.getLocalDate().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionCard)) {
            return false;
        }

        // state check
        TransactionCard card = (TransactionCard) other;
        return id.getText().equals(card.id.getText())
                && transaction.equals(card.transaction);
    }
}

