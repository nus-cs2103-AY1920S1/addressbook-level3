package seedu.elisa.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.elisa.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.elisa.logic.commands.AddEventCommand;
import seedu.elisa.logic.commands.AddReminderCommand;
import seedu.elisa.logic.commands.AddTaskCommand;
import seedu.elisa.logic.commands.ClearCommand;
import seedu.elisa.logic.commands.CloseCommand;
import seedu.elisa.logic.commands.ContinueCommand;
import seedu.elisa.logic.commands.DeleteCommand;
import seedu.elisa.logic.commands.DoneCommand;
import seedu.elisa.logic.commands.DownCommand;
import seedu.elisa.logic.commands.EditCommand;
import seedu.elisa.logic.commands.ExitCommand;
import seedu.elisa.logic.commands.FindCommand;
import seedu.elisa.logic.commands.GameCommand;
import seedu.elisa.logic.commands.JokeCommand;
import seedu.elisa.logic.commands.OpenCommand;
import seedu.elisa.logic.commands.PriorityCommand;
import seedu.elisa.logic.commands.RedoCommand;
import seedu.elisa.logic.commands.ScheduledPriorityCommand;
import seedu.elisa.logic.commands.ShowCommand;
import seedu.elisa.logic.commands.SnoozeCommand;
import seedu.elisa.logic.commands.SortCommand;
import seedu.elisa.logic.commands.ThemeCommand;
import seedu.elisa.logic.commands.UndoCommand;
import seedu.elisa.logic.commands.UpCommand;
import seedu.elisa.logic.parser.exceptions.ParseException;
import seedu.elisa.model.ElisaCommandHistoryManager;

public class ElisaParserTest {
    private ElisaParser testParser = new ElisaParser(new ElisaCommandHistoryManager());

    @Test
    public void parse_task_newTaskCommand() throws ParseException {
        assertTrue(testParser.parseCommand("task desc") instanceof AddTaskCommand);
    }

    @Test
    public void parse_event_newEventommand() throws ParseException {
        assertTrue(testParser.parseCommand("event test -d 1.min.later") instanceof AddEventCommand);
    }

    @Test
    public void parse_reminder_newReminderCommand() throws ParseException {
        assertTrue(testParser.parseCommand("reminder test -r 1.min.later") instanceof AddReminderCommand);
    }

    @Test
    public void parse_edit_newEditCommand() throws ParseException {
        assertTrue(testParser.parseCommand("edit 1 --tk") instanceof EditCommand);
    }

    @Test
    public void parse_delete_newDeleteCommand() throws ParseException {
        assertEquals(testParser.parseCommand("delete 1"), new DeleteCommand(ParserUtil.parseIndex("1")));
    }

    @Test
    public void parse_clear_newClearCommand() throws ParseException {
        assertEquals(testParser.parseCommand("clear"), new ClearCommand());
    }

    @Test
    public void parse_find_newFindCommand() throws ParseException {
        assertTrue(testParser.parseCommand("find test") instanceof FindCommand);
    }

    @Test
    public void parse_undo_newUndoCommand() throws ParseException {
        assertTrue(testParser.parseCommand("undo") instanceof UndoCommand);
    }

    @Test
    public void parse_redo_newRedoCommand() throws ParseException {
        assertTrue(testParser.parseCommand("redo") instanceof RedoCommand);
    }

    @Test
    public void parse_show_newShowCommand() throws ParseException {
        assertEquals(testParser.parseCommand("show T"), new ShowCommand("T"));
    }

    @Test
    public void parse_sort_newSortCommand() throws ParseException {
        assertEquals(testParser.parseCommand("sort"), new SortCommand(Optional.empty()));
    }

    @Test
    public void parse_exit_newExitCommand() throws ParseException {
        assertTrue(testParser.parseCommand("exit") instanceof ExitCommand);
    }

    @Test
    public void parse_priority_newPriorityCommand() throws ParseException {
        assertTrue(testParser.parseCommand("priority") instanceof PriorityCommand);
    }

    @Test
    public void parse_scheduledPriority_newSchedulePriorityCommand() throws ParseException {
        assertTrue(testParser.parseCommand("priority 1.min.later") instanceof ScheduledPriorityCommand);
    }

    @Test
    public void parse_done_newDoneCommand() throws ParseException {
        assertEquals(testParser.parseCommand("done 1"), new DoneCommand(ParserUtil.parseIndex("1")));
    }

    @Test
    public void parse_continue_newContinueCommand() throws ParseException {
        assertEquals(testParser.parseCommand("cont 1"), new ContinueCommand(ParserUtil.parseIndex("1")));
    }

    @Test
    public void parse_joke_newJokeCommand() throws ParseException {
        assertTrue(testParser.parseCommand("joke") instanceof JokeCommand);
    }

    @Test
    public void parse_theme_newThemeCommand() throws ParseException {
        assertTrue(testParser.parseCommand("theme white") instanceof ThemeCommand);
    }

    @Test
    public void parse_up_newUpCommand() throws ParseException {
        assertTrue(testParser.parseCommand("up") instanceof UpCommand);
    }

    @Test
    public void parse_down_newDownCommand() throws ParseException {
        assertTrue(testParser.parseCommand("down") instanceof DownCommand);
    }

    @Test
    public void parse_game_newGameCommand() throws ParseException {
        assertTrue(testParser.parseCommand("game") instanceof GameCommand);
    }

    @Test
    public void parse_snooze_newSnoozeCommand() throws ParseException {
        assertTrue(testParser.parseCommand("snooze") instanceof SnoozeCommand);
    }

    @Test
    public void parse_open_newOpenCommand() throws ParseException {
        assertTrue(testParser.parseCommand("open 1") instanceof OpenCommand);
    }

    @Test
    public void parse_close_newCloseCommand() throws ParseException {
        assertTrue(testParser.parseCommand("close") instanceof CloseCommand);
    }

    @Test
    public void parse_invalidCommand_throwsParseException () {
        assertThrows(ParseException.class, () -> testParser.parseCommand("test"));
    }
}
