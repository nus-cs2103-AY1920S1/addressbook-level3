package com.dukeacademy.logic.commands.exit;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogicManager;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.testutil.MockQuestionsLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExitCommandFactoryTest {
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
        ExitCommandFactory factory = new ExitCommandFactory(questionsLogic, submissionLogic);
        assertEquals("exit", factory.getCommandWord());
    }

    @Test
    void getCommand() throws InvalidCommandArgumentsException {
        ExitCommandFactory factory = new ExitCommandFactory(questionsLogic, submissionLogic);
        Command command = factory.getCommand("    ");
        assertTrue(command instanceof ExitCommand);

        assertThrows(InvalidCommandArgumentsException.class, () -> factory.getCommand("abc"));
    }
}
