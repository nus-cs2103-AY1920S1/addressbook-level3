package budgetbuddy.ui.card;

import budgetbuddy.model.account.Account;

import budgetbuddy.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Account}.
 */
public class AccountCard extends UiPart<Region> {

    private static final String FXML = "AccountCard.fxml";

    public final Account account;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;

    public AccountCard(Account account, int displayedIndex) {
        super(FXML);
        this.account = account;
        id.setText(displayedIndex + ". ");
        id.setMinWidth(30);
        name.setText(account.getName().toString());
        description.setText(account.getDescription().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AccountCard)) {
            return false;
        }

        // state check
        AccountCard card = (AccountCard) other;
        return id.getText().equals(card.id.getText())
                && account.equals(card.account);
    }
}

