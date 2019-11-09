package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASSID_DESC_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CLASSID_DESC_NOTE_CALVIN;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASSID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_NOTE_CALVIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_CONTENT_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_NOTES_LAB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.AddNotesCommand;
import seedu.address.model.classid.ClassId;

public class AddNotesCommandParserTest {
    private AddNotesCommandParser parser = new AddNotesCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNotesCommand.MESSAGE_USAGE);

        // missing classid prefix
        assertParseFailure(parser, VALID_ATTENDANCE_AMY
                + TYPE_DESC_NOTE_AMY + CONTENT_DESC_NOTE_AMY, expectedMessage);

        // missing type prefix
        assertParseFailure(parser, CLASSID_DESC_NOTE_BOB
                        + VALID_TYPE_NOTES_LAB + CONTENT_DESC_NOTE_BOB, expectedMessage);

        // all content missing
        assertParseFailure(parser, CLASSID_DESC_NOTE_CALVIN
                        + TYPE_DESC_NOTE_CALVIN + VALID_NOTE_CONTENT_3, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid classid
        assertParseFailure(parser, INVALID_CLASSID_DESC
                + TYPE_DESC_NOTE_AMY + CONTENT_DESC_NOTE_AMY, ClassId.MESSAGE_CONSTRAINTS);
    }
}
