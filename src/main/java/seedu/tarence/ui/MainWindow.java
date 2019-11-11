package seedu.tarence.ui;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.logic.Logic;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.DisplayFormat;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.Tutorial;

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
    private TutorialListPanel tutorialListPanel;
    private DefaultPanel defaultPanel;
    private AssignmentListPanel assignmentListPanel;
    private AssignmentTablePanel assignmentTablePanel;
    private AssignmentStatisticsPanel assignmentStatisticsPanel;
    private AttendancePanel attendancePanel;
    private StudentListPanel studentListPanel;
    private ModuleListPanel moduleListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private CommandBox commandBox;

    @FXML
    private TabPane listTabPane;

    @FXML
    private TabPane displayTabPane;

    @FXML
    private Tab tutorialsTab;

    @FXML
    private Tab studentsTab;

    @FXML
    private Tab modulesTab;

    @FXML
    private Tab attendanceTab;

    @FXML
    private Tab assignmentTab;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane tutorialListPanelPlaceholder;

    @FXML
    private StackPane studentListPanelPlaceholder;

    @FXML
    private StackPane moduleListPanelPlaceholder;

    @FXML
    private StackPane assignmentPanelPlaceholder;

    @FXML
    private StackPane attendancePanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Set listeners for window width changing
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            updateCommandBoxWindowWidth((Double) newValue);
        });

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
        // Set default stackpane
        assignmentListPanel = new AssignmentListPanel();
        attendancePanel = new AttendancePanel();
        assignmentTablePanel = new AssignmentTablePanel();
        assignmentStatisticsPanel = new AssignmentStatisticsPanel();
        defaultPanel = new DefaultPanel();
        assignmentPanelPlaceholder.getChildren().add(defaultPanel.getPane());
        attendancePanelPlaceholder.getChildren().add(new AttendancePanel().getPane());

        moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());

        studentListPanel = new StudentListPanel(logic.getFilteredStudentList());
        studentListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());

        tutorialListPanel = new TutorialListPanel(logic.getFilteredTutorialList());
        tutorialListPanelPlaceholder.getChildren().add(tutorialListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getApplicationFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand, this::executeAutocomplete, this::executeNextSuggestion,
                this::executeInputChanged, this::getPastInput, this::focusOnInputField, this::executeScrollPanel);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        updateCommandBoxWindowWidth(primaryStage.getWidth());
        // initialise autocomplete suggestions for command words
        executeAutocomplete("");
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
     * Selects the Student Tab
     */
    public void handleStudentTabSelected() {
        if (!studentsTab.isSelected()) {
            listTabPane.getSelectionModel().select(studentsTab);
        }
    }

    /**
     * Selects the module Tab
     */
    public void handleModuleTabSelected() {
        if (!modulesTab.isSelected()) {
            listTabPane.getSelectionModel().select(modulesTab);
        }
    }

    /**
     * Selects the tutorial Tab
     */
    public void handleTutorialTabSelected() {
        if (!tutorialsTab.isSelected()) {
            listTabPane.getSelectionModel().select(tutorialsTab);
        }
    }

    /**
     * Selects the attendance Tab
     */
    public void handleAttendanceTabSelected() {
        if (!attendanceTab.isSelected()) {
            displayTabPane.getSelectionModel().select(attendanceTab);
        }
    }

    /**
     * Executes the command and returns the result.
     * @see seedu.tarence.logic.Logic#execute(String)
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

            if (!isDisplayCommand(commandResult)) {
                logger.info("GUI ---- Displaying default panel");
                displayDefaultPanel();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Handles searching for autocomplete data and modifying the command box input field.
     */
    private CommandResult executeAutocomplete(String partialInput) {
        try {
            String autocompletedString = logic.autocomplete(partialInput);
            commandBox.setAutocompleteBox(autocompletedString);
            commandBox.setFocus();
        } catch (IndexOutOfBoundsException | ParseException e) {
            commandBox.clearAutocompleteBox();
            // resultDisplay.setFeedbackToUser(e.getMessage());
            commandBox.setFocus();
        }
        return new CommandResult("");
    }

    /**
     * Handles getting next autocomplete result.
     */
    private CommandResult executeNextSuggestion(String dummy) throws IndexOutOfBoundsException {
        try {
            commandBox.setAutocompleteBox(logic.getNextSuggestion());
        } catch (ParseException e) {
            commandBox.clearAutocompleteBox();
            commandBox.setFocus();
        }
        return new CommandResult("");
    }

    /**
     * Autocomplete helper method. Sets a flag indicating that autocomplete search data needs to be updated.
     */
    private CommandResult executeInputChanged(String direction) throws ParseException {
        logic.markInputChanged();
        return new CommandResult("");
    }

    /**
     * Handles the user clicking on the autocomplete display box.
     */
    private CommandResult focusOnInputField(String dummy) {
        commandBox.setFocus();
        return new CommandResult("");
    }

    /**
     * Handles getting past commands.
     */
    private CommandResult getPastInput(String arrowDirection) {
        String pastInput = logic.getPastInput(arrowDirection);
        if (pastInput != null) {
            commandBox.setInput(pastInput);
        }
        return new CommandResult("");
    }

    /**
     * Handles scrolling of student list panel.
     */
    private CommandResult executeScrollPanel(String direction) {
        Tab selectedTab = listTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == modulesTab) {
            moduleListPanel.scrollPanel(direction);
        } else if (selectedTab == studentsTab) {
            studentListPanel.scrollPanel(direction);
        } else { // tutorialsTab
            tutorialListPanel.scrollPanel(direction);
        }
        return new CommandResult("");
    }

    /**
     * Sets the displaypane to display the valid assignment format.
     * @param assignment - assignment to be displayed.
     * @param studentScores - scores of students and their info.
     * @param displayFormat - format to display.
     */
    private void displayAssignment(Assignment assignment, Map<Student, Integer> studentScores,
                                   DisplayFormat displayFormat) {
        assignmentPanelPlaceholder.getChildren().clear();
        switch(displayFormat) {
        case TABLE:
            logger.info("Displaying table :" + assignment.getAssignName());
            assignmentTablePanel.generateTable(studentScores);
            assignmentPanelPlaceholder.getChildren().add(assignmentTablePanel.getPane());
            break;
        case GRAPH:
        default:
            logger.info("Displaying graph :" + assignment.getAssignName());
            assignmentStatisticsPanel.generateGraph(studentScores, assignment);
            assignmentPanelPlaceholder.getChildren().add(assignmentStatisticsPanel.getPane());
        }
        if (!assignmentTab.isSelected()) {
            displayTabPane.getSelectionModel().select(assignmentTab);
        }
    }

    /**
     * Displays attendance table if the given tutorial is non null. Else displays default panel
     * @param tutorial - tutorial to display
     */
    private void displayAttendance(Tutorial tutorial) {
        attendancePanelPlaceholder.getChildren().clear();
        attendancePanel.generateTable(tutorial);
        attendancePanelPlaceholder.getChildren().add(attendancePanel.getPane());
    }

    /**
     * Updates the window width known to the CommandBox.
     */
    private void updateCommandBoxWindowWidth(double newWidth) {
        commandBox.updateWindowWidth(newWidth);
    }

    /**
     * Displays the list of assignments and swiches to the assignment tab.
     */
    private void displayAssignmentList(List<Assignment> assignments) {
        assignmentPanelPlaceholder.getChildren().clear();
        logger.info("Listing assignments");
        assignmentListPanel.generateList(assignments);
        assignmentPanelPlaceholder.getChildren().add(assignmentListPanel.getPane());
        if (!assignmentTab.isSelected()) {
            displayTabPane.getSelectionModel().select(assignmentTab);
        }
    }

    /**
     * Sets the GUI to display default pane
     */
    private void displayDefaultPanel() {
        if (!attendancePanel.isDefaultView()) {
            attendancePanelPlaceholder.getChildren().clear();
            attendancePanelPlaceholder.getChildren().add(defaultPanel.getPane());
        }
        if (!assignmentTablePanel.isDefaultView() || !assignmentStatisticsPanel.isDefaultView()
                || !assignmentListPanel.isDefaultView()) {
            assignmentPanelPlaceholder.getChildren().clear();
            assignmentPanelPlaceholder.getChildren().add(defaultPanel.getPane());
        }
    }

    /**
     * returns true if the command results has an object to display in the GUI and subsequently toggles GUI settings.
     */
    private boolean isDisplayCommand(CommandResult commandResult) {
        if (commandResult.isShowAttendance()) {
            logger.info("GUI --- Display Attendance");
            displayAttendance(commandResult.getTutorialAttendance());
            handleAttendanceTabSelected();
            return true;
        }

        if (commandResult.isAssignmentsDisplay()) {
            logger.info("GUI --- Display Assignments");
            displayAssignmentList(commandResult.getAssignmentsToDisplay());
            return true;
        }

        if (commandResult.isChangeTabs()) {
            logger.info("GUI --- Changing tab");
            switch(commandResult.getTabToDisplay()) {
            case MODULES:
                handleModuleTabSelected();
                return true;
            case STUDENTS:
                handleStudentTabSelected();
                return true;
            case TUTORIALS:
                handleTutorialTabSelected();
                return true;
            default:
                return false;
            }
        }

        if (commandResult.isAssignmentDisplay()) {
            logger.info("GUI --- Displaying Assignment");
            displayAssignment(commandResult.getAssignmentToDisplay(), commandResult.getStudentScores(),
                    commandResult.getAssignmentDisplayFormat());
            return true;
        }
        return false;
    }
}
