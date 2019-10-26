package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.model.password.Password;

import static java.util.Objects.requireNonNull;

public class ReadDisplayPassword extends UiPart<Region> {
    private static final String FXML = "ReadDisplayPassword.fxml";

    @FXML
    private TextArea readDisplayPassword;

    public ReadDisplayPassword() {
        super(FXML);
    }

    public void setFeedbackToUser(Password password) {
        requireNonNull(password);
        readDisplayPassword.setText(password.getPasswordValue().value);
    }
}
