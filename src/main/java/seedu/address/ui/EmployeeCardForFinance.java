package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.processor.EmployeeEventProcessor;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of a {@code Employee}.
 */
public class EmployeeCardForFinance extends UiPart<Region> {

    private static final String FXML = "EmployeeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Employee employee;

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

    public EmployeeCardForFinance(Employee employee, int displayedIndex, ObservableList<Event> eventList) {
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
        phone.setText("Already Paid : $" + employee.getEmployeeSalaryPaid());

        double totalHours = EmployeeEventProcessor.findEmployeeTotalWorkedHours(employee, eventList);
        double totalSalary = totalHours * employee.getEmployeePay().getPay();
        address.setText("Total Salary : $" + totalSalary);
        double pendingpay = totalSalary - employee.getEmployeeSalaryPaid().getValue();
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
        if (!(other instanceof EmployeeCardForFinance)) {
            return false;
        }

        // state check
        EmployeeCardForFinance card = (EmployeeCardForFinance) other;
        return id.getText().equals(card.id.getText())
                && employee.equals(card.employee);
    }
}
