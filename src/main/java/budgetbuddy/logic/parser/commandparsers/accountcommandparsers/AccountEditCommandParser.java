package budgetbuddy.logic.parser.commandparsers.accountcommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.accountcommands.AccountEditCommand;
import budgetbuddy.logic.commands.accountcommands.AccountEditCommand.AccountEditDescriptor;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AccountEditCommand object.
 */
public class AccountEditCommandParser implements CommandParser<AccountEditCommand> {
    @Override
    public String name() {
        return AccountEditCommand.COMMAND_WORD;
    }

    @Override
    public AccountEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION);

        checkArgumentMultimap(argMultiMap);

        if (argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountEditCommand.MESSAGE_USAGE));
        }

        Index accountIndex = CommandParserUtil.parseIndex(argMultiMap.getPreamble());

        AccountEditDescriptor accountEditDescriptor = new AccountEditDescriptor();
        if (argMultiMap.getValue(PREFIX_NAME).isPresent()) {
            accountEditDescriptor.setName(
                    CommandParserUtil.parseName(argMultiMap.getValue(PREFIX_NAME).get()));
        }

        if (argMultiMap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            accountEditDescriptor.setDescription(
                    CommandParserUtil.parseDescription(argMultiMap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!accountEditDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AccountEditCommand.MESSAGE_UNEDITED);
        }

        return new AccountEditCommand(accountIndex, accountEditDescriptor);
    }

    /**
     * Checks if the given {@code ArgumentMultimap} is valid for parsing into a {@code AccountEditCommand}.
     * @throws ParseException If the {@code argMultimap} is invalid.
     */
    private void checkArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValueCount(PREFIX_NAME) > 1
                || argMultimap.getValueCount(PREFIX_DESCRIPTION) > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountEditCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getPreamble().isBlank() || argMultimap.getPreamble().split("\\s+").length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccountEditCommand.MESSAGE_USAGE));
        }
    }
}

