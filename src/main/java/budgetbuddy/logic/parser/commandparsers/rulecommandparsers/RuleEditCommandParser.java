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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PREDICATE, PREFIX_ACTION);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RuleEditCommand.MESSAGE_USAGE));
        }
        Index ruleIndex = CommandParserUtil.parseIndex(argMultimap.getPreamble());

        RuleEditDescriptor ruleEditDescriptor = new RuleEditDescriptor();
        if (argMultimap.getValue(PREFIX_PREDICATE).isPresent()) {
            ruleEditDescriptor.setPredicate(
                    CommandParserUtil.parsePredicate(argMultimap.getValue(PREFIX_PREDICATE).get()));
        }
        if (argMultimap.getValue(PREFIX_ACTION).isPresent()) {
            ruleEditDescriptor.setAction(
                    CommandParserUtil.parseAction(argMultimap.getValue(PREFIX_ACTION).get()));
        }

        if (!ruleEditDescriptor.isAnyFieldEdited()) {
            throw new ParseException(RuleEditCommand.MESSAGE_UNEDITED);
        }

        return new RuleEditCommand(ruleIndex, ruleEditDescriptor);
    }
}
