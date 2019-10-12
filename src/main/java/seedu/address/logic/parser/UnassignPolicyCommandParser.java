package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignPolicyCommand;
import seedu.address.logic.commands.UnassignPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_INDEX;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_INDEX, PREFIX_PERSON_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_POLICY_INDEX, PREFIX_PERSON_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignPolicyCommand.MESSAGE_USAGE));
        }

        Index personIndex = null;
        Index policyIndex = null;

        try {
            policyIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_POLICY_INDEX).get());
            personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignPolicyCommand.MESSAGE_USAGE), pe);
        }

        return new UnassignPolicyCommand(policyIndex, personIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
