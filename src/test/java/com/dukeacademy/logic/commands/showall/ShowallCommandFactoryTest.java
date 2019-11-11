package com.dukeacademy.logic.commands.showall;

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
import com.dukeacademy.model.state.ApplicationState;
import com.dukeacademy.testutil.MockQuestionsLogic;


public class ShowallCommandFactoryTest {
    @TempDir public Path tempFolder;

    private MockQuestionsLogic questionsLogic;
    private ApplicationState applicationState;

    @BeforeEach
    void initializeTest() throws LogicCreationException {
        this.questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        this.applicationState = new ApplicationState();
    }

    @Test
    void getCommandWord() {
        ShowallCommandFactory
            factory = new ShowallCommandFactory(questionsLogic, applicationState);
        assertEquals("showall", factory.getCommandWord());
    }

    @Test
    void getCommand() throws InvalidCommandArgumentsException {
        ShowallCommandFactory
            factory = new ShowallCommandFactory(questionsLogic, applicationState);
        Command command = factory.getCommand("    ");
        assertTrue(command instanceof ShowallCommand);

        assertThrows(InvalidCommandArgumentsException.class,
            "Showall command does not take any arguments", () -> factory
                .getCommand("123"));
    }
}
