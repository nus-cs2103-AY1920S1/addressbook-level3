package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AddTimetableCommandParser implements Parser<AddTimetableCommand> {

    // Cannot use argMultimap for this because argument is a classpath, and the current tokenizer would tokenize the filepath as well
    @Override
    public AddTimetableCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, AddTimetableCommand.PREFIX_TIMETABLE);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimetableCommand.MESSAGE_USAGE), pe);
        }

        String absoluteFilepath;
        for (char c = 'a'; c <= 'z'; c++) {
            System.out.println(c + "" + "/ :" + argMultimap.getValue(new Prefix(c + "" + "/")).isPresent());
        }
        if (argMultimap.getValue(AddTimetableCommand.PREFIX_TIMETABLE).isPresent()) {
            absoluteFilepath = argMultimap.getValue(AddTimetableCommand.PREFIX_TIMETABLE).get();
        } else {
            throw new ParseException(AddTimetableCommand.MESSAGE_NO_TIMETABLE);
        }

        return new AddTimetableCommand(index, absoluteFilepath);
    }
}
