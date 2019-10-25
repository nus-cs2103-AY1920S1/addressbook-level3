package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.hasDuplicates;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.ArrayList;
import java.util.Collection;
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

/**
 * Splits a group payment among a list of persons.
 */
public class LoanSplitCommand extends Command {

    public static final String COMMAND_WORD = "loan split";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Splits a group payment into a list of who owes who how much.\n"
                    + "Parameters: "
                    + PREFIX_PERSON + "PERSON "
                    + PREFIX_AMOUNT + "AMOUNT "
                    + "...\n"
                    + "Example: " + COMMAND_WORD + " "
                    + PREFIX_PERSON + "Mary Jesus Judas "
                    + PREFIX_AMOUNT + "10 90 0";

    public static final String MESSAGE_SUCCESS = "Loans split.";
    public static final String MESSAGE_PERSON_AMOUNT_NUMBERS_MISMATCH =
            "The number of persons does not match the number of payments.";
    public static final String MESSAGE_DUPLICATE_PERSONS = "Names of persons entered must be unique.";
    public static final String MESSAGE_INVALID_TOTAL = "Total amount must be more than zero.";
    public static final String MESSAGE_ALREADY_SPLIT = "The amounts have already been split equally.";

    private HashMap<Person, Amount> personAmountHash;

    /**
     * Constructs a hash map with a person as the key and the amount they paid as the value.
     * The hash map is used during execution to calculate the splitting of payment.
     * @param persons The list of persons to use in constructing the hash map.
     * @param amounts The list of amounts to be mapped to the list of persons.
     * @throws CommandException If the number of persons is not equal to the number of amounts,
     * or if the list of persons contains duplicates.
     */
    public LoanSplitCommand(List<Person> persons, List<Amount> amounts) throws CommandException {
        requireAllNonNull(persons, amounts);

        if (persons.size() != amounts.size()) {
            throw new CommandException(MESSAGE_PERSON_AMOUNT_NUMBERS_MISMATCH);
        } else if (hasDuplicates(persons)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSONS);
        }

        this.personAmountHash = new HashMap<Person, Amount>();
        for (int i = 0; i < persons.size(); i++) {
            personAmountHash.put(persons.get(i), amounts.get(i));
        }
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

        List<Map.Entry<Person, Amount>> personsOverpaid = personAmountHash.entrySet().stream()
                .filter(personAmountEntry -> personAmountEntry.getValue().toLong() > perPerson)
                .collect(Collectors.toList());
        List<Map.Entry<Person, Amount>> personsUnderpaid = personAmountHash.entrySet().stream()
                .filter(personAmountEntry -> personAmountEntry.getValue().toLong() < perPerson)
                .collect(Collectors.toList());

        List<LoanSplit> splitList = getLoanSplitList(personsOverpaid, personsUnderpaid, perPerson);

        model.getLoansManager().setSplitList(splitList);
        return new CommandResult(model.getLoansManager().getSplitList().toString(), CommandCategory.LOAN_SPLIT);
    }

    /**
     * Returns a list of {@code LoanSplit} to replace the existing one in the loans manager.
     * Each {@code LoanSplit} contains information about a creditor, debtor, and amount owed between the two.
     * @param personsOverpaidList The persons who over-paid, relative to the correct amount {@code perPerson}.
     * @param personsUnderpaidList The persons who under-paid, relative to the correct amount {@code perPerson}.
     * @param perPerson The correct that should be paid per person for the entire payment.
     * @throws CommandException If an error occurs during the calculations.
     */
    private List<LoanSplit> getLoanSplitList(
            List<Map.Entry<Person, Amount>> personsOverpaidList,
            List<Map.Entry<Person, Amount>> personsUnderpaidList, long perPerson) throws CommandException {
        requireAllNonNull(personsOverpaidList, personsUnderpaidList, perPerson);

        long numPersonsUnderpaid = personsUnderpaidList.size();

        /*
        This function acts on a person who over-paid, returning a stream of LoanSplit objects.
        Each LoanSplit object in the stream indicates who owes them money (and how much).
         */
        Function<Map.Entry<Person, Amount>, Stream<LoanSplit>> calculateLoanSplit = personOverpaidEntry -> {
            List<LoanSplit> loanSplitList = new ArrayList<LoanSplit>();
            for (Map.Entry<Person, Amount> personUnderpaidEntry : personsUnderpaidList) {
                long creditorPaid = personOverpaidEntry.getValue().toLong();
                long debtorPaid = personUnderpaidEntry.getValue().toLong();
                long amountOwed = ((creditorPaid - perPerson) / numPersonsUnderpaid) - debtorPaid;
                loanSplitList.add(new LoanSplit(
                        personUnderpaidEntry.getKey(),
                        personOverpaidEntry.getKey(),
                        new Amount(amountOwed)));
            }
            return loanSplitList.stream();
        };

        return personsOverpaidList
                .stream()
                .map(calculateLoanSplit)
                .flatMap(Function.identity())
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
