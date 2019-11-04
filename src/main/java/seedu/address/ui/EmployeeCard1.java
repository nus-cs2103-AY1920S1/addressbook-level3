package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeePay;

/**
 * An UI component that displays information of a {@code Employee}.
 */
public class EmployeeCard1 extends UiPart<Region> {

    private static final String FXML = "EmployeeListCard.fxml";
    private static final String FETCH_WINDOW_FXML = "EmployeeListCardForFetch.fxml";

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
    private ErrorWindow errorWindow;

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

    public EmployeeCard1(Employee employee, int displayedIndex) {
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
        phone.setText("Totally Paid : $" + employee.getEmployeeSalaryPaid());
        address.setText("Total Salary : $" + employee.getEmployeePay());
        double pendingpay  = Double.parseDouble(employee.getEmployeePay().value)
                - employee.getEmployeeSalaryPaid().value;
        email.setText("Pending to Pay : $" + pendingpay);
        employee.getTags().stream()
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
        if (!(other instanceof seedu.address.ui.EmployeeCard)) {
            return false;
        }

        // state check
        seedu.address.ui.EmployeeCard1 card = (seedu.address.ui.EmployeeCard1) other;
        return id.getText().equals(card.id.getText())
                && employee.equals(card.employee);
    }
}
