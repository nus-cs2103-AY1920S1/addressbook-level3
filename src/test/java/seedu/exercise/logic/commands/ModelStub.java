package seedu.exercise.logic.commands;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.ReadOnlyUserPrefs;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * A default model stub that have all of the methods failing.
 * This stub is used for extension in {@code CommandTest} to define simple behaviour of model
 */
class ModelStub implements Model {

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    //=======================exercise=============================================================================
    @Override
    public Path getExerciseBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setExerciseBookFilePath(Path exerciseBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addExercise(Exercise exercise) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setExerciseBook(ReadOnlyResourceBook<Exercise> anotherBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyResourceBook<Exercise> getExerciseBookData() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasExercise(Exercise exercise) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setExercise(Exercise target, Exercise editedExercise) {
        throw new AssertionError("This method should not be called.");
    }

    //=======================regime================================================================================
    @Override
    public Path getRegimeBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRegimeBookFilePath(Path exerciseBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addRegime(Regime regime) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRegimeBook(ReadOnlyResourceBook<Regime> anotherBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyResourceBook<Regime> getAllRegimeData() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasRegime(Regime regime) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteRegime(Regime regime) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRegime(Regime target, Regime editedRegime) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getRegimeIndex(Regime regime) {
        throw new AssertionError("This method shuold not be called.");
    }

    @Override
    public ObservableList<Exercise> getSortedExerciseList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Regime> getSortedRegimeList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Schedule> getSortedScheduleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasSchedule(Schedule schedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addSchedule(Schedule schedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeSchedule(Schedule schedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyResourceBook<Schedule> getAllScheduleData() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void completeSchedule(Schedule schedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Schedule resolveConflict(Name regimeName, List<Index> indexFromSchedule, List<Index> indexFromConflict) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Conflict getConflict() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setConflict(Conflict conflict) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isSelectedIndexesFromRegimeDuplicate(List<Index> scheduledIndex, List<Index> conflictingIndex) {
        throw new AssertionError("This method should not be called.");
    }

    //=======================suggest================================================================================

    @Override
    public ObservableList<Exercise> getSuggestedExerciseList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setSuggestions(List<Exercise> suggestions) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateSuggestedExerciseList(Predicate<Exercise> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyResourceBook<Exercise> getDatabaseBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateStatistic() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStatistic(Statistic statistic) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Statistic getStatistic() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyResourceBook<Exercise> getExerciseDatabaseData() {
        throw new AssertionError("This method should not be called.");
    }

}
