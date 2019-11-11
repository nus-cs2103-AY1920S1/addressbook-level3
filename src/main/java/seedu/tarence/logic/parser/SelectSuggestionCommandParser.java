package seedu.tarence.logic.parser;

import java.util.stream.Stream;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.SelectSuggestionCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class SelectSuggestionCommandParser implements Parser<SelectSuggestionCommand> {

    /**
     * Checks if the top command in the pending command stack is a {@code SelectSuggestionCommand}. If it is, pop it off
     * the stack, modify it to include the input index, and return it command for execution.
     * @throws ParseException if the user input is out of range, or when there is no pending
     * {@code SelectSuggestionCommand}
     */
    public SelectSuggestionCommand parse(String args) throws ParseException {
        Index index = Index.fromOneBased(Integer.parseInt(args));

        return new SelectSuggestionCommand(index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

