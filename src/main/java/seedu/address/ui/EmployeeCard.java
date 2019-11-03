package seedu.address.ui;

import java.util.Comparator;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.employee.Employee;


/**
 * An UI component that displays information of a {@code Employee}.
 */
public class EmployeeCard extends UiPart<Region> {

    private static final String FXML = "EmployeeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Employee employee;
    private MainWindow mainWindow;
    private Integer index;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView imgBox;

    /**
     * Default constructor for EmployeeCard.
     */
    public EmployeeCard(Employee employee, int displayedIndex) {
        super(FXML);
        this.employee = employee;
        if (employee.getEmployeeGender().gender.equals("male")) {
            Image image = new Image("/images/maleEmployee.png");
            imgBox.setImage(image);
        } else {
            Image image = new Image("/images/femaleEmployee.png");
            imgBox.setImage(image);
        }
        id.setText(displayedIndex + ". ");
        name.setText(employee.getEmployeeName().fullName);
        phone.setText(employee.getEmployeePhone().value);
        address.setText(employee.getEmployeeAddress().value);
        email.setText(employee.getEmployeeEmail().value);
        employee.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }


    /**
     * Constructor for EmployeeCard used in Main Tab which uses the default constructor.
     */
    EmployeeCard(Employee employee, int displayedIndex, MainWindow mainWindow) {
        this(employee, displayedIndex);
        this.mainWindow = mainWindow;
        this.index = displayedIndex - 1;

        EventHandler<MouseEvent> eventHandler = mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    mainWindow.handleEmployeeFetch(index);
                }
            }
        };
        cardPane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmployeeCard)) {
            return false;
        }

        // state check
        EmployeeCard card = (EmployeeCard) other;
        return id.getText().equals(card.id.getText())
                && employee.equals(card.employee);
    }
}
