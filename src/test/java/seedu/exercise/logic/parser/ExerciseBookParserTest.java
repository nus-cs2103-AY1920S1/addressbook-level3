package seedu.exercise.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.CATEGORY_DESC_EXERCISE;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_INDEX;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_LIST_TYPE_EXERCISE;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_LIST_TYPE_SUGGESTION;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIRST;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.ClearCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.ExitCommand;
import seedu.exercise.logic.commands.HelpCommand;
import seedu.exercise.logic.commands.ListCommand;
import seedu.exercise.logic.commands.RedoCommand;
import seedu.exercise.logic.commands.UndoCommand;
import seedu.exercise.logic.commands.builder.EditExerciseBuilder;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.ExerciseUtil;
import seedu.exercise.testutil.builder.EditExerciseDescriptorBuilder;
import seedu.exercise.testutil.builder.ExerciseBuilder;

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
                + PREFIX_INDEX + INDEX_ONE_BASED_FIRST.getOneBased());
        assertEquals(new DeleteExerciseCommand(INDEX_ONE_BASED_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Exercise build = new ExerciseBuilder().build();
        EditExerciseBuilder descriptor = new EditExerciseDescriptorBuilder(build).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD
            + VALID_PREFIX_INDEX + " " + ExerciseUtil.getEditExerciseDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_ONE_BASED_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser
            .parseCommand(ListCommand.COMMAND_WORD
                + VALID_PREFIX_LIST_TYPE_EXERCISE) instanceof ListCommand);
        assertTrue(parser
            .parseCommand(ListCommand.COMMAND_WORD
                + VALID_PREFIX_LIST_TYPE_SUGGESTION) instanceof ListCommand);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 3") instanceof RedoCommand);
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
