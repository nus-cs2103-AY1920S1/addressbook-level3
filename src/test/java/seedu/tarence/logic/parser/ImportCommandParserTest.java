package seedu.tarence.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.ImportCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;

public class ImportCommandParserTest {

    @Test
    public void parse_validUrl_successfulParse() throws ParseException {
        final String url = "https://nusmods.com/timetable/sem-1/share?CS1101S=TUT:09F,REC:16,LEC:2&GET1029=TUT:W6";

        ImportCommand expectedCommand = new ImportCommand(NusModsParser.urlToTutorials(url));
        ImportCommand actualCommand = new ImportCommandParser().parse(url);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidUrl_throwsParseException() throws ParseException {
        final String url = "https://nusmods.com/timetable/sem-3/share?CS1101S=TUT:09F,REC:16,LEC:2&GET1029=TUT:W6";

        assertThrows(ParseException.class, () -> new ImportCommandParser().parse(url), ImportCommand.MESSAGE_USAGE);
    }
}
