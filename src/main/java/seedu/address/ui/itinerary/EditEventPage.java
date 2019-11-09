package seedu.address.ui.itinerary;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.ParserDateUtil.TIME_FORMATTER;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.itinerary.events.edit.CancelEditEventCommand;
import seedu.address.logic.commands.itinerary.events.edit.DoneEditEventCommand;
import seedu.address.logic.commands.itinerary.events.edit.EditEventFieldCommand;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.DoubleFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.components.form.TimeFormItem;
import seedu.address.ui.template.Page;

/**
 * WARNING INCOMEPLETE: TODO: FIELDS FOR INVENTORY AND BOOKING.
 */
public class EditEventPage extends Page<AnchorPane> {
    private static final String FXML = "itinerary/events/EditEventPage.fxml";
    private static final String FORM_ITEM_STYLESHEET = "/view/trips/trips.css";

    private TextFormItem eventNameFormItem;
    private TextFormItem eventDestinationFormItem;
    private TimeFormItem eventStartTimeFormItem;
    private TimeFormItem eventEndTimeFormItem;
    private DoubleFormItem eventTotalBudgetFormItem;
    private TextFormItem eventDescriptionFormItem;
    //private TextFormItem eventInventoryFormItem;
    //private TextFormItem eventBookingFormItem;

    @javafx.fxml.FXML
    private VBox formItemsPlaceholder;

    @FXML
    private Button addButton;

    public EditEventPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        initFormWithModel();
    }

    /**
     * Fills the {@code FormItem}s with the model data.
     */
    public void fillPage() {
        EditEventFieldCommand.EditEventDescriptor currentEditDescriptor =
                model.getPageStatus().getEditEventDescriptor();

        if (currentEditDescriptor == null) {
            return;
        }

        currentEditDescriptor.getName().ifPresent(name ->
                eventNameFormItem.setValue(name.toString()));
        currentEditDescriptor.getDestination().ifPresent(destination ->
                eventDestinationFormItem.setValue(destination.toString()));
        currentEditDescriptor.getBudget().ifPresent(budget ->
                eventTotalBudgetFormItem.setValue(budget.getValue()));

        currentEditDescriptor.getStartTime().ifPresent(startDate ->
                eventStartTimeFormItem.setValue(startDate));
        currentEditDescriptor.getEndTime().ifPresent(endDate ->
                eventEndTimeFormItem.setValue(endDate));
        currentEditDescriptor.getDescription().ifPresent(description ->
                eventDescriptionFormItem.setValue(description.toString()));
    }

    /**
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        //Initialise with new display data
        eventNameFormItem = new TextFormItem("Name of Event : ", nameFormValue -> {
            mainWindow.executeGuiCommand(
                    EditEventFieldCommand.COMMAND_WORD
                            + " " + PREFIX_NAME + nameFormValue);
        });

        eventStartTimeFormItem = new TimeFormItem("Start time : ", startTime -> {
            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DATE_START
                    + " " + startTime.format(TIME_FORMATTER));
        });
        eventEndTimeFormItem = new TimeFormItem("End time : ", endTime -> {
            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DATE_END
                    + " " + endTime.format(TIME_FORMATTER));
        });

        eventTotalBudgetFormItem = new DoubleFormItem("Total budget (in Singapore Dollar): ", totalBudget -> {
            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_BUDGET + String.format("%.2f", totalBudget));
        });
        eventDestinationFormItem = new TextFormItem("Destination : ", destinationValue -> {
            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_LOCATION + destinationValue);
        });
        eventDescriptionFormItem = new TextFormItem("Description : ", descriptionValue -> {
            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DESCRIPTION + descriptionValue);
        });

        eventNameFormItem.getRoot().getStylesheets().clear();
        eventStartTimeFormItem.getRoot().getStylesheets().clear();
        eventEndTimeFormItem.getRoot().getStylesheets().clear();
        eventTotalBudgetFormItem.getRoot().getStylesheets().clear();
        eventDestinationFormItem.getRoot().getStylesheets().clear();

        eventNameFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        eventStartTimeFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        eventEndTimeFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        eventTotalBudgetFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        eventDestinationFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        eventDescriptionFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);

        formItemsPlaceholder.getChildren().addAll(
                eventNameFormItem.getRoot(),
                eventStartTimeFormItem.getRoot(),
                eventEndTimeFormItem.getRoot(),
                eventTotalBudgetFormItem.getRoot(),
                eventDestinationFormItem.getRoot(),
                eventDescriptionFormItem.getRoot());
    }

    @FXML
    private void handleEditEventDone() {
        String commandText = DoneEditEventCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }

    @FXML
    private void handleEditCancel() {
        String commandText = CancelEditEventCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }
}
