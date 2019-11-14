package seedu.address.logic.parser.itinerary.dayview.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_CHOOSER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;

import java.io.File;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ImageChooser;
import seedu.address.logic.commands.itinerary.days.edit.EditDayFieldCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.common.PhotoUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.itinerary.ItineraryParserUtil;

/**
 * Parser for {@link EditDayFieldCommand}.
 */
public class EditDayFieldParser implements Parser<EditDayFieldCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDayFieldCommand
     * and returns an EditDayFieldCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDayFieldCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_BUDGET,
                        PREFIX_LOCATION,
                        PREFIX_INDEX,
                        PREFIX_DESCRIPTION,
                        PREFIX_FILE_CHOOSER,
                        PREFIX_DATA_FILE_PATH);

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

        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            editDayDescriptor.setBudget(
                    ItineraryParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editDayDescriptor.setDestination(
                    ItineraryParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editDayDescriptor.setDescription(
                    ItineraryParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        File imageFile = null;
        if (argMultimap.getValue(PREFIX_FILE_CHOOSER).isPresent()) {
            ImageChooser imageChooser = new ImageChooser();
            imageFile = imageChooser.showDialog();
        }
        if (argMultimap.getValue(PREFIX_DATA_FILE_PATH).isPresent()) {
            editDayDescriptor.setPhoto(
                    PhotoUtil.parseFilePath(argMultimap.getValue(PREFIX_DATA_FILE_PATH).get(), imageFile));
        }

        if (!editDayDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDayFieldCommand.MESSAGE_NOT_EDITED);
        }

        return new EditDayFieldCommand(editDayDescriptor);
    }

}
