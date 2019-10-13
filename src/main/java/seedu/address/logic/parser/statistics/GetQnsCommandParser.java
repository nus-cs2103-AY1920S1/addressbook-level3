package seedu.address.logic.parser.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.List;

import seedu.address.logic.commands.statistics.GetQnsCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class GetQnsCommandParser implements Parser<GetQnsCommand> {

    public GetQnsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        List subjects = argMultimap.getAllValues(PREFIX_SUBJECT);

        if (subjects.isEmpty() || !(args.contains("-c") || args.contains("-i"))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GetQnsCommand.MESSAGE_USAGE));
        }

        boolean getCorrectQns = args.contains("-c");
        boolean getAnswers = args.contains("-a");

        return new GetQnsCommand(subjects, getCorrectQns, getAnswers);
    }

}
