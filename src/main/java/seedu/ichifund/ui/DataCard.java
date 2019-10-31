package seedu.ichifund.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.analytics.exceptions.FieldNotFoundException;

/**
 * An UI component that displays information of {@code Data}.
 */
public class DataCard extends UiPart<Region> {

    private static final String FXML = "DataCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Data data;

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label year;
    @FXML
    private Label month;
    @FXML
    private Label day;
    @FXML
    private Label category;

    public DataCard(Data data, int displayedIndex) {
        super(FXML);
        this.data = data;
        id.setText(displayedIndex + ". ");
        description.setText(data.getDescription());
        amount.setText(data.getAmount().toString());
        try {
            data.getCategory();
            category.setText("");
        } catch (FieldNotFoundException e) {
            category.setText("All categories");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DataCard)) {
            return false;
        }

        // state check
        DataCard card = (DataCard) other;
        return id.getText().equals(card.id.getText())
                && amount.equals(card.amount)
                && year.equals(card.year)
                && month.equals(card.month)
                && day.equals(card.day)
                && category.equals(card.category);
    }
}
