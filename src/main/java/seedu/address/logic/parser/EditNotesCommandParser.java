package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.note.EditNotesCommand;
import seedu.address.logic.commands.note.EditNotesCommand.EditNotesDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditNotesCommand object
 */
public class EditNotesCommandParser implements Parser<EditNotesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditNotesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASSID, PREFIX_TYPE, PREFIX_CONTENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNotesCommand.MESSAGE_USAGE), pe);
        }

        EditNotesDescriptor editNotesDescriptor = new EditNotesDescriptor();
        if (argMultimap.getValue(PREFIX_CLASSID).isPresent()) {
            editNotesDescriptor
                    .setModuleCode(ParserUtil.parseClassId(argMultimap.getValue(PREFIX_CLASSID).get()));
        }
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            editNotesDescriptor
                    .setClassType(ParserUtil.parseClassType(argMultimap.getValue(PREFIX_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTENT).isPresent()) {
            editNotesDescriptor.setContent(ParserUtil.parseContent(argMultimap.getValue(PREFIX_CONTENT).get()));
        }

        if (!editNotesDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditNotesCommand.MESSAGE_NOT_EDITED);
        }

        return new EditNotesCommand(index, editNotesDescriptor);
    }
}
