package seedu.address.ui;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.lesson.Lesson;
import seedu.address.ui.scheduler.Scheduler;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    public static final String ALERT_SOUND_PATH = "/alert.wav";
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private StudentListPanel studentListPanel;
    private AssignmentListPanel assignmentListPanel;
    private ClassroomListPanel classroomListPanel;
    private Classroom currentClassroom;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ReminderListPanel monReminderListPanel;
    private ReminderListPanel tueReminderListPanel;
    private ReminderListPanel wedReminderListPanel;
    private ReminderListPanel thurReminderListPanel;
    private ReminderListPanel friReminderListPanel;
    private ReminderListPanel satReminderListPanel;
    private ReminderListPanel sunReminderListPanel;

    private ListChangeListener<Lesson> listener;

    @FXML
    private TabPane lessonTabPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane studentListPanelPlaceholder;

    @FXML
    private StackPane combinedListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane monReminderListPanelPlaceholder;

    @FXML
    private StackPane tueReminderListPanelPlaceholder;

    @FXML
    private StackPane wedReminderListPanelPlaceholder;

    @FXML
    private StackPane thurReminderListPanelPlaceholder;

    @FXML
    private StackPane friReminderListPanelPlaceholder;

    @FXML
    private StackPane satReminderListPanelPlaceholder;

    @FXML
    private StackPane sunReminderListPanelPlaceholder;

    @FXML
    private StackPane assignmentListPanelPlaceholder;

    @FXML
    private StackPane classroomListPanelPlaceholder;

    @FXML
    private StackPane currentClassroomListPanelPlaceholder;

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

        listenToLesson();

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

        studentListPanel = new StudentListPanel(logic.getFilteredStudentList());
        //studentListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());

        assignmentListPanel = new AssignmentListPanel(logic.getFilteredAssignmentList());
        //assignmentListPanelPlaceholder.getChildren().add(assignmentListPanel.getRoot());

        combinedListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());
        currentClassroom = logic.getNotebook().getCurrentClassroom();
        classroomListPanel = new ClassroomListPanel(logic.getClassroomList(), currentClassroom);
        classroomListPanelPlaceholder.getChildren().add(classroomListPanel.getRoot());

        //reminderListPanel = new ReminderListPanel(logic.getFilteredLessonList());
        //reminderListPanelPlaceholder.getChildren().add(reminderListPanel.getRoot());
        createLessonTabs();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getNotebookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBox.linkResultsDisplay(resultDisplay);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Creates the tabs that will separate lessons based on the days of the week.
     */
    public void createLessonTabs() {
        ObservableList<Tab> tabs = lessonTabPanel.getTabs();

        Tab mon = tabs.get(0);
        monReminderListPanel = new ReminderListPanel(logic.getFilteredLessonWeekList().get(0)
                .asUnmodifiableObservableList());
        monReminderListPanelPlaceholder.getChildren().add(monReminderListPanel.getRoot());
        mon.setContent(monReminderListPanelPlaceholder);

        Tab tue = tabs.get(1);
        tueReminderListPanel = new ReminderListPanel(logic.getFilteredLessonWeekList().get(1)
                .asUnmodifiableObservableList());
        tueReminderListPanelPlaceholder.getChildren().add(tueReminderListPanel.getRoot());
        tue.setContent(tueReminderListPanelPlaceholder);

        Tab wed = tabs.get(2);
        wedReminderListPanel = new ReminderListPanel(logic.getFilteredLessonWeekList().get(2)
                .asUnmodifiableObservableList());
        wedReminderListPanelPlaceholder.getChildren().add(wedReminderListPanel.getRoot());
        wed.setContent(wedReminderListPanelPlaceholder);

        Tab thur = tabs.get(3);
        thurReminderListPanel = new ReminderListPanel(logic.getFilteredLessonWeekList().get(3)
                .asUnmodifiableObservableList());
        thurReminderListPanelPlaceholder.getChildren().add(thurReminderListPanel.getRoot());
        thur.setContent(thurReminderListPanelPlaceholder);

        Tab fri = tabs.get(4);
        friReminderListPanel = new ReminderListPanel(logic.getFilteredLessonWeekList().get(4)
                .asUnmodifiableObservableList());
        friReminderListPanelPlaceholder.getChildren().add(friReminderListPanel.getRoot());
        fri.setContent(friReminderListPanelPlaceholder);

        Tab sat = tabs.get(5);
        satReminderListPanel = new ReminderListPanel(logic.getFilteredLessonWeekList().get(5)
                .asUnmodifiableObservableList());
        satReminderListPanelPlaceholder.getChildren().add(satReminderListPanel.getRoot());
        sat.setContent(satReminderListPanelPlaceholder);

        Tab sun = tabs.get(6);
        sunReminderListPanel = new ReminderListPanel(logic.getFilteredLessonWeekList().get(6)
                .asUnmodifiableObservableList());
        sunReminderListPanelPlaceholder.getChildren().add(sunReminderListPanel.getRoot());
        sun.setContent(sunReminderListPanelPlaceholder);
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
        //logic.getFilteredLessonList().removeListener(listener);
    }

    public StudentListPanel getStudentListPanel() {
        return studentListPanel;
    }
    //public ReminderListPanel getReminderListPanel() {
    //return reminderListPanel;
    //}

    public AssignmentListPanel getAssignmentListPanel() {
        return assignmentListPanel;
    }

    //@@author SebastianLie
    /**
     * opens filechooser and returns
     * path of file, if cancelled, throws
     * nullpointer exception
     * @return String
     * @throws NullPointerException
     */
    public String openFileChooser() throws NullPointerException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        return file.toURI().toString();
    }

    //@@author SebastianLie
    /**
     * checks if upload command called
     * adds name of file to command
     * filename: f/file:filepath
     * @param commandText
     * @return commandText
     */
    private String uploadCommandCheck(String commandText) {
        if (commandText.length() > 7 && commandText.substring(0, 6).equals("upload")) {
            String filePath = openFileChooser();
            commandText = commandText + " " + "f/" + filePath;
        }
        return commandText;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            commandText = uploadCommandCheck(commandText);
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            updateStudentsAndAssignments();
            if (logic.isDisplayStudents()) {
                combinedListPanelPlaceholder.getChildren().clear();
                combinedListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());
            } else {
                combinedListPanelPlaceholder.getChildren().clear();
                combinedListPanelPlaceholder.getChildren().add(assignmentListPanel.getRoot());
            }

            currentClassroom = logic.getNotebook().getCurrentClassroom();
            classroomListPanel = new ClassroomListPanel(logic.getClassroomList(), currentClassroom);
            classroomListPanelPlaceholder.getChildren().add(classroomListPanel.getRoot());

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
        } catch (NullPointerException e) {
            logger.info("Null pointer exception.");
            resultDisplay.setFeedbackToUser("Upload operation cancelled.");
            throw e;
        }
    }

    /**
     * Updates the window with the updated students and assignments.
     */
    public void updateStudentsAndAssignments() {
        studentListPanel = new StudentListPanel(logic.getFilteredStudentList());
        assignmentListPanel = new AssignmentListPanel(logic.getFilteredAssignmentList());
        //combinedListPanelPlaceholder.getChildren().clear();
        //combinedListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());
        //assignmentListPanelPlaceholder.getChildren().clear();
        //assignmentListPanelPlaceholder.getChildren().add(assignmentListPanel.getRoot());
    }

    /**
     * schedules a lesson.
     * @param lesson Lesson object.
     */
    public void createSchedule(Lesson lesson) {
        Scheduler scheduler = new Scheduler(lesson);
        scheduler.scheduleLesson(new Runnable() {
            @Override
            public void run() {
                logger.info("creating countdown");
                countDownAlert("You have a lesson", lesson.toString());
            }
        });
    }

    /**
     * method to add a listener to lesson observable list.
     * whenever a lesson is added to the list, a scheduler is created.
     */
    public void listenToLesson() {
        logger.info("listening to lesson");
        ObservableList<Lesson> lessons = logic.getFilteredLessonList();
        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            createSchedule(lesson);
        }
        listener = new ListChangeListener<Lesson>() {
            @Override
            public void onChanged(Change<? extends Lesson> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (Object addedItem : c.getAddedSubList()) {
                            logger.info("creating scheduler");
                            createSchedule((Lesson) addedItem);
                        }
                    }
                }
            }
        };
        lessons.addListener(listener);
    }

    //@@author SebastianLie
    /**
     * alert for scheduler.
     * sets properties of alert then
     * plays sound file and shows alert dialog
     */
    public void countDownAlert(String reminderType, String reminderDetails) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
                    alert.initOwner(getPrimaryStage());
                    //alert.initOwner(new Stage());
                    alert.setTitle("Reminder!");
                    alert.setHeaderText(reminderType);
                    alert.setContentText(reminderDetails);
                    playSound();
                    alert.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //@@author SebastianLie
    /**
     * handles playing alert audio for scheduled alert.
     * gets .wav file from resource folder as input stream,
     * then open and play.
     */
    private void playSound() {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(ALERT_SOUND_PATH);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
            Clip sound = AudioSystem.getClip();
            sound.open(audioStream);
            sound.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
