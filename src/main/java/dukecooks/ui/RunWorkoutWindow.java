package dukecooks.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.exercise.ExerciseSetAttempt;
import dukecooks.model.workout.exercise.details.Sets;
import dukecooks.model.workout.history.ExerciseRun;
import dukecooks.model.workout.history.WorkoutRun;
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
    private int setsCompleted = 0;
    private int exercisesDone = 0;

    private final LocalDateTime workoutStartTime = LocalDateTime.now();
    private LocalDateTime exerciseStartTime;
    private LocalDateTime currentTime;
    private ArrayList<ExerciseRun> exercisesRan = new ArrayList<>();
    private WorkoutRun workoutRun = null;

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
        workoutProgress.setProgress((double) 0);
        commandBoxPlaceHolder.getChildren().add(new WorkoutCommandBox(this::executeDoneCommand).getRoot());
        exerciseStartTime = LocalDateTime.now();
        currentTime = LocalDateTime.now();
    }

    public WorkoutRun getWorkoutRun() {
        return workoutRun;
    }

    public Workout getWorkoutToRun() {
        return workoutToRun;
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
        currentTime = LocalDateTime.now();
        ExerciseSetAttemptListPanel currentPanel = attemptPanels.get(exercisesDone);
        setDetailsPlaceHolder.getChildren().remove(currentPanel.getRoot());
        currentPanel.setAttemptIsDone(setsCompleted, true);
        setsCompleted++;
        checkAndHandleExerciseCompletion(currentPanel);
    }

    /**
     * Checks and handles run workout window exit if workout is complete.
     */
    private boolean checkAndHandleExit() {
        if (exercisesDone == workoutSize) {
            workoutRun = new WorkoutRun(workoutStartTime, currentTime, exercisesDone, exercisesRan);
            getRoot().hide();
        }
        return exercisesDone == workoutSize;
    }

    /**
     * Checks and handles exercise completion.
     */
    private void checkAndHandleExerciseCompletion(ExerciseSetAttemptListPanel currentPanel) {
        if (setsCompleted == workoutToRun.getSet(exercisesDone).getMagnitudeAsInteger()) {
            exercisesRan.add(new ExerciseRun(exerciseStartTime, currentTime,
                    new Sets(setsCompleted), new Sets(setsCompleted),
                    generateExerciseSetAttempts(setsCompleted, exerciseSetAttempts
                            .get(exercisesDone)), workoutToRun.getName()));
            workoutProgress.setProgress(((double) exercisesDone + 1) / workoutSize);
            exercisesDone++;
            if (checkAndHandleExit()) {
                return;
            }
            setDetailsPlaceHolder.getChildren().add(attemptPanels.get(exercisesDone).getRoot());
            setsCompleted = 0;
            exerciseStartTime = LocalDateTime.now();
        } else {
            setDetailsPlaceHolder.getChildren().add(currentPanel.getRoot());
        }
    }

    /**
     * Generates an ArrayList with number of ExerciseSetAttempts equal to that of
     * the numvber of sets.
     */
    private ArrayList<ExerciseSetAttempt> generateExerciseSetAttempts(int noSets,
                                                                      ExerciseSetAttempt exerciseSetAttempt) {
        ArrayList<ExerciseSetAttempt> toReturn = new ArrayList<>();
        for (int i = 0; i < noSets; i++) {
            toReturn.add(exerciseSetAttempt.clone());
        }
        return toReturn;
    }
}
