package seedu.address.logic.parser.itinerary.eventview.edit;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INVENTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_INVENTORY;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itinerary.events.edit.EditEventFieldCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.itinerary.ItineraryParserUtil;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.InventoryList;
import seedu.address.model.inventory.exceptions.DuplicateInventoryException;

/**
 * Placeholder javadoc.
 */
public class EditEventFieldParser implements Parser<EditEventFieldCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventFieldCommand
     * and returns an EditEventFieldCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventFieldCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME,
                        PREFIX_DATE_START,
                        PREFIX_DATE_END,
                        PREFIX_BUDGET,
                        PREFIX_LOCATION,
                        PREFIX_INDEX,
                        PREFIX_BOOKING,
                        PREFIX_ADD_INVENTORY,
                        PREFIX_DELETE_INVENTORY,
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
            throw new UnsupportedOperationException("Parsing edit event by index not yet supported.");

        }
        //edit by prefixes
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor =
                new EditEventFieldCommand.EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editEventDescriptor.setName(ItineraryParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE_START).isPresent()) {
            editEventDescriptor.setStartTime(
                    ParserDateUtil.getTimeFromString(argMultimap.getValue(PREFIX_DATE_START).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE_END).isPresent()) {
            editEventDescriptor.setEndTime(
                    ParserDateUtil.getTimeFromString(argMultimap.getValue(PREFIX_DATE_END).get()));
        }
        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            editEventDescriptor.setBudget(ItineraryParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editEventDescriptor.setDestination(
                    ItineraryParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editEventDescriptor.setDescription(
                    ItineraryParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        /*
        if(argMultimap.getValue(PREFIX_BOOKING).isPresent()) {
            editEventDescriptor.setBooking(ItineraryParserUtil.parseBooking());
        }*/

        if (argMultimap.getValue(PREFIX_ADD_INVENTORY).isPresent()) {

            InventoryList inventoryList = new InventoryList();

            for (String name : argMultimap.getAllValues(PREFIX_ADD_INVENTORY)) {
                Inventory inventory = ItineraryParserUtil.parseAddInventory(name);
                try {
                    inventoryList.add(inventory);
                } catch (DuplicateInventoryException e) {

                }
            }

            editEventDescriptor.setInventoryList(inventoryList);

        }

        if (argMultimap.getValue(PREFIX_DELETE_INVENTORY).isPresent()) {

            List<Index> inventoriesToDelete = new ArrayList<>();

            for (String s : argMultimap.getAllValues(PREFIX_DELETE_INVENTORY)) {
                Index i = ParserUtil.parseIndex(s);
                inventoriesToDelete.add(i);
            }

            editEventDescriptor.setInventoriesToDelete(inventoriesToDelete);

        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventFieldCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventFieldCommand(editEventDescriptor);
    }

}
