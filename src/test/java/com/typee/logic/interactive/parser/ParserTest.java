package com.typee.logic.interactive.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.ClearCommand;
import com.typee.logic.commands.Command;
import com.typee.logic.commands.CommandResult;
import com.typee.logic.commands.CurrentCommand;
import com.typee.logic.commands.HelpCommand;
import com.typee.logic.commands.RedoCommand;
import com.typee.logic.commands.TabCommand;
import com.typee.logic.commands.UndoCommand;
import com.typee.logic.interactive.parser.exceptions.ParseException;
import com.typee.ui.Tab;

class ParserTest {

    private static final String INVALID_COMMAND_WORD_1 = "   ";
    private static final String INVALID_COMMAND_WORD_2 = "atta";
    private static final String INVALID_COMMAND_WORD_3 = "help add";
    private static final String VALID_COMMAND_WORD_1 = "clear";
    private static final String VALID_COMMAND_WORD_2 = "undo";
    private static final String VALID_COMMAND_WORD_3 = "redo";
    private static final String COMMAND_WORD_SORT = "sort";
    private static final String COMMAND_WORD_CURRENT = "// current";
    private static final String MESSAGE_SORT_PROPERTY_STATE = "Sort command initiated."
            + " Please enter the property you would"
            + " like to sort by, prefixed by \"p/\". The sortable properties are start date,"
            + " end date, description and priority, to be specified as \"start\", \"end\","
            + " \"description\" and \"priority\" respectively.";
    private static final String INVALID_COMMAND_EXCESSIVE_ARGUMENTS = "delete i/5 t/meeting";
    private static final String MESSAGE_INACTIVE_PARSER = "The arguments of the"
            + " previously entered command have been flushed."
            + " Please enter another command to get started!";
    private static final String COMMAND_WORD_DELETE = "delete";
    private static final String COMMAND_WORD_CLEAR_ARGUMENTS = "// clear";
    private static final String PARTIAL_CALENDAR_INPUT = "calendar c/opendisplay";
    private static final String MESSAGE_OPEN_DISPLAY_STATE = "Please enter a valid date in"
            + " the dd/mm/yyyy format, prefixed by \"d/\".";
    private static final String COMMAND_WORD_EXIT = "exit";
    private static final String PARTIAL_ADD_COMMAND_1 = "add t/interview s/15/11/2019/1600 e/16/11/2019/1700";
    private static final String PARTIAL_ADD_COMMAND_2 = "l/COM-1 d/Team Meeting";
    private static final String PARTIAL_ADD_COMMAND_3 = "a/ Jon Snow | Damith";
    private static final String PARTIAL_ADD_COMMAND_4 = "p/HIGh";
    private static final String PARTIAL_PDF_COMMAND = "pdf i/5";
    private static final String PARTIAL_FIND_COMMAND = "find";
    private static final String COMMAND_WORD_HELP = "help";
    private static final String COMMAND_WORD_TAB = "tab";
    private static final String PARTIAL_CALENDAR_COMMAND = "b/calendar";
    private static final String KEYWORD_CALENDAR_TAB = "Calendar";
    private InteractiveParser interactiveParser;

    @BeforeEach
    void setUp() {
        interactiveParser = new Parser();
    }

    @Test
    void parseInput_invalidCommand_returnsUnknownCommandMessage() {
        // Equivalence Partitions : Blank input, invalid command word, multiple command words

        // EP : Blank input
        assertThrows(ParseException.class, () -> interactiveParser.parseInput(INVALID_COMMAND_WORD_1));

        // EP : invalid command word
        interactiveParser = new Parser();
        assertThrows(ParseException.class, () -> interactiveParser.parseInput(INVALID_COMMAND_WORD_2));

        // EP : multiple command words
        interactiveParser = new Parser();
        assertThrows(ParseException.class, () -> interactiveParser.parseInput(INVALID_COMMAND_WORD_3));

    }

    @Test
    void parseInput_staticCommandsWithNoParameters_allowsMakeCommandToBuildCorrectCommand() throws ParseException {
        // Clear List Command
        interactiveParser.parseInput(VALID_COMMAND_WORD_1);
        assertTrue(interactiveParser.hasParsedCommand());
        assertEquals(interactiveParser.makeCommand(), new ClearCommand());

        // Undo Command
        interactiveParser = new Parser();
        interactiveParser.parseInput(VALID_COMMAND_WORD_2);
        assertTrue(interactiveParser.hasParsedCommand());
        assertEquals(interactiveParser.makeCommand(), new UndoCommand());

        // Redo Command
        interactiveParser = new Parser();
        interactiveParser.parseInput(VALID_COMMAND_WORD_3);
        assertTrue(interactiveParser.hasParsedCommand());
        assertEquals(interactiveParser.makeCommand(), new RedoCommand());
    }

    @Test
    void parseInput_staticCommandsWithParameters_allowsFetchingCorrectStateResult() throws ParseException {
        interactiveParser.parseInput(COMMAND_WORD_SORT);
        interactiveParser.parseInput(COMMAND_WORD_CURRENT);
        Command command = interactiveParser.makeCommand();
        assertEquals(command, new CurrentCommand(MESSAGE_SORT_PROPERTY_STATE));
    }

    @Test
    void parseInput_excessiveArguments_throwsParseException() {
        assertThrows(ParseException.class, () -> interactiveParser.parseInput(INVALID_COMMAND_EXCESSIVE_ARGUMENTS));
    }

    @Test
    void fetchResult_inactiveParser_returnsInactiveParserMessage() {
        assertEquals(interactiveParser.fetchResult(), new CommandResult(MESSAGE_INACTIVE_PARSER));
    }

    @Test
    void fetchResult_clearArgumentsCommand_returnsInactiveParserMessage() throws ParseException {
        interactiveParser.parseInput(COMMAND_WORD_DELETE);
        interactiveParser.parseInput(COMMAND_WORD_CLEAR_ARGUMENTS);
        assertEquals(interactiveParser.fetchResult(), new CommandResult(MESSAGE_INACTIVE_PARSER));
    }

    @Test
    void fetchResult_calendarPartialArguments_returnCorrectStateMessage() throws ParseException {
        interactiveParser.parseInput(PARTIAL_CALENDAR_INPUT);
        assertEquals(interactiveParser.fetchResult(), new CommandResult(MESSAGE_OPEN_DISPLAY_STATE));
    }

    @Test
    void hasParsedCommand_completeInputNoParameters_returnsTrue() throws ParseException {
        interactiveParser.parseInput(COMMAND_WORD_EXIT);
        assertTrue(interactiveParser.hasParsedCommand());
    }

    @Test
    void hasParsedCommand_completeInputWithParameters_returnsTrue() throws ParseException {
        interactiveParser.parseInput(PARTIAL_ADD_COMMAND_1);
        interactiveParser.parseInput(PARTIAL_ADD_COMMAND_2);
        interactiveParser.parseInput(PARTIAL_ADD_COMMAND_3);
        interactiveParser.parseInput(PARTIAL_ADD_COMMAND_4);
        assertTrue(interactiveParser.hasParsedCommand());
    }

    @Test
    void hasParsedCommand_noInput_returnsFalse() {
        assertFalse(interactiveParser.hasParsedCommand());
    }

    @Test
    void hasParsedCommand_incompleteInput_returnsFalse() throws ParseException {
        interactiveParser.parseInput(PARTIAL_PDF_COMMAND);
        assertFalse(interactiveParser.hasParsedCommand());
    }

    @Test
    void hasParsedCommand_incompleteInputOptionalCommand_returnsFalse() throws ParseException {
        interactiveParser.parseInput(PARTIAL_FIND_COMMAND);
        assertFalse(interactiveParser.hasParsedCommand());
    }

    @Test
    void makeCommand_helpCommand_returnsHelpCommand() throws ParseException {
        interactiveParser.parseInput(COMMAND_WORD_HELP);
        Command command = interactiveParser.makeCommand();
        assertEquals(command, new HelpCommand());
    }

    @Test
    void makeCommand_tabCommand_returnsTabCommand() throws ParseException {
        interactiveParser.parseInput(COMMAND_WORD_TAB);
        interactiveParser.parseInput(PARTIAL_CALENDAR_COMMAND);
        Command command = interactiveParser.makeCommand();
        assertEquals(command, new TabCommand(new Tab(KEYWORD_CALENDAR_TAB)));
    }
}
