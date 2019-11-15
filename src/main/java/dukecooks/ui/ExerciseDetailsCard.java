package dukecooks.ui;

import java.util.Set;

import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;


/**
 * Ui component showing the data stored in ExerciseDetails
 */
public class ExerciseDetailsCard extends UiPart<Region> {

    public static final String FXML = "ExerciseDetailsCard.fxml";

    private final ExerciseName exerciseName;
    private final Set<ExerciseDetail> details;

    @FXML
    private Label name;
    @FXML
    private FlowPane exerciseDetails;

    public ExerciseDetailsCard(WorkoutHistoryPanel.ExercisePair pair) {
        super(FXML);
        this.exerciseName = pair.getName();
        this.details = pair.getDetails();
        name.setText(exerciseName.exerciseName);
        exerciseDetails.getChildren().add(new Label("Exercise Details: "));
        details.stream().forEach(detail -> exerciseDetails
                .getChildren().add(new Label(detail.toString())));
    }
}
