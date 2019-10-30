package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.events.exceptions.EventException;
import seedu.address.logic.commands.result.ResultInformation;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    // private SplitDisplay splitDisplay;
    private CentralDisplay centralDisplay;
    private FeedbackDisplay feedbackDisplay;
//    private HelpWindow helpWindow;

    @FXML
    private BorderPane centralDisplayPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane feedbackDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        primaryStage.initStyle(StageStyle.UNDECORATED);
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

//        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        /* splitDisplay = new SplitDisplay(logic.getFilteredAccommodationList(), logic.getFilteredActivityList(),
                logic.getFilteredContactList());
        splitDisplayPanelPlaceholder.getChildren().add(splitDisplay.getRoot()); */

        updateCentralDisplay();
        updateFeedbackDisplay();

        //StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getPlannerFilePath());
        //statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    private void updateCentralDisplay() {
        centralDisplay = new CentralDisplay(
                logic.getFilteredItinerary(),
                logic.getFilteredAccommodationList(),
                logic.getFilteredActivityList(),
                logic.getFilteredContactList(),
                logic.getItinerary().getStartDateProperty()
        );
        centralDisplayPlaceholder.getChildren().add(centralDisplay.getRoot());
        centralDisplay.changeFocus(new UiFocus[]{UiFocus.ACTIVITY});
        centralDisplay.getRoot().prefHeightProperty().bind(centralDisplayPlaceholder.heightProperty());
        centralDisplay.getRoot().prefWidthProperty().bind(centralDisplayPlaceholder.widthProperty());
    }

    private void updateFeedbackDisplay() {
        feedbackDisplay = new FeedbackDisplay();
        feedbackDisplayPlaceholder.getChildren().add(feedbackDisplay.getRoot());
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

//    /**
//     * Opens the help window or focuses on it if it's already opened.
//     */
//    @FXML
//    public void handleHelp() {
//        if (!helpWindow.isShowing()) {
//            helpWindow.show();
//        } else {
//            helpWindow.focus();
//        }
//    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() { // @ernest: now limited to exit command
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
//        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException, EventException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            feedbackDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                centralDisplay.generateCommandHelpSummary();
            }
            if (commandResult.isExit()) {
                handleExit();
            }
            commandResult.getInformationToUser().ifPresent(this::applyInfoChange);
            commandResult.getUiFocus().ifPresent(this::applyUiFocusChange);

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            feedbackDisplay.setErrorFeedbackToUser(e.getMessage()); // @ernest: may need to rework this part
            throw e;
        }
    }

    /**
     * Updates the UI and changes the focus to the relevant tab.
     */
    private void applyUiFocusChange(UiFocus[] uiFocus) {
        centralDisplay.changeFocus(uiFocus);
    }

    /**
     * Updates the UI and changes the focus to the relevant tab.
     */
    private void applyInfoChange(ResultInformation[] resultInformation) {
        centralDisplay.changeInfo(resultInformation);
    }
}
