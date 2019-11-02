package seedu.address.logic.commands.statistics;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class GetReportCommandTest {
    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    public void constructor_nullQuizResultFilter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GetOverviewCommand(null, ""));
    }

    /*@Test
    public void execute_validQnsIndex_success() {
        Index index = Index.fromOneBased(model.getQuizResultList().size() - 1);
        GetReportCommand getReportCommand = new GetReportCommand(index);

        assertCommandSuccess(getReportCommand, model, MESSAGE_SUCCESS, model);
    }*/

    @Test
    public void execute_invalidQnsIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQuestionList().size() + 1);
        GetReportCommand getReportCommand = new GetReportCommand(outOfBoundIndex);

        assertCommandFailure(getReportCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }
}
