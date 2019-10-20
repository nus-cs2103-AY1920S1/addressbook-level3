package seedu.tarence.ui;

import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.logic.Logic;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Attendance;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

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
    private PersonListPanel personListPanel;
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
    private TableView attendancePlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane tutorialListPanelPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane studentListPanelPlaceholder;

    @FXML
    private StackPane moduleListPanelPlaceholder;

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

        attendancePlaceholder.setPlaceholder(getPlaceHolderLabel());
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
        //personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        //personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

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

        commandBox = new CommandBox(this::executeCommand, this::executeAutocomplete, this::executeInputChanged);
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
     * Displays the given attendance to the user
     * @param tutorialAttendance
     */
    public void showAttendance(Tutorial tutorialAttendance) {
        attendancePlaceholder.getItems().clear();
        try {
            ObservableList<String[]> observableAttendance = generateData(tutorialAttendance);
            attendancePlaceholder.setItems(observableAttendance);
            attendancePlaceholder.getColumns().setAll(createColumns());
            logger.info("successfully displayed:)");
        } catch (NullPointerException e) {
            attendancePlaceholder.getItems().clear();
        }
    }

    /**
     * Generates an observable list based on the given tutorial attendane
     * Solution below adopted from:
     * {https://stackoverflow.com/questions/41771098/how-to-plot-a-simple-double-matrix-into-tableview-in-javafx}
     */
    private ObservableList<String[]> generateData(Tutorial tutorialAttendance) {
        ObservableList<String[]> list = FXCollections.observableArrayList();

        String checkMark = Character.toString((char) 0x2713);
        String uncheckedSlot = "";
        int totalNumOfWeeks = 13;

        Set<Week> weeks = tutorialAttendance.getTimeTable().getWeeks();
        List<Student> students = tutorialAttendance.getStudents();
        Attendance attendance = tutorialAttendance.getAttendance();

        // TODO: to be refactored
        for (Student student : students) {
            List<String> attendanceList = new ArrayList<>();
            attendanceList.add(student.getName().toString());
            for (int i = 0; i < totalNumOfWeeks; i++) {
                Week week = new Week(i + 1);
                if (weeks.contains(week) && attendance.isPresent(week, student)) {
                    attendanceList.add(checkMark);
                } else {
                    attendanceList.add(uncheckedSlot);
                }
            }
            String[] arr = attendanceList.toArray(new String[0]);
            list.add(arr);
        }
        return list;
    }

    private List<TableColumn<String[], String>> createColumns() {
        return IntStream.range(0, 14)
                .mapToObj(this::createColumn)
                .collect(Collectors.toList());
    }

    /**
     * Creates and returns a valid table column containing information from each column
     * of the attendance
     */
    private TableColumn<String[], String> createColumn(int col) {
        String header;
        if (col == 0) {
            header = "Name";
        } else {
            header = Integer.toString(col);
        }
        TableColumn<String[], String> column = new TableColumn<>(header);
        column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()[col]));

        return column;
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

    private Label getPlaceHolderLabel() {
        String defaultMessage = "Welcome to T.A.rence \uD83D\uDE0A\n"
                + "To see all user commands, type \"help\"\n"
                + "To view a class attendance, type:\n"
                + "displayAttendance "
                + PREFIX_TUTORIAL_NAME + "TUTORIAL_NAME "
                + PREFIX_MODULE + "MODULE_CODE \n";

        Label placeholder = new Label(defaultMessage);
        return placeholder;
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

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.tarence.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());

            logger.info("display Tab " + commandResult.isChangeTabs());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            showAttendance(commandResult.getTutorialAttendance());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowAttendance()) {
                handleAttendanceTabSelected();
            }

            if (commandResult.isChangeTabs()) {
                switch(commandResult.getTabToDisplay()) {
                case MODULES:
                    handleModuleTabSelected();
                    break;
                case STUDENTS:
                    handleStudentTabSelected();
                    break;
                default:
                    handleTutorialTabSelected();
                }
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
            resultDisplay.setFeedbackToUser(autocompletedString);
            commandBox.setInput(autocompletedString);
        } catch (IndexOutOfBoundsException | ParseException e) {
            resultDisplay.setFeedbackToUser(e.getMessage());
            commandBox.setFocus();
        }
        return new CommandResult("");
    }

    /**
     * Autocomplete helper method. Sets a flag indicating that autocomplete search data needs to be updated.
     */
    private CommandResult executeInputChanged(String dummy) throws ParseException {
        logic.markInputChanged();
        return new CommandResult("");
    }

}
