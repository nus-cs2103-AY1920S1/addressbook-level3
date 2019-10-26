package thrift.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import thrift.model.transaction.Transaction;

/**
 * An UI component that displays information of a {@code Income}.
 */
public class IncomeTransactionCard extends UiPart<Region> {

    private static final String FXML = "IncomeTransactionCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Thrift level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label incomeDescription;
    @FXML
    private Label id;
    @FXML
    private Label incomeValue;
    @FXML
    private Label incomeDate;
    @FXML
    private Label incomeRemark;
    @FXML
    private FlowPane tags;

    public IncomeTransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");

        incomeDescription.setText(transaction.getDescription().toString());
        incomeValue.setText("$" + transaction.getValue().toString());
        incomeDate.setText(transaction.getDate().toString());
        incomeRemark.setText("Remarks: " + transaction.getRemark().toString());
        transaction.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        incomeDescription.setWrapText(true);
        incomeValue.setWrapText(true);
        incomeRemark.setWrapText(true);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IncomeTransactionCard)) {
            return false;
        }

        // state check
        IncomeTransactionCard card = (IncomeTransactionCard) other;
        return id.getText().equals(card.id.getText())
                && transaction.equals(card.transaction);
    }
}
