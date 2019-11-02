package seedu.address.itinerary.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.logic.commands.EditCommand;
import seedu.address.itinerary.ui.TagDropdown;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DATE,
                        PREFIX_TIME, PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditEventDescriptor editEventDescriptor = new EditCommand.EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editEventDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editEventDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editEventDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }

        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editEventDescriptor.setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editEventDescriptor.setDescription(ParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            editEventDescriptor.setTag(ParserUtil
                    .parseTag(TagDropdown.getDropdownText()));
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editEventDescriptor);
    }
}
