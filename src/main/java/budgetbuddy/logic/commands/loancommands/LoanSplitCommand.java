package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.LoanSplit;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

public class LoanSplitCommand extends Command {

    public static final String COMMAND_WORD = "loan split";

    public static final String MESSAGE_USAGE = "";

    public static final String MESSAGE_SUCCESS = "Loans split.";
    public static final String MESSAGE_INVALID_TOTAL = "Total amount must be more than zero.";
    public static final String MESSAGE_ALREADY_SPLIT = "The amounts have already been split equally.";

    private HashMap<Person, Amount> personAmountHash;

    public LoanSplitCommand(HashMap<Person, Amount> personAmountHash) {
        requireNonNull(personAmountHash);
        this.personAmountHash = personAmountHash;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getLoansManager());

        long totalAmount = personAmountHash.values().stream()
                .map(Amount::toLong)
                .reduce(Long::sum)
                .orElse(0L);

        if (totalAmount <= 0) {
            throw new CommandException(MESSAGE_INVALID_TOTAL);
        }

        long perPerson = totalAmount / personAmountHash.size();

        Stream<Map.Entry<Person, Amount>> personsOverpaid = personAmountHash.entrySet().stream()
                .filter(personAmountEntry -> personAmountEntry.getValue().toLong() > perPerson);
        Stream<Map.Entry<Person, Amount>> personsUnderpaid = personAmountHash.entrySet().stream()
                .filter(personAmountEntry -> personAmountEntry.getValue().toLong() < perPerson);

        List<LoanSplit> splitList = getLoanSplitList(personsOverpaid, personsUnderpaid, perPerson);

        model.getLoansManager().setSplitList(splitList);
        return new CommandResult(MESSAGE_SUCCESS, CommandCategory.LOAN_SPLIT);
    }

    /**
     * Returns a list of {@code LoanSplit} to replace the existing one in the loans manager.
     * Each {@code LoanSplit} contains information about a creditor, debtor, and amount owed between the two.
     * @param personsOverpaid The persons who over-paid, relative to the correct amount {@code perPerson}.
     * @param personsUnderpaid The persons who under-paid, relative to the correct amount {@code perPerson}.
     * @param perPerson The correct that should be paid per person for the entire payment.
     * @throws CommandException If an error occurs during the calculations.
     */
    private List<LoanSplit> getLoanSplitList(
            Stream<Map.Entry<Person, Amount>> personsOverpaid,
            Stream<Map.Entry<Person, Amount>> personsUnderpaid, long perPerson) throws CommandException {
        requireAllNonNull(personsOverpaid, personsUnderpaid, perPerson);

        long numPersonsUnderpaid = personsUnderpaid.count();

        /*
        This function acts on a person who over-paid, returning a stream of LoanSplit objects.
        Each LoanSplit object in the stream indicates who owes them money (and how much).
         */
        Function<Map.Entry<Person, Amount>, Stream<LoanSplit>> calculateLoanSplit = personOverpaidEntry -> {
            return personsUnderpaid.map(personUnderpaidEntry -> {
                long creditorPaid = personOverpaidEntry.getValue().toLong();
                long debtorPaid = personUnderpaidEntry.getValue().toLong();
                long amountOwed = ((creditorPaid - perPerson) / numPersonsUnderpaid) - debtorPaid;
                return new LoanSplit(
                        personUnderpaidEntry.getKey(), personOverpaidEntry.getKey(), new Amount(amountOwed));
            });
        };

        return personsOverpaid
                .map(calculateLoanSplit)
                .reduce(Stream::concat)
                .orElseThrow(() -> new CommandException(MESSAGE_ALREADY_SPLIT))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoanSplitCommand)) {
            return false;
        }

        LoanSplitCommand otherCommand = (LoanSplitCommand) other;
        return personAmountHash.equals(otherCommand.personAmountHash);
    }
}
