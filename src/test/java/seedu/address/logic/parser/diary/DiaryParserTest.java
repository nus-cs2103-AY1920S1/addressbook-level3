package seedu.address.logic.parser.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Unit test negative test case of {@link DiaryParser}.
 */
class DiaryParserTest {

    private static final String INVALID_DIARY_COMMAND = "ABCDEFG";

    @Test
    void parse_invalidDiaryCommand_throwsParseException() {
        DiaryParser diaryParser = new DiaryParser();
        try {
            diaryParser.parse(INVALID_DIARY_COMMAND, "");
        } catch (ParseException ex) {
            assertEquals(
                    String.format(MESSAGE_INVALID_COMMAND_TYPE, DiaryParser.MESSAGE_COMMAND_TYPES),
                    ex.getMessage());
        }
    }

}
