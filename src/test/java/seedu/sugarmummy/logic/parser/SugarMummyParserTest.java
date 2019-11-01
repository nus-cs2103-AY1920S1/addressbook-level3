package seedu.sugarmummy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.sugarmummy.testutil.Assert.assertThrows;
import static seedu.sugarmummy.testutil.TypicalIndexes.INDEX_FIRST_RECORD;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.AddCommand;
import seedu.sugarmummy.logic.commands.DeleteCommand;
import seedu.sugarmummy.logic.commands.ExitCommand;
import seedu.sugarmummy.logic.commands.HelpCommand;
import seedu.sugarmummy.logic.commands.ListCommand;
import seedu.sugarmummy.logic.commands.achvm.AchvmCommand;
import seedu.sugarmummy.logic.commands.bio.BioCommand;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.record.BloodSugar;
import seedu.sugarmummy.model.record.Concentration;
import seedu.sugarmummy.model.time.DateTime;

public class SugarMummyParserTest {

    private final SugarMummyParser parser = new SugarMummyParser();

    @Test
    public void parseCommand_add() throws Exception {

        LocalDate ld = LocalDate.of(1970, Month.JANUARY, 1);
        LocalTime lt = LocalTime.of(8, 0, 0);
        DateTime dt = new DateTime(ld, lt);
        BloodSugar bs = new BloodSugar(new Concentration("12.34"), dt);

        AddCommand command = (AddCommand) parser.parseCommand("add rt/BLOODSUGAR con/12.34 dt/1970-01-01 08:00");

        assertEquals(new AddCommand(bs), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_RECORD), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(ExitCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(HelpCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_bio() throws Exception {
        assertTrue(parser.parseCommand(BioCommand.COMMAND_WORD) instanceof BioCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(BioCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_achvm() throws Exception {
        assertTrue(parser.parseCommand(AchvmCommand.COMMAND_WORD) instanceof AchvmCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(AchvmCommand.COMMAND_WORD + " 3"));
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
