package budgetbuddy.logic.parser.commandparsers.rulecommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PREDICATE;
import static java.util.Objects.requireNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.rulecommands.RuleEditCommand;
import budgetbuddy.logic.commands.rulecommands.RuleEditCommand.RuleEditDescriptor;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.rule.Rule;

/**
 * Parses input arguments and creates a new RuleEditCommand object.
 */
public class RuleEditCommandParser implements CommandParser<RuleEditCommand> {
    @Override
    public String name() {
        return RuleEditCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RuleEditCommand
     * and returns a RuleEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RuleEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_PREDICATE, PREFIX_ACTION);

        Index ruleIndex;
        try {
            ruleIndex = CommandParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RuleEditCommand.MESSAGE_USAGE), e);
        }

        RuleEditDescriptor ruleEditDescriptor = new RuleEditDescriptor();
        if (argMultiMap.getValue(PREFIX_PREDICATE).isPresent()) {
            ruleEditDescriptor.setPredicate(
                    CommandParserUtil.parsePredicate(argMultiMap.getValue(PREFIX_PREDICATE).get(),
                            Rule.TYPE_EXPRESSION));
        }
        if (argMultiMap.getValue(PREFIX_ACTION).isPresent()) {
            ruleEditDescriptor.setAction(
                    CommandParserUtil.parseAction(argMultiMap.getValue(PREFIX_ACTION).get(),
                            Rule.TYPE_EXPRESSION));
        }

        if (!ruleEditDescriptor.isAnyFieldEdited()) {
            throw new ParseException(RuleEditCommand.MESSAGE_UNEDITED);
        }

        return new RuleEditCommand(ruleIndex, ruleEditDescriptor);
    }
}
