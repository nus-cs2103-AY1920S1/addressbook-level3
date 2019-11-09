package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.calendar.CalendarWindow;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static String stylesheet;
    private static final String FXML = "MainWindow.fxml";
    private static Stage primaryStage;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private FlashCardListPanel flashCardListPanel;
    private DeadlineListPanel deadlineListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private StatsWindow statsWindow;
    private CategoryListPanel categoryListPanel;
    private CalendarWindow calendarWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane flashcardListPanelPlaceholder;

    @FXML
    private StackPane categoryListPanelPlaceholder;

    @FXML
    private StackPane deadlineListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        setStyleSheet(logic.getGuiSettings());
        setAccelerators();

        helpWindow = new HelpWindow();
        statsWindow = new StatsWindow();
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
        flashCardListPanel = new FlashCardListPanel(logic.getFilteredFlashCardList());
        flashcardListPanelPlaceholder.getChildren().add(flashCardListPanel.getRoot());

        categoryListPanel = new CategoryListPanel(logic.getCategoryList());
        categoryListPanelPlaceholder.getChildren().add(categoryListPanel.getRoot());

        deadlineListPanel = new DeadlineListPanel(logic.getFilteredDeadlineList());
        deadlineListPanelPlaceholder.getChildren().add(deadlineListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getKeyboardFlashCardsFilePath());
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
    //@@ author shutingy
    public static void setStyleSheet(GuiSettings guiSettings) {
        stylesheet = guiSettings.getStyleSheets();
        primaryStage.getScene().getStylesheets().add(stylesheet);
    }
    //@@ author shutingy
    public static void setStylesheet(String newStylesheet) {
        primaryStage.getScene().getStylesheets().remove(stylesheet);
        stylesheet = newStylesheet;
        primaryStage.getScene().getStylesheets().add(stylesheet);
    }
    //@@ author shutingy

    /**
     * modified the flashcardList for test mode
     */
    public void updateScene(TestFlashCardPanel testFlashCardPanel) {
        requireNonNull(testFlashCardPanel);
        flashcardListPanelPlaceholder.getChildren().add(testFlashCardPanel.getRoot());
    }
    //@@ author shutingy

    /**
     * update the scene when test mode ended.
     */
    @FXML
    public void handleEndTest() {
        flashcardListPanelPlaceholder.getChildren().clear();
        flashcardListPanelPlaceholder.getChildren().add(flashCardListPanel.getRoot());
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

    /**
     * Opens the stats window or focuses on it if it's already opened.
     */
    @FXML
    public void handleStats() {
        if (!statsWindow.isShowing()) {
            statsWindow.show(logic.getModel());
        } else {
            statsWindow.focus();
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
                (int) primaryStage.getX(), (int) primaryStage.getY(),
                 stylesheet);
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        statsWindow.hide();
        primaryStage.hide();
    }

    public FlashCardListPanel getFlashCardListPanel() {
        return flashCardListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (!(commandResult.isShowStats())) {
                statsWindow.hide();
            } else {
                handleStats();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isTestMode()) {
                updateScene(commandResult.getTestFlashCardPanel());
            }

            if (commandResult.isEndTest()) {
                handleEndTest();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
