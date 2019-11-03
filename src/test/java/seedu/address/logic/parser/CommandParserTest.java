package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class CommandParserTest {

    private static final String COMMAND_STUB_KEYWORD = "stub";

    private static CommandParser commandParser;

    @BeforeAll
    static void setUp() {
        CommandKeywordParser keywordParser = new CommandKeywordParser();
        keywordParser.addCommand(COMMAND_STUB_KEYWORD, CommandStub::newBuilder);
        commandParser = new CommandParser(keywordParser);
    }

    @Test
    void parse_noCommandKeyword_failure() {
        String[] tests = { "", " " };
        for (String test : tests) {
            assertThrows(ParseException.class, () -> commandParser.parse(test));
        }
    }

    @Test
    void parse_validCommandPhrase_success() {
        String[] tests = { "", " '", " ''", " '''" };
        for (String test : tests) {
            assertDoesNotThrow(() -> commandParser.parse(COMMAND_STUB_KEYWORD + test));
        }
    }
}
