package seedu.address.ui.itinerary;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * {@code Page} class implementing the edit day page.
 */
public class EditDayPage extends Page<AnchorPane> {

    private static final String FXML = "itinerary/days/EditDayPage.fxml";
    private TextFormItem dayNameFormItem;
    private TextFormItem dayDestinationFormItem;
    private DateFormItem dayDateFormItem;
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
                dayDateFormItem.setValue(startDate.toLocalDate()));
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
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                            + " " + PREFIX_NAME + nameFormValue);
        });
        dayDateFormItem = new DateFormItem("Date : ", date -> {
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DATE_START
                    + ParserDateUtil.getStringFromDate(date.atStartOfDay()));
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DATE_END
                    + ParserDateUtil.getStringFromDate(date.atTime(23, 59)));
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
        formItemsPlaceholder.getChildren().add(new Label("Edit Day"));

        formItemsPlaceholder.getChildren().addAll(
                dayNameFormItem.getRoot(),
                dayDateFormItem.getRoot(),
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
