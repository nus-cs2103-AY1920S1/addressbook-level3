package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.diary.Diary;

/**
 * Represents the in-memory model of Duke Cooks data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DiaryRecords diaryRecords;
    private final UserPrefs userPrefs;
    private final FilteredList<Diary> filteredDiaries;

    /**
     * Initializes a ModelManager with the given diaryRecords and userPrefs.
     */
    public ModelManager(ReadOnlyDiary diary, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(diary, userPrefs);

        logger.fine("Initializing with Duke Cooks: " + diary + " and user prefs " + userPrefs);

        this.diaryRecords = new DiaryRecords(diary);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredDiaries = new FilteredList<>(this.diaryRecords.getDiaryList());
    }

    public ModelManager() {
        this(new DiaryRecords(), new UserPrefs());
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
    public Path getDiaryFilePath() {
        return userPrefs.getDiaryFilePath();
    }

    @Override
    public void setDiaryFilePath(Path diaryFilePath) {
        requireNonNull(diaryFilePath);
        userPrefs.setDiaryFilePath(diaryFilePath);
    }

    //=========== DukeBooks ================================================================================

    @Override
    public void setDiaryRecords(ReadOnlyDiary diaryRecords) {
        this.diaryRecords.resetData(diaryRecords);
    }

    @Override
    public ReadOnlyDiary getDiaryRecords() {
        return diaryRecords;
    }

    @Override
    public boolean hasDiary(Diary diary) {
        requireNonNull(diary);
        return diaryRecords.hasDiary(diary);
    }

    @Override
    public void deleteDiary(Diary target) {
        diaryRecords.removeDiary(target);
    }

    @Override
    public void addDiary(Diary diary) {
        diaryRecords.addDiary(diary);
        updateFilteredDiaryList(PREDICATE_SHOW_ALL_DIARIES);
    }

    @Override
    public void setDiary(Diary target, Diary editedDiary) {
        requireAllNonNull(target, editedDiary);

        diaryRecords.setDiary(target, editedDiary);
    }

    //=========== Filtered Diary List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Diary} backed by the internal list of
     * {@code versionedDiaries}
     */
    @Override
    public ObservableList<Diary> getFilteredDiaryList() {
        return filteredDiaries;
    }

    @Override
    public void updateFilteredDiaryList(Predicate<Diary> predicate) {
        requireNonNull(predicate);
        filteredDiaries.setPredicate(predicate);
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
        return diaryRecords.equals(other.diaryRecords)
                && userPrefs.equals(other.userPrefs)
                && filteredDiaries.equals(other.filteredDiaries);
    }

}
