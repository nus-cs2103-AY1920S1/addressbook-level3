package thrift.logic.parser;

import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE;
import static thrift.logic.parser.CommandParserTestUtil.assertParseFailure;
import static thrift.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.ConvertCommand;
import thrift.model.transaction.Value;

class ConvertCommandParserTest {

    private ConvertCommandParser parser = new ConvertCommandParser();

    @Test
    void parse_withRecommendedInput() {
        String input = "convert v/10 c/SGD c/USD";

        ConvertCommand expectedCommand = new ConvertCommand(10.00, (List<String>) List.of("SGD", "USD"));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    void parse_withOneCurrency() {
        String input = "convert v/10 c/MYR";

        ConvertCommand expectedCommand = new ConvertCommand(10.00,
                (List<String>) List.of("SGD", "MYR"));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    void parse_withManyCurrencies() {
        String input = "convert v/10 c/SGD c/USD c/EUR c/MYR";

        ConvertCommand expectedCommand = new ConvertCommand(10.00,
                (List<String>) List.of("SGD", "USD", "EUR", "MYR"));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    void parse_withNoValue_andRecommendedInput() {
        String input = "convert c/SGD c/USD";

        ConvertCommand expectedCommand = new ConvertCommand(1.00,
                (List<String>) List.of("SGD", "USD"));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    void parse_withNoValue_andOneCurrency() {
        String input = "convert c/USD";

        ConvertCommand expectedCommand = new ConvertCommand(1.00,
                (List<String>) List.of("SGD", "USD"));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    void parse_withNoValue_andManyCurrencies() {
        String input = "convert c/SGD c/USD c/EUR c/MYR";

        ConvertCommand expectedCommand = new ConvertCommand(1.00,
                (List<String>) List.of("SGD", "USD", "EUR", "MYR"));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    void parse_withBadValue() {
        String input = "convert v/FAKER c/SGD c/USD";

        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE,
                ConvertCommand.MESSAGE_USAGE, Value.VALUE_CONSTRAINTS));
    }

    @Test
    void parse_withNonExistentCurrency() {
        String input = "convert v/10 c/LOL c/LMAO";

        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE,
                ConvertCommand.MESSAGE_USAGE, ParserUtil.MESSAGE_INVALID_CURRENCY));
    }

    @Test
    void parse_wrongSyntax() {
        String input = "convert";

        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
    }

}
