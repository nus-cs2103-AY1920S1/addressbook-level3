package com.dukeacademy.logic.commands.submit;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogicManager;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.state.ApplicationState;
import com.dukeacademy.testutil.MockQuestionsLogic;
import com.dukeacademy.testutil.TypicalQuestions;

import javafx.collections.ObservableList;

class SubmitCommandTest {
    @TempDir public Path tempFolder;

    private MockQuestionsLogic questionsLogic;
    private ProgramSubmissionLogic submissionLogic;
    private ApplicationState applicationState;

    @BeforeEach void initializeTest() throws LogicCreationException {
        this.questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        this.submissionLogic = new ProgramSubmissionLogicManager(tempFolder.toString());
        this.applicationState = new ApplicationState();
    }

    @Test void execute() throws IOException, CommandException {
        SubmitCommand command = new SubmitCommand(questionsLogic, submissionLogic, applicationState);

        // Check that an exception is thrown if the question is not set previously
        submissionLogic.setUserProgramSubmissionChannel(() -> new UserProgram("Main", ""));
        assertThrows(CommandException.class, "You have not attempted a question yet.", command::execute);

        // Check that an exception is thrown if program is empty
        submissionLogic.setCurrentQuestion(questionsLogic.getAllQuestionsList().get(0));
        assertThrows(CommandException.class, "Program must not be empty.", command::execute);

        // Check that an exception is thrown if no appropriate class name is found
        UserProgram invalidClassNameSubmission = new UserProgram("Main", "public class Test {}");
        submissionLogic.setUserProgramSubmissionChannel(() -> invalidClassNameSubmission);
        assertThrows(CommandException.class, "Please write your main method in a class called Main",
                command::execute);

        // Check that questionsLogic has changed accordingly
        List<Question> expected = TypicalQuestions.getTypicalQuestions();
        expected.set(0, expected.get(0).withNewUserProgram(invalidClassNameSubmission));
        assertTrue(this.matchListData(questionsLogic.getFilteredQuestionsList(), expected));

        // Check correct results
        Path solutionPath = Paths.get("src", "test", "data", "TestPrograms", "adder", "adder.txt");
        String solution = Files.readString(solutionPath);
        UserProgram correctProgram = new UserProgram("Main", solution);
        submissionLogic.setUserProgramSubmissionChannel(() -> correctProgram);
        CommandResult result = command.execute();
        assertTrue(result.getFeedbackToUser().contains("success"));

        List<Question> expected1 = TypicalQuestions.getTypicalQuestions();
        expected1.set(0, expected1.get(0).withNewStatus(Status.PASSED).withNewUserProgram(correctProgram));
        assertTrue(this.matchListData(questionsLogic.getFilteredQuestionsList(), expected1));
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
