package dukecooks.logic.parser.diary;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.diary.EditDiaryCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.diary.EditDiaryDescriptorBuilder;

public class EditDiaryCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditDiaryCommand.MESSAGE_USAGE);

    private EditDiaryCommandParser parser = new EditDiaryCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditDiaryCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC,
                DiaryName.MESSAGE_CONSTRAINTS); // invalid name

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC,
                DiaryName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_DIARY;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_AMY;

        EditDiaryCommand.EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_AMY).build();
        EditDiaryCommand expectedCommand = new EditDiaryCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_DIARY;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_AMY;
        EditDiaryCommand.EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_AMY).build();
        EditDiaryCommand expectedCommand = new EditDiaryCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
