package seedu.guilttrip.ui.income;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.ui.UiPart;

/**
 * An UI component that displays information of a {@code Income}.
 */
public class IncomeCard extends UiPart<Region> {

    private static final String FXML = "income/IncomeCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GuiltTrip level 4</a>
     */

    public final Income income;

    @FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label amt;
    @FXML
    private Label category;
    @FXML
    private FlowPane tags;

    public IncomeCard(Income income, int displayedIndex) {
        super(FXML);
        this.income = income;
        id.setText(displayedIndex + ". ");

        String descWithType = income.getDesc().fullDesc;
        desc.setText(descWithType);
        date.setText(income.getDate().toString());
        amt.setText("$" + income.getAmount().value);
        category.setText(income.getCategory().getCategoryName());

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
        if (!(other instanceof IncomeCard)) {
            return false;
        }

        // state check
        IncomeCard card = (IncomeCard) other;
        return id.getText().equals(card.id.getText())
                && income.equals(card.income);
    }
}
