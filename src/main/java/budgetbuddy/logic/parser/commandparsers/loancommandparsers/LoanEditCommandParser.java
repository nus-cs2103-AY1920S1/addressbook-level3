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
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.person.Person;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE);

        checkArgumentMultimap(argMultimap);

        Index loanIndex = parseIndex(argMultimap.getPreamble());
        LoanEditDescriptor loanEditDescriptor = parseOptionalArguments(argMultimap);

        return new LoanEditCommand(loanIndex, loanEditDescriptor);
    }

    /**
     * Checks if the given {@code ArgumentMultimap} is valid for parsing into a {@code LoanEditCommand}.
     * @throws ParseException If the {@code argMultimap} is invalid.
     */
    private void checkArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValueCount(PREFIX_PERSON) > 1
                || argMultimap.getValueCount(PREFIX_AMOUNT) > 1
                || argMultimap.getValueCount(PREFIX_DESCRIPTION) > 1
                || argMultimap.getValueCount(PREFIX_DATE) > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanEditCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getPreamble().isBlank() || argMultimap.getPreamble().split("\\s+").length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanEditCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given string into an {@code Index}.
     * @return The parsed {@code Index}.
     * @throws ParseException If the string cannot be parsed into an {@code Index}.
     */
    private Index parseIndex(String indexStr) throws ParseException {
        return CommandParserUtil.parseIndex(indexStr);
    }

    /**
     * Converts the optional arguments in the given {@code ArgumentMultimap} into a {@code LoanEditDescriptor}.
     * @return The {@code LoanEditDescriptor}.
     * @throws ParseException If an error occurs while parsing any of the optional arguments.
     */
    private LoanEditDescriptor parseOptionalArguments(ArgumentMultimap argMultimap) throws ParseException {
        LoanEditDescriptor loanEditDescriptor = new LoanEditDescriptor();

        if (argMultimap.getValue(PREFIX_PERSON).isPresent()) {
            loanEditDescriptor.setPerson(
                    new Person(CommandParserUtil.parseName(argMultimap.getValue(PREFIX_PERSON).get())));
        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            Amount editedAmount = CommandParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            if (editedAmount.toLong() == 0) {
                throw new ParseException(Loan.MESSAGE_AMOUNT_POSITIVE_CONSTRAINT);
            }
            loanEditDescriptor.setAmount(editedAmount);
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            loanEditDescriptor.setDescription(
                    CommandParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            loanEditDescriptor.setDate(
                    CommandParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (!loanEditDescriptor.isAnyFieldEdited()) {
            throw new ParseException(LoanEditCommand.MESSAGE_UNEDITED);
        }

        return loanEditDescriptor;
    }
}
