package seedu.address.logic.parser.itinerary.dayview.edit;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itinerary.days.edit.EditDayFieldCommand;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.itinerary.ItineraryParserUtil;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class EditDayFieldParser implements Parser<EditDayFieldCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDayFieldCommand
     * and returns an EditDayFieldCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDayFieldCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME,
                        PREFIX_DATE_START,
                        PREFIX_DATE_END,
                        PREFIX_BUDGET,
                        PREFIX_LOCATION,
                        PREFIX_INDEX,
                        PREFIX_DESCRIPTION);

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
            throw new UnsupportedOperationException("Parsing edit day by index not yet supported.");

        }
        //edit by prefixes
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor =
                new EditDayFieldCommand.EditDayDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDayDescriptor.setName(ItineraryParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE_START).isPresent()) {
            editDayDescriptor.setStartDate(
                    ParserDateUtil.getDateFromString(argMultimap.getValue(PREFIX_DATE_START).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE_END).isPresent()) {
            editDayDescriptor.setEndDate(
                    ParserDateUtil.getDateFromString(argMultimap.getValue(PREFIX_DATE_END).get()));
        }
        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            editDayDescriptor.setBudget(ItineraryParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editDayDescriptor.setDestination(ItineraryParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()){
            editDayDescriptor.setDescription(ItineraryParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!editDayDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDayFieldCommand.MESSAGE_NOT_EDITED);
        }

        return new EditDayFieldCommand(editDayDescriptor);
    }

}
