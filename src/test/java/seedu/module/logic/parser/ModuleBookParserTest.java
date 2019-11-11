package seedu.module.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.module.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.module.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.module.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.module.logic.commands.AddCommand;
import seedu.module.logic.commands.BackCommand;
import seedu.module.logic.commands.DeleteCommand;
import seedu.module.logic.commands.ExitCommand;
import seedu.module.logic.commands.FindCommand;
import seedu.module.logic.commands.HelpCommand;
import seedu.module.logic.commands.ListCommand;
import seedu.module.logic.commands.ViewCommand;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.Module;
import seedu.module.model.module.TrackedModule;
import seedu.module.model.module.predicate.ModuleCodeContainsKeywordsPredicate;
import seedu.module.model.module.predicate.SameModuleCodePredicate;
import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.TrackedModuleBuilder;

public class ModuleBookParserTest {

    private final ModuleBookParser parser = new ModuleBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        SameModuleCodePredicate predicate = new SameModuleCodePredicate(archivedModule.getModuleCode());

        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_WORD
                + " " + archivedModule.getModuleCode());
        assertEquals(new AddCommand(predicate), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        SameModuleCodePredicate predicate = new SameModuleCodePredicate(trackedModule.getModuleCode());

        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + trackedModule.getModuleCode());
        assertEquals(new DeleteCommand(predicate), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("mod\\", "cs2030", "ma1521");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));

        List<String> predicateKeywords = Arrays.asList("cs2030", "ma1521");
        ModuleCodeContainsKeywordsPredicate predicate = new ModuleCodeContainsKeywordsPredicate(predicateKeywords);
        ArrayList<Predicate<Module>> listOfPredicates = new ArrayList<>();
        listOfPredicates.add(predicate);
        assertEquals(new FindCommand(listOfPredicates), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }


    @Test
    public void parseCommand_view() throws Exception {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();

        ViewCommand command = (ViewCommand) parser.parseCommand(ViewCommand.COMMAND_WORD
                + " " + archivedModule.getModuleCode());
        assertEquals(new ViewCommand(archivedModule.getModuleCode()), command);
    }

    @Test
    public void parseCommand_back() throws Exception {
        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD) instanceof BackCommand);
    }


    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
