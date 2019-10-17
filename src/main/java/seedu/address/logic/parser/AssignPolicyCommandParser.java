package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyName;

/**
 * Parses input arguments and creates a new AssignPolicyCommand object
 */
public class AssignPolicyCommandParser implements Parser<AssignPolicyCommand> {

    /**
     *
     * Parses the given {@code String} of arguments in the context of the AssignPolicyCommand
     * and returns an AssignPolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY);

        if (!arePrefixesPresent(argMultimap, PREFIX_POLICY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignPolicyCommand.MESSAGE_USAGE));
        }

        Index personIndex;
        PolicyName policyName;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            policyName = ParserUtil.parsePolicyName(argMultimap.getValue(PREFIX_POLICY).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignPolicyCommand.MESSAGE_USAGE), pe);
        }

        return new AssignPolicyCommand(personIndex, policyName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
