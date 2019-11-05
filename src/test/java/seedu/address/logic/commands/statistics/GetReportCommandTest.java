package seedu.address.logic.commands.statistics;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.statistics.GetReportCommand.MESSAGE_NO_REPORT;
import static seedu.address.logic.commands.statistics.GetReportCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.question.Question;

class GetReportCommandTest {
    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    void constructor_nullQuizResultFilter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GetOverviewCommand(null, ""));
    }

    @Test
    void execute_validQnsIndex_success() {
        Index index = Index.fromOneBased(model.getFilteredQuestionList().size() - 1);
        GetReportCommand getReportCommand = new GetReportCommand(index);

        Model expectedModel = new ModelManager(model.getAppData(), new UserPrefs());
        List<Question> lastShownList = expectedModel.getFilteredQuestionList();
        Question question = lastShownList.get(index.getZeroBased());
        expectedModel.generateQnsReport(question);

        assertCommandSuccess(getReportCommand, model,
                String.format(MESSAGE_SUCCESS, index.getOneBased()), expectedModel);
    }

    @Test
    void execute_invalidQnsIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQuestionList().size() + 1);
        GetReportCommand getReportCommand = new GetReportCommand(outOfBoundIndex);

        assertCommandFailure(getReportCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    void execute_emptyQuizResultList_throwsCommandException() {
        Index index = Index.fromZeroBased(model.getFilteredQuestionList().size() - 1);
        GetReportCommand getReportCommand = new GetReportCommand(index);

        assertCommandFailure(getReportCommand, model, MESSAGE_NO_REPORT);
    }
}
