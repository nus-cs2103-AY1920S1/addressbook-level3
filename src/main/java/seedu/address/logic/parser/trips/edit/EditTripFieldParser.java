package seedu.address.logic.parser.trips.edit;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_CHOOSER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.io.File;

import seedu.address.commons.util.ImageChooser;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.common.PhotoUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.trips.TripParserUtil;

/**
 * Parses the arguments to return a {@code EditTripFieldCommand}.
 */
public class EditTripFieldParser implements Parser<EditTripFieldCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTripFieldCommand
     * and returns an EditTripFieldCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTripFieldCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME,
                        PREFIX_DATE_START,
                        PREFIX_DATE_END,
                        PREFIX_BUDGET,
                        PREFIX_LOCATION,
                        PREFIX_INDEX,
                        PREFIX_DATA_FILE_PATH,
                        PREFIX_FILE_CHOOSER);

        //edit by prefixes
        EditTripFieldCommand.EditTripDescriptor editTripDescriptor =
                new EditTripFieldCommand.EditTripDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editTripDescriptor.setName(TripParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE_START).isPresent()) {
            editTripDescriptor.setStartDate(
                    ParserDateUtil.getDateFromString(argMultimap.getValue(PREFIX_DATE_START).get())
                            .withHour(0).withMinute(0));
        }
        if (argMultimap.getValue(PREFIX_DATE_END).isPresent()) {
            editTripDescriptor.setEndDate(
                    ParserDateUtil.getDateFromString(argMultimap.getValue(PREFIX_DATE_END)
                            .get()).withHour(23).withMinute(59));
        }
        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            editTripDescriptor.setBudget(TripParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editTripDescriptor.setDestination(
                    TripParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        File imageFile = null;
        if (argMultimap.getValue(PREFIX_FILE_CHOOSER).isPresent()) {
            ImageChooser imageChooser = new ImageChooser();
            imageFile = imageChooser.showDialog();
        }
        if (argMultimap.getValue(PREFIX_DATA_FILE_PATH).isPresent()) {
            editTripDescriptor.setPhoto(
                    PhotoUtil.parseFilePath(argMultimap.getValue(PREFIX_DATA_FILE_PATH).get(), imageFile));
        }
        if (!editTripDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTripFieldCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTripFieldCommand(editTripDescriptor);
    }
}
