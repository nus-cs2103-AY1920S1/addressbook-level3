package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CONTACTS;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.DeleteFolderCommand;
import seedu.mark.model.bookmark.Folder;

public class DeleteFolderCommandParserTest {
    private DeleteFolderCommandParser parser = new DeleteFolderCommandParser();
    @Test
    public void parse_withFolder_success() {
        DeleteFolderCommand expectedCommand =
                new DeleteFolderCommand(new Folder(VALID_FOLDER_CONTACTS));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_FOLDER_CONTACTS, expectedCommand);
    }

    @Test
    public void parse_invalidFolder_failure() {
        // whitespace only preamble
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " folder with %",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFolderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_empty_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFolderCommand.MESSAGE_USAGE));
    }
}
