package seedu.address.logic.commands.statistics;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.statistics.GetStatisticsCommand.MESSAGE_NO_STATISTICS;
import static seedu.address.logic.commands.statistics.GetStatisticsCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.testutil.QuizResultFilterBuilder;

class GetStatisticsCommandTest {
    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    void constructor_nullQuizResultFilter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GetStatisticsCommand(null, ""));
    }

    @Test
    void execute_validQuizResultFilter_success() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withSubjects(Arrays.asList("CS2105"))
                .withDifficulty("hard")
                .buildWithSubjectsAndDifficulty();

        GetStatisticsCommand statisticsCommand = new GetStatisticsCommand(quizResultFilter,
                "\n[hard]\n[CS2105]");
        Model expectedModel = new ModelManager(model.getAppData(), new UserPrefs());
        expectedModel.filterQuizResult(quizResultFilter);

        assertCommandSuccess(statisticsCommand, model,
                String.format(MESSAGE_SUCCESS, "\n[hard]\n[CS2105]"), expectedModel);
    }

    @Test
    void execute_emptyQuizResultList_throwsCommandException() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withSubjects(new ArrayList<>(Collections.singletonList("random")))
                .buildWithSubjects();
        GetStatisticsCommand statisticsCommand = new GetStatisticsCommand(quizResultFilter, "\n[random]");
        assertCommandFailure(statisticsCommand, model, MESSAGE_NO_STATISTICS);
    }
}
