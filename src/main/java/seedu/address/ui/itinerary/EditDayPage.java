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
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.DayPhotoFormItem;
import seedu.address.ui.components.form.DoubleFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;


/**
 * {@code Page} class implementing the edit day page.
 */
public class EditDayPage extends Page<AnchorPane> {

    private static final String FXML = "itinerary/days/EditDayPage.fxml";
    private TextFormItem dayDestinationFormItem;
    private DoubleFormItem dayTotalBudgetFormItem;
    private TextFormItem dayDescriptionFormItem;
    private DayPhotoFormItem dayPhotoFormItem;

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

        currentEditDescriptor.getDestination().ifPresent(destination ->
                dayDestinationFormItem.setValue(destination.toString()));
        currentEditDescriptor.getBudget().ifPresent(budget ->
                dayTotalBudgetFormItem.setValue(budget.getValue()));
        currentEditDescriptor.getDescription().ifPresent((description ->
                dayDescriptionFormItem.setValue(description.description)));
        currentEditDescriptor.getPhoto().ifPresent(photo ->
                dayPhotoFormItem.setValue(photo));

    }

    /**
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        //Initialise with new display data
        dayTotalBudgetFormItem = new DoubleFormItem("Total budget (in Singapore Dollar): ", totalBudget -> {
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                    + " " + CliSyntax.PREFIX_BUDGET + String.format("%.2f", totalBudget));
        });
        dayDestinationFormItem = new TextFormItem("Destination : ", destinationValue -> {
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                    + " " + CliSyntax.PREFIX_LOCATION + destinationValue);
        });
        dayDescriptionFormItem = new TextFormItem("Description : ", description -> {
            mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                    + " " + CliSyntax.PREFIX_DESCRIPTION + description);
        });
        dayPhotoFormItem = new DayPhotoFormItem("Photo : ", photo ->
                mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD
                        + " " + CliSyntax.PREFIX_DATA_FILE_PATH + photo.getImageFilePath()), () ->
                mainWindow.executeGuiCommand(EditDayFieldCommand.COMMAND_WORD + " "
                        + CliSyntax.PREFIX_FILE_CHOOSER + " " + CliSyntax.PREFIX_DATA_FILE_PATH));


        fillPage(); //update and overwrite with existing edit descriptor
        formItemsPlaceholder.getChildren().add(new Label("Edit Day"));

        formItemsPlaceholder.getChildren().addAll(
                dayTotalBudgetFormItem.getRoot(),
                dayDestinationFormItem.getRoot(),
                dayDescriptionFormItem.getRoot(),
                dayPhotoFormItem.getRoot());
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
