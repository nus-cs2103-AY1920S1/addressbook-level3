package seedu.address.logic.parser.commandparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREDICATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.RuleAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.CommandParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.RuleAction;
import seedu.address.model.rule.RulePredicate;

/**
 * Parses input arguments and creates a new RuleAddCommand object
 */
public class RuleAddCommandParser implements CommandParser<RuleAddCommand> {

    @Override
    public String name() {
        return RuleAddCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RuleAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PREDICATE, PREFIX_ACTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_PREDICATE, PREFIX_ACTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RuleAddCommand.MESSAGE_USAGE));
        }

        RulePredicate predicate = CommandParserUtil.parsePredicate(argMultimap.getValue(PREFIX_PREDICATE).get(),
                CommandParserUtil.TYPE_EXPRESSION);
        RuleAction action = CommandParserUtil.parseAction(argMultimap.getValue(PREFIX_PREDICATE).get());

        Rule rule = new Rule(predicate, action);

        return new RuleAddCommand(rule);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
