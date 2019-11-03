package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.MarkParticipationCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new MarkParticipationCommandParser object
 */
public class MarkParticipationCommandParser implements Parser<MarkParticipationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkParticipationCommand
     * and returns an MarkParticipationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkParticipationCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Prefix[] emptyPrefix = new Prefix[0];
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, emptyPrefix);

        ArrayList<Index> indexes = new ArrayList<>();

        try {
            String allIndexes = argMultimap.getPreamble();
            String[] splitIndex = allIndexes.split(",");
            for (String index : splitIndex) {
                Index parsedIndex = ParserUtil.parseIndex(index);
                indexes.add(parsedIndex);
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkParticipationCommand.MESSAGE_USAGE), pe);
        }

        return new MarkParticipationCommand(indexes);
    }

}
