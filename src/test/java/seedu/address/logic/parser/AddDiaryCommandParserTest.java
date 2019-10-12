package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDiaries.AMY;
import static seedu.address.testutil.TypicalDiaries.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddDiaryCommand;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;
import seedu.address.testutil.DiaryBuilder;

public class AddDiaryCommandParserTest {
    private AddDiaryCommandParser parser = new AddDiaryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Diary expectedDiary = new DiaryBuilder(BOB).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BOB, new AddDiaryCommand(expectedDiary));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Diary expectedDiary = new DiaryBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY,
                new AddDiaryCommand(expectedDiary));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }
}
