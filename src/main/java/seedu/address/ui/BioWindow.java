package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class BioWindow extends UiPart<Stage> {

    private static final String FXML = "BioWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private static final String ICON_APPLICATION = "/images/address_book_32.png";

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private Profile profile;
    private BioTable bioTable;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private HBox profilePlaceholder;

    @FXML
    private VBox bioTablePlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public BioWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        Image img = new Image(MainApp.class.getResourceAsStream(ICON_APPLICATION));

        String manyText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus sed mollis quam, vitae iaculis risus. Suspendisse potenti. Cras sit amet magna tempus, pellentesque enim vulputate, commodo diam. Cras a tristique lacus. Maecenas non cursus ipsum. Nunc sit amet interdum diam, at ultrices nunc. Sed non dignissim risus, sit amet egestas ipsum. Aenean quis nisl non massa lobortis tristique in sit amet tellus. Aenean non lectus sed lectus aliquet interdum eget a mi. Morbi eget porta leo. Pellentesque a pretium lorem, sit amet semper ante. Phasellus pellentesque ante sit amet eros maximus aliquet in ut purus. In maximus lectus quis tellus pulvinar, a malesuada orci suscipit. Aenean sodales euismod mauris, sit amet vehicula eros placerat nec. Nullam in tincidunt lectus.\n" +
                "\n" +
                "Vestibulum ipsum lacus, fermentum at elit quis, accumsan ullamcorper metus. Aliquam fermentum sem vel arcu maximus, at gravida lacus maximus. Donec mollis nibh efficitur condimentum vulputate. Mauris ut urna nec odio congue dignissim. Praesent vitae risus vitae sem suscipit faucibus. Sed elementum lobortis orci, a hendrerit dolor vulputate ut. Etiam ornare erat nisi, at porta sapien scelerisque a. Cras eu velit pretium, egestas nisl sit amet, posuere tortor. Duis pretium nec ligula ac lacinia. Nunc non convallis orci, sit amet egestas nibh.\n" +
                "\n" +
                "Praesent nec purus mauris. Donec porta congue eleifend. Cras feugiat facilisis euismod. Nullam vehicula quam id pellentesque luctus. Fusce ut vehicula elit. Nullam nec nisi et purus interdum faucibus. Fusce viverra nisl at metus finibus, eget mollis elit fermentum. Pellentesque auctor viverra erat, nec pretium quam fringilla sit amet. Curabitur a gravida felis, scelerisque cursus mauris.\n" +
                "\n" +
                "Proin et purus nisl. Donec vulputate bibendum augue. Suspendisse volutpat lobortis tellus in tincidunt. Phasellus felis nulla, scelerisque eget odio non, vestibulum cursus lacus. Curabitur erat tellus, faucibus vel sem vel, dapibus consectetur urna. Nulla facilisi. Duis commodo fringilla ante, egestas ultrices dui scelerisque eu. Suspendisse faucibus pharetra aliquet.\n" +
                "\n" +
                "Etiam vitae pulvinar mauris, id convallis ex. Pellentesque sit amet pellentesque velit. Donec condimentum dolor massa, vitae accumsan nibh sollicitudin sed. Sed commodo gravida arcu, at venenatis ex tempus nec. Proin ac interdum tellus. Aliquam nec tincidunt nulla. Morbi elementum tincidunt massa placerat pellentesque. Fusce vitae sapien quis ante tincidunt eleifend id id tortor. Donec nec tristique eros, quis ullamcorper velit. Donec justo orci, efficitur ut arcu vitae, faucibus elementum enim. Vivamus sed facilisis tellus, non imperdiet tortor. Vestibulum ac enim quis quam venenatis auctor. Duis in justo elit. Donec auctor egestas dolor, sit amet euismod ante condimentum quis.";

        String abit = "Example Age \nExample Gender \nExample Address";
        String little = "Example Profile";

        profile = new Profile(img, "Example Name", abit);
//        profile = new Profile(img, "Example Name", "Example Profile");
        profilePlaceholder.getChildren().add(profile.getRoot());

        bioTable = new BioTable();
        bioTablePlaceholder.getChildren().add(bioTable.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
