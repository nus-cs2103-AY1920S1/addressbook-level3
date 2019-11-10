package seedu.address.ui.trips;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_CHOOSER;
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
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand.EditTripDescriptor;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.trips.edit.EditTripCommand;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.DateFormItem;
import seedu.address.ui.components.form.DoubleFormItem;
import seedu.address.ui.components.form.PhotoFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;

/**
 * {@code Page} class for displaying the edit trip page.
 */
public class EditTripPage extends Page<AnchorPane> {

    private static final String FXML = "trips/EditTripPage.fxml";

    private static final String FORM_ITEM_STYLESHEET = "/view/trips/trips.css";

    /** Format string for executing commands from the user interface form. */
    private static final String EXECUTE_COMMAND_FORMAT = EditTripFieldCommand.COMMAND_WORD + " %1$s%2$s";

    private TextFormItem tripNameFormItem;
    private TextFormItem tripDestinationFormItem;
    private DateFormItem tripStartDateFormItem;
    private DateFormItem tripEndDateFormItem;
    private DoubleFormItem tripTotalBudgetFormItem;
    private PhotoFormItem tripPhotoFormItem;

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
        EditTripDescriptor currentEditDescriptor = model.getPageStatus().getEditTripDescriptor();
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
                tripTotalBudgetFormItem.setValue(budget.getValue()));
        currentEditDescriptor.getPhoto().ifPresent(photo ->
                tripPhotoFormItem.setValue(photo));
    }

    /**
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        initFormItems();
        initFormItemStyles();

        formItemsPlaceholder.getChildren().addAll(
                tripNameFormItem.getRoot(),
                tripStartDateFormItem.getRoot(),
                tripEndDateFormItem.getRoot(),
                tripTotalBudgetFormItem.getRoot(),
                tripDestinationFormItem.getRoot(),
                tripPhotoFormItem.getRoot());
    }

    /**
     * Overrides the default style sheets used by the form items.
     * Checks that the form items are not null before doing so.
     */
    private void initFormItemStyles() {
        requireAllNonNull(tripNameFormItem, tripStartDateFormItem, tripEndDateFormItem, tripTotalBudgetFormItem,
                tripDestinationFormItem, tripPhotoFormItem);

        tripNameFormItem.getRoot().getStylesheets().clear();
        tripStartDateFormItem.getRoot().getStylesheets().clear();
        tripEndDateFormItem.getRoot().getStylesheets().clear();
        tripTotalBudgetFormItem.getRoot().getStylesheets().clear();
        tripDestinationFormItem.getRoot().getStylesheets().clear();
        tripPhotoFormItem.getRoot().getStylesheets().clear();

        tripNameFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        tripStartDateFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        tripEndDateFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        tripTotalBudgetFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        tripDestinationFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        tripPhotoFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
    }

    /**
     * Initialises the form items used by the edit trip page.
     */
    private void initFormItems() {
        tripNameFormItem = new TextFormItem("Name of trip : ", nameFormValue ->
                mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_NAME, nameFormValue)));

        tripStartDateFormItem = new DateFormItem("Start date : ", startDate ->
                mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_DATE_START,
                        ParserDateUtil.getStringFromDate(startDate.atStartOfDay()))));

        tripEndDateFormItem = new DateFormItem("End date : ", endDate ->
                mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_DATE_END,
                        ParserDateUtil.getStringFromDate(endDate.atTime(23, 59, 59)))));

        tripTotalBudgetFormItem = new DoubleFormItem("Total budget (in Singapore Dollar): ", totalBudget ->
                mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_BUDGET,
                        String.format("%.2f", totalBudget))));

        tripDestinationFormItem = new TextFormItem("Destination : ", destinationValue ->
                mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_LOCATION, destinationValue)));

        tripPhotoFormItem = new PhotoFormItem("Photo : ", photo ->
                mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_DATA_FILE_PATH,
                        photo.getImageFilePath())), () ->
                mainWindow.executeGuiCommand(EditTripCommand.EDIT + " "
                        + PREFIX_FILE_CHOOSER + " " + PREFIX_DATA_FILE_PATH));
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
