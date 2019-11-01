package seedu.tarence.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.model.student.Student;

/**
 * Panel containing table display for assignments.
 */
public class AssignmentTablePanel extends UiPart<Region> {
    private static final String FXML = "AssignmentTablePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AssignmentTablePanel.class);
    private long lowerPercentile;
    private long upperPercentile;

    @FXML
    private Pane defaultPanel;

    @FXML
    private TableView assignmentPlaceholder;

    public AssignmentTablePanel() {
        super(FXML);
        this.defaultPanel = new StackPane();
        defaultPanel.getChildren().add(assignmentPlaceholder);
    }

    /**
     * Generates the table based on the given hashmap of results.
     * @param scores - hashmap of students and their scores)
     */
    public void generateTable(Map<Student, Integer> scores) {
        requireNonNull(scores);
        if (!isEmpty(scores)) {
            setStatistics(scores);
            setAssignmentTable(scores);
            assignmentPlaceholder.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } else {
            setDefaultPlaceHolderLabel();
        }
        defaultPanel.getChildren().clear();
        defaultPanel.getChildren().add(assignmentPlaceholder);
    }

    /**
     * @return Pane with attendance table to display.
     */
    public Pane getPane() {
        return this.defaultPanel;
    }

    /**
     * sets default placeholder. To be used only during exceptions.
     */
    private void setDefaultPlaceHolderLabel() {
        String defaultMessage = "No scores have been set for this\n"
                + "assignment yet :(";

        Label placeholder = new Label(defaultMessage);
        assignmentPlaceholder.setPlaceholder(placeholder);
    }

    /**
     * Fills up assignment tableview with hashmap data.
     * @param namesAndScores - hashmap containing the names and scores of the students.
     */
    private void setAssignmentTable(Map<Student, Integer> namesAndScores) {
        TableColumn<Map.Entry<Student, Integer>, String> names = new TableColumn<>("Name");
        names.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Student, Integer>,
                String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Student, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey().getName().toString());
            }
        });

        TableColumn<Map.Entry<Student, Integer>, Integer> scores = new TableColumn<>("Score");
        scores.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Student, Integer>, Integer>,
                ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<Student, Integer>, Integer> p) {
                return new SimpleIntegerProperty(Integer.valueOf(p.getValue().getValue())).asObject();
            }
        });
        scores.setCellFactory(new Callback<TableColumn<Map.Entry<Student, Integer>, Integer>,
                TableCell<Map.Entry<Student, Integer>, Integer>>() {
            public TableCell call(TableColumn param) {
                return new TableCell<Map.Entry<Student, Integer>, Integer>() {
                    @Override
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            this.setTextFill(Color.WHITE);
                            setText(item.toString());
                            if (item == -1) {
                                this.setStyle("-fx-background-color: " + "#606060FF");
                                setText("Score is not set");
                            } else if (item >= upperPercentile) {
                                this.setStyle("-fx-background-color: " + "#2BAE66FF");
                                setText(item.toString() + " (75th Percentile)");
                            } else if (item <= lowerPercentile) {
                                this.setStyle("-fx-background-color: " + "#E94B3CFF");
                                setText(item.toString() + " (25th Percentile)");
                            } else {
                                this.setStyle("-fx-background-color: " + "#ffa333");
                                setText(item.toString() + " (Average)");
                            }
                        }
                    }
                };
            }
        });

        ObservableList<Map.Entry<Student, Integer>> items =
                FXCollections.observableArrayList(namesAndScores.entrySet());
        assignmentPlaceholder = new TableView<Map.Entry<Student, Integer>>(items);
        assignmentPlaceholder.getColumns().setAll(names, scores);
    }

    /**
     * sets the 75th and 25th percentile scores.
     * @param namesAndScores - hashmap containing the names and scores of the students.
     */
    private void setStatistics(Map<Student, Integer> namesAndScores) {
        this.upperPercentile = calcPercentile(namesAndScores, 75);
        this.lowerPercentile = calcPercentile(namesAndScores, 25);
        logger.info("Upper percentile: " + upperPercentile);
        logger.info("Lower percentile: " + lowerPercentile);
    }

    /**
     * calculates the given percentile from the hashmap values.
     * @param map - hashmap containing the names and scores of the students.
     * @param percentile - percentile to calculate.
     */
    private long calcPercentile(Map<Student, Integer> map, double percentile) {
        ArrayList<Integer> scores = (ArrayList<Integer>) map.values()
                .stream().filter(i -> i != -1).collect(Collectors.toList());
        Collections.sort(scores);
        int index = (int) Math.ceil((percentile / 100) * scores.size());
        return scores.get(index - 1);
    }

    /**
     * Checks if the given hashmap is empty.
     * scores of -1 are considered as empty scores.
     */
    private boolean isEmpty(Map<Student, Integer> scores) {
        long numScores = scores.values().stream().filter(i -> i != -1).count();
        logger.info("Number of students in assignment: " + numScores);
        return (numScores == 0);
    }
}
