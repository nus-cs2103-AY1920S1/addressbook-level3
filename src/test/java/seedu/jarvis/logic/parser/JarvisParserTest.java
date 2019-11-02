package seedu.jarvis.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;
import static seedu.jarvis.logic.parser.CliSyntax.UndoRedoSyntax.PREFIX_UNDO_REDO;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.ExitCommand;
import seedu.jarvis.logic.commands.HelpCommand;
import seedu.jarvis.logic.commands.course.AddCourseCommand;
import seedu.jarvis.logic.commands.course.CheckCommand;
import seedu.jarvis.logic.commands.course.ClearCourseCommand;
import seedu.jarvis.logic.commands.course.DeleteCourseCommand;
import seedu.jarvis.logic.commands.course.ListCourseCommand;
import seedu.jarvis.logic.commands.course.LookUpCommand;
import seedu.jarvis.logic.commands.course.ShowCourseHelpCommand;
import seedu.jarvis.logic.commands.history.RedoCommand;
import seedu.jarvis.logic.commands.history.UndoCommand;
import seedu.jarvis.logic.parser.exceptions.ParseException;

public class JarvisParserTest {

    private final JarvisParser parser = new JarvisParser();

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

    /**
     * Verifies that parsing undo commands work as intended.
     */
    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " " + PREFIX_UNDO_REDO + "5")
                instanceof UndoCommand);
    }

    /**
     * Verifies that parsing redo commands work as intended.
     */
    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " " + PREFIX_UNDO_REDO + "5")
                instanceof RedoCommand);
    }

    @Test
    public void parseCommand_lookUp() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(LookUpCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(LookUpCommand.COMMAND_WORD + " " + PREFIX_COURSE + "CS3230")
                instanceof LookUpCommand);
    }

    @Test
    public void parseCommand_check() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(CheckCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(CheckCommand.COMMAND_WORD + " " + PREFIX_COURSE + "CS3230")
            instanceof CheckCommand);
    }

    @Test
    public void parseCommand_addCourse() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(AddCourseCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(AddCourseCommand.COMMAND_WORD + " " + PREFIX_COURSE + "CS3230")
                instanceof AddCourseCommand);
        assertTrue(parser.parseCommand(AddCourseCommand.COMMAND_WORD + " " + PREFIX_COURSE
                + "CS3230 " + PREFIX_COURSE + "CS1010") instanceof AddCourseCommand);
    }

    @Test
    public void parseCommand_deleteCourse() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(DeleteCourseCommand.COMMAND_WORD));
        assertTrue(
            parser.parseCommand(String.format("%s %sCS2102", DeleteCourseCommand.COMMAND_WORD, PREFIX_COURSE))
                    instanceof DeleteCourseCommand
        );
        assertTrue(
            parser.parseCommand(String.format("%s %s", DeleteCourseCommand.COMMAND_WORD, 1))
                instanceof DeleteCourseCommand
        );
    }

    @Test
    public void parseCommand_listCourse() throws Exception {
        assertTrue(parser.parseCommand(ListCourseCommand.COMMAND_WORD) instanceof ListCourseCommand);
        assertTrue(parser.parseCommand(ListCourseCommand.COMMAND_WORD + " 3") instanceof ListCourseCommand);
    }

    @Test
    public void parseCommand_clearCourse() throws Exception {
        assertTrue(parser.parseCommand(ClearCourseCommand.COMMAND_WORD) instanceof ClearCourseCommand);
        assertTrue(parser.parseCommand(ClearCourseCommand.COMMAND_WORD + " 3") instanceof ClearCourseCommand);
    }

    @Test
    public void parseCommand_showCourseHelp() throws Exception {
        assertTrue(parser.parseCommand(ShowCourseHelpCommand.COMMAND_WORD) instanceof ShowCourseHelpCommand);
        assertTrue(parser.parseCommand(ShowCourseHelpCommand.COMMAND_WORD + " 3") instanceof ShowCourseHelpCommand);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }
}
