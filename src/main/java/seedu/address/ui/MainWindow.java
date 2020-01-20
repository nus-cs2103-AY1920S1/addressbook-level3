package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Logic;
import seedu.address.logic.UiEvent;
import seedu.address.logic.commands.*;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.Optional;
import java.util.logging.Logger;

import static seedu.address.logic.commands.CheckoutCommand.MESSAGE_CHECKOUT_SUCCESS;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private State currentState = State.PROJECT_LIST;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private BudgetListPanel budgetListPanel;
    private ProjectListPanel projectListPanel;
    private ProjectOverview projectOverview;
    private PerformanceOverviewCard performanceOverviewCard;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ShowTimetablePanel timetablePanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private VBox projectList;

    @FXML
    private VBox budgetList;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane budgetListPanelPlaceholder;

    @FXML
    private StackPane showTimetablePanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane projectListPanelPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        logic.addUiEvent(new UiEvent(State.PROJECT_LIST, Optional.empty()));
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
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        projectListPanel = new ProjectListPanel(logic.getFilteredProjectList());
        projectListPanelPlaceholder.getChildren().add(projectListPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), logic);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

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

    /**
     * Display the previous Ui.
     */
    private void handleBack() throws CommandException, IllegalValueException {
        State temp = currentState;
        if (currentState == State.PROJECT_LIST) {
            throw new CommandException("Oops can't go back any further!");
        }
        UiEvent event = logic.getPreviousEvent();
        logger.severe("previous state: " + event.getState());
        if (!event.getProjectIndex().isEmpty()) {
            logic.setWorkingProject(logic.getFilteredProjectList().get(event.getProjectIndex().get()));
        }
        changeUiDisplay(event.getState());
        switch (event.getState()) {
        case PROJECT_LIST:
            if (!logic.getWorkingProject().isEmpty()) {
                logic.removeWorkingProject();
            }
            if (temp != State.ADDRESS_BOOK) {
                resultDisplay.setFeedbackToUser("You've checked out of the project!");
            }
            break;

        case PROJECT_OVERVIEW:
            resultDisplay.setFeedbackToUser(String.format(MESSAGE_CHECKOUT_SUCCESS, logic.getFilteredProjectList().get(event.getProjectIndex().get()).toString()));
            break;

        default:
            assert false : "Unrecognised previous state";
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, IllegalValueException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            String commandWord = commandResult.getCommandWord();
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isBack()) {
                handleBack();
            }

            // Only change Ui if certain command demands it
            if (commandResult.changeNeeded()) {
                State nextState = stateOf(commandWord);
                if (logic.getWorkingProject().isEmpty()) {
                    if (!nextState.equals(currentState)) {
                        logic.addUiEvent(new UiEvent(nextState, Optional.empty()));
                    }
                } else {
                    int projectIndex = logic.getFilteredProjectList().indexOf(logic.getWorkingProject().get());
                    if (!nextState.equals(currentState)) {
                        logic.addUiEvent(new UiEvent(nextState, Optional.of(projectIndex)));
                    }
                }
                changeUiDisplay(nextState);
            } else {
                // if not needed refresh current page unless is back command
                if (!commandWord.equals("back")) {
                    changeUiDisplay(currentState);
                }
            }

            return commandResult;
        } catch (CommandException | IllegalValueException e) {
            logger.info("Invalid command: " + commandText);
            logger.info(e.getMessage());
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    private void changeUiDisplay(State nextState) {
        switch (nextState) {
        case ADDRESS_BOOK:
            projectListPanelPlaceholder.getChildren().setAll(personListPanel.getRoot());
            currentState = nextState;
            break;

        case PROJECT_LIST:
            projectListPanelPlaceholder.getChildren().setAll(projectListPanel.getRoot());
            logic.removeWorkingProject();
            currentState = nextState;
            break;

        case PROJECT_OVERVIEW:
            projectOverview = new ProjectOverview(logic.getFilteredProjectList(), logic.getWorkingProject().get());
            projectListPanelPlaceholder.getChildren().setAll(projectOverview.getRoot());
            currentState = nextState;
            break;

        case PROJECT_FINANCE:
            budgetListPanel = new BudgetListPanel(logic.getWorkingProject().get().getFinance().getBudgetObservableList());
            projectListPanelPlaceholder.getChildren().setAll(budgetListPanel.getRoot());
            currentState = nextState;
            break;

        case PERFORMANCE_OVERVIEW:
            performanceOverviewCard = new PerformanceOverviewCard(logic.getPerformanceOverview());
            projectListPanelPlaceholder.getChildren().setAll(performanceOverviewCard.getRoot());
            break;

        case SHOW_TIMETABLE:
            timetablePanel = new ShowTimetablePanel(logic.getWorkingProject().get().getGeneratedTimetable());
            projectListPanelPlaceholder.getChildren().setAll(timetablePanel.getRoot());
            currentState = nextState;
            break;

        default:
            assert false : "Unrecognised state";
        }
    }

    private State stateOf(String commandWord) {
        State state = State.PROJECT_LIST;
        switch (commandWord) {
        case AddProjectCommand.COMMAND_WORD:
            state = State.PROJECT_LIST;
            break;

        case AddBudgetCommand.COMMAND_WORD:

        case AddFromContactsCommand.COMMAND_WORD:

        case AddMemberCommand.COMMAND_WORD:

        case AddProjectMeetingCommand.COMMAND_WORD:

        case AddTaskCommand.COMMAND_WORD:

        case AssignTaskCommand.COMMAND_WORD:

        case DeleteProjectMeetingCommand.COMMAND_WORD:

        case DeleteTaskCommand.COMMAND_WORD:

        case EditTaskCommand.COMMAND_WORD:

        case MarkAttendanceCommand.COMMAND_WORD:

        case RemoveMemberCommand.COMMAND_WORD:

        case SortMeetingCommand.COMMAND_WORD:

        case SortTaskCommand.COMMAND_WORD:

        case UnassignTaskCommand.COMMAND_WORD:

        case CheckoutCommand.COMMAND_WORD:
            state = State.PROJECT_OVERVIEW;
            break;

        case AddSpendingCommand.COMMAND_WORD:

        case SortSpendingCommand.COMMAND_WORD:

        case DeleteBudgetCommand.COMMAND_WORD:

        case ListBudgetCommand.COMMAND_WORD:
            state = State.PROJECT_FINANCE;
            break;

        case AddCommand.COMMAND_WORD:

        case AddProfilePictureCommand.COMMAND_WORD:

        case DeleteCommand.COMMAND_WORD:

        case EditCommand.COMMAND_WORD:

        case FindCommand.COMMAND_WORD:

        case ListCommand.COMMAND_WORD:
            state = State.ADDRESS_BOOK;
            break;

        case ShowPerformanceOverviewCommand.COMMAND_WORD:
            state = State.PERFORMANCE_OVERVIEW;
            break;

        case GenerateSlotCommand.COMMAND_WORD:
            state = State.SHOW_TIMETABLE;
            break;

        default:
            assert false : "Unrecognised Command";
        }

        return state;
    }
}
