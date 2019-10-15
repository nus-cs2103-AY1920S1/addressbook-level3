package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddExerciseCommand;
import seedu.address.logic.commands.ClearExerciseCommand;
import seedu.address.logic.commands.DeleteExerciseCommand;
import seedu.address.logic.commands.EditExerciseCommand;
import seedu.address.logic.commands.EditExerciseCommand.EditExerciseDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindExerciseCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListExerciseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditExerciseDescriptorBuilder;
import seedu.address.testutil.ExerciseBuilder;
import seedu.address.testutil.ExerciseUtil;

public class WorkoutPlannerParserTest {

    private final WorkoutPlannerParser parser = new WorkoutPlannerParser();

    @Test
    public void parseCommand_add() throws Exception {
        Exercise exercise = new ExerciseBuilder().build();
        AddExerciseCommand command = (AddExerciseCommand) parser
                .parseCommand(ExerciseUtil.getAddExerciseCommand(exercise));
        assertEquals(new AddExerciseCommand(exercise), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearExerciseCommand.COMMAND_WORD) instanceof ClearExerciseCommand);
        assertTrue(parser.parseCommand(ClearExerciseCommand.COMMAND_WORD + " 3") instanceof ClearExerciseCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteExerciseCommand command = (DeleteExerciseCommand) parser.parseCommand(
                DeleteExerciseCommand.COMMAND_WORD + " " + INDEX_FIRST_EXERCISE.getOneBased());
        assertEquals(new DeleteExerciseCommand(INDEX_FIRST_EXERCISE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Exercise exercise = new ExerciseBuilder().build();
        EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(exercise).build();
        EditExerciseCommand command = (EditExerciseCommand) parser.parseCommand(EditExerciseCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EXERCISE.getOneBased() + " " + ExerciseUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditExerciseCommand(INDEX_FIRST_EXERCISE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindExerciseCommand command = (FindExerciseCommand) parser.parseCommand(
                FindExerciseCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindExerciseCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListExerciseCommand.COMMAND_WORD) instanceof ListExerciseCommand);
        assertTrue(parser.parseCommand(ListExerciseCommand.COMMAND_WORD + " 3") instanceof ListExerciseCommand);
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
