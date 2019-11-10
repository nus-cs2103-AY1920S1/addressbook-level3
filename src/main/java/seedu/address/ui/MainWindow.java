package seedu.address.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private LoginWindow loginWindow;
    private boolean isUnknown;

    // Independent Ui parts residing in this Ui container
    private EarningsListPanel earningsListPanel;
    private ReminderListPanel reminderListPanel;
    private PersonListPanel personListPanel;
    private TaskListPanel taskListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ReminderWindow reminderWindow;
    private ReminderBox reminderBox;
    private FullCalendarView fullCalendarView;
    private NotesListPanel notesListPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane reminderBoxPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Pane calendarPane;


    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        this.isUnknown = false;
        reminderWindow = new ReminderWindow(logic);
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
    void fillStudents() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        reminderBox = new ReminderBox();
        //reminderBoxPlaceholder.getChildren().add(reminderBox.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTutorAidFilePath());
        statusBarFooter.setTab("Students Tab");
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills up all the placeholders with earnings list in the window.
     */
    void fillEarnings() {
        earningsListPanel = new EarningsListPanel(logic.getFilteredEarningsList());
        personListPanelPlaceholder.getChildren().add(earningsListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        reminderBox = new ReminderBox();
        //reminderBoxPlaceholder.getChildren().add(reminderBox.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTutorAidFilePath());
        statusBarFooter.setTab("Earnings Tab");
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills up all the placeholders with reminders list in the window.
     */
    void fillReminders() {
        reminderListPanel = new ReminderListPanel(logic.getFilteredReminderList());
        personListPanelPlaceholder.getChildren().add(reminderListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        reminderBox = new ReminderBox();
        //reminderBoxPlaceholder.getChildren().add(reminderBox.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTutorAidFilePath());
        statusBarFooter.setTab("Reminders Tab");
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillTasks() {
        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        personListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        reminderBox = new ReminderBox();
        //reminderBoxPlaceholder.getChildren().add(reminderBox.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTutorAidFilePath());
        statusBarFooter.setTab("Tasks Tab");
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillCalendar() throws IOException {

        fullCalendarView = new FullCalendarView(YearMonth.now());
        personListPanelPlaceholder.getChildren().add(fullCalendarView.getRoot());
        personListPanelPlaceholder.getChildren().add(fullCalendarView.getView());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        reminderBox = new ReminderBox();
        //reminderBoxPlaceholder.getChildren().add(reminderBox.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTutorAidFilePath());
        statusBarFooter.setTab("Tasks Tab");
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillNotes() {
        notesListPanel = new NotesListPanel(logic.getFilteredNotesList());
        personListPanelPlaceholder.getChildren().add(notesListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        reminderBox = new ReminderBox();

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTutorAidFilePath());
        statusBarFooter.setTab("Notes Tab");
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

    /**
     * Opens the reminder window or focuses on it if it's already opened.
     */
    @FXML
    public void handleReminderBox() {
        if (!reminderWindow.isShowing()) {
            reminderWindow.show();
        } else {
            reminderWindow.focus();
        }
    }

    /**
     * Switches to the reminder tab.
     */
    @FXML
    public void handleReminder() throws CommandException, ParseException {
        try {
            String userCommand = "change_tab tab/reminders";
            executeCommand(userCommand);
        } catch (CommandException e) {
            logger.info("User attempting to change tab during the learning of an unknown command!");
        }
    }

    /**
     * Switches to the students tab.
     */
    @FXML
    public void handleStudents() throws CommandException, ParseException {
        try {
            String userCommand = "list";
            executeCommand(userCommand);
        } catch (CommandException e) {
            logger.info("User attempting to change tab during the learning of an unknown command!");
        }
    }

    /**
     * Switches to the earnings tab.
     */
    @FXML
    public void handleEarnings() throws ParseException, CommandException {
        try {
            String userCommand = "change_tab tab/earnings";
            executeCommand(userCommand);
        } catch (CommandException e) {
            logger.info("User attempting to change tab during the learning of an unknown command!");
        }

    }

    /**
     * Switches to the calendar tab sorted by the date.
     */
    @FXML
    public void handleCalendarDate() throws ParseException, CommandException {
        try {
            String userCommand = "change_tab tab/calendar";
            executeCommand(userCommand);
        } catch (CommandException e) {
            logger.info("User attempting to change tab during the learning of an unknown command!");
        }
    }

    /**
     * Switches to the calendar tab sorted by the task.
     */
    @FXML
    public void handleCalendarTask() throws ParseException, CommandException {
        try {
            String userCommand = "change_tab tab/task";
            executeCommand(userCommand);
        } catch (CommandException e) {
            logger.info("User attempting to change tab during the learning of an unknown command!");
        }
    }

    /**
     * Switches to the notepad tab.
     */
    @FXML
    public void handleNotes() throws ParseException, CommandException {
        try {
            String userCommand = "change_tab tab/notepad";
            executeCommand(userCommand);
        } catch (CommandException e) {
            logger.info("User attempting to change tab during the learning of an unknown command!");
        }
    }

    /**
     * Switches to the calendar tab sorted by the task.
     */
    @FXML
    public void findTaskByDate(LocalDate date) throws ParseException, CommandException {
        try {
            System.out.println(date);
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String userCommand = "find_task_by_date " + formattedDate;
            executeCommand(userCommand);
        } catch (CommandException e) {
            logger.info("User attempting to change tab during the learning of an unknown command!");
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

    private void setUnknownFalse() {
        this.isUnknown = false;
    }

    private void setUnknownTrue() {
        this.isUnknown = true;
    }

    public EarningsListPanel getEarningsListPanel() {
        return earningsListPanel;
    }

    public ReminderListPanel getReminderListPanel() {
        return reminderListPanel;
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    /**
     * Switches to the calendar tab sorted by the date.
     */
    public void deleteNoteButton(int index) throws ParseException, CommandException {
        try {
            String userCommand = "deletenote " + index;
            executeCommand(userCommand);
        } catch (CommandException e) {
            logger.info("User attempting to change tab during the learning of an unknown command!");
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String, boolean)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {

            CommandResult commandResult = logic.execute(commandText, this.isUnknown);
            if (this.isUnknown) {
                if (!commandResult.isUnknown()) {
                    setUnknownFalse();
                }
            }

            if (commandResult.isUnknown()) {
                setUnknownTrue();
            } else if (commandResult.isExit()) {
                handleExit();
            } else if (commandResult.isShowHelp()) {
                handleHelp();
            } else if (commandResult.isPersons()) {
                UiManager.startStudentProfile();
            } else if (commandResult.isTasks()) {
                UiManager.startTasks();
            } else if (commandResult.isEarnings()) {
                UiManager.startEarnings();
            } else if (commandResult.isNotes()) {
                UiManager.startNotes();
            } else if (commandResult.isReminder()) {
                UiManager.startReminders();
            } else if (commandResult.isLogin()) {
                UiManager.startLoginWindow();
            }

            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            reminderBox.setFeedbackToUser(commandResult.getFeedbackToUser());
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            reminderBox.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    public void hide() {
        primaryStage.hide();
    }

    /**
     * Shows the login window.
     */
    public void showLogin() {
        loginWindow = new LoginWindow(new Stage(), logic);
        loginWindow.show();
    }
}
