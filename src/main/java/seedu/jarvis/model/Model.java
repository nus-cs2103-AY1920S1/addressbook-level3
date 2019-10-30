package seedu.jarvis.model;

import seedu.jarvis.model.address.AddressModel;
import seedu.jarvis.model.cca.CcaTrackerModel;
import seedu.jarvis.model.course.CoursePlannerModel;
import seedu.jarvis.model.finance.FinanceTrackerModel;
import seedu.jarvis.model.history.HistoryModel;
import seedu.jarvis.model.planner.PlannerModel;
import seedu.jarvis.model.userprefs.UserPrefsModel;
import seedu.jarvis.model.viewstatus.ViewStatus;
import seedu.jarvis.model.viewstatus.ViewType;

/**
 * The API of the {@code Model} component.
 * This extends all the different models for all the features.
 * {@code AddressModel} is the API for AddressBook interactions.
 * {@code UserPrefsModel} is the API for User Preferences and GUI settings.
 * {@code HistoryModel} is the API for undoing and redoing commands.
 */

public interface Model extends AddressModel, UserPrefsModel, HistoryModel, FinanceTrackerModel,
        CcaTrackerModel, PlannerModel, CoursePlannerModel {

    ViewStatus getViewStatus();

    void setViewStatus(ViewType viewType);
}


