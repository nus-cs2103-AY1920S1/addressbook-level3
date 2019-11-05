package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a login page
 */
public class LoginWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(LoginWindow.class);
    private static final String FXML = "LoginWindow.fxml";

    @FXML
    private Label loginStatus;

    @FXML
    private TextField userName;

    @FXML
    private TextField passWord;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public LoginWindow(Stage root) {
        super(FXML, root);
        root.sizeToScene();
    }

    /**
     * Creates a new HelpWindow.
     */
    public LoginWindow() {
        this(new Stage());
    }
}
