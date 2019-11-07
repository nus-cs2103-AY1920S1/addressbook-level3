package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;
import static java.util.Objects.requireNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.loancommands.LoanEditCommand;
import budgetbuddy.logic.commands.loancommands.LoanEditCommand.LoanEditDescriptor;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Parses input arguments and creates a new LoanEditCommand object.
 */
public class LoanEditCommandParser implements CommandParser<LoanEditCommand> {
    @Override
    public String name() {
        return LoanEditCommand.COMMAND_WORD;
    }

    @Override
    public LoanEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE);

        if (argMultiMap.getValueCount(PREFIX_PERSON) > 1
                || argMultiMap.getValueCount(PREFIX_AMOUNT) > 1
                || argMultiMap.getValueCount(PREFIX_DESCRIPTION) > 1
                || argMultiMap.getValueCount(PREFIX_DATE) > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanEditCommand.MESSAGE_USAGE));
        }

        if (argMultiMap.getPreamble().isBlank() || argMultiMap.getPreamble().split("\\s+").length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanEditCommand.MESSAGE_USAGE));
        }
        Index loanIndex = CommandParserUtil.parseIndex(argMultiMap.getPreamble());

        LoanEditDescriptor loanEditDescriptor = new LoanEditDescriptor();
        if (argMultiMap.getValue(PREFIX_PERSON).isPresent()) {
            loanEditDescriptor.setPerson(
                    new Person(CommandParserUtil.parseName(argMultiMap.getValue(PREFIX_PERSON).get())));
        }
        if (argMultiMap.getValue(PREFIX_AMOUNT).isPresent()) {
            Amount editedAmount = CommandParserUtil.parseAmount(argMultiMap.getValue(PREFIX_AMOUNT).get());
            if (editedAmount.toLong() == 0) {
                throw new ParseException(Loan.MESSAGE_AMOUNT_POSITIVE_CONSTRAINT);
            }
            loanEditDescriptor.setAmount(editedAmount);
        }
        if (argMultiMap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            loanEditDescriptor.setDescription(
                    CommandParserUtil.parseDescription(argMultiMap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultiMap.getValue(PREFIX_DATE).isPresent()) {
            loanEditDescriptor.setDate(
                    CommandParserUtil.parseDate(argMultiMap.getValue(PREFIX_DATE).get()));
        }

        if (!loanEditDescriptor.isAnyFieldEdited()) {
            throw new ParseException(LoanEditCommand.MESSAGE_UNEDITED);
        }

        return new LoanEditCommand(loanIndex, loanEditDescriptor);
    }
}
