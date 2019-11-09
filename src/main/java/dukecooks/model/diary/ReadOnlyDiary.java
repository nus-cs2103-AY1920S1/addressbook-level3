package dukecooks.model.diary;

import dukecooks.model.diary.components.Diary;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of Diary
 */
public interface ReadOnlyDiary {

    /**
     * Returns an unmodifiable view of the diary list.
     * This list will not contain any duplicate diaries.
     */
    ObservableList<Diary> getDiaryList();

}
