package dukecooks.logic.parser.diary;

import static dukecooks.testutil.diary.TypicalDiaries.AMY_DIARY;
import static dukecooks.testutil.diary.TypicalDiaries.BOB_DIARY;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.diary.AddDiaryCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.testutil.diary.DiaryBuilder;

public class AddDiaryCommandParserTest {
    private AddDiaryCommandParser parser = new AddDiaryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Diary expectedDiary = new DiaryBuilder(BOB_DIARY).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.NAME_DESC_BOB, new AddDiaryCommand(expectedDiary));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY
                + CommandTestUtil.NAME_DESC_BOB, new AddDiaryCommand(expectedDiary));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Diary expectedDiary = new DiaryBuilder(AMY_DIARY).build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY,
                new AddDiaryCommand(expectedDiary));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddDiaryCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC,
                DiaryName.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC,
                DiaryName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                + CommandTestUtil.NAME_DESC_BOB, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddDiaryCommand.MESSAGE_USAGE));
    }
}
