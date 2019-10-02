package seedu.address.ui.trips;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.trips.edit.CancelEditTripCommand;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.ui.components.CommandBox;
import seedu.address.ui.components.ResultDisplay;
import seedu.address.ui.components.StatusBarFooter;
import seedu.address.ui.components.form.DateFormItem;
import seedu.address.ui.components.form.ExpenditureFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.WindowWithoutSidebar;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

public class EditTripPage extends WindowWithoutSidebar {

    private static final String FXML = "EditTripPage.fxml";

    private TextFormItem tripNameFormItem;
    private TextFormItem tripDestinationFormItem;
    private DateFormItem tripStartDateFormItem;
    private DateFormItem tripEndDateFormItem;
    private ExpenditureFormItem tripTotalBudgetFormItem;

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

        //create new trip, use fresh display data
        tripNameFormItem = new TextFormItem("Name of trip : ", nameFormValue -> {
            try {
                executeCommand(EditTripFieldCommand.COMMAND_WORD + " " + PREFIX_NAME + nameFormValue);
            } catch (CommandException | ParseException e) {
                e.printStackTrace();
            }
        });
        tripStartDateFormItem = new DateFormItem("Start date : ",
                startDate -> {
                    try {
                        executeCommand(EditTripFieldCommand.COMMAND_WORD
                                + " " + PREFIX_DATE_START
                                + ParserDateUtil.getStringFromDate(startDate.atStartOfDay()));
                    } catch (CommandException | ParseException e) {
                        e.printStackTrace();
                    }
                });
        tripEndDateFormItem = new DateFormItem("End date : ",
                endDate -> {
                    try {
                        executeCommand(EditTripFieldCommand.COMMAND_WORD
                                + " " + PREFIX_DATE_END
                                + ParserDateUtil.getStringFromDate(endDate.atStartOfDay()));
                    } catch (CommandException | ParseException e) {
                        e.printStackTrace();
                    }
                });
        tripTotalBudgetFormItem = new ExpenditureFormItem("Total budget : ", totalBudget -> {
            try {
                executeCommand(EditTripFieldCommand.COMMAND_WORD + " " + PREFIX_BUDGET + totalBudget);
            } catch (CommandException | ParseException e) {
                e.printStackTrace();
            }
        });
        tripDestinationFormItem = new TextFormItem("Destination : ", destinationValue -> {
            try {
                executeCommand(EditTripFieldCommand.COMMAND_WORD + " " + PREFIX_LOCATION + destinationValue);
            } catch (CommandException | ParseException e) {
                e.printStackTrace();
            }
        });

        //update with existing edit descriptor
        fillFormWithModel();

        formItemsPlaceholder.getChildren().addAll(
                tripNameFormItem.getRoot(),
                tripStartDateFormItem.getRoot(),
                tripEndDateFormItem.getRoot(),
                tripTotalBudgetFormItem.getRoot(),
                tripDestinationFormItem.getRoot());
    }

    @FXML
    private void handleEditTripDone() {
        //handle edit done

    }

    @FXML
    private void handleEditCancel() {
        //handle cancel
        String commandText = CancelEditTripCommand.COMMAND_WORD;

        try {
            executeCommand(commandText);
        } catch (ParseException | CommandException ex) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(ex.getMessage());
        }
    }

    private void fillFormWithModel() {
        EditTripFieldCommand.EditTripDescriptor currentEditDescriptor
                = model.getPageStatus().getEditTripDescriptor();
        if (currentEditDescriptor == null) {
            return;
        }
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
            tripEndDateFormItem.setValue(currentEditDescriptor.getEndDate().toLocalDate());
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
        new EditTripPage(stage, logic, model);
    }

}
