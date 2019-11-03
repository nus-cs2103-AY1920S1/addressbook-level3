package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;

import java.util.Comparator;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.allocate.DeallocateCommand;
import seedu.address.logic.commands.allocate.ManualAllocateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Employee;


/**
 * An UI component that displays information of a {@code Employee} in the {@code FetchEventWindow}.
 */
public class FetchEventEmployeeCard extends UiPart<Region> {

    private static final String FXML = "EmployeeListCardForFetch.fxml";

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
    @FXML
    private ImageView imgBox;

    /**
     * Default constructor for EmployeeCard used in {@code FetchEventWindow}.
     */
    private FetchEventEmployeeCard(Employee employee, int displayedIndex) {
        super(FXML);
        this.employee = employee;
        id.setText(displayedIndex + ". ");
        name.setText(employee.getEmployeeName().fullName + " ID: " + employee.getEmployeeId().id);
        phone.setText(employee.getEmployeePhone().value);
        address.setText(employee.getEmployeeAddress().value);
        email.setText(employee.getEmployeeEmail().value);
        employee.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

    }

    /**
     * Constructor for EmployeeCard used in {@code FetchEventWindow} with Mouse Event Handler.
     */
    FetchEventEmployeeCard(Employee employee, int displayedIndex, Logic logic, int eventOneBasedIndex,
                           FetchEventWindow fetchWindow, boolean isAllocate) {
        this(employee, displayedIndex);

        String manualAllocateCommandText = ManualAllocateCommand.COMMAND_WORD + " " + eventOneBasedIndex
                + " " + PREFIX_EMPLOYEE_ID + employee.getEmployeeId();
        String deallocateCommandText = DeallocateCommand.COMMAND_WORD + " " + eventOneBasedIndex
                + " " + PREFIX_EMPLOYEE_ID + employee.getEmployeeId();

        EventHandler<MouseEvent> eventHandler = mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                try {
                    if (isAllocate) {
                        logic.execute(manualAllocateCommandText);
                    } else {
                        logic.execute(deallocateCommandText);
                    }
                    fetchWindow.updateCards();

                } catch (ParseException | CommandException e) {
                    if (errorWindow != null) {
                        errorWindow.hide();
                    }
                    errorWindow = new ErrorWindow(e.getMessage());
                    errorWindow.show();
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
        if (!(other instanceof FetchEventEmployeeCard)) {
            return false;
        }

        // state check
        FetchEventEmployeeCard card = (FetchEventEmployeeCard) other;
        return id.getText().equals(card.id.getText())
                && employee.equals(card.employee);
    }
}
