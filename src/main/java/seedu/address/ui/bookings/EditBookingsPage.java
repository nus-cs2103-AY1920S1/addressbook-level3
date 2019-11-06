package seedu.address.ui.bookings;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.bookings.edit.CancelEditBookingsCommand;
import seedu.address.logic.commands.bookings.edit.DoneEditBookingsCommand;
import seedu.address.logic.commands.bookings.edit.EditBookingsFieldCommand;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;


/**
 * WARNING INCOMEPLETE: TODO: FIELDS FOR INVENTORY AND BOOKING.
 */
public class EditBookingsPage extends Page<AnchorPane> {

    private static final String FXML = "bookings/EditBookingsPage.fxml";

    private TextFormItem bookingsNameFormItem;
    private TextFormItem bookingsContactFormItem;

    @javafx.fxml.FXML
    private VBox formItemsPlaceholder;

    @FXML
    private Button addButton;

    public EditBookingsPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        initFormWithModel();
    }

    /**
     * Fills the {@code FormItem}s with the model data.
     */
    public void fillPage() {
        EditBookingsFieldCommand.EditBookingsDescriptor currentEditDescriptor =
                model.getPageStatus().getEditBookingsDescriptor();

        if (currentEditDescriptor == null) {
            return;
        }

        currentEditDescriptor.getName().ifPresent(name ->
                bookingsNameFormItem.setValue(name.toString()));
        currentEditDescriptor.getContact().ifPresent(contact ->
                bookingsContactFormItem.setValue(contact.toString()));
    }

    /**
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        //Initialise with new display data
        bookingsNameFormItem = new TextFormItem("Name of Expenditure : ", nameFormValue -> {
            mainWindow.executeGuiCommand(
                    EditBookingsFieldCommand.COMMAND_WORD
                            + " " + PREFIX_NAME + nameFormValue);
        });
        bookingsContactFormItem = new TextFormItem("Day Number : ", dayNumber -> {
            mainWindow.executeGuiCommand(EditBookingsFieldCommand.COMMAND_WORD
                    + " " + PREFIX_DAY_NUMBER + dayNumber);
        });

        fillPage(); //update and overwrite with existing edit descriptor

        formItemsPlaceholder.getChildren().addAll(
                bookingsNameFormItem.getRoot(),
                bookingsContactFormItem.getRoot());
    }

    @FXML
    private void handleEditBookingsDone() {
        String commandText = DoneEditBookingsCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }

    @FXML
    private void handleEditCancel() {
        String commandText = CancelEditBookingsCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }
}
