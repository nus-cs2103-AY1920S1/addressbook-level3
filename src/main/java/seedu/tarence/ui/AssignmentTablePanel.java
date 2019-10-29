package seedu.tarence.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

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

    public AssignmentTablePanel(Map<Student, Integer> scores) {
        super(FXML);
        requireNonNull(scores);
        try {
            setStatistics(scores);
            setAssignmentTable(scores);
            assignmentPlaceholder.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (RuntimeException e) {
            setDefaultPlaceHolderLabel();
        }
        this.defaultPanel = new StackPane();
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
        String defaultMessage = "Welcome to T.A.rence \uD83D\uDE0A\n"
                + "To see all user commands, type \"help\"\n"
                + "To view a class assignment, type:\n";

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
                            if (item <= lowerPercentile) {
                                this.setTextFill(Color.RED);
                            }
                            if (item >= upperPercentile) {
                                this.setTextFill(Color.rgb(0, 255, 0));
                            }
                            if (item == -1) {
                                setText("");
                            } else {
                                setText(item.toString());
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
    }

    /**
     * calculates the given percentile from the hashmap values.
     * @param map - hashmap containing the names and scores of the students.
     * @param percentile - percentile to calculate.
     */
    private long calcPercentile(Map<Student, Integer> map, double percentile) {
        ArrayList<Integer> scores = new ArrayList<Integer>(map.values());
        Collections.sort(scores);
        int index = (int) Math.ceil(((double) percentile / (double) 100) * (double) scores.size());
        return scores.get(index - 1);
    }
}
