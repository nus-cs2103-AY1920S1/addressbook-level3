package seedu.address.ui.trips;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.ui.components.CommandBox;
import seedu.address.ui.components.ResultDisplay;
import seedu.address.ui.components.StatusBarFooter;
import seedu.address.ui.components.form.DateFormItem;
import seedu.address.ui.components.form.ExpenditureFormItem;
import seedu.address.ui.components.form.FormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.WindowWithoutSidebar;

import java.time.LocalDate;

public class EditTripPage extends WindowWithoutSidebar {

    private static final String FXML = "EditTripPage.fxml";

    private static final int DEFAULT_TRIP_DURATION = 7;
    TextFormItem tripNameFormItem = null;
    TextFormItem tripDestinationFormItem = null;
    DateFormItem tripStartDateFormItem = null;
    DateFormItem tripEndDateFormItem = null;
    ExpenditureFormItem tripTotalBudgetFormItem = null;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox formItemsPlaceholder;

    @FXML
    private Button addButton;

    public EditTripPage(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    protected void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(model.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        if (model.getPageStatus().getTrip() == null) {
            //create new trip, use fresh display data
            tripNameFormItem = new TextFormItem("Name of trip : ");
            tripStartDateFormItem = new DateFormItem("Start date : ");
            tripEndDateFormItem = new DateFormItem("End date : ",
                    LocalDate.now().plusDays(DEFAULT_TRIP_DURATION));
            tripTotalBudgetFormItem = new ExpenditureFormItem("Total budget : ");
            tripDestinationFormItem = new TextFormItem("Destination : ");
        } else {
            //edit a trip, use existing edit descriptor
            fillFormWithModel();
        }

        formItemsPlaceholder.getChildren().addAll(
                tripNameFormItem.getRoot(),
                tripStartDateFormItem.getRoot(),
                tripEndDateFormItem.getRoot(),
                tripTotalBudgetFormItem.getRoot(),
                tripDestinationFormItem.getRoot());
    }

    @FXML
    private void handleEditTripDone() {
        //handle add
    }

    private void fillFormWithModel() {
        EditTripFieldCommand.EditTripDescriptor currentEditDescriptor
                = model.getPageStatus().getEditTripDescriptor();

        if (currentEditDescriptor.getName() != null) {
            tripNameFormItem.setValue(currentEditDescriptor.getName().toString());
        }
        if (currentEditDescriptor.getDestination() != null) {
            tripDestinationFormItem.setValue(currentEditDescriptor.getDestination().toString());
        }
        if (currentEditDescriptor.getStartDate() != null) {
            tripStartDateFormItem.setValue(currentEditDescriptor.getStartDate().toLocalDate());
        }
        if (currentEditDescriptor.getEndDate() != null) {
            tripEndDateFormItem.setValue(currentEditDescriptor.getEndDate().get().toLocalDate());
        }
        if (currentEditDescriptor.getBudget() != null) {
            tripTotalBudgetFormItem.setValue(currentEditDescriptor.getBudget());
        }
    }

    @Override
    protected CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        CommandResult executionResult = super.executeCommand(commandText);
        fillFormWithModel();
        return executionResult;
    }

    public static void switchTo(Stage stage, Logic logic, Model model) {
        EditTripPage p = new EditTripPage(stage, logic, model);
        p.show();
        p.fillInnerParts();
    }

}
