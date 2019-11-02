package seedu.address.logic.commands.statistics;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.statistics.GetStatisticsCommand.MESSAGE_NO_STATISTICS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.testutil.QuizResultFilterBuilder;

class GetStatisticsCommandTest {

    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    public void constructor_nullQuizResultFilter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GetStatisticsCommand(null, ""));
    }

    /*@Test
    public void execute_validQuizResultFilter_success() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withSubjects(new ArrayList<>())
                .withDifficulty("easy")
                .buildWithSubjectsAndDifficulty();

        GetStatisticsCommand statisticsCommand = new GetStatisticsCommand(quizResultFilter);
        Model expectedModel = new ModelManager(model.getAppData(), new UserPrefs());
        expectedModel.filterQuizResult(quizResultFilter);

        assertCommandSuccess(statisticsCommand, model, MESSAGE_SUCCESS, expectedModel);
    }*/

    @Test
    public void execute_emptyQuizResultList_throwsCommandException() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withSubjects(new ArrayList<>(Arrays.asList("random")))
                .buildWithSubjects();
        GetStatisticsCommand statisticsCommand = new GetStatisticsCommand(quizResultFilter, "\n[random]");
        assertCommandFailure(statisticsCommand, model, MESSAGE_NO_STATISTICS);
    }
}
