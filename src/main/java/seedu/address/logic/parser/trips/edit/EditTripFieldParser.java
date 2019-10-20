package seedu.address.logic.parser.trips.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import java.io.File;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.diary.fileutil.ImageChooser;
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
                    ParserDateUtil.getDateFromString(argMultimap.getValue(PREFIX_DATE_START).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE_END).isPresent()) {
            editTripDescriptor.setEndDate(
                    ParserDateUtil.getDateFromString(argMultimap.getValue(PREFIX_DATE_END).get()));
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
                    TripParserUtil.parseFilePath(argMultimap.getValue(PREFIX_DATA_FILE_PATH).get(), imageFile));
        }
        if (!editTripDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTripFieldCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTripFieldCommand(editTripDescriptor);
    }
}
