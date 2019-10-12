package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;

import seedu.address.model.password.Password;


public class PasswordCard extends UiPart<Region> {
    private static final String FXML = "PasswordListCard.fxml";

    public final Password password;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label username;
    @FXML
    private Label passwordValue;

    public PasswordCard(Password password, int displayedIndex) {
        super(FXML);
        this.password = password;
        id.setText(displayedIndex + ". ");
        description.setText(password.getDescription().value);
        username.setText(password.getUsername().value);
        passwordValue.setText(password.getPasswordValue().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PasswordCard)) {
            return false;
        }

        // state check
        PasswordCard card = (PasswordCard) other;
        return id.getText().equals(card.id.getText())
                && password.equals(card.password);
    }
}
