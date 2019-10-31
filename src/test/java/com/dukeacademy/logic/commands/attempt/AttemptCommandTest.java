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
        AttemptCommand command = new AttemptCommand(1, questionsLogic, submissionLogic, applicationState);
        command.execute();
        assertTrue(this.submissionLogic.getCurrentQuestion().isPresent());
        assertEquals(this.questionsLogic.getQuestion(0), this.submissionLogic.getCurrentQuestion().get());

        AttemptCommand command1 = new AttemptCommand(3, questionsLogic, submissionLogic, applicationState);
        command1.execute();
        assertTrue(this.submissionLogic.getCurrentQuestion().isPresent());
        assertEquals(this.questionsLogic.getQuestion(2), this.submissionLogic.getCurrentQuestion().get());

        AttemptCommand command2 = new AttemptCommand(9, questionsLogic, submissionLogic, applicationState);
        assertThrows(CommandException.class,
                "Index 9 entered out of range for current list of questions.", command2::execute);
    }
}
