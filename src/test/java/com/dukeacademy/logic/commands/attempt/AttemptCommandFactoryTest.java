package com.dukeacademy.logic.commands.attempt;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogicManager;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.testutil.MockQuestionsLogic;

class AttemptCommandFactoryTest {
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
    void getCommandWord() {
        AttemptCommandFactory factory = new AttemptCommandFactory(questionsLogic, submissionLogic);
        assertEquals("attempt", factory.getCommandWord());
    }

    @Test
    void getCommand() throws InvalidCommandArgumentsException {
        AttemptCommandFactory factory = new AttemptCommandFactory(questionsLogic, submissionLogic);
        assertTrue(factory.getCommand("1") instanceof AttemptCommand);

        assertThrows(InvalidCommandArgumentsException.class,
                "Index should be a valid number.", () -> factory.getCommand("Test"));
    }
}
