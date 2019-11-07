package cs.f10.t1.nursetraverse.logic.parser;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.logic.commands.ExportCommand;
import cs.f10.t1.nursetraverse.testutil.TypicalIndexes;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        ExportCommand expectedCommandNoIndex = new ExportCommand("filename", Optional.empty());
        // only filename, no indexes
        CommandParserTestUtil.assertParseSuccess(parser, " n/filename", expectedCommandNoIndex);

        ExportCommand expectedCommandWithIndex =
                new ExportCommand("filename", Optional.of(TypicalIndexes.getTypicalIndexSet()));
        // filename and indexes
        CommandParserTestUtil.assertParseSuccess(parser,
                " n/filename i/1 i/2 i/3", expectedCommandWithIndex);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // No filename
        CommandParserTestUtil.assertParseFailure(parser, "", String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

        // Invalid index
        CommandParserTestUtil.assertParseFailure(parser, " n/filename i/a",
                ParserUtil.MESSAGE_INVALID_FORMAT);

        // Invalid filename
        CommandParserTestUtil.assertParseFailure(parser, " n/`/\\:;.@# i/1",
                String.format(ParserUtil.MESSAGE_INVALID_FILENAME, "`/\\:;.@#"));
    }
}
