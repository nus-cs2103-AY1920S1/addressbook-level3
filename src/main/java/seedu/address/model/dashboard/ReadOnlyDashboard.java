package seedu.address.model.diary;

import javafx.collections.ObservableList;
import seedu.address.model.diary.components.Diary;

/**
 * Unmodifiable view of Duke Cooks
 */
public interface ReadOnlyDashboard {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate diaries.
     */
    ObservableList<Diary> getDiaryList();

}
