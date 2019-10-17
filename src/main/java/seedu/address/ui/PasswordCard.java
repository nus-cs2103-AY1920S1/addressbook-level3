package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.password.Password;


/**
 * An UI component that displays information of a {@code Password}.
 */
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
    @FXML
    private FlowPane tags;

    public PasswordCard(Password password, int displayedIndex) {
        super(FXML);
        this.password = password;
        id.setText(displayedIndex + ". ");
        description.setText(password.getDescription().value);
        username.setText(password.getUsername().value);
        passwordValue.setText(password.getPasswordValue().toString());
        password.getTags().stream()
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
        if (!(other instanceof PasswordCard)) {
            return false;
        }

        // state check
        PasswordCard card = (PasswordCard) other;
        return id.getText().equals(card.id.getText())
                && password.equals(card.password);
    }
}
