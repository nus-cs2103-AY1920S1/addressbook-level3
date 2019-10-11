package seedu.address.ui.utility;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_GUI_LOCK;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_HEIGHT;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_WIDTH;

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
import seedu.address.ui.components.form.CheckBoxFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;

/**
 * {@code Page} class implementing the preferences page.
 */
public class PreferencesPage extends Page<AnchorPane> {

    private static final String FXML = "common/PreferencesPage.fxml";

    private TextFormItem windowHeightFormItem;
    private TextFormItem windowWidthFormItem;
    private TextFormItem windowXPosFormItem;
    private TextFormItem windowYPosFormItem;
    private TextFormItem dataFilePathFormItem;
    private CheckBoxFormItem guiLockFormItem;

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
        EditPrefsDescriptor currentPrefsDescriptor =
                model.getPageStatus().getEditPrefsDescriptor();
        requireNonNull(currentPrefsDescriptor);

        windowWidthFormItem.setValue(
                String.format("%1$.3f", currentPrefsDescriptor.getWindowWidth().getAsDouble()));
        windowHeightFormItem.setValue(
                String.format("%1$.3f", currentPrefsDescriptor.getWindowHeight().getAsDouble()));
        windowXPosFormItem.setValue(currentPrefsDescriptor.getWindowXPos().getAsInt() + "");
        windowYPosFormItem.setValue(currentPrefsDescriptor.getWindowYPos().getAsInt() + "");
        dataFilePathFormItem.setValue(currentPrefsDescriptor.getDataFilePath().get());
        guiLockFormItem.setValue(currentPrefsDescriptor.isGuiLocked().get());
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
        windowXPosFormItem = new TextFormItem("Window X Position: ", xPos -> {
            mainWindow.executeGuiCommand(
                    EditPrefsFieldCommand.COMMAND_WORD + " " + PREFIX_WINDOW_WIDTH + xPos);
        });
        windowYPosFormItem = new TextFormItem("Window Y Position: ", yPos -> {
            mainWindow.executeGuiCommand(
                    EditPrefsFieldCommand.COMMAND_WORD + " " + PREFIX_WINDOW_WIDTH + yPos);
        });
        dataFilePathFormItem = new TextFormItem("Data file path: ", dataFilePath -> {
            mainWindow.executeGuiCommand(
                    EditPrefsFieldCommand.COMMAND_WORD + " " + PREFIX_DATA_FILE_PATH + dataFilePath);
        });
        guiLockFormItem = new CheckBoxFormItem("Lock gui settings: ", isLocked -> {
            mainWindow.executeGuiCommand(EditPrefsFieldCommand.COMMAND_WORD
                    + " " + PREFIX_GUI_LOCK
                    + (isLocked.booleanValue() ? "t" : "f"));
        });

        fillPage();

        formItemsPlaceholder.getChildren().addAll(
                windowWidthFormItem.getRoot(),
                windowHeightFormItem.getRoot(),
                windowXPosFormItem.getRoot(),
                windowYPosFormItem.getRoot(),
                guiLockFormItem.getRoot(),
                dataFilePathFormItem.getRoot());
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
