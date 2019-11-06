package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.net.MalformedURLException;
import java.net.URL;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AddTimetableCommandParser implements Parser<AddTimetableCommand> {

    // Cannot use argMultimap for this because argument is a classpath, and the current tokenizer would tokenize the filepath as well
    @Override
    public AddTimetableCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, AddTimetableCommand.PREFIX_FILEPATH, AddTimetableCommand.PREFIX_NUSMODS_URL);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimetableCommand.MESSAGE_USAGE), pe);
        }

        String absoluteFilepath;
        URL url;
        if (argMultimap.getValue(AddTimetableCommand.PREFIX_FILEPATH).isPresent()) {
            absoluteFilepath = argMultimap.getValue(AddTimetableCommand.PREFIX_FILEPATH).get();
            return new AddTimetableCommand(index, absoluteFilepath);
        } else if (argMultimap.getValue(AddTimetableCommand.PREFIX_NUSMODS_URL).isPresent()) {
            try {
                if (!isValidUrl(argMultimap.getValue(AddTimetableCommand.PREFIX_NUSMODS_URL).get())) {
                    throw new ParseException(AddTimetableCommand.MESSAGE_INVALID_URL);
                }
                url = new URL(argMultimap.getValue(AddTimetableCommand.PREFIX_NUSMODS_URL).get());
                return new AddTimetableCommand(index, url);
            } catch (MalformedURLException e) {
                throw new ParseException(AddTimetableCommand.MESSAGE_INVALID_URL);
            }
        } else {
            throw new ParseException(AddTimetableCommand.MESSAGE_NO_TIMETABLE_SOURCE);
        }
    }

    public static boolean isValidUrl(String url) {
        return url.matches(AddTimetableCommand.VALIDATION_REGEX);
    }
}
