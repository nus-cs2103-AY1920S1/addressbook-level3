package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DIARY;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_DIARY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditDiaryCommand;
import seedu.address.logic.commands.EditDiaryCommand.EditDiaryDescriptor;
import seedu.address.testutil.EditDiaryDescriptorBuilder;

public class EditDiaryCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDiaryCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_DIARY;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;

        EditDiaryCommand.EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditDiaryCommand expectedCommand = new EditDiaryCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_DIARY;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditDiaryCommand expectedCommand = new EditDiaryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
