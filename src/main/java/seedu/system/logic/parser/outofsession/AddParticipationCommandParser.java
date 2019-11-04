package seedu.system.logic.parser.outofsession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.commons.core.Messages.MESSAGE_INVALID_NEGATIVE_ATTEMPT_WEIGHT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_BENCH;
import static seedu.system.logic.parser.CliSyntax.PREFIX_COMP;
import static seedu.system.logic.parser.CliSyntax.PREFIX_DEADLIFT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.system.logic.parser.CliSyntax.PREFIX_SQUAT;

import java.util.ArrayList;
import java.util.List;

import seedu.system.logic.commands.outofsession.AddParticipationCommand;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.Prefix;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.person.Name;

/**
 * Parses input arguments and returns an AddParticipationCommand.
 */
public class AddParticipationCommandParser implements Parser<AddParticipationCommand> {
    private List<Integer> attemptWeights = new ArrayList<>(9);

    /**
     * Parses {@code args} into an AddParticipationCommand and returns it.
     *
     * @throws ParseException if {@code args} does not conform the expected format
     */
    @Override
    public AddParticipationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COMP, PREFIX_SQUAT, PREFIX_BENCH, PREFIX_DEADLIFT);

        if (!ParserUtil.arePrefixesPresent(
            argMultimap, PREFIX_NAME, PREFIX_COMP, PREFIX_SQUAT, PREFIX_BENCH, PREFIX_DEADLIFT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddParticipationCommand.MESSAGE_USAGE));
        }

        Name athleteName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Name compName = ParserUtil.parseName((argMultimap.getValue(PREFIX_COMP).get()));
        stringToAttemptInt(PREFIX_SQUAT, argMultimap);
        stringToAttemptInt(PREFIX_BENCH, argMultimap);
        stringToAttemptInt(PREFIX_DEADLIFT, argMultimap);
        return new AddParticipationCommand(athleteName, compName, attemptWeights);
    }

    /**
     * Adds the attempt weights in integer from its string form.
     *
     * @param prefix prefix to get the value from
     * @param argumentMultimap mapper of the arguments
     */
    private void stringToAttemptInt(Prefix prefix, ArgumentMultimap argumentMultimap) throws ParseException {
        String stringThreeAttempts = argumentMultimap.getValue(prefix).get();
        String[] threeAttemptsArray = stringThreeAttempts.split("/");
        for (String s : threeAttemptsArray) {
            Integer weight = Integer.parseInt(s);
            if (weight.intValue() < 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_NEGATIVE_ATTEMPT_WEIGHT,
                        AddParticipationCommand.MESSAGE_USAGE));
            }
            attemptWeights.add(weight);
        }
    }
}
