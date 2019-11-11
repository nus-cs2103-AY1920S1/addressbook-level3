package seedu.address.ui;

import static seedu.address.ui.Map.WEBVIEW_WRAPPER;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Customer;

/**
 * Controller for a customer window
 */
public class CustomerWindow extends UiPart<Stage> {

    public static final String DEFAULT_MESSAGE = "Customer Window";

    private static final Logger logger = LogsCenter.getLogger(CustomerWindow.class);
    private static final String FXML = "CustomerWindow.fxml";

    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label customerId;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private WebView map;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public CustomerWindow(Stage root) {
        super(FXML, root);
        name.setText(DEFAULT_MESSAGE);
        phone.setText(DEFAULT_MESSAGE);
        customerId.setText(DEFAULT_MESSAGE);
        email.setText(DEFAULT_MESSAGE);
        address.setText(DEFAULT_MESSAGE);
        map.getEngine().loadContent(String.format(WEBVIEW_WRAPPER, ""));
    }

    /**
     * Creates a new Customer Window.
     */
    public CustomerWindow() {
        this(new Stage());
    }

    /**
     * Shows the Customer window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing customer window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the customer window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the customer window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the customer window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Populate fields with relevant details.
     */
    public void fillFields(Customer customer) {
        name.setText(customer.getName().fullName);
        phone.setText("Phone: " + customer.getPhone().value);
        customerId.setText("Customer ID: #" + customer.getId());
        email.setText("Email: " + customer.getEmail());
        address.setText("Address: " + customer.getAddress());
        // Map
        map.getEngine().loadContent(String.format(WEBVIEW_WRAPPER,
                Map.getAddressString(customer.getAddress().value)));
    }
}
