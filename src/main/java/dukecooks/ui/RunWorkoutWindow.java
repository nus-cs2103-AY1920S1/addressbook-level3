package dukecooks.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.exercise.ExerciseSetAttempt;
import dukecooks.model.workout.exercise.details.Sets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controller for RunWorkoutWindow.
 */
public class RunWorkoutWindow extends UiPart<Stage> {

    private static final String FXML = "RunWorkoutWindow.fxml";

    private static final Logger logger = LogsCenter.getLogger(RunWorkoutWindow.class);
    private final ArrayList<ExerciseSetAttempt> exerciseSetAttempts;
    private final Workout workoutToRun;
    private final int workoutSize;

    private List<ExerciseSetAttemptListPanel> attemptPanels;
    private int exercisesDone = 0;
    private int setsCompleted = 0;

    @FXML
    private Label workoutName;

    @FXML
    private ProgressBar workoutProgress;

    @FXML
    private StackPane setDetailsPlaceHolder;

    @FXML
    private StackPane commandBoxPlaceHolder;

    public RunWorkoutWindow(Workout workoutToRun) {
        super(FXML);
        this.workoutToRun = workoutToRun;
        exerciseSetAttempts = workoutToRun.getExerciseSetAttempts();
        workoutSize = workoutToRun.getExercises().size();
        workoutName.setText(workoutToRun.getName().workoutName);
        initAttemptPanels(exerciseSetAttempts);
        setDetailsPlaceHolder.getChildren().add(attemptPanels.get(0).getRoot());
        workoutProgress.setProgress(1 / workoutSize);
        commandBoxPlaceHolder.getChildren().add(new WorkoutCommandBox(this::executeDoneCommand).getRoot());
    }

    /**
     * Initialises Attempt Panels.
     */
    private void initAttemptPanels(ArrayList<ExerciseSetAttempt> exerciseSetAttempts) {
        List<ExerciseSetAttemptListPanel> attemptPanels = new ArrayList<>();
        for (int i = 0; i < exerciseSetAttempts.size(); i++) {
            Sets set = workoutToRun.getSet(i);
            ExerciseSetAttempt attempt = exerciseSetAttempts.get(i);
            ObservableList<ExerciseSetAttempt> attempts = FXCollections.observableArrayList();
            for (int j = 0; j < set.getMagnitudeAsInteger(); j++) {
                attempts.add(attempt.clone());
            }
            attemptPanels.add(new ExerciseSetAttemptListPanel(attempts, workoutToRun
                    .getExercises().get(i)));
        }
        this.attemptPanels = attemptPanels;
    }

    /**
     * Opens and run workout.
     */
    public void show() {
        logger.fine("Running Workout.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Executes the done command.
     */
    public void executeDoneCommand() {
        ExerciseSetAttemptListPanel currentPanel = attemptPanels.get(setsCompleted);
        setDetailsPlaceHolder.getChildren().remove(currentPanel.getRoot());
        currentPanel.setAttemptIsDone(exercisesDone, true);
        exercisesDone++;
        if (exercisesDone == workoutToRun.getSet(setsCompleted).getMagnitudeAsInteger()) {
            exercisesDone = 0;
            setsCompleted++;
            if (setsCompleted == workoutSize) {
                //todo: Close and update history
                getRoot().hide();
                return;
            }
            setDetailsPlaceHolder.getChildren().add(attemptPanels.get(setsCompleted).getRoot());
        } else {
            setDetailsPlaceHolder.getChildren().add(currentPanel.getRoot());
        }
    }
}
