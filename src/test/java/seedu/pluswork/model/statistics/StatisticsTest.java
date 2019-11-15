package seedu.pluswork.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.task.TaskStatus;

public class StatisticsTest {
    private Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
    private Model expectedModel = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());

    @Test
    public void executecorrectTasksByStatusStats() {
        HashMap<TaskStatus, Integer> expectedResult = new HashMap<>();
        expectedResult.put(TaskStatus.UNBEGUN, 3);
        expectedResult.put(TaskStatus.DOING, 2);
        expectedResult.put(TaskStatus.DONE, 2);

        assertEquals(expectedResult, expectedModel.getStatistics().getPortionTasksByStatus());
    }

    @Test
    public void execute_correctMembersByTasksStats() {
        HashMap<Member, Integer> expectedResult = new HashMap<>();
        List<Member> members = model.getFilteredMembersList();

        expectedResult.put(members.get(1), 2);
        expectedResult.put(members.get(2), 1);
        expectedResult.put(members.get(3), 1);
        expectedResult.put(members.get(4), 3);
        expectedResult.put(members.get(5), 1);
        expectedResult.put(members.get(0), 0);
        expectedResult.put(members.get(6), 0);

        assertEquals(expectedResult, expectedModel.getStatistics().getPortionMembersByTasks());
    }

    @Test
    public void execute_correctMembersByInvStats() {
        HashMap<Member, Integer> expectedResult = new HashMap<>();
        List<Member> members = model.getFilteredMembersList();

        expectedResult.put(members.get(1), 2);
        expectedResult.put(members.get(2), 1);
        expectedResult.put(members.get(3), 1);
        expectedResult.put(members.get(4), 3);
        expectedResult.put(members.get(5), 1);
        expectedResult.put(members.get(0), 0);
        expectedResult.put(members.get(6), 0);

        assertEquals(expectedResult, expectedModel.getStatistics().getPortionMembersByTasks());
    }
}
