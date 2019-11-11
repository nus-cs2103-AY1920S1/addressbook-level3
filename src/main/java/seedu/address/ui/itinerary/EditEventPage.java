package seedu.address.ui.itinerary;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INVENTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import static seedu.address.logic.parser.ParserDateUtil.TIME_FORMATTER;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.itinerary.events.edit.CancelEditEventCommand;
import seedu.address.logic.commands.itinerary.events.edit.DoneEditEventCommand;
import seedu.address.logic.commands.itinerary.events.edit.EditEventFieldCommand;
import seedu.address.model.Model;
import seedu.address.model.inventory.Inventory;
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

    private TextFormItem eventInventoryFormItem;
    private Button addInventoryButton;

    private TextFormItem eventDescriptionFormItem;
    //private TextFormItem eventInventoryFormItem;

    //private TextFormItem eventBookingFormItem;

    private ListView<Inventory> listView;

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

        //Added by Karan Dev Sapra
        currentEditDescriptor.getInventoryList().ifPresent(inventoryList ->
                listView.setItems(FXCollections.observableList(inventoryList.getList())));

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

        eventInventoryFormItem = new TextFormItem("Inventory Items Needed : ", null);


        //No need for any action since this is already done by the add button
        /*
        eventInventoryFormItem = new TextFormItem("Inventory Items Needed : ", itemName -> {

            //No need for any action since this is already done by the add button

            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_ADD_INVENTORY + itemName);
        });*/

        addInventoryButton = new Button("add");


        addInventoryButton.setOnAction((actionEvent) -> {
            mainWindow.executeGuiCommand(EditEventFieldCommand.COMMAND_WORD
                    + " " + PREFIX_ADD_INVENTORY + eventInventoryFormItem.getValue());
        });

        listView = new ListView<>();



        listView.setCellFactory(param -> new ListCell<Inventory>() {
            @Override
            protected void updateItem(Inventory item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName().fullName);
                }
            }
        });

        eventNameFormItem.getRoot().getStylesheets().clear();
        eventStartTimeFormItem.getRoot().getStylesheets().clear();
        eventEndTimeFormItem.getRoot().getStylesheets().clear();
        eventTotalBudgetFormItem.getRoot().getStylesheets().clear();
        eventDestinationFormItem.getRoot().getStylesheets().clear();
        eventInventoryFormItem.getRoot().getStylesheets().clear();

        eventNameFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        eventStartTimeFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        eventEndTimeFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        eventTotalBudgetFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);
        eventDestinationFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);

        eventInventoryFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);

        //Looks like this has no effect
        listView.getStylesheets().add(FORM_ITEM_STYLESHEET);

        eventDescriptionFormItem.getRoot().getStylesheets().add(FORM_ITEM_STYLESHEET);


        formItemsPlaceholder.getChildren().addAll(
                eventNameFormItem.getRoot(),
                eventStartTimeFormItem.getRoot(),
                eventEndTimeFormItem.getRoot(),
                eventTotalBudgetFormItem.getRoot(),
                eventDestinationFormItem.getRoot(),
                eventInventoryFormItem.getRoot(),
                addInventoryButton,
                listView,
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

    /*
    public static class HBoxCell extends HBox {
        private Label label = new Label();
        private Button button = new Button();

        HBoxCell(String labelText, String buttonText) {
            super();

            label.setText(labelText);
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);

            button.setText(buttonText);

            this.getChildren().addAll(label, button);
        }
    }*/



}
