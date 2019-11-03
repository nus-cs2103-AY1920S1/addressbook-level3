package seedu.jarvis.logic.commands.cca;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;
import seedu.jarvis.model.cca.ccaprogress.CcaProgress;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.cca.CcaBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code IncreaseProgressCommand} and  unit tests for
 * {@code IncreaseProgressCommand}.
 */
public class IncreaseProgressCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                new CcaTracker(), new HistoryManager(), new FinanceTracker(),
                new UserPrefs(), new Planner(), new CoursePlanner()
        );
        expectedModel = new ModelManager(
                new CcaTracker(), new HistoryManager(), new FinanceTracker(),
                new UserPrefs(), new Planner(), new CoursePlanner()
        );
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IncreaseProgressCommand(
                null, new CcaBuilder().build()
        ));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        model.addCca(new CcaBuilder().build());
        model.addCca(new CcaBuilder().withName("another").build());
        IncreaseProgressCommand ipc = new IncreaseProgressCommand(
                Index.fromZeroBased(2),
                new CcaBuilder().build()
        );
        String expectedMessage = Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX;
        assertCommandFailure(ipc, model, expectedMessage);
    }

    @Test
    public void execute_ccaDoesNotContainProgress_throwsCommandException() {
        model.addCca(new CcaBuilder().build());
        IncreaseProgressCommand ipc = new IncreaseProgressCommand(Index.fromZeroBased(0));
        assertCommandFailure(ipc, model, IncreaseProgressCommand.MESSAGE_CCA_PROGRESS_NOT_YET_SET);
    }

    @Test
    public void execute_ccaAtMaxIncrement_throwsCommandException() {
        CcaProgress ccaProgress = new CcaProgress();
        ccaProgress.setMilestones(List.of(
                new CcaMilestone("1"), new CcaMilestone("2"), new CcaMilestone("3")
        ));
        Cca cca = new CcaBuilder().withCcaProgress(ccaProgress).build();
        cca.increaseProgress();
        cca.increaseProgress();
        cca.increaseProgress();
        model.addCca(cca);
        IncreaseProgressCommand ipc = new IncreaseProgressCommand(Index.fromZeroBased(0));
        assertCommandFailure(ipc, model, IncreaseProgressCommand.MESSAGE_INCREMENT_AT_MAX);
    }

    @Test
    public void execute_validInput_success() {
        CcaProgress ccaProgress = new CcaProgress();
        ccaProgress.setMilestones(List.of(
                new CcaMilestone("1"), new CcaMilestone("2"), new CcaMilestone("3")
        ));
        Cca cca1 = new CcaBuilder().withCcaProgress(ccaProgress).build();
        model.addCca(cca1);
        IncreaseProgressCommand ipc = new IncreaseProgressCommand(Index.fromZeroBased(0));

        Cca cca2 = new CcaBuilder().withCcaProgress(ccaProgress).build();
        cca2.increaseProgress();
        expectedModel.addCca(cca2);

        assertCommandSuccess(
                ipc, model,
                String.format(IncreaseProgressCommand.MESSAGE_INCREMENT_PROGRESS_SUCCESS, 1),
                expectedModel
        );
    }
}
