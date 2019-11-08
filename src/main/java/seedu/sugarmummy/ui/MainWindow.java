package seedu.sugarmummy.ui;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_ACHIEVEMENTS_ATTAINED;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_ACHIEVEMENTS_ATTAINED_AND_LOST;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_ACHIEVEMENTS_LOST;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_NO_BIO_FOUND;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_UNABLE_TO_LOAD_REFERENCES;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.logic.Logic;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.calendar.CalendarCommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.recmf.exceptions.FoodNotSuitableException;
import seedu.sugarmummy.model.time.YearMonth;
import seedu.sugarmummy.model.time.YearMonthDay;
import seedu.sugarmummy.ui.aesthetics.StyleManager;
import seedu.sugarmummy.ui.calendar.ReminderListPanel;
import seedu.sugarmummy.ui.motivationalquotes.MotivationalQuotesLabel;

/**
 * Provides the basic application layout containing a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final double SPLITPANE_RATIO = 0.8;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private StyleManager styleManager;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private MainDisplayPane mainDisplayPane;
    private ReminderListPanel reminderListPanel;
    private MotivationalQuotesLabel motivationalQuotesLabel;

    @FXML
    private Scene scene;

    @FXML
    private VBox mainWindowPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane mainDisplayPanePlaceholder;

    @FXML
    private SplitPane splitPane;

    @FXML
    private HBox resultDisplayPlaceholder;

    @FXML
    private StackPane reminderListPlaceholder;

    @FXML
    private StackPane motivationalQuotesPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        mainDisplayPane = new MainDisplayPane(logic);
        helpWindow = new HelpWindow();
        styleManager = new StyleManager(scene, mainWindowPlaceholder);
        setFontColour(logic.getGuiSettings());
        setBackground(logic.getGuiSettings());
        styleManager.setFontFamily("Futura");
    }

    /**
     * Constructor that displays an initial background image;
     */
    public MainWindow(Stage primaryStage, Logic logic, String imagePath) {
        this(primaryStage, logic);
        showInitialBackground(mainDisplayPanePlaceholder, imagePath);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
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
     * Returns messages for invalid references in a given map.
     *
     * @param fieldsContainingInvalidReferences Map that contains field and invalid reference pairs.
     * @return Messages for invalid references in a given map.
     */
    private String getMessageForInvalidReferencesInMap(Map<String, String> fieldsContainingInvalidReferences) {
        StringBuilder sb = new StringBuilder();
        for (String fieldLabels : fieldsContainingInvalidReferences.keySet()) {
            String invalidReference = fieldsContainingInvalidReferences.get(fieldLabels);
            sb.append("- ").append(invalidReference).append(" of field ").append(fieldLabels).append("\n");
        }
        return sb.toString();
    }

    /**
     * Displays invalid references to the user if any. Clears the list of invalid references after displaying to the
     * user so the message would not be displayed again during the next startup.
     *
     * @param resultDisplay ResultDisplay object that is used to display information to user.
     */
    private void displayInvalidReferences(ResultDisplay resultDisplay) {
        List<Map<String, String>> listOfFieldsContainingInvalidReferences = logic
                .getListOfFieldsContainingInvalidReferences();
        Map<String, String> guiFieldsContainingInvalidReferences =
                logic.getGuiSettings().getFieldsContainingInvalidReferences();

        StringBuilder sb = new StringBuilder();

        if (!listOfFieldsContainingInvalidReferences.isEmpty() || !guiFieldsContainingInvalidReferences.isEmpty()) {
            resultDisplay.appendNewLineInFeedBackToUser(2);
            sb.append(MESSAGE_UNABLE_TO_LOAD_REFERENCES);

            sb.append(getMessageForInvalidReferencesInMap(guiFieldsContainingInvalidReferences));

            for (Map<String, String> map : listOfFieldsContainingInvalidReferences) {
                sb.append(getMessageForInvalidReferencesInMap(map));
            }
        }
        resultDisplay.appendFeedbackToUser(sb.toString().trim());
        listOfFieldsContainingInvalidReferences.clear();
    }

    /**
     * Displays the welcome message to the user.
     *
     * @param resultDisplay ResultDisplay object that is used to display information to user.
     */
    private void displayWelcomeMessage(ResultDisplay resultDisplay) {
        if (!logic.getFilteredUserList().isEmpty()) {
            String name = logic.getFilteredUserList().get(0).getName().toString();
            resultDisplay.appendFeedbackToUser("Hi " + name + "! How are you feeling, and how can SugarMummy "
                    + "assist you today?");
        } else {
            resultDisplay.appendFeedbackToUser("Hello there! How are you feeling, and how can SugarMummy "
                    + "assist you today?\n" + MESSAGE_NO_BIO_FOUND);
        }
    }

    /**
     * Displays the main background to the user.
     *
     * @param mainDisplayPanePlaceholder MainDisplayPaneholder containing background to be displayed.
     * @imagePath String representation of path to background image to be displayed to the user upon startup.
     */
    private void showInitialBackground(StackPane mainDisplayPanePlaceholder, String imagePath) {

        mainDisplayPanePlaceholder.setStyle("-fx-background-image: url('" + imagePath + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: no-repeat;"
                + "-fx-background-size: contain;");
    }

    /**
     * Fills up intiial placeholders of this window.
     *
     * @imagePath String representation of path to background image to be displayed to the user upon startup.
     */
    void fillInnerParts() throws URISyntaxException {
        resultDisplay = new ResultDisplay();
        displayWelcomeMessage(resultDisplay);
        displayInvalidReferences(resultDisplay);
        if (logic.getBackground().showDefaultBackground()) {
            setFontColour(logic.getGuiSettings());
        }

        splitPane.setDividerPosition(0, SPLITPANE_RATIO);

        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        reminderListPanel = new ReminderListPanel(logic.getPastReminderList(),
                logic.getFilteredCalendarEntryList(), logic.getToday(), logic.getAppStartingDateTime());
        reminderListPlaceholder.getChildren().add(reminderListPanel.getRoot());
        logic.schedule();

        motivationalQuotesLabel = new MotivationalQuotesLabel(logic.getMotivationalQuotesList(), primaryStage);
        motivationalQuotesPlaceholder.getChildren().add(motivationalQuotesLabel.getRoot());
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
     * Sets the font colour based on {@code guiSettings}.
     *
     * @param guiSettings GuiSettings to retrieve and store font colour in.
     */
    private void setFontColour(GuiSettings guiSettings) {
        styleManager.setFontColour(guiSettings.getFontColour());
    }

    /**
     * Sets the background based on {@code guiSettings}.
     *
     * @param guiSettings GuiSettings to retrieve and store background in.
     */
    private void setBackground(GuiSettings guiSettings) {
        Background background = guiSettings.getBackground();
        styleManager.setBackground(guiSettings.getBackground());
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
     * Returns the pane to be displayed depending on the type of pane to be displayed and whether the GUI has been
     * modified. If the GUI has been modified, then the BIO page needs to reload to display the update if the current
     * pane displayed happens to be so.
     *
     * @param displayPaneType DisplayPaneType indicating whether the GUI is to be modified.
     * @param guiIsModified   Boolean indicating whether the GUI has been modified.
     * @return Pane to be displayed.
     */
    private DisplayPaneType getNextPaneType(DisplayPaneType displayPaneType, boolean guiIsModified) {
        if (guiIsModified && mainDisplayPane.getCurrPaneType() == DisplayPaneType.BIO) {
            return DisplayPaneType.BIO;
        } else if (guiIsModified) {
            return null;
        } else {
            return displayPaneType;
        }
    }

    /**
     * Modifies the GUI based on the displayPaneType and returns true if the GUI has been modified
     *
     * @param displayPaneType DisplayPaneType indicating whether the GUI is to be modified.
     * @return Boolean indicating whether the GUI has been modified.
     */
    private boolean guiIsModified(DisplayPaneType displayPaneType) {
        if (displayPaneType == DisplayPaneType.COLOUR) {
            setFontColour(logic.getGuiSettings());
            return true;
        } else if (displayPaneType == DisplayPaneType.BACKGROUND) {
            setBackground(logic.getGuiSettings());
            return true;
        } else if (displayPaneType == DisplayPaneType.COLOUR_AND_BACKGROUND) {
            setFontColour(logic.getGuiSettings());
            setBackground(logic.getGuiSettings());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Switches the main display pane to the specified UI part.
     */
    private void switchToMainDisplayPane(DisplayPaneType displayPaneType, boolean newPaneIsToBeCreated) {
        assert displayPaneType != null : "displayPaneType cannot be null";
        assert Arrays.asList(DisplayPaneType.values()).contains(displayPaneType) : "The displayPaneType "
                + displayPaneType + " is not inside the enum";
        if (displayPaneType == displayPaneType.NONE) {
            return;
        }
        if (displayPaneType != mainDisplayPane.getCurrPaneType() || newPaneIsToBeCreated) {
            DisplayPaneType nextPaneType = getNextPaneType(displayPaneType, guiIsModified(displayPaneType));
            if (nextPaneType == null) {
                return;
            }
            newPaneIsToBeCreated = ((displayPaneType == DisplayPaneType.COLOUR
                    || displayPaneType == DisplayPaneType.BACKGROUND)
                    && nextPaneType == DisplayPaneType.BIO) || newPaneIsToBeCreated;
            updateMainDisplayPane(requireNonNull(mainDisplayPane.get(nextPaneType, newPaneIsToBeCreated)));
        }
    }

    /**
     * Switches the main display pane to the calendar pane.
     */
    private void switchToMainDisplayPane(DisplayPaneType displayPaneType, boolean newPaneIsToBeCreated,
                                         YearMonth yearMonth, Optional<YearMonthDay> yearMonthDay,
                                         boolean isShowingWeek) {
        if (!Arrays.asList(DisplayPaneType.values()).contains(displayPaneType)) {
            throw new NullPointerException();
        } else if (displayPaneType != mainDisplayPane.getCurrPaneType() || newPaneIsToBeCreated) {
            DisplayPaneType paneToDisplay = getNextPaneType(displayPaneType, guiIsModified(displayPaneType));
            if (paneToDisplay == null) {
                return;
            }
            updateMainDisplayPane(requireNonNull(mainDisplayPane.get(paneToDisplay, newPaneIsToBeCreated,
                    yearMonth, yearMonthDay, isShowingWeek)));
        }
    }

    private void updateMainDisplayPane(UiPart<Region> paneUi) {
        mainDisplayPanePlaceholder.setStyle(null);
        mainDisplayPanePlaceholder.getChildren().clear();
        mainDisplayPanePlaceholder.getChildren().add(paneUi.getRoot());
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
                (int) primaryStage.getX(), (int) primaryStage.getY(), logic.getFontColour(), logic.getBackground());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
        styleManager.resetStyleSheets();
    }

    /**
     * Returns an achievement notification to indicate change in user's achievement list if any.
     */
    private String getAchievementsNotification() {
        if (logic.newAchievementsHaveBeenAttained()
                && logic.existingAchievementsHaveBeenLost()) {
            logic.resetNewAchievementsState();
            return MESSAGE_ACHIEVEMENTS_ATTAINED_AND_LOST;
        } else if (logic.newAchievementsHaveBeenAttained()) {
            logic.resetNewAchievementsState();
            return MESSAGE_ACHIEVEMENTS_ATTAINED;
        } else if (logic.existingAchievementsHaveBeenLost()) {
            logic.resetNewAchievementsState();
            return MESSAGE_ACHIEVEMENTS_LOST;
        } else {
            assert !logic.newAchievementsHaveBeenAttained()
                    && !logic.existingAchievementsHaveBeenLost() : "There should no longer be new achievements at this "
                    + "point but this was not as such.";
            return "";
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.sugarmummy.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {

            CommandResult commandResult = logic.execute(commandText);

            String achievementsNotification = "\n" + getAchievementsNotification();

            logger.info("Result: " + commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
                return commandResult;
            } else if (commandResult.isExit()) {
                handleExit();
                return commandResult;
            } else {
                if (commandResult.isCalendar()) {
                    CalendarCommandResult calendarCommandResult = (CalendarCommandResult) commandResult;
                    switchToMainDisplayPane(logic.getDisplayPaneType(), logic.getNewPaneIsToBeCreated(),
                            calendarCommandResult.getYearMonth(), calendarCommandResult.getYearMonthDay(),
                            calendarCommandResult.isShowingWeek());
                } else if (logic.getDisplayPaneType() != DisplayPaneType.NONE) {
                    switchToMainDisplayPane(logic.getDisplayPaneType(), logic.getNewPaneIsToBeCreated());
                }
                logger.info("Result: " + commandResult.getFeedbackToUser() + achievementsNotification);
                resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser() + achievementsNotification);
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } catch (FoodNotSuitableException e) {
            logger.info("Not suitable food input: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
