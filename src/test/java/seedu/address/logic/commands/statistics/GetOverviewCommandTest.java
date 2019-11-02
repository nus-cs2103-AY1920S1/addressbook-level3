package seedu.address.logic.commands.statistics;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.statistics.GetOverviewCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.testutil.QuizResultFilterBuilder;

class GetOverviewCommandTest {

    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    public void constructor_nullQuizResultFilter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GetOverviewCommand(null, ""));
    }

    @Test
    public void execute_validQuizResultFilter_success() {
        QuizResultFilter quizResultFilter = null;
        try {
            quizResultFilter = new QuizResultFilterBuilder()
                    .withStartDate("09/10/2019")
                    .withEndDate("10/11/2019")
                    .buildWithDates();
        } catch (ParseException e) {
            fail(MESSAGE_INVALID_DATE_FORMAT);
        }
        GetOverviewCommand overviewCommand = new GetOverviewCommand(quizResultFilter,
                "\n(09/10/2019 to 10/11/2019)");

        Model expectedModel = new ModelManager(model.getAppData(), new UserPrefs());
        expectedModel.updateQuizResultFilter(quizResultFilter);

        assertCommandSuccess(overviewCommand, model, String.format(MESSAGE_SUCCESS,
                "\n(09/10/2019 to 10/11/2019)"), expectedModel);
    }

    /*@Test
    public void execute_emptyQuizResultList_throwsCommandException() {
        QuizResultFilter quizResultFilter = null;
        try {
            quizResultFilter = new QuizResultFilterBuilder()
                    .withStartDate("12/12/1920")
                    .withEndDate("13/12/1920")
                    .buildWithDates();
        } catch (ParseException e) {
            fail(MESSAGE_INVALID_DATE_FORMAT);
        }
        GetOverviewCommand overviewCommand = new GetOverviewCommand(quizResultFilter);
        assertCommandFailure(overviewCommand, model, MESSAGE_NO_STATISTICS);
    }*/
}
