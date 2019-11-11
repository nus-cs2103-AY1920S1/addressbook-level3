package seedu.jarvis.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_DES;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_TYPE;
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
import seedu.jarvis.logic.commands.history.ListHistoryCommand;
import seedu.jarvis.logic.commands.history.RedoCommand;
import seedu.jarvis.logic.commands.history.UndoCommand;
import seedu.jarvis.logic.commands.planner.AddTaskCommand;
import seedu.jarvis.logic.commands.planner.DeleteTaskCommand;
import seedu.jarvis.logic.commands.planner.DoneTaskCommand;
import seedu.jarvis.logic.commands.planner.FindTaskCommand;
import seedu.jarvis.logic.commands.planner.ListScheduleCommand;
import seedu.jarvis.logic.commands.planner.ListTaskCommand;
import seedu.jarvis.logic.commands.planner.PullTaskCommand;
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

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }


    //======================= history manager ==========================================================

    @Test
    public void parseCommand_listHistory() throws Exception {
        assertTrue(parser.parseCommand(ListHistoryCommand.COMMAND_WORD) instanceof ListHistoryCommand);
        assertTrue(parser.parseCommand(ListHistoryCommand.COMMAND_WORD + " 5")
                instanceof ListHistoryCommand);
    }

    /**
     * Verifies that parsing undo commands work as intended.
     */
    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " " + "5")
                instanceof UndoCommand);
    }

    /**
     * Verifies that parsing redo commands work as intended.
     */
    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " " + "5")
                instanceof RedoCommand);
    }

    //========================== course planner ===============================================

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

    //============================= task planner ==============================================

    @Test
    public void parseCommand_addTask() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(AddTaskCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(AddTaskCommand.COMMAND_WORD + " " + PREFIX_TASK_TYPE + "todo "
                                        + PREFIX_TASK_DES + "borrow book") instanceof AddTaskCommand);
    }

    @Test
    public void parseCommand_deleteTask() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(DeleteTaskCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(DeleteTaskCommand.COMMAND_WORD + " 2") instanceof DeleteTaskCommand);
    }

    @Test
    public void parseCommand_findTask() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(FindTaskCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(FindTaskCommand.COMMAND_WORD + " book") instanceof FindTaskCommand);
    }

    @Test
    public void parseCommand_doneTask() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(DoneTaskCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(DoneTaskCommand.COMMAND_WORD + " 1") instanceof DoneTaskCommand);
    }

    @Test
    public void parseCommand_listTask() throws Exception {
        assertTrue(parser.parseCommand(ListTaskCommand.COMMAND_WORD) instanceof ListTaskCommand);
    }

    @Test
    public void parseCommand_listSchedule() throws Exception {
        assertTrue(parser.parseCommand(ListScheduleCommand.COMMAND_WORD) instanceof ListScheduleCommand);
    }

    @Test
    public void parseCommand_pullTask() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(PullTaskCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(PullTaskCommand.COMMAND_WORD + " " + PREFIX_TASK_TYPE + "todo")
                    instanceof PullTaskCommand);
    }
}
