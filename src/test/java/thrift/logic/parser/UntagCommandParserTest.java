package thrift.logic.parser;

import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE;
import static thrift.logic.parser.CommandParserTestUtil.assertParseFailure;
import static thrift.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.UntagCommand;
import thrift.model.tag.Tag;
import thrift.testutil.TagSetBuilder;
import thrift.testutil.TypicalIndexes;


class UntagCommandParserTest {

    private UntagCommandParser parser = new UntagCommandParser();

    @Test
    void parse_withTags() {
        String input = "untag i/1 t/Debt t/Horror";
        String input2 = "untag i/1 t/Horror t/Debt";

        UntagCommand expectedCommand = new UntagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                new TagSetBuilder("Debt", "Horror").build());

        assertParseSuccess(parser, input, expectedCommand);
        assertParseSuccess(parser, input2, expectedCommand);
    }

    @Test
    void parse_withInvalidTags() {
        String input = "untag i/1 t/!@#$%^&*()";
        String input2 = "untag i/1 t/REALTAG t/WRONGTAG!!!";

        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE,
                        UntagCommand.MESSAGE_USAGE, Tag.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, input2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE,
                        UntagCommand.MESSAGE_USAGE, Tag.MESSAGE_CONSTRAINTS));
    }

    @Test
    void parse_withBlankTags() {
        String input = "untag i/1 t/";

        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE,
                        UntagCommand.MESSAGE_USAGE, Tag.MESSAGE_CONSTRAINTS));
    }

    @Test
    void parse_wrongSyntax() {
        String input = "untag";

        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_noTags() {
        String input = "untag i/1";

        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE,
                UntagCommand.MESSAGE_USAGE, UntagCommand.MESSAGE_NOT_UNTAGGED));
    }

}
