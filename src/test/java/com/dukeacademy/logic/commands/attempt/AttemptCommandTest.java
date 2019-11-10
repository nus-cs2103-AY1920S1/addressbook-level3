package com.dukeacademy.logic.commands.attempt;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogicManager;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.model.state.ApplicationState;
import com.dukeacademy.testutil.MockQuestionsLogic;

class AttemptCommandTest {
    @TempDir public Path tempFolder;

    private MockQuestionsLogic questionsLogic;
    private ProgramSubmissionLogic submissionLogic;
    private ApplicationState applicationState;

    @BeforeEach void initializeTest() throws LogicCreationException {
        this.questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        this.submissionLogic = new ProgramSubmissionLogicManager(tempFolder.toString());
        this.applicationState = new ApplicationState();
    }

    @Test
    void execute() throws CommandException {
        int id1 = questionsLogic.getAllQuestionsList().get(1).getId();
        AttemptCommand command = new AttemptCommand(id1, questionsLogic, submissionLogic, applicationState);
        command.execute();
        assertTrue(this.submissionLogic.getCurrentQuestion().isPresent());
        assertEquals(this.questionsLogic.getQuestion(id1), this.submissionLogic.getCurrentQuestion().get());

        int id2 = questionsLogic.getAllQuestionsList().get(2).getId();
        AttemptCommand command1 = new AttemptCommand(id2, questionsLogic, submissionLogic, applicationState);
        command1.execute();
        assertTrue(this.submissionLogic.getCurrentQuestion().isPresent());
        assertEquals(this.questionsLogic.getQuestion(id2), this.submissionLogic.getCurrentQuestion().get());

        AttemptCommand command2 = new AttemptCommand(-20, questionsLogic, submissionLogic, applicationState);
        assertThrows(CommandException.class,
                "No question with id  " + -20 + " found.", command2::execute);
    }
}
