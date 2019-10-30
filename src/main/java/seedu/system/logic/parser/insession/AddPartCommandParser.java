package seedu.system.logic.parser.insession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_BENCH;
import static seedu.system.logic.parser.CliSyntax.PREFIX_COMP;
import static seedu.system.logic.parser.CliSyntax.PREFIX_DEADLIFT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.system.logic.parser.CliSyntax.PREFIX_SQUAT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.system.logic.commands.insession.AddPartCommand;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.Prefix;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.person.Name;

/**
 * Parses input arguments and returns an AddPartCommand.
 */
public class AddPartCommandParser implements Parser<AddPartCommand> {
    private List<Integer> attemptWeights = new ArrayList<>(9);

    /**
     * Parses {@code args} into an AddPartCommand and returns it.
     *
     * @throws ParseException if {@code args} does not conform the expected format
     */
    @Override
    public AddPartCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COMP, PREFIX_SQUAT, PREFIX_BENCH, PREFIX_DEADLIFT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_COMP, PREFIX_SQUAT, PREFIX_BENCH, PREFIX_DEADLIFT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPartCommand.MESSAGE_USAGE));
        }

        Name athleteName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Name compName = ParserUtil.parseName((argMultimap.getValue(PREFIX_COMP).get()));
        stringToAttemptInt(PREFIX_SQUAT, argMultimap);
        stringToAttemptInt(PREFIX_BENCH, argMultimap);
        stringToAttemptInt(PREFIX_DEADLIFT, argMultimap);
        return new AddPartCommand(athleteName, compName, attemptWeights);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Adds the attempt weights in integer from its string form.
     *
     * @param prefix prefix to get the value from
     * @param argumentMultimap mapper of the arguments
     */
    private void stringToAttemptInt(Prefix prefix, ArgumentMultimap argumentMultimap) {
        String stringThreeAttempts = argumentMultimap.getValue(prefix).get();
        String[] threeAttemptsArray = stringThreeAttempts.split("/");
        for (String s : threeAttemptsArray) {
            Integer weight = Integer.parseInt(s);
            attemptWeights.add(weight);
        }
    }
}
