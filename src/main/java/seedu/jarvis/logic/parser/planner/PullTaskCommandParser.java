package seedu.jarvis.logic.parser.planner;

import seedu.jarvis.logic.commands.planner.PullTaskCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.Prefix;
import seedu.jarvis.logic.parser.exceptions.ParseException;

import java.util.function.Predicate;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_DATE;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_FREQ;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_PRIORITY;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TAG;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_DES;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_TYPE;
import static seedu.jarvis.logic.parser.ParserUtil.MESSAGE_MULTIPLE_SAME_PREFIX;

/**
 * Parses input arguments and creates a new PullTaskCommand object
 */
//TODO tests
public class PullTaskCommandParser implements Parser<PullTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PullTaskCommand
     * and returns a PullTaskCommand object for execution.
     * @param userInput one or more keywords to match the tasks to
     * @return a PullTaskCommand
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public PullTaskCommand parse(String userInput) throws ParseException {
        String args = userInput.trim();

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PullTaskCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_TYPE, PREFIX_DATE,
                                                                        PREFIX_FREQ, PREFIX_PRIORITY, PREFIX_TAG);

        if (argumentMultimap.getPrefixSet().size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PullTaskCommand.MESSAGE_USAGE));
        }

        Prefix prefixToBePulled = (Prefix) argumentMultimap.getPrefixSet().toArray()[0];

        if (!isValidPrefix(prefixToBePulled)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PullTaskCommand.MESSAGE_USAGE));
        }

        if (argumentMultimap.getAllValues(prefixToBePulled).size() > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_SAME_PREFIX);
        }




    }

    /**
     * Checks if the prefix input by the user is valid
     * @param p {@code Prefix} to be checked
     * @return true if it is allowed, false if it is not
     */
    private boolean isValidPrefix(Prefix p) {
        return p == PREFIX_DATE || p == PREFIX_TASK_TYPE || p == PREFIX_FREQ
                || p == PREFIX_PRIORITY || p == PREFIX_TAG;
    }


}
