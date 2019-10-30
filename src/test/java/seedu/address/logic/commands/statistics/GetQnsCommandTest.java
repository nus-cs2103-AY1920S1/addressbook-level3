package seedu.address.logic.commands.statistics;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.statistics.GetQnsCommand.MESSAGE_NO_QNS;
import static seedu.address.logic.commands.statistics.GetQnsCommand.MESSAGE_SUCCESS;
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

class GetQnsCommandTest {

    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    public void constructor_nullQuizResultFilter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GetOverviewCommand(null));
    }

    @Test
    public void execute_validQuizResultFilter_success() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withIsCorrectQns("true")
                .withSubjects(new ArrayList<>())
                .buildWithSubjectsAndResult();
        GetQnsCommand qnsCommand = new GetQnsCommand(quizResultFilter);

        Model expectedModel = new ModelManager(model.getAppData(), new UserPrefs());
        expectedModel.filterQuizResult(quizResultFilter);
        expectedModel.updateQuizResultFilter(quizResultFilter);

        assertCommandSuccess(qnsCommand, model, MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyQuizResultList_throwsCommandException() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withSubjects(new ArrayList<>(Arrays.asList("random")))
                .buildWithSubjects();
        GetQnsCommand qnsCommand = new GetQnsCommand(quizResultFilter);
        assertCommandFailure(qnsCommand, model, MESSAGE_NO_QNS);
    }
}
