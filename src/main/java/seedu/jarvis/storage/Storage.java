package seedu.jarvis.storage;

/**
 * API of the Storage component.
 * This extends al the different models for all the features.
 * {@code UserPrefsStorage} is the API for User Preferences and GUI settings.
 * {@code HistoryManagerStorage} is the API for undoing and redoing commands.
 */
public interface Storage extends UserPrefsStorageComponent, HistoryManagerStorageComponent, CcaTrackerStorageComponent,
        CoursePlannerStorageComponent, PlannerStorageComponent, FinanceTrackerStorageComponent {}
