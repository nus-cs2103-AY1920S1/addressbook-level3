package seedu.tarence.ui;

import static java.util.Objects.requireNonNull;
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
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Attendance;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

/**
 * Panel containing table display for attendance.
 */
public class AttendancePanel extends UiPart<Region> {
    private static final String FXML = "AttendancePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AssignmentTablePanel.class);
    private boolean isDefault;

    @FXML
    private Pane panel;

    @FXML
    private TableView attendancePlaceholder;

    /**
     * Constructor that sets panel to default display
     */
    public AttendancePanel() {
        super(FXML);
        this.panel = new StackPane();
        setDefaultPlaceHolder();
        panel.getChildren().add(attendancePlaceholder);
        logger.info("Display default attendance");
    }

    /**
     * Generates the table based on the given tutorial
     * @param tutorial - tutorial containing attendance to display
     */
    public void generateTable(Tutorial tutorial) {
        requireNonNull(tutorial);
        this.panel.getChildren().clear();
        try {
            ObservableList<String[]> observableAttendance = generateData(tutorial);
            attendancePlaceholder.setItems(observableAttendance);
            attendancePlaceholder.getColumns().setAll(createColumns());
            logger.info("successfully displayed attendance:)");
            panel.getChildren().add(attendancePlaceholder);
            this.isDefault = false;
        } catch (NullPointerException e) {
            setDefaultPlaceHolder();
        }
    }

    /**
     * Returns default panel with user info
     */
    private void setDefaultPlaceHolder() {
        String defaultMessage = "Welcome to T.A.rence \uD83D\uDE0A\n"
                + "To see all user commands, type \"help\"\n"
                + "To view a class attendance, type:\n"
                + "displayAttendance "
                + PREFIX_TUTORIAL_NAME + "TUTORIAL_NAME "
                + PREFIX_MODULE + "MODULE_CODE \n";

        Label placeholder = new Label(defaultMessage);
        attendancePlaceholder.setPlaceholder(placeholder);
        this.isDefault = true;
    }

    /**
     * Returns true is the default view is being displayed.
     */
    public boolean isDefaultView() {
        return this.isDefault;
    }

    /**
     * @return Pane with attendance table to display.
     */
    public Pane getPane() {
        return this.panel;
    }

    /**
     * Generates an observable list based on the given tutorial attendane
     * Solution below adopted from:
     * {https://stackoverflow.com/questions/41771098/how-to-plot-a-simple-double-matrix-into-tableview-in-javafx}
     */
    private ObservableList<String[]> generateData(Tutorial tutorialAttendance) {
        logger.info("Tutorial to display: " + tutorialAttendance.getTutName());
        ObservableList<String[]> list = FXCollections.observableArrayList();

        String checkMark = Character.toString((char) 0x2713);
        String uncheckedSlot = "";
        int totalNumOfWeeks = 13;

        Set<Week> weeks = tutorialAttendance.getTimeTable().getWeeks();
        List<Student> students = tutorialAttendance.getStudents();
        Attendance attendance = tutorialAttendance.getAttendance();

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

}
