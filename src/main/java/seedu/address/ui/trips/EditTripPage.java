package seedu.address.ui.trips;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.trips.edit.CancelEditTripCommand;
import seedu.address.logic.commands.trips.edit.DoneEditTripCommand;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.DateFormItem;
import seedu.address.ui.components.form.DoubleFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;

/**
 * {@code Page} class for displaying the edit trip page.
 */
public class EditTripPage extends Page<AnchorPane> {

    private static final String FXML = "trips/EditTripPage.fxml";

    private TextFormItem tripNameFormItem;
    private TextFormItem tripDestinationFormItem;
    private DateFormItem tripStartDateFormItem;
    private DateFormItem tripEndDateFormItem;
    private DoubleFormItem tripTotalBudgetFormItem;

    @FXML
    private VBox formItemsPlaceholder;

    @FXML
    private Button addButton;

    public EditTripPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        initFormWithModel();
    }

    /**
     * Fills up all the controls of this window.
     */
    public void fillPage() {
        EditTripFieldCommand.EditTripDescriptor currentEditDescriptor =
                model.getPageStatus().getEditTripDescriptor();
        if (currentEditDescriptor == null) {
            return;
        }

        currentEditDescriptor.getName().ifPresent(name ->
                tripNameFormItem.setValue(name.toString()));
        currentEditDescriptor.getDestination().ifPresent(destination ->
                tripDestinationFormItem.setValue(destination.toString()));
        currentEditDescriptor.getStartDate().ifPresent(startDate ->
                tripStartDateFormItem.setValue(startDate.toLocalDate()));
        currentEditDescriptor.getEndDate().ifPresent(endDate ->
                tripEndDateFormItem.setValue(endDate.toLocalDate()));
        currentEditDescriptor.getBudget().ifPresent(budget ->
                tripTotalBudgetFormItem.setValue(budget.value));
    }

    /**
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        //Initialise with new display data
        tripNameFormItem = new TextFormItem("Name of trip : ", nameFormValue -> {
            mainWindow.executeGuiCommand(
                    EditTripFieldCommand.COMMAND_WORD
                            + " " + PREFIX_NAME + nameFormValue);
        });
        tripStartDateFormItem = new DateFormItem("Start date : ", startDate -> {
            mainWindow.executeGuiCommand(EditTripFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DATE_START
                    + ParserDateUtil.getStringFromDate(startDate.atStartOfDay()));
        });
        tripEndDateFormItem = new DateFormItem("End date : ", endDate -> {
            mainWindow.executeGuiCommand(EditTripFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DATE_END
                    + ParserDateUtil.getStringFromDate(endDate.atStartOfDay()));
        });
        tripTotalBudgetFormItem = new DoubleFormItem("Total budget : ", totalBudget -> {
            mainWindow.executeGuiCommand(EditTripFieldCommand.COMMAND_WORD
                    + " " + PREFIX_BUDGET + totalBudget);
        });
        tripDestinationFormItem = new TextFormItem("Destination : ", destinationValue -> {
            mainWindow.executeGuiCommand(EditTripFieldCommand.COMMAND_WORD
                    + " " + PREFIX_LOCATION + destinationValue);
        });

        fillPage(); //update and overwrite with existing edit descriptor

        formItemsPlaceholder.getChildren().addAll(
                tripNameFormItem.getRoot(),
                tripStartDateFormItem.getRoot(),
                tripEndDateFormItem.getRoot(),
                tripTotalBudgetFormItem.getRoot(),
                tripDestinationFormItem.getRoot());
    }

    @FXML
    private void handleEditTripDone() {
        String commandText = DoneEditTripCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }

    @FXML
    private void handleEditCancel() {
        String commandText = CancelEditTripCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }
}
