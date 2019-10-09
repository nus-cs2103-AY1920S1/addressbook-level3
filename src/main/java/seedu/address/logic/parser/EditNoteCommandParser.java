package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditNoteCommand;
import seedu.address.logic.commands.EditNoteCommand.EditNoteDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditNoteCommand object.
 */
public class EditNoteCommandParser implements Parser<EditNoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditNoteCommand
     * and returns an EditNoteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_CONTENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNoteCommand.MESSAGE_USAGE), pe);
        }

        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptor();
        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editNoteDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTENT).isPresent()) {
            editNoteDescriptor.setContent(ParserUtil.parseContent(argMultimap.getValue(PREFIX_CONTENT).get()));
        }

        if (!editNoteDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditNoteCommand.MESSAGE_NOT_EDITED);
        }

        return new EditNoteCommand(index, editNoteDescriptor);
    }
}
