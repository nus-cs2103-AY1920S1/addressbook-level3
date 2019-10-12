package seedu.address.ui.itinerary;

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
import seedu.address.logic.commands.itinerary.events.edit.CancelEditEventCommand;
import seedu.address.logic.commands.itinerary.events.edit.DoneEditEventCommand;
import seedu.address.logic.commands.itinerary.events.edit.EditEventFieldCommand;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.DateFormItem;
import seedu.address.ui.components.form.DoubleFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;

/**
 * WARNING INCOMEPLETE: TODO: FIELDS FOR INVENTORY AND BOOKING.
 */
public class EditEventPage extends Page<AnchorPane> {

    private static final String FXML = "itinerary/events/EditEventPage.fxml";

    private TextFormItem eventNameFormItem;
    private TextFormItem eventDestinationFormItem;
    private DateFormItem eventStartDateFormItem;
    private DateFormItem eventEndDateFormItem;
    private DoubleFormItem eventTotalBudgetFormItem;
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
        currentEditDescriptor.getStartDate().ifPresent(startDate ->
                eventStartDateFormItem.setValue(startDate.toLocalDate()));
        currentEditDescriptor.getEndDate().ifPresent(endDate ->
                eventEndDateFormItem.setValue(endDate.toLocalDate()));
        currentEditDescriptor.getBudget().ifPresent(budget ->
                eventTotalBudgetFormItem.setValue(budget.value));
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
        eventStartDateFormItem = new DateFormItem("Start date : ", startDate -> {
            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DATE_START
                    + ParserDateUtil.getStringFromDate(startDate.atStartOfDay()));
        });
        eventEndDateFormItem = new DateFormItem("End date : ", endDate -> {
            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DATE_END
                    + ParserDateUtil.getStringFromDate(endDate.atStartOfDay()));
        });
        eventTotalBudgetFormItem = new DoubleFormItem("Total budget : ", totalBudget -> {
            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_BUDGET + totalBudget);
        });
        eventDestinationFormItem = new TextFormItem("Destination : ", destinationValue -> {
            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_LOCATION + destinationValue);
        });

        fillPage(); //update and overwrite with existing edit descriptor

        formItemsPlaceholder.getChildren().addAll(
                eventNameFormItem.getRoot(),
                eventStartDateFormItem.getRoot(),
                eventEndDateFormItem.getRoot(),
                eventTotalBudgetFormItem.getRoot(),
                eventDestinationFormItem.getRoot());
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
