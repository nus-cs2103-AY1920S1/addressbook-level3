package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASSID_DESC_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASSID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTE_CONTENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_CONTENT_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.EditNotesCommand;
import seedu.address.model.classid.ClassId;
import seedu.address.model.note.ClassType;
import seedu.address.model.note.Content;

public class EditNotesCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNotesCommand.MESSAGE_USAGE);

    private EditNotesCommandParser parser = new EditNotesCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NOTE_CONTENT_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "3", EditNotesCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + CLASSID_DESC_NOTE_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TYPE_DESC_NOTE_AMY , MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "2 randome prefix", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "4 x/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_CLASSID_DESC, ClassId.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "2" + INVALID_TYPE_DESC, ClassType.MESSAGE_CONSTRAINTS); // invalid picture
        assertParseFailure(parser, "1" + INVALID_NOTE_CONTENT, Content.MESSAGE_CONSTRAINTS); // invalid NOTE_CONTENT
    }
}
