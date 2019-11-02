package budgetbuddy.logic.parser.commandparsers.transactioncommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DIRECTION;
import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.transactioncommands.TransactionEditCommand;
import budgetbuddy.logic.commands.transactioncommands.TransactionEditCommand.TransactionEditDescriptor;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Category;

/**
 * Parses input and creates a new TransactionAddCommand
 */
public class TransactionEditCommandParser implements CommandParser {
    @Override
    public String name() {
        return TransactionEditCommand.COMMAND_WORD;
    }

    @Override
    public TransactionEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIRECTION, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_ACCOUNT,
                        PREFIX_CATEGORY, PREFIX_DATE);

        if (argMultiMap.getValueCount(PREFIX_DIRECTION) > 1
                || argMultiMap.getValueCount(PREFIX_AMOUNT) > 1
                || argMultiMap.getValueCount(PREFIX_DESCRIPTION) > 1
                || argMultiMap.getValueCount(PREFIX_ACCOUNT) > 1
                || argMultiMap.getValueCount(PREFIX_DATE) > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TransactionEditCommand.MESSAGE_USAGE));
        }

        if (argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TransactionEditCommand.MESSAGE_USAGE));
        }
        Index transactionIndex = CommandParserUtil.parseIndex(argMultiMap.getPreamble());

        TransactionEditDescriptor transactionEditDescriptor = new TransactionEditDescriptor();
        if (argMultiMap.getValue(PREFIX_DIRECTION).isPresent()) {
            transactionEditDescriptor.setDirection(
                    CommandParserUtil.parseDirection(argMultiMap.getValue(PREFIX_DIRECTION).get()));
        }

        if (argMultiMap.getValue(PREFIX_AMOUNT).isPresent()) {
            transactionEditDescriptor.setAmount(
                    CommandParserUtil.parseAmount(argMultiMap.getValue(PREFIX_AMOUNT).get()));
        }

        if (argMultiMap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            transactionEditDescriptor.setDescription(
                    CommandParserUtil.parseDescription(argMultiMap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argMultiMap.getValue(PREFIX_DATE).isPresent()) {
            transactionEditDescriptor.setDate(
                    CommandParserUtil.parseDate(argMultiMap.getValue(PREFIX_DATE).get()));
        }

        if (argMultiMap.getValue(PREFIX_CATEGORY).isPresent()) {
            Set<Category> newCategories = new HashSet<>();
            newCategories.add(CommandParserUtil.parseCategory(argMultiMap.getValue(PREFIX_DATE).get()));
            transactionEditDescriptor.setCategories(newCategories);
        }

        Account updatedAccount = argMultiMap.getValue(PREFIX_ACCOUNT).isPresent()
                ? CommandParserUtil.parseAccount(argMultiMap.getValue(PREFIX_ACCOUNT).get())
                : null;

        return new TransactionEditCommand(transactionIndex, transactionEditDescriptor, updatedAccount);
    }
}
