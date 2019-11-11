package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnassignPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyName;

/**
 * Parses input arguments and creates a new UnassignPolicyCommand object
 */
public class UnassignPolicyCommandParser implements Parser<UnassignPolicyCommand> {

    /**
     *
     * Parses the given {@code String} of arguments in the context of the UnassignPolicyCommand
     * and returns an UnassignPolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnassignPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY);

        if (!arePrefixesPresent(argMultimap, PREFIX_POLICY)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignPolicyCommand.MESSAGE_USAGE));
        }

        Index personIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignPolicyCommand.MESSAGE_USAGE), pe);
        }

        PolicyName policyName = ParserUtil.parsePolicyName(argMultimap.getValue(PREFIX_POLICY).get());

        return new UnassignPolicyCommand(personIndex, policyName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
