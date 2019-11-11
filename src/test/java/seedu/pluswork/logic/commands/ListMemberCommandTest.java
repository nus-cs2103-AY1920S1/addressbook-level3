package seedu.pluswork.logic.commands;

import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.logic.commands.CommandTestUtil.showMemberAtId;
import static seedu.pluswork.testutil.TypicalIds.ID_FIRST_MEMBER;
import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.member.ListMemberCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;

public class ListMemberCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
        expectedModel = new ModelManager(model.getProjectDashboard(), new UserPrefs(), new UserSettings());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListMemberCommand(), model, ListMemberCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showMemberAtId(model, ID_FIRST_MEMBER);
        assertCommandSuccess(new ListMemberCommand(), model, ListMemberCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
