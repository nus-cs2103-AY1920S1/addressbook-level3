package budgetbuddy.logic.parser.commandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PREDICATE;

import java.util.stream.Stream;

import budgetbuddy.logic.commands.RuleAddCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.Prefix;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.RulePredicate;

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
