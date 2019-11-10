package dukecooks.logic.parser.health;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.ListCommand;
import dukecooks.logic.commands.health.ListHealthByTypeCommand;
import dukecooks.logic.commands.health.ListHealthCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Type;

public class ListRecordCommandParserTest {

    private static final String REMARK_EMPTY = " " + CliSyntax.PREFIX_REMARK;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListHealthCommand.MESSAGE_USAGE);

    private ListRecordCommandParser parser = new ListRecordCommandParser();

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "zxc/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_TYPE_DESC,
                Type.messageConstraints()); // invalid type

        CommandParserTestUtil.assertParseFailure(parser, CliSyntax.PREFIX_TYPE + " ",
                MESSAGE_INVALID_FORMAT); // empty type arg
    }


    @Test
    public void parse_typeSpecified_success() {
        // valid calories
        Predicate<Record> expectedPredicate = x -> x.getType()
                .equals(Type.valueOf(CommandTestUtil.VALID_TYPE_CALORIES));
        ListCommand expectedCommand = new ListHealthByTypeCommand(expectedPredicate);

        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.TYPE_DESC_CALORIES, expectedCommand);

        // valid glucose
        expectedPredicate = x -> x.getType()
                .equals(Type.valueOf(CommandTestUtil.VALID_TYPE_GLUCOSE));
        expectedCommand = new ListHealthByTypeCommand(expectedPredicate);

        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.TYPE_DESC_GLUCOSE, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        // same repeated fields
        Predicate<Record> expectedPredicate = x -> x.getType()
                .equals(Type.valueOf(CommandTestUtil.VALID_TYPE_CALORIES));
        ListCommand expectedCommand = new ListHealthByTypeCommand(expectedPredicate);
        String userInput = CommandTestUtil.TYPE_DESC_CALORIES + CommandTestUtil.TYPE_DESC_CALORIES;

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // different repeated fields
        expectedPredicate = x -> x.getType()
                .equals(Type.valueOf(CommandTestUtil.VALID_TYPE_CALORIES));
        expectedCommand = new ListHealthByTypeCommand(expectedPredicate);
        userInput = CommandTestUtil.TYPE_DESC_GLUCOSE + CommandTestUtil.TYPE_DESC_CALORIES;

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
