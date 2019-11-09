package seedu.exercise.model;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.AppUtil.requireMainAppState;
import static seedu.exercise.commons.util.CollectionUtil.append;
import static seedu.exercise.commons.util.CollectionUtil.areListsEmpty;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.core.State;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.ResolveCommand;
import seedu.exercise.logic.commands.builder.EditExerciseBuilder;
import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.logic.commands.statistic.StatsFactory;
import seedu.exercise.logic.parser.ParserUtil;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * Represents the in-memory model of the exercise book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ReadOnlyResourceBook<Exercise> exerciseBook;
    private final ReadOnlyResourceBook<Regime> regimeBook;
    private final ReadOnlyResourceBook<Exercise> databaseBook;
    private final ReadOnlyResourceBook<Schedule> scheduleBook;
    private final UserPrefs userPrefs;
    private final SortedList<Exercise> sortedExercises;
    private final SortedList<Regime> sortedRegimes;
    private final SortedList<Schedule> sortedSchedules;
    private final ObservableList<Exercise> suggestions = FXCollections.observableArrayList();
    private final Statistic statistic;

    private Conflict conflict;

    /**
     * Initializes a ModelManager with the given exerciseBook and userPrefs.
     */
    public ModelManager(ReadOnlyResourceBook<Exercise> exerciseBook, ReadOnlyResourceBook<Regime> regimeBook,
                        ReadOnlyResourceBook<Exercise> databaseBook, ReadOnlyResourceBook<Schedule> scheduleBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(exerciseBook, regimeBook, databaseBook, scheduleBook, userPrefs);

        logger.fine("Initializing with exercise book: " + exerciseBook + " and user prefs " + userPrefs);

        this.exerciseBook = new ReadOnlyResourceBook<>(exerciseBook, DEFAULT_EXERCISE_COMPARATOR);
        this.databaseBook = new ReadOnlyResourceBook<>(databaseBook, DEFAULT_EXERCISE_COMPARATOR);
        this.regimeBook = new ReadOnlyResourceBook<>(regimeBook, DEFAULT_REGIME_COMPARATOR);
        this.scheduleBook = new ReadOnlyResourceBook<>(scheduleBook, DEFAULT_SCHEDULE_COMPARATOR);
        this.userPrefs = new UserPrefs(userPrefs);
        sortedExercises = new SortedList<>(this.exerciseBook.getSortedResourceList(),
            DEFAULT_EXERCISE_COMPARATOR);
        removeInvalidCustomProperties();
        sortedRegimes = new SortedList<>(this.regimeBook.getSortedResourceList(),
            DEFAULT_REGIME_COMPARATOR);
        sortedSchedules = new SortedList<>(this.scheduleBook.getSortedResourceList(),
            DEFAULT_SCHEDULE_COMPARATOR);
        StatsFactory statsFactory = new StatsFactory(exerciseBook, "linechart", "calories", null, null);
        this.statistic = statsFactory.getDefaultStatistic();
        conflict = null;
    }

    public ModelManager() {
        this(new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getExerciseBookFilePath() {
        return userPrefs.getExerciseBookFilePath();
    }

    @Override
    public void setExerciseBookFilePath(Path exerciseBookFilePath) {
        requireNonNull(exerciseBookFilePath);
        userPrefs.setExerciseBookFilePath(exerciseBookFilePath);
    }

    @Override
    public Path getRegimeBookFilePath() {
        return userPrefs.getRegimeBookFilePath();
    }

    @Override
    public void setRegimeBookFilePath(Path regimeBookFilePath) {
        requireNonNull(regimeBookFilePath);
        userPrefs.setRegimeBookFilePath(regimeBookFilePath);
    }

    //=========== ExerciseBook ================================================================================

    @Override
    public void setExerciseBook(ReadOnlyResourceBook<Exercise> anotherBook) {
        this.exerciseBook.resetData(anotherBook);
    }

    @Override
    public ReadOnlyResourceBook<Exercise> getExerciseBookData() {
        return exerciseBook;
    }

    @Override
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        return exerciseBook.hasResource(exercise);
    }

    @Override
    public void deleteExercise(Exercise target) {
        exerciseBook.removeResource(target);
    }

    /**
     * Adds an {@code Exercise} object into the exercise book.
     */
    public void addExercise(Exercise exercise) {
        exerciseBook.addResource(exercise);
    }

    public void setExercise(Exercise target, Exercise editedExercise) {
        requireAllNonNull(target, editedExercise);
        exerciseBook.removeResource(target);
        exerciseBook.addResource(editedExercise);
    }

    //===================RegimeBook==============================================================================

    @Override
    public void setRegimeBook(ReadOnlyResourceBook<Regime> anotherBook) {
        this.regimeBook.resetData(anotherBook);
    }

    @Override
    public ReadOnlyResourceBook<Regime> getAllRegimeData() {
        return regimeBook;
    }

    /**
     * Adds a {@code Regime} object into the regime book.
     */
    @Override
    public void addRegime(Regime regime) {
        regimeBook.addResource(regime);
    }

    @Override
    public void deleteRegime(Regime target) {
        regimeBook.removeResource(target);
    }

    @Override
    public void setRegime(Regime target, Regime editedRegime) {
        regimeBook.setResource(target, editedRegime);
    }

    @Override
    public boolean hasRegime(Regime regime) {
        requireNonNull(regime);
        return regimeBook.hasResource(regime);
    }

    @Override
    public int getRegimeIndex(Regime regime) {
        return regimeBook.getResourceIndex(regime);
    }

    //===================ReadOnlyResourceBook<Schedule>===============================================================
    @Override
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return scheduleBook.hasResource(schedule);
    }

    @Override
    public void addSchedule(Schedule schedule) {
        requireNonNull(schedule);
        scheduleBook.addResource(schedule);
    }

    @Override
    public void removeSchedule(Schedule schedule) {
        requireNonNull(schedule);
        scheduleBook.removeResource(schedule);
    }

    @Override
    public void completeSchedule(Schedule schedule) {
        requireNonNull(schedule);
        scheduleBook.removeResource(schedule);
        Collection<Exercise> scheduledExercises = schedule.getExercises();
        for (Exercise exercise : scheduledExercises) {
            if (!exerciseBook.hasResource(exercise)) {
                exerciseBook.addResource(exercise);
            }
        }
    }

    @Override
    public ReadOnlyResourceBook<Schedule> getAllScheduleData() {
        return scheduleBook;
    }

    //===================Conflicts===============================================================

    @Override
    public Schedule resolveConflict(Name name, List<Index> indexFromSchedule, List<Index> indexFromConflict) {
        requireAllNonNull(name, indexFromSchedule, indexFromConflict);
        requireMainAppState(State.IN_CONFLICT);

        removeOldSchedule();
        Schedule resolvedSchedule;
        if (areListsEmpty(indexFromConflict, indexFromSchedule)) {
            if (name.toString().equals(ResolveCommand.TAKE_FROM_SCHEDULED)) {
                resolvedSchedule = conflict.getScheduled();
            } else {
                resolvedSchedule = conflict.getConflicted();
            }
            addResolvedSchedule(resolvedSchedule);
        } else {
            SortedUniqueResourceList<Exercise> resolvedExercises =
                getResolvedExerciseList(indexFromSchedule, indexFromConflict);
            resolvedSchedule = getResolvedSchedule(name, resolvedExercises);
            addCombinedRegime(resolvedSchedule.getRegime());
            addResolvedSchedule(resolvedSchedule);
        }

        logger.info("Schedule conflict resolved: " + resolvedSchedule.getRegimeName()
                + " on " + resolvedSchedule.getDate());
        return resolvedSchedule;
    }

    @Override
    public Conflict getConflict() {
        requireMainAppState(State.IN_CONFLICT);

        return conflict;
    }

    @Override
    public void setConflict(Conflict conflict) {
        requireMainAppState(State.IN_CONFLICT);
        requireNonNull(conflict);

        logger.info("Conflict set:\n" + conflict);
        this.conflict = conflict;
    }

    @Override
    public boolean isSelectedIndexesFromRegimeDuplicate(List<Index> scheduledIndex, List<Index> conflictingIndex) {
        requireMainAppState(State.IN_CONFLICT);
        requireAllNonNull(scheduledIndex, conflictingIndex);
        requireNonNull(conflict);

        return isIndexesForRegimeDuplicate(scheduledIndex, conflictingIndex);
    }

    //=========== Filtered Exercise List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Exercise} backed by the internal list of
     * {@code exerciseBook} and sorted by Date.
     */
    @Override
    public ObservableList<Exercise> getSortedExerciseList() {
        return sortedExercises;
    }


    //=========== Filtered Regime List Accessors ===============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Regime} backed by the internal list of
     * {@code regimeBook}.
     */
    public ObservableList<Regime> getSortedRegimeList() {
        return sortedRegimes;
    }


    //=========== Filtered Schedule List Accessors ===============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Schedule} backed by the internal list of
     * {@code scheduleBook}
     */
    public ObservableList<Schedule> getSortedScheduleList() {
        return sortedSchedules;
    }

    //=========== ExerciseDatabase ===============================================================

    @Override
    public ReadOnlyResourceBook<Exercise> getDatabaseBook() {
        return databaseBook;
    }

    public ReadOnlyResourceBook<Exercise> getExerciseDatabaseData() {
        return databaseBook;
    }

    //=========== Suggested Exercise Accessors ===============================================================

    @Override
    public ObservableList<Exercise> getSuggestedExerciseList() {
        return suggestions;
    }

    @Override
    public void setSuggestions(List<Exercise> suggestions) {
        this.suggestions.setAll(suggestions);
    }

    @Override
    public void updateSuggestedExerciseList(Predicate<Exercise> predicate) {
        requireNonNull(predicate);
        List<Exercise> filteredSuggestions = generateAllSuggestions(predicate);
        setSuggestions(filteredSuggestions);
    }

    /**
     * Returns an unmodifiable view of the list of {@code suggestions} backed by the internal list of
     * {@code exerciseBook} and {@code databaseBook}
     */
    private ObservableList<Exercise> generateAllSuggestions(Predicate<Exercise> predicate) {
        List<Exercise> trackedExercises = getExerciseBookData().getSortedResourceList().filtered(predicate);
        List<Exercise> databaseExercises = getDatabaseBook().getSortedResourceList().filtered(predicate);
        ObservableList<Exercise> allSuggestions = FXCollections.observableArrayList();
        ObservableList<Exercise> addedTrackedExercises = addExerciseList(allSuggestions, trackedExercises);
        return addExerciseList(addedTrackedExercises, databaseExercises);
    }

    /**
     * Returns an unmodifiable view of the list of exercises from {@code exerciseList} to an {@code exerciseList},
     * excluding any exercise that has a duplicate name.
     */
    private ObservableList<Exercise> addExerciseList(ObservableList<Exercise> originalExerciseList,
                                                     List<Exercise> exerciseList) {
        ObservableList<Exercise> newExerciseList = FXCollections.observableArrayList(originalExerciseList);
        for (Exercise exercise : exerciseList) {
            boolean isDuplicate = false;
            for (Exercise e : newExerciseList) {
                if (exercise.getName().equals(e.getName())) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                newExerciseList.add(exercise);
            }
        }
        return newExerciseList;
    }

    @Override
    public void updateStatistic() {
        ReadOnlyResourceBook<Exercise> exercises = getExerciseBookData();
        Statistic outdatedStatistic = getStatistic();
        StatsFactory statsFactory = new StatsFactory(exercises, outdatedStatistic.getChart(),
            outdatedStatistic.getCategory(), outdatedStatistic.getStartDate(), outdatedStatistic.getEndDate());
        Statistic statistic = statsFactory.generateStatistic();
        this.statistic.resetData(statistic);
    }

    @Override
    public void setStatistic(Statistic statistic) {
        this.statistic.resetData(statistic);
    }

    @Override
    public Statistic getStatistic() {
        return statistic;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return exerciseBook.equals(other.exerciseBook)
            && regimeBook.equals(other.regimeBook)
            && scheduleBook.equals(other.scheduleBook)
            && userPrefs.equals(other.userPrefs)
            && sortedExercises.equals(other.sortedExercises)
            && sortedRegimes.equals(other.sortedRegimes)
            && sortedSchedules.equals(other.sortedSchedules)
            && databaseBook.equals(other.databaseBook)
            && suggestions.equals(other.suggestions);
    }

    private SortedUniqueResourceList<Exercise> getResolvedExerciseList(List<Index> indexFromSchedule,
                                                                       List<Index> indexFromConflict) {
        Regime scheduledRegime = conflict.getScheduledRegime();
        Regime conflictRegime = conflict.getConflictingRegime();
        List<Exercise> exercisesToAddFromScheduled = scheduledRegime.getRegimeExercises()
            .getAllResourcesIndex(indexFromSchedule);
        List<Exercise> exercisesToAddFromConflicted = conflictRegime.getRegimeExercises()
            .getAllResourcesIndex(indexFromConflict);
        List<Exercise> resolvedExercises = append(exercisesToAddFromScheduled, exercisesToAddFromConflicted);
        SortedUniqueResourceList<Exercise> uniqueResolveList =
            new SortedUniqueResourceList<>(DEFAULT_EXERCISE_COMPARATOR);
        uniqueResolveList.setAll(resolvedExercises);
        return uniqueResolveList;
    }

    /**
     * Checks if the provided indexes have some duplicate exercises they are referring to
     */
    private boolean isIndexesForRegimeDuplicate(List<Index> scheduledIndex, List<Index> conflictingIndex) {
        SortedUniqueResourceList<Exercise> listToAdd = new SortedUniqueResourceList<>(DEFAULT_EXERCISE_COMPARATOR);
        List<Exercise> scheduledExercises = conflict
            .getScheduledRegime().getRegimeExercises().getAllResourcesIndex(scheduledIndex);
        List<Exercise> conflictExercises = conflict
            .getConflictingRegime().getRegimeExercises().getAllResourcesIndex(conflictingIndex);
        listToAdd.setAll(scheduledExercises);
        for (Exercise conflicted : conflictExercises) {
            if (listToAdd.contains(conflicted)) {
                return true;
            }
            listToAdd.add(conflicted);
        }
        return false;
    }

    private Schedule getResolvedSchedule(Name regimeName, SortedUniqueResourceList<Exercise> exerciseList) {
        Regime regime = new Regime(regimeName, exerciseList);
        return new Schedule(regime, conflict.getConflictDate());
    }

    private void removeOldSchedule() {
        scheduleBook.removeResource(conflict.getScheduled());
    }

    private void addResolvedSchedule(Schedule resolvedSchedule) {
        scheduleBook.addResource(resolvedSchedule);
    }

    private void addCombinedRegime(Regime regime) {
        regimeBook.addResource(regime);
    }

    /**
     * Removes invalid custom properties that are present in the exercises.
     * This ensures that undefined custom properties and custom properties of invalid values do not exist.
     */
    private void removeInvalidCustomProperties() {
        for (Exercise exercise : sortedExercises) {
            Map<String, String> toCheck = exercise.getCustomPropertiesMap();
            Map<String, String> newMap;
            try {
                newMap = ParserUtil.parseCustomProperties(toCheck);
            } catch (ParseException e) {
                newMap = new TreeMap<>();
            }
            EditExerciseBuilder editor = new EditExerciseBuilder(exercise);
            editor.setCustomProperties(newMap);
            setExercise(exercise, editor.buildEditedExercise());
        }
    }
}
