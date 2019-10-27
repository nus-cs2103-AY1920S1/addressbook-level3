package com.dukeacademy.logic.commands.exit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogicManager;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.testutil.MockQuestionsLogic;
import com.dukeacademy.testutil.TypicalQuestions;

import javafx.collections.ObservableList;

class ExitCommandTest {
    @TempDir
    public Path tempFolder;

    private MockQuestionsLogic questionsLogic;
    private ProgramSubmissionLogic submissionLogic;

    @BeforeEach
    public void initializeTest() throws LogicCreationException {
        this.questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        this.submissionLogic = new ProgramSubmissionLogicManager(tempFolder.toString());
    }

    @Test
    public void execute() {
        ExitCommand command = new ExitCommand(questionsLogic, submissionLogic);
        UserProgram program = new UserProgram("Main", "abc123");
        this.submissionLogic.setUserProgramSubmissionChannel(() -> program);

        CommandResult result = command.execute();
        assertTrue(result.isExit());

        List<Question> expectedQuestions = TypicalQuestions.getTypicalQuestions();
        this.matchListData(questionsLogic.getFilteredQuestionsList(), expectedQuestions);

        Question currentQuestion = this.questionsLogic.getQuestion(0);
        this.submissionLogic.setCurrentQuestion(currentQuestion);
        this.submissionLogic.setUserProgramSubmissionChannel(() -> program);

        //Check that latest submission is saved
        command.execute();
        List<Question> expectedQuestions1 = TypicalQuestions.getTypicalQuestions();
        expectedQuestions1.set(0, currentQuestion.withNewStatus(Status.ATTEMPTED).withNewUserProgram(program));
        this.matchListData(questionsLogic.getFilteredQuestionsList(), expectedQuestions1);
    }


    /**
     * Helper method to compare an observable list to a list for equality.
     * @param observableList the observable list to be compared.
     * @param questionList the question list to be compared.
     * @return true if both lists are equal.
     */
    private boolean matchListData(ObservableList<Question> observableList, List<Question> questionList) {
        if (observableList.size() != questionList.size()) {
            return false;
        }

        if (observableList.size() == 0) {
            return true;
        }

        return IntStream.range(0, observableList.size())
                .mapToObj(i -> observableList.get(i).checkContentsEqual(questionList.get(i)))
                .reduce((x, y) -> x && y).get();
    }
}
