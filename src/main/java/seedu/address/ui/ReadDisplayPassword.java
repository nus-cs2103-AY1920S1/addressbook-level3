package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.model.password.ExpiryMode;
import seedu.address.model.password.Password;

/**
 * A Ui for the displaying password that is displayed when read command is called.
 */
public class ReadDisplayPassword extends UiPart<Region> {
    private static final String FXML = "ReadDisplayPassword.fxml";
    private Logic logic;

    @FXML
    private Label description;
    @FXML
    private TextArea username;
    @FXML
    private TextArea passwordValue;
    @FXML
    private FlowPane tags;
    @FXML
    private TextArea website;
    @FXML
    private Label lastModified;
    @FXML
    private Label status;
    @FXML
    private Label statusLabel;

    public ReadDisplayPassword() {
        super(FXML);
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    /**
     *
     * @param password
     */
    public void setFeedbackToUser(Password password, Index index) {
        requireNonNull(password);
        description.setText(index.getOneBased() + ". " + password.getPasswordDescription().value);
        username.setText(password.getUsername().value);
        passwordValue.setText(password.getPasswordValue().toString());
        lastModified.setText(password.getPasswordModifiedAt().toString());
        website.setText(password.getWebsite().value);
        password.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        ExpiryMode exp = password.getExpiryMode();
        switch (exp) {
        case EXPIRED:
            status.setText("WARNING! Password expired. Change your password");
            break;
        case EXPIRING:
            status.setText("Password expiring soon.");
            break;
        case HEALTHY:
            status.setVisible(false);
            statusLabel.setVisible(false);
            break;
        default:
            status.setText("Error");
        }

    }
}
