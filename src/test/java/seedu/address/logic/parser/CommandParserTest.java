package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.stubs.CommandStub;
import seedu.address.logic.commands.stubs.OneArgumentCommandStub;
import seedu.address.logic.commands.stubs.OneVarArgumentCommandStub;
import seedu.address.logic.commands.stubs.OptionalTwoArgumentsOneVarArgumentCommandStub;
import seedu.address.logic.commands.stubs.TwoArgumentsCommandStub;
import seedu.address.logic.commands.stubs.TwoArgumentsOneVarArgumentCommandStub;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Test if CommandParser can parse any kind of command.
 */
class CommandParserTest {

    private static final String COMMAND_KEYWORD = "stub1";
    private static final String ONE_ARGUMENT_COMMAND_KEYWORD = "stub2";
    private static final String TWO_ARGUMENTS_COMMAND_KEYWORD = "stub3";
    private static final String ONE_VAR_ARGUMENT_COMMAND_KEYWORD = "stub4";
    private static final String TWO_ARGUMENTS_ONE_VAR_ARGUMENT_COMMAND_KEYWORD = "stub5";
    private static final String OPTIONAL_TWO_ARGUMENTS_ONE_VAR_ARGUMENT_COMMAND_KEYWORD = "stub6";

    private static CommandParser commandParser;

    @BeforeAll
    static void beforeAll() {
        CommandKeywordParser keywordParser = new CommandKeywordParser();
        keywordParser.addCommand(COMMAND_KEYWORD, CommandStub::newBuilder);
        keywordParser.addCommand(ONE_ARGUMENT_COMMAND_KEYWORD, OneArgumentCommandStub::newBuilder);
        keywordParser.addCommand(TWO_ARGUMENTS_COMMAND_KEYWORD, TwoArgumentsCommandStub::newBuilder);
        keywordParser.addCommand(ONE_VAR_ARGUMENT_COMMAND_KEYWORD, OneVarArgumentCommandStub::newBuilder);
        keywordParser.addCommand(TWO_ARGUMENTS_ONE_VAR_ARGUMENT_COMMAND_KEYWORD,
            TwoArgumentsOneVarArgumentCommandStub::newBuilder);
        keywordParser.addCommand(OPTIONAL_TWO_ARGUMENTS_ONE_VAR_ARGUMENT_COMMAND_KEYWORD,
            OptionalTwoArgumentsOneVarArgumentCommandStub::newBuilder);
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
    void parseOneArgument_noArgument_failure() {
        String[] tests = { "", " " };
        for (String test : tests) {
            assertThrows(ParseException.class, () -> commandParser.parse(String.format("%s %s",
                ONE_ARGUMENT_COMMAND_KEYWORD, test)));
        }
    }

    @Test
    void parseOneArgument_quotes_success() {
        Map<String, String> tests = Map.of(
            "'", "'",
            "''", "",
            "'''", "'",
            "\"'\"'", "\"'\"'",
            "\"'\"'\"", "'\"'",
            "‘", "‘",
            "‘’", "",
            "‘‘’", "‘",
            "“‘”’", "“‘”’",
            "“‘“’”", "‘“’"
        );

        for (Map.Entry<String, String> entry : tests.entrySet()) {
            assertDoesNotThrow(() -> {
                String argument = commandParser.parse(String.format("%s %s",
                        ONE_ARGUMENT_COMMAND_KEYWORD, entry.getKey()))
                    .execute()
                    .toString();
                assertEquals(entry.getValue(), argument);
            });
        }
    }

    @Test
    void parseOneArgument_arguments_success() {
        Map<String, String> tests = Map.of(
            "a b c", "a",
            "'a a' 'b b' 'c c'", "a a"
        );

        for (Map.Entry<String, String> entry : tests.entrySet()) {
            assertDoesNotThrow(() -> {
                String argument = commandParser.parse(String.format("%s %s",
                        ONE_ARGUMENT_COMMAND_KEYWORD, entry.getKey()))
                    .execute()
                    .toString();
                assertEquals(entry.getValue(), argument);
            });
        }
    }

    @Test
    void parseTwoArguments_arguments_success() {
        Map<String, String> tests = Map.of(
            "a b c", "a,b",
            "'a a' 'b b' 'c c'", "a a,b b"
        );

        for (Map.Entry<String, String> entry : tests.entrySet()) {
            assertDoesNotThrow(() -> {
                String argument = commandParser.parse(String.format("%s %s",
                    TWO_ARGUMENTS_COMMAND_KEYWORD, entry.getKey()))
                    .execute()
                    .toString();
                assertEquals(entry.getValue(), argument);
            });
        }
    }

    @Test
    void parseOneVarArgument_arguments_success() {
        Map<String, String> tests = Map.of(
            "", "",
            "a b c", "a,b,c",
            "'a a' 'b b' 'c c'", "a a,b b,c c"
        );

        for (Map.Entry<String, String> entry : tests.entrySet()) {
            assertDoesNotThrow(() -> {
                String argument = commandParser.parse(String.format("%s %s",
                    ONE_VAR_ARGUMENT_COMMAND_KEYWORD, entry.getKey()))
                    .execute()
                    .toString();
                assertEquals(entry.getValue(), argument);
            });
        }
    }

    @Test
    void parseTwoArgumentsOneVarArgument_arguments_success() {
        Map<String, String> tests = Map.of(
            "a b", "[a,b][]",
            "a b c d e", "[a,b][c,d,e]",
            "'a a' 'b b' 'c c' 'd d' 'e e'", "[a a,b b][c c,d d,e e]"
        );

        for (Map.Entry<String, String> entry : tests.entrySet()) {
            assertDoesNotThrow(() -> {
                String argument = commandParser.parse(String.format("%s %s",
                    TWO_ARGUMENTS_ONE_VAR_ARGUMENT_COMMAND_KEYWORD, entry.getKey()))
                    .execute()
                    .toString();
                assertEquals(entry.getValue(), argument);
            });
        }
    }

    @Test
    void parseOptionalTwoArgumentsOneVarArgument_arguments_success() {
        Map<String, String> tests = Map.of(
            "a b", "[null,null][]",
            "-o a b", "[a,b][]",
            "-o a b c d e", "[a,b][c,d,e]",
            "'-o' 'a a' 'b b' 'c c' 'd d' 'e e'", "[a a,b b][c c,d d,e e]"
        );

        for (Map.Entry<String, String> entry : tests.entrySet()) {
            assertDoesNotThrow(() -> {
                String argument = commandParser.parse(String.format("%s %s",
                        OPTIONAL_TWO_ARGUMENTS_ONE_VAR_ARGUMENT_COMMAND_KEYWORD, entry.getKey()))
                    .execute()
                    .toString();
                assertEquals(entry.getValue(), argument);
            });
        }
    }
}
