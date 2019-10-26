package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserSettings;

public class GetStatisticsCommandTest {
    private Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
    private Model expectedModel = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());


}
