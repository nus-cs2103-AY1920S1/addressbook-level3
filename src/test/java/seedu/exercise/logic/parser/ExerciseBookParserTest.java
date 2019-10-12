package seedu.exercise.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.exercise.logic.commands.CommandTestUtil.CATEGORY_DESC_EXERCISE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.ClearCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.EditCommand.EditExerciseDescriptor;
import seedu.exercise.logic.commands.ExitCommand;
import seedu.exercise.logic.commands.FindCommand;
import seedu.exercise.logic.commands.HelpCommand;
import seedu.exercise.logic.commands.ListCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.NameContainsKeywordsPredicate;
import seedu.exercise.testutil.EditExerciseDescriptorBuilder;
import seedu.exercise.testutil.ExerciseBuilder;
import seedu.exercise.testutil.ExerciseUtil;

public class ExerciseBookParserTest {

    private final ExerciseBookParser parser = new ExerciseBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Exercise exercise = new ExerciseBuilder().build();
        AddExerciseCommand command = (AddExerciseCommand) parser.parseCommand(ExerciseUtil.getAddCommand(exercise));
        assertEquals(new AddExerciseCommand(exercise), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteExerciseCommand command = (DeleteExerciseCommand) parser.parseCommand(
                DeleteExerciseCommand.COMMAND_WORD + " " + CATEGORY_DESC_EXERCISE + " "
                        + PREFIX_INDEX + INDEX_FIRST_EXERCISE.getOneBased());
        assertEquals(new DeleteExerciseCommand(INDEX_FIRST_EXERCISE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Exercise build = new ExerciseBuilder().build();
        EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(build).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EXERCISE.getOneBased() + " " + ExerciseUtil.getEditExerciseDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_EXERCISE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
