package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.EXCEL_FILE_ILLEGAL_INPUT;
import static seedu.address.commons.core.Messages.EXCEL_FILE_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.question.QuestionEditCommand;
import seedu.address.logic.commands.statistics.StatisticsAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;
import seedu.address.testutil.QuestionUtil;

public class NjoyParserTest {

    private final NjoyParser parser = new NjoyParser();

    @Test
    public void parseCommand_statistics_addValidFile() throws Exception {
        Command command = parser.parseCommand(
                "statistics file/src/test/data/SampleStatisticsData/ValidSampleStatistics.xlsx");
        assertTrue(command instanceof StatisticsAddCommand);
    }

    @Test
    public void parseCommand_statistics_addInvalidFile() throws Exception {
        assertThrows(ParseException.class, EXCEL_FILE_ILLEGAL_INPUT, () ->
            parser.parseCommand(
                "statistics file/src/test/data/SampleStatisticsData/InvalidSampleStatistics.xlsx"));
    }

    @Test
    public void parseCommand_statistics_addEmptyFile() throws Exception {
        assertThrows(ParseException.class, EXCEL_FILE_NOT_FOUND, () ->
            parser.parseCommand("statistics file/e"));
    }

    @Test
    public void parseCommand_statistics_invalidSyntax() {
        assertThrows(ParseException.class, String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, StatisticsAddCommand.MESSAGE_USAGE), () ->
            parser.parseCommand("statistics"));
    }

    @Test
    public void parseCommand_question_edit() throws Exception {
        Question question = new OpenEndedQuestion("Test", "Test Answer");
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");
        QuestionEditCommand command = (QuestionEditCommand) parser
            .parseCommand(QuestionUtil.getEditCommand(question));
        assertEquals(new QuestionEditCommand(INDEX_ONE, fields), command);
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
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand"));
    }
}
