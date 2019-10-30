package seedu.address.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.allocate.DeallocateCommand;
import seedu.address.logic.commands.allocate.ManualAllocateCommand;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

import java.util.Comparator;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;


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

    public EmployeeCard1(Employee employee, int displayedIndex) {
        super(FXML);
        this.employee = employee;
        id.setText(displayedIndex + ". ");
        name.setText(employee.getEmployeeName().fullName);
        phone.setText("Totally Paid : " + employee.getEmployeePay().value);
        address.setText(employee.getEmployeeAddress().value);
        email.setText(employee.getEmployeeEmail().value);
        employee.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    public EmployeeCard1(Employee employee, int displayedIndex, String linkToFxml) {
        super(linkToFxml);
        this.employee = employee;
        id.setText(displayedIndex + ". ");
        name.setText(employee.getEmployeeName().fullName + " ID: " + employee.getEmployeeId().id); //for debug
        phone.setText("Totally Paid : " + employee.getEmployeePay().value);
        address.setText(employee.getEmployeeAddress().value);
        email.setText(employee.getEmployeeEmail().value);
        employee.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }


    public EmployeeCard1(Employee employee, int displayedIndex, Logic logic, Event event, int eventOneBasedIndex,
                         FetchWindow fetchWindow, boolean isAllocate) {
        this(employee, displayedIndex, FETCH_WINDOW_FXML);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        try {
                            if (isAllocate) {
                                logic.execute(ManualAllocateCommand.COMMAND_WORD + " " + eventOneBasedIndex
                                        + " " + PREFIX_EMPLOYEE_ID + employee.getEmployeeId());
                            } else {
                                logic.execute(DeallocateCommand.COMMAND_WORD + " " + eventOneBasedIndex
                                        + " " + PREFIX_EMPLOYEE_ID + employee.getEmployeeId());

                            }
                            fetchWindow.updateCards();
                        } catch (Exception e) {
                            if (errorWindow != null) {
                                errorWindow.hide();
                            }
                            errorWindow = new ErrorWindow(e.getMessage());
                            errorWindow.show();
                        }
                    }
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
        if (!(other instanceof EmployeeCard1)) {
            return false;
        }

        // state check
        EmployeeCard1 card = (EmployeeCard1) other;
        return id.getText().equals(card.id.getText())
                && employee.equals(card.employee);
    }
}
