package com.dukeacademy.logic.commands.submit;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogicManager;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.model.state.ApplicationState;
import com.dukeacademy.testutil.MockQuestionsLogic;

class SubmitCommandFactoryTest {
    @TempDir public Path tempFolder;

    private MockQuestionsLogic questionsLogic;
    private ProgramSubmissionLogic submissionLogic;
    private ApplicationState applicationState;

    @BeforeEach void initializeTest() throws LogicCreationException {
        this.questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        this.submissionLogic = new ProgramSubmissionLogicManager(tempFolder.toString());
        this.applicationState = new ApplicationState();
    }

    @Test void getCommandWord() {
        SubmitCommandFactory factory = new SubmitCommandFactory(questionsLogic, submissionLogic, applicationState);
        assertEquals("submit", factory.getCommandWord());
    }

    @Test void getCommand() throws InvalidCommandArgumentsException {
        SubmitCommandFactory factory = new SubmitCommandFactory(questionsLogic, submissionLogic, applicationState);
        Command command = factory.getCommand("    ");
        assertTrue(command instanceof SubmitCommand);

        assertThrows(InvalidCommandArgumentsException.class,
                "Submit command does not take any arguments", () -> factory
                        .getCommand("123"));
    }
}
