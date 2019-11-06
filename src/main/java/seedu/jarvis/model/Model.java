package seedu.jarvis.model;

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
 * The model extends from other feature specific model which define their own API.
 * Therefore model is composed of the API of the feature specific model that it extends from.
 */

public interface Model extends UserPrefsModel, HistoryModel, FinanceTrackerModel,
        CcaTrackerModel, PlannerModel, CoursePlannerModel {

    ViewStatus getViewStatus();

    void setViewStatus(ViewType viewType);
}


