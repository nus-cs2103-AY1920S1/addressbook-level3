package seedu.system.logic.parser.outofsession;

import static java.util.Objects.requireNonNull;
import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.system.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.system.commons.core.index.Index;
import seedu.system.logic.commands.outofsession.EditPersonCommand;
import seedu.system.logic.commands.outofsession.EditPersonCommand.EditPersonDescriptor;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditPersonCommand object.
 */
public class EditPersonCommandParser implements Parser<EditPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPersonCommand
     * and returns an EditPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPersonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DOB, PREFIX_GENDER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE),
                pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DOB).isPresent()) {
            editPersonDescriptor.setDateOfBirth(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DOB).get()));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editPersonDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPersonCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPersonCommand(index, editPersonDescriptor);
    }

}
