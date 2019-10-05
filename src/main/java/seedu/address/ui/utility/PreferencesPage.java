package seedu.address.ui.utility;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.preferences.CancelEditPrefsCommand;
import seedu.address.logic.commands.preferences.DoneEditPrefsCommand;
import seedu.address.logic.commands.preferences.EditPrefsFieldCommand;
import seedu.address.logic.commands.preferences.EditPrefsFieldCommand.EditPrefsDescriptor;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_HEIGHT;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_WIDTH;

public class PreferencesPage extends Page<AnchorPane> {

    private static final String FXML = "common/PreferencesPage.fxml";

    private TextFormItem windowHeightFormItem;
    private TextFormItem windowWidthFormItem;
    private TextFormItem windowXPosFormItem;
    private TextFormItem windowYPosFormItem;
    private TextFormItem dataFilePathFormItem;

    @FXML
    private VBox formItemsPlaceholder;

    @FXML
    private Button addButton;

    public PreferencesPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        initFormWithModel();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        EditPrefsDescriptor currentPrefsDescriptor
                = model.getPageStatus().getEditPrefsDescriptor();
        requireNonNull(currentPrefsDescriptor);

        windowWidthFormItem.setValue(currentPrefsDescriptor.getWindowWidth().get().toString());
        windowHeightFormItem.setValue(currentPrefsDescriptor.getWindowHeight().get().toString());
        //windowXPosFormItem.setValue(currentPrefsDescriptor.getWindowXPos().get().toString());
        //windowYPosFormItem.setValue(currentPrefsDescriptor.getWindowYPos().get().toString());
        dataFilePathFormItem.setValue(currentPrefsDescriptor.getDataFilePath().get());
    }

    /**
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        //Initialise with new display data
        windowWidthFormItem = new TextFormItem("Window Width: ", windowWidth -> {
            mainWindow.executeGuiCommand(
                    EditPrefsFieldCommand.COMMAND_WORD + " " + PREFIX_WINDOW_WIDTH + windowWidth);
        });
        windowHeightFormItem = new TextFormItem("Window Height: ", windowHeight -> {
            mainWindow.executeGuiCommand(
                    EditPrefsFieldCommand.COMMAND_WORD + " " + PREFIX_WINDOW_HEIGHT + windowHeight);
        });
        dataFilePathFormItem = new TextFormItem("Data file path: ", dataFilePath -> {
            mainWindow.executeGuiCommand(
                    EditPrefsFieldCommand.COMMAND_WORD + " " + PREFIX_DATA_FILE_PATH + dataFilePath);
        });
        /*
        windowWidthFormItem = new TextFormItem("Window Width: ", windowWidth -> {
            mainWindow.executeGuiCommand(
                    EditPrefsFieldCommand.COMMAND_WORD + " " + PREFIX_WINDOW_WIDTH + windowWidth);
        });
        windowWidthFormItem = new TextFormItem("Window Width: ", windowWidth -> {
            mainWindow.executeGuiCommand(
                    EditPrefsFieldCommand.COMMAND_WORD + " " + PREFIX_WINDOW_WIDTH + windowWidth);
        });
        */


        fillPage(); //update and overwrite with existing edit descriptor

        formItemsPlaceholder.getChildren().addAll(
                windowWidthFormItem.getRoot(),
                windowHeightFormItem.getRoot(),
                dataFilePathFormItem.getRoot());
                //tripTotalBudgetFormItem.getRoot(),
                //tripDestinationFormItem.getRoot());
    }

    @FXML
    private void handleEditPrefsDone() {
        String commandText = DoneEditPrefsCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }

    @FXML
    private void handleEditPrefsCancel() {
        String commandText = CancelEditPrefsCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }
}
