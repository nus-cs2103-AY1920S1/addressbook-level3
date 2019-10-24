package seedu.exercise.model.conflict;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * Represents a scheduling conflict between two schedules.
 *
 * A conflict can only happen when the {@code date} between two
 * {@code schedules} are the same as specified by {@link Schedule#isSameResource}.
 */
public class Conflict {

    private final Schedule scheduled;
    private final Schedule conflicted;

    public Conflict(Schedule scheduled, Schedule conflicted) {
        this.scheduled = scheduled;
        this.conflicted = conflicted;
    }

    public Schedule getScheduled() {
        return scheduled;
    }

    public Schedule getConflicted() {
        return conflicted;
    }

    public Regime getScheduledRegime() {
        return scheduled.getRegime();
    }

    public Regime getConflictingRegime() {
        return conflicted.getRegime();
    }

    public Date getConflictDate() {
        return scheduled.getDate();
    }

    public Schedule getScheduleByRegime(Regime regime) {
        requireNonNull(regime);

        return scheduled.getRegime().equals(regime)
                ? scheduled
                : conflicted.getRegime().equals(regime)
                    ? conflicted
                    : null;
    }

    public List<Exercise> getScheduledExerciseList() {
        return scheduled.getExercises();
    }

    public List<Exercise> getConflictedExerciseList() {
        return conflicted.getExercises();
    }

    public ObservableList<Exercise> getScheduledUnmodifiableExerciseList() {
        return scheduled.getRegime().getRegimeExercises().asUnmodifiableObservableList();
    }

    public ObservableList<Exercise> getConflictedUnmodifiableExerciseList() {
        return conflicted.getRegime().getRegimeExercises().asUnmodifiableObservableList();
    }

    public String getScheduledName() {
        return scheduled.getRegimeName();
    }

    public String getConflictedName() {
        return conflicted.getRegimeName();
    }

    @Override
    public boolean equals(Object other) {
        return (other == this)
            || (other instanceof Conflict)
            && ((scheduled.equals(((Conflict) other).scheduled)
                && conflicted.equals(((Conflict) other).conflicted)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduled, conflicted);
    }
}
