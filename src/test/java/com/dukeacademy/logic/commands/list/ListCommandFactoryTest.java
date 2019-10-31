package com.dukeacademy.logic.commands.list;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.testutil.MockQuestionsLogic;


public class ListCommandFactoryTest {
    @TempDir public Path tempFolder;
    private MockQuestionsLogic questionsLogic;
    @BeforeEach void initializeTest() throws LogicCreationException {
        this.questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
    }

    @Test void getCommandWord() {
        ListCommandFactory factory = new ListCommandFactory(questionsLogic);
        assertEquals("showall", factory.getCommandWord());
    }

    @Test void getCommand() throws InvalidCommandArgumentsException {
        ListCommandFactory factory = new ListCommandFactory(questionsLogic);
        Command command = factory.getCommand("    ");
        assertTrue(command instanceof ListCommand);

        assertThrows(InvalidCommandArgumentsException.class,
            "Showall command does not take any arguments", () -> factory
                .getCommand("123"));
    }
}
