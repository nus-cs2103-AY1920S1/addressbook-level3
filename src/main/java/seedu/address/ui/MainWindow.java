package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Student;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where
 * other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    //private PersonListPanel personListPanel;
    private StudentListPanel studentListPanel;
    private QuestionListPanel questionListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private SlideshowWindow slideShowWindow;
    private GroupWindow groupWindow;
    private StatsReportWindow statsReportWindow;
    private NotesListPanel notesListPanel;
    private EventSchedulePanel eventSchedulePanel;
    private Node studentPanelNode;
    private Node eventPaneNode;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane mainPanelPlaceholder;

    @FXML
    private StackPane notesListPanelPlaceholder;

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

        setAccelerators();

        helpWindow = new HelpWindow();
        statsReportWindow = new StatsReportWindow();
        slideShowWindow = new SlideshowWindow(new Stage(), logic);
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
        studentListPanel = new StudentListPanel(logic.getFilteredStudentList());
        this.studentPanelNode = studentListPanel.getRoot();
        mainPanelPlaceholder.getChildren().add(studentPanelNode);

        questionListPanel = new QuestionListPanel(logic.getAllQuestions());
        mainPanelPlaceholder.getChildren().add(questionListPanel.getRoot());

        notesListPanel = new NotesListPanel(logic.getFilteredNotesList());
        notesListPanelPlaceholder.getChildren().add(notesListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        eventSchedulePanel = new EventSchedulePanel(logic.getVEventList());
        mainPanelPlaceholder.getChildren().add(eventSchedulePanel.getRoot());
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
     * Opens the group window or focuses on it if it's already opened.
     */
    @FXML
    public void handleGroup() {
        groupWindow = new GroupWindow();
        //get observable list of students in group and put inside.
        ObservableList<Student> students = logic.getStudentsInGroup();
        groupWindow.setStudentsInGroup(students);
        if (!groupWindow.isShowing()) {
            groupWindow.show();
        } else {
            groupWindow.focus();
        }
    }

    /**
     * Opens the slideshow window or focuses on it if it's already opened.
     */
    @FXML
    public void handleSlideshow() {
        if (!slideShowWindow.isShowing()) {
            slideShowWindow.show();
        } else {
            slideShowWindow.focus();
        }
    }

    /**
     * Opens the schedule window or focuses on it if it's already opened.
     */
    @FXML
    public void handleSchedule() {
        eventSchedulePanel.getRoot().toFront();
    }

    /**
     * Opens the schedule window or focuses on it if it's already opened.
     */
    @FXML
    public void handleStudent() {
        studentListPanel.getRoot().toFront();
    }

    /**
     * Opens the schedule window or focuses on it if it's already opened.
     */
    @FXML
    public void handleQuestion() {
        questionListPanel.getRoot().toFront();
    }

    /**
     * Opens the statistics report window or focuses on it if it's already opened.
     */
    @FXML
    public void handleStats() {
        StatisticsCard statsCard = new StatisticsCard(logic.getProcessedStatistics());
        statsReportWindow.setStatsCard(statsCard);
        if (!statsReportWindow.isShowing()) {
            statsReportWindow.show();
        } else {
            statsReportWindow.focus();
        }
    }

    /**
     * Show UI
     */
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
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText)
            throws CommandException, ParseException, IOException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            CommandResultType commandResultType = commandResult.getCommandResultType();
            switch (commandResultType) {

            case SHOW_HELP:
                handleHelp();
                break;
            case SHOW_SLIDESHOW:
                handleSlideshow();
                break;
            case EXIT:
                handleExit();
                break;
            case SHOW_SCHEDULE:
                eventSchedulePanel.updateScheduler();
                handleSchedule();
                break;
            case SHOW_STATISTIC:
                handleStats();
                break;
            case SHOW_GROUP:
                handleGroup();
                break;
            case SHOW_STUDENT:
                handleStudent();
                break;
            case SHOW_QUESTION:
                handleQuestion();
                break;
            default:
                break;
            }
            return commandResult;
        } catch (CommandException | ParseException | IOException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
