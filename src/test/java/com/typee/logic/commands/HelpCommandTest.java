package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.model.Model;
import com.typee.model.ModelManager;

public class HelpCommandTest {

    private HelpCommand helpCommand;
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        helpCommand = new HelpCommand();
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(helpCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        HelpCommand otherHelpCommand = new HelpCommand();
        assertEquals(helpCommand, helpCommand);
        assertEquals(helpCommand, otherHelpCommand);
    }

}
