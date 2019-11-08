package com.typee.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.exceptions.CommandException;
import com.typee.ui.Tab;

public class CommandResultTest {
    private static LocalDate localDate = LocalDate.of(2019, 1, 12);
    private static Tab tab = new Tab("main");
    private static String calendarCommandType = "closedisplay";
    private static Path pathStub = new Path() {
        @Override
        public FileSystem getFileSystem() {
            return null;
        }

        @Override
        public boolean isAbsolute() {
            return false;
        }

        @Override
        public Path getRoot() {
            return null;
        }

        @Override
        public Path getFileName() {
            return null;
        }

        @Override
        public Path getParent() {
            return null;
        }

        @Override
        public int getNameCount() {
            return 0;
        }

        @Override
        public Path getName(int index) {
            return null;
        }

        @Override
        public Path subpath(int beginIndex, int endIndex) {
            return null;
        }

        @Override
        public boolean startsWith(Path other) {
            return false;
        }

        @Override
        public boolean endsWith(Path other) {
            return false;
        }

        @Override
        public Path normalize() {
            return null;
        }

        @Override
        public Path resolve(Path other) {
            return null;
        }

        @Override
        public Path relativize(Path other) {
            return null;
        }

        @Override
        public URI toUri() {
            return null;
        }

        @Override
        public Path toAbsolutePath() {
            return null;
        }

        @Override
        public Path toRealPath(LinkOption... options) throws IOException {
            return null;
        }

        @Override
        public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers)
                throws IOException {
            return null;
        }

        @Override
        public int compareTo(Path other) {
            return 0;
        }
    };
    private static final CommandResult TYPICAL_CALENDAR_COMMAND_RESULT = new CommandResult("Calendar command",
            true, localDate, calendarCommandType);
    private static final CommandResult TYPICAL_UNDO_COMMAND_RESULT = new CommandResult("Successfully undone!",
            false, false);
    private static final CommandResult TYPICAL_TAB_COMMAND_RESULT = new CommandResult("Tab command",
            true, tab);
    private static final CommandResult TYPICAL_HELP_COMMAND_RESULT = new CommandResult("Help", true, true);
    private static final CommandResult TYPICAL_EXIT_COMMAND_RESULT = new CommandResult("Exits", false, true);
    private static final CommandResult TYPICAL_PDF_COMMAND_RESULT = new CommandResult("pdf", true, pathStub);

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }

    @Test
    public void isShowHelp() {
        assertFalse(TYPICAL_CALENDAR_COMMAND_RESULT.isShowHelp());
        assertFalse(TYPICAL_TAB_COMMAND_RESULT.isShowHelp());
        assertFalse(TYPICAL_UNDO_COMMAND_RESULT.isShowHelp());
        assertTrue(TYPICAL_HELP_COMMAND_RESULT.isShowHelp());
    }

    @Test
    public void isExit() {
        assertFalse(TYPICAL_UNDO_COMMAND_RESULT.isExit());
        assertFalse(TYPICAL_TAB_COMMAND_RESULT.isExit());
        assertFalse(TYPICAL_CALENDAR_COMMAND_RESULT.isExit());
        assertTrue(TYPICAL_EXIT_COMMAND_RESULT.isExit());
    }

    @Test
    public void isTabCommand() {
        assertTrue(TYPICAL_TAB_COMMAND_RESULT.isTabCommand());
        assertFalse(TYPICAL_CALENDAR_COMMAND_RESULT.isTabCommand());
    }

    @Test
    public void getTab() {
        assertEquals(TYPICAL_TAB_COMMAND_RESULT.getTab(), this.tab);
    }

    @Test
    public void isCalendarCommand() {
        assertTrue(TYPICAL_CALENDAR_COMMAND_RESULT.isCalendarCommand());
        assertFalse(TYPICAL_TAB_COMMAND_RESULT.isCalendarCommand());
    }

    @Test
    public void isPdfCommand() {
        assertTrue(TYPICAL_PDF_COMMAND_RESULT.isPdfCommand());
        assertFalse(TYPICAL_TAB_COMMAND_RESULT.isPdfCommand());
    }

    @Test
    public void getCalendarDate() {
        assertThrows(CommandException.class, () -> TYPICAL_EXIT_COMMAND_RESULT.getCalendarDate());
        assertDoesNotThrow(()->assertEquals(TYPICAL_CALENDAR_COMMAND_RESULT.getCalendarDate(), localDate));
    }

    @Test
    public void getCalendarCommandTyoe() {
        assertThrows(CommandException.class, () -> TYPICAL_EXIT_COMMAND_RESULT.getCalendarCommandType());
        assertDoesNotThrow(()->assertEquals(TYPICAL_CALENDAR_COMMAND_RESULT.getCalendarCommandType(),
                calendarCommandType));
    }

    @Test
    public void getDocPath() {
        assertEquals(TYPICAL_PDF_COMMAND_RESULT.getDocPath(), pathStub);
    }
}
