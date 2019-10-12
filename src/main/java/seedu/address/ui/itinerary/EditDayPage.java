package seedu.address.ui.itinerary;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.itinerary.days.edit.CancelEditDayCommand;
import seedu.address.logic.commands.itinerary.days.edit.DoneEditDayCommand;
import seedu.address.logic.commands.itinerary.days.edit.EditDayFieldCommand;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.DateFormItem;
import seedu.address.ui.components.form.DoubleFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;

/**
 * {@code Page} class implementing the edit day page.
 */
public class EditDayPage extends Page<AnchorPane> {

    private static final String FXML = "itinerary/days/EditDayPage.fxml";
    private TextFormItem dayNameFormItem;
    private TextFormItem dayDestinationFormItem;
    private DateFormItem dayStartDateFormItem;
    private DateFormItem dayEndDateFormItem;
    private DoubleFormItem dayTotalBudgetFormItem;
    private TextFormItem dayDescriptionFormItem;

    @FXML
    private VBox formItemsPlaceholder;

    @FXML
    private Button addButton;

    public EditDayPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        initFormWithModel();
    }

    /**
     * Fills the form items with model data.
     */
    public void fillPage() {
        EditDayFieldCommand.EditDayDescriptor currentEditDescriptor =
                model.getPageStatus().getEditDayDescriptor();

        if (currentEditDescriptor == null) {
            return;
        }

        currentEditDescriptor.getName().ifPresent(name ->
                dayNameFormItem.setValue(name.toString()));
        currentEditDescriptor.getDestination().ifPresent(destination ->
                dayDestinationFormItem.setValue(destination.toString()));
        currentEditDescriptor.getStartDate().ifPresent(startDate ->
                dayStartDateFormItem.setValue(startDate.toLocalDate()));
        currentEditDescriptor.getEndDate().ifPresent(endDate ->
                dayEndDateFormItem.setValue(endDate.toLocalDate()));
        currentEditDescriptor.getBudget().ifPresent(budget ->
                dayTotalBudgetFormItem.setValue(budget.value));
        currentEditDescriptor.getDescription().ifPresent((description ->
                dayDescriptionFormItem.setValue(description.description)));
    }

    /**
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        //Initialise with new display data
        dayNameFormItem = new TextFormItem("Name of Day : ", nameFormValue -> {
            mainWindow.executeGuiCommand(
                    EditDayFieldCommand.COMMAND_WORD
                            + " " + PREFIX_NAME + nameFormValue);
        });
        dayStartDateFormItem = new DateFormItem("Start date : ", startDate -> {
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DATE_START
                    + ParserDateUtil.getStringFromDate(startDate.atStartOfDay()));
        });
        dayEndDateFormItem = new DateFormItem("End date : ", endDate -> {
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DATE_END
                    + ParserDateUtil.getStringFromDate(endDate.atStartOfDay()));
        });
        dayTotalBudgetFormItem = new DoubleFormItem("Total budget : ", totalBudget -> {
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                    + " " + PREFIX_BUDGET + totalBudget);
        });
        dayDestinationFormItem = new TextFormItem("Destination : ", destinationValue -> {
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                    + " " + PREFIX_LOCATION + destinationValue);
        });
        dayDescriptionFormItem = new TextFormItem("Description : ", description -> {
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DESCRIPTION + description);
        });

        fillPage(); //update and overwrite with existing edit descriptor

        formItemsPlaceholder.getChildren().addAll(
                dayNameFormItem.getRoot(),
                dayStartDateFormItem.getRoot(),
                dayEndDateFormItem.getRoot(),
                dayTotalBudgetFormItem.getRoot(),
                dayDestinationFormItem.getRoot(),
                dayDescriptionFormItem.getRoot());
    }

    @FXML
    private void handleEditDayDone() {
        String commandText = DoneEditDayCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }

    @FXML
    private void handleEditCancel() {
        String commandText = CancelEditDayCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }
}
