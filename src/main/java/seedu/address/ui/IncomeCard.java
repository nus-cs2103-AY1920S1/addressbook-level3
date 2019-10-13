package seedu.address.ui;

import java.util.Comparator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.income.Income;

/**
 * An UI component that displays information of a {@code FinSec}.
 */
public class IncomeCard extends UiPart<Region> {

    private static final String FXML = "IncomeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on FinSec level 4</a>
     */

    public final Income income;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    //displayed index
    @FXML
    private Label description;
    @FXML
    private Label amount;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private FlowPane tags;

    public IncomeCard(Income income, int displayedIndex) {
        super(FXML);
        this.income = income;
        id.setText(displayedIndex + ". ");
        description.setText(income.getDescription().text);
        amount.setText(income.getAmount().value);
        name.setText(income.getName().fullName);
        phone.setText(income.getPhone().value);
        income.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        IncomeCard card = (IncomeCard) other;
        return id.getText().equals(card.id.getText())
                && income.equals(card.income);
    }
}
