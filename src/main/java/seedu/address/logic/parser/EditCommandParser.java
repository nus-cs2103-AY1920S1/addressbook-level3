package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.EditBorrowerCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditBorrowerCommand object
 */
public class EditCommandParser implements Parser<EditBorrowerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBorrowerCommand
     * and returns an EditBorrowerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBorrowerCommand parse(String args) throws ParseException, CommandException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        EditBorrowerCommand.EditBorrowerDescriptor editBorrowerDescriptor =
                new EditBorrowerCommand.EditBorrowerDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editBorrowerDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editBorrowerDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)
                    .get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editBorrowerDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        if (!editBorrowerDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBorrowerCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBorrowerCommand(editBorrowerDescriptor);
    }
}
