package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetTimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.net.MalformedURLException;
import java.net.URL;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class SetTimetableCommandParser implements Parser<SetTimetableCommand> {

    // Cannot use argMultimap for this because argument is a classpath, and the current tokenizer would tokenize the filepath as well
    @Override
    public SetTimetableCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, SetTimetableCommand.PREFIX_FILEPATH, SetTimetableCommand.PREFIX_NUSMODS_URL);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetTimetableCommand.MESSAGE_USAGE), pe);
        }

        String absoluteFilepath;
        URL url;
        if (argMultimap.getValue(SetTimetableCommand.PREFIX_FILEPATH).isPresent()) {
            absoluteFilepath = argMultimap.getValue(SetTimetableCommand.PREFIX_FILEPATH).get();
            return new SetTimetableCommand(index, absoluteFilepath);
        } else if (argMultimap.getValue(SetTimetableCommand.PREFIX_NUSMODS_URL).isPresent()) {
            try {
                if (!isValidUrl(argMultimap.getValue(SetTimetableCommand.PREFIX_NUSMODS_URL).get())) {
                    throw new ParseException(SetTimetableCommand.MESSAGE_INVALID_URL);
                }
                url = new URL(argMultimap.getValue(SetTimetableCommand.PREFIX_NUSMODS_URL).get());
                return new SetTimetableCommand(index, url);
            } catch (MalformedURLException e) {
                throw new ParseException(SetTimetableCommand.MESSAGE_INVALID_URL);
            }
        } else {
            throw new ParseException(SetTimetableCommand.MESSAGE_NO_TIMETABLE_SOURCE);
        }
    }

    public static boolean isValidUrl(String url) {
        return url.matches(SetTimetableCommand.VALIDATION_REGEX);
    }
}
