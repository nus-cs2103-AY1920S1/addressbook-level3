package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.WritableImage;
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
import seedu.address.model.event.EventScheduleViewMode;
import seedu.address.model.student.Student;
import seedu.address.storage.printable.StatisticsPrintable;
import seedu.address.ui.util.DisplayType;


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
    private QuestionListPanel searchQuestionListPanel;
    private QuizQuestionListPanel quizQuestionListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private SlideshowWindow slideShowWindow;
    private QuizWindow quizWindow;
    private GroupWindow groupWindow;
    private StatsReportWindow statsReportWindow;
    private NotesListPanel notesListPanel;
    private EventSchedulePanel eventSchedulePanel;

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
        mainPanelPlaceholder.getChildren().add(studentListPanel.getRoot());

        questionListPanel = new QuestionListPanel(logic.getAllQuestions(), false);
        mainPanelPlaceholder.getChildren().add(questionListPanel.getRoot());

        searchQuestionListPanel = new QuestionListPanel(logic.getSearchQuestions(), true);
        mainPanelPlaceholder.getChildren().add(searchQuestionListPanel.getRoot());

        notesListPanel = new NotesListPanel(logic.getFilteredNotesList());
        notesListPanelPlaceholder.getChildren().add(notesListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        FooterBar footerBar = new FooterBar();
        statusbarPlaceholder.getChildren().add(footerBar.getRoot());

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
     * Opens the quiz window or focuses on it if it's already opened.
     */
    @FXML
    public void handleQuizQuestions() {
        quizQuestionListPanel = new QuizQuestionListPanel(logic.getQuestionsInQuiz(), DisplayType.QUESTIONS);
        mainPanelPlaceholder.getChildren().add(quizQuestionListPanel.getRoot());
        quizQuestionListPanel.getRoot().toFront();
    }

    /**
     * Opens the quiz window or focuses on it if it's already opened.
     */
    @FXML
    public void handleQuizAnswers() {
        quizQuestionListPanel = new QuizQuestionListPanel(logic.getQuestionsInQuiz(), DisplayType.ANSWERS);
        mainPanelPlaceholder.getChildren().add(quizQuestionListPanel.getRoot());
        quizQuestionListPanel.getRoot().toFront();
    }

    /**
     * Opens the quiz window or focuses on it if it's already opened.
     */
    @FXML
    public void handleQuizAll() {
        quizQuestionListPanel = new QuizQuestionListPanel(logic.getQuestionsInQuiz(), DisplayType.ALL);
        mainPanelPlaceholder.getChildren().add(quizQuestionListPanel.getRoot());
        quizQuestionListPanel.getRoot().toFront();
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
     * Opens the schedule panel or focuses on it if it's already opened.
     */
    @FXML
    public void handleSchedule() {
        eventSchedulePanel.updateScheduler();
        if (logic.getScheduleViewMode().equals(EventScheduleViewMode.DAILY)) {
            eventSchedulePanel.setDailySkin();
        } else if (logic.getScheduleViewMode().equals(EventScheduleViewMode.WEEKLY)) {
            eventSchedulePanel.setWeeklySkin();
        }
        eventSchedulePanel.setDisplayedDateTime(logic.getEventScheduleTargetDateTime());
        eventSchedulePanel.getRoot().toFront();
    }

    /**
     * Opens the student panel or focuses on it if it's already opened.
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
     * Opens the schedule window or focuses on it if it's already opened.
     */
    @FXML
    public void handleQuestionSearch() {
        searchQuestionListPanel.getRoot().toFront();
    }

    /**
     * Opens the statistics report window or focuses on it if it's already opened. Saves printable
     * report if specified by user.
     */
    @FXML
    public void handleStats(CommandResultType commandResultType) throws IOException {
        statsReportWindow = new StatsReportWindow();
        StatisticsCard statsCard = new StatisticsCard(logic.getProcessedStatistics());
        statsReportWindow.setStatsCard(statsCard);
        if (!statsReportWindow.isShowing()) {
            statsReportWindow.show();
        } else {
            statsReportWindow.focus();
        }
        if (commandResultType.isPrintable()) {
            makePrintableStatistics(commandResultType.getPrintableName());
        }
    }

    /**
     * Saves a printable statistics report file with the specified fileName.
     */
    public void makePrintableStatistics(String fileName) throws IOException {
        WritableImage image = statsReportWindow.getStatisticsPanelPlaceholder()
                .snapshot(new SnapshotParameters(), null);
        logic.savePrintable(new StatisticsPrintable(image, fileName));
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
                handleSchedule();
                break;
            case SHOW_STATISTIC:
                handleStats(commandResultType);
                break;
            case SHOW_QUIZ_QUESTIONS:
                handleQuizQuestions();
                break;
            case SHOW_QUIZ_ANSWERS:
                handleQuizAnswers();
                break;
            case SHOW_QUIZ_ALL:
                handleQuizAll();
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
            case SHOW_QUESTION_SEARCH:
                handleQuestionSearch();
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
