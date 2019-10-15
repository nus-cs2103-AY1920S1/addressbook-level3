package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.commands.CommandTestUtil.PARENT_FOLDER_DESC_CS2103T;
import static seedu.mark.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CONTACTS;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CS2103T;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.AddFolderCommand;
import seedu.mark.model.bookmark.Folder;

public class AddFolderCommandParserTest {
    private AddFolderCommandParser parser = new AddFolderCommandParser();
    @Test
    public void parse_withParent_success() {
        AddFolderCommand expectedCommand =
                new AddFolderCommand(new Folder(VALID_FOLDER_CONTACTS), new Folder(VALID_FOLDER_CS2103T));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_FOLDER_CONTACTS + PARENT_FOLDER_DESC_CS2103T,
                expectedCommand);
    }

    @Test
    public void parse_withoutParent_success() {
        AddFolderCommand expectedCommand =
                new AddFolderCommand(new Folder(VALID_FOLDER_CONTACTS), null);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_FOLDER_CONTACTS, expectedCommand);
    }

    @Test
    public void parse_empty_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFolderCommand.MESSAGE_USAGE));
    }
}
