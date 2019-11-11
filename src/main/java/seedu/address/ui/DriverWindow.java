package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Schedule;

/**
 * Controller for a customer window.
 */
public class DriverWindow extends UiPart<Stage> {

    public static final String DEFAULT_MESSAGE = "Driver Window";

    private static final Logger logger = LogsCenter.getLogger(DriverWindow.class);
    private static final String FXML = "DriverWindow.fxml";

    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label driverId;
    @FXML
    private Label availability;
    @FXML
    private Label rating;

    /**
     * Creates a new DriverWindow.
     *
     * @param root Stage to use as the root of the DriverWindow.
     */
    public DriverWindow(Stage root) {
        super(FXML, root);
        name.setText(DEFAULT_MESSAGE);
        phone.setText(DEFAULT_MESSAGE);
        driverId.setText(DEFAULT_MESSAGE);
        availability.setText(DEFAULT_MESSAGE);
        rating.setText(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new DriverWindow.
     */
    public DriverWindow() {
        this(new Stage());
    }

    /**
     * Shows the driver window.
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
        logger.fine("Showing DriverWindow.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the Driver window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the Driver window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the Driver window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Populate fields with relevant details.
     */
    public void fillFields(Driver driver) {
        name.setText(driver.getName().fullName);
        phone.setText("Phone: " + driver.getPhone().value);
        driverId.setText("Driver ID: #" + driver.getId());
        if (driver.getRating() == 0) {
            rating.setText("Driver Rating: Not rated yet");
        } else {
            rating.setText("Driver Rating: " + driver.getRating() + "/5");
        }
        if (driver.getSchedule().toString().equals(Schedule.MESSAGE_EMPTY_SCHEDULE)) {
            availability.setText("Unavailable Time: Available all times");
        } else {
            availability.setText("Unavailable Time: " + driver.getSchedule());
        }
    }

}
