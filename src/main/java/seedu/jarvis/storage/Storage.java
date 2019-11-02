package seedu.jarvis.storage;

import seedu.jarvis.storage.cca.CcaTrackerStorage;
import seedu.jarvis.storage.course.CoursePlannerStorage;
import seedu.jarvis.storage.finance.FinanceTrackerStorage;
import seedu.jarvis.storage.history.HistoryManagerStorage;
import seedu.jarvis.storage.planner.PlannerStorage;
import seedu.jarvis.storage.userprefs.UserPrefsStorage;

/**
 * API of the Storage component.
 * This extends al the different models for all the features.
 * {@code UserPrefsStorage} is the API for User Preferences and GUI settings.
 * {@code HistoryManagerStorage} is the API for undoing and redoing commands.
 */
public interface Storage extends UserPrefsStorage, HistoryManagerStorage, CcaTrackerStorage,
        CoursePlannerStorage, PlannerStorage, FinanceTrackerStorage {}
