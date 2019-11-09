package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.commands.CommandTestUtil.NEW_FOLDER_DESC_CS2103T;
import static seedu.mark.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CONTACTS;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CS2103T;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.EditFolderCommand;
import seedu.mark.model.bookmark.Folder;

public class EditFolderCommandParserTest {
    private EditFolderCommandParser parser = new EditFolderCommandParser();
    @Test
    public void parse_withNewFolder_success() {
        EditFolderCommand expectedCommand =
                new EditFolderCommand(new Folder(VALID_FOLDER_CONTACTS), new Folder(VALID_FOLDER_CS2103T));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_FOLDER_CONTACTS + " " + NEW_FOLDER_DESC_CS2103T,
                expectedCommand);
    }

    @Test
    public void parse_withoutNewFolder_failure() {
        // whitespace only preamble
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_FOLDER_CONTACTS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditFolderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_empty_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditFolderCommand.MESSAGE_USAGE));
    }
}
