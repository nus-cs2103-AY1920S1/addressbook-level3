package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandBuilder;
import seedu.address.logic.commands.stubs.CommandStub;
import seedu.address.logic.commands.stubs.OneArgumentCommandStub;
import seedu.address.logic.parser.exceptions.ParseException;

class CommandKeywordParserTest {

    private static final String KEYWORD1 = "keyword1";
    private static final String KEYWORD2 = "keyword2";

    @Test
    void add_nullCommand_failure() {
        CommandKeywordParser keywordParser = new CommandKeywordParser();
        assertThrows(NullPointerException.class, () -> keywordParser.addCommand(KEYWORD1, null));
        assertThrows(NullPointerException.class, () -> keywordParser.addCommand(KEYWORD2, () -> null));
    }

    @Test
    void parse_invalidKeyword_failure() {
        CommandKeywordParser keywordParser = new CommandKeywordParser();
        keywordParser.addCommand(KEYWORD1, CommandStub::newBuilder);
        keywordParser.addCommand(KEYWORD2, CommandStub::newBuilder);
        assertThrows(NullPointerException.class, () -> keywordParser.parse(null));
        assertThrows(ParseException.class, () -> keywordParser.parse(""));
    }

    @Test
    void parse_validKeyword_success() {
        CommandKeywordParser keywordParser = new CommandKeywordParser();
        keywordParser.addCommand(KEYWORD1, CommandStub::newBuilder);
        keywordParser.addCommand(KEYWORD2, OneArgumentCommandStub::newBuilder);
        assertDoesNotThrow(() -> {
            CommandBuilder builder = keywordParser.parse(KEYWORD1);
            assertTrue(builder instanceof CommandStub.Builder);
        });
        assertDoesNotThrow(() -> {
            CommandBuilder builder = keywordParser.parse(KEYWORD2);
            assertTrue(builder instanceof OneArgumentCommandStub.Builder);
        });
    }
}
