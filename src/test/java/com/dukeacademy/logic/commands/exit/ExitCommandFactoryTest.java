package com.dukeacademy.logic.commands.exit;

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
import com.dukeacademy.testutil.MockNotesLogic;
import com.dukeacademy.testutil.MockQuestionsLogic;

class ExitCommandFactoryTest {
    @TempDir public Path tempFolder;

    private MockQuestionsLogic questionsLogic;
    private ProgramSubmissionLogic submissionLogic;
    private MockNotesLogic notesLogic;

    @BeforeEach void initializeTest() throws LogicCreationException {
        this.questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        this.submissionLogic = new ProgramSubmissionLogicManager(tempFolder.toString());
    }

    @Test
    void getCommandWord() {
        ExitCommandFactory factory = new ExitCommandFactory(questionsLogic, submissionLogic, notesLogic);
        assertEquals("exit", factory.getCommandWord());
    }

    @Test
    void getCommand() throws InvalidCommandArgumentsException {
        ExitCommandFactory factory = new ExitCommandFactory(questionsLogic, submissionLogic, notesLogic);
        Command command = factory.getCommand("    ");
        assertTrue(command instanceof ExitCommand);

        assertThrows(InvalidCommandArgumentsException.class, () -> factory.getCommand("abc"));
    }
}
