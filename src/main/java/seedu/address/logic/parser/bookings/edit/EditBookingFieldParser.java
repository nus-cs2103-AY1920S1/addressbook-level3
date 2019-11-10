package seedu.address.logic.parser.bookings.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.bookings.edit.EditBookingsFieldCommand;
import seedu.address.logic.commands.expense.edit.EditExpenseFieldCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.bookings.BookingParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Placeholder javadoc.
 */
public class EditBookingFieldParser implements Parser<EditBookingsFieldCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDayFieldCommand
     * and returns an EditDayFieldCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBookingsFieldCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME,
                        PREFIX_CONTACT);

        Optional<Index> index;

        try {
            index = Optional.ofNullable(ParserUtil.parseIndex(argMultimap.getPreamble()));
        } catch (ParseException pe) {
            index = Optional.empty();
            //throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            //        EditTripFieldCommand.MESSAGE_USAGE), pe);
        }

        if (!index.isEmpty()) {
            //edit by field specified by index only
            throw new UnsupportedOperationException("Parsing edit booking by index not yet supported.");

        }
        //edit by prefixes
        EditBookingsFieldCommand.EditBookingsDescriptor editBookingsDescriptor =
                new EditBookingsFieldCommand.EditBookingsDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editBookingsDescriptor.setName(BookingParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            editBookingsDescriptor.setContact(
                    BookingParserUtil.parseContact(argMultimap.getValue(PREFIX_CONTACT).get()));
        }
        if (!editBookingsDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditExpenseFieldCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBookingsFieldCommand(editBookingsDescriptor);
    }
}
