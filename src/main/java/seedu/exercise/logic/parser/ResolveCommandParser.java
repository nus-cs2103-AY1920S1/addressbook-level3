package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CONFLICT_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.ResolveCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.property.Name;

/**
 * Parses an input and returns a {@code ResolveCommand}
 */
public class ResolveCommandParser implements Parser<ResolveCommand> {

    @Override
    public ResolveCommand parse(String userInput) throws ParseException {
        ArgumentMultimap commandMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_INDEX, PREFIX_CONFLICT_INDEX);

        checkValidResolveCommand(commandMultimap);

        return parseResolveCommand(commandMultimap);
    }

    private void checkValidResolveCommand(ArgumentMultimap multimap) throws ParseException {
        if (!(multimap.arePrefixesPresent(PREFIX_NAME) && multimap.getPreamble().isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResolveCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses a valid resolve command.
     *
     * The list of indexes are empty if no indexes are provided by the user.
     */
    private ResolveCommand parseResolveCommand(ArgumentMultimap multimap) throws ParseException {
        Name scheduledOrConflicting = ParserUtil.parseName(multimap.getValue(PREFIX_NAME).get());
        List<Index> scheduledIndex = ParserUtil.parseIndexes(multimap.getAllValues(PREFIX_INDEX));
        List<Index> conflictingIndex = ParserUtil.parseIndexes(multimap.getAllValues(PREFIX_CONFLICT_INDEX));

        return new ResolveCommand(scheduledOrConflicting, scheduledIndex, conflictingIndex);
    }
}
