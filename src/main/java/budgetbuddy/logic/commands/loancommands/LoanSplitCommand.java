package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.hasDuplicates;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Debtor;
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

    public static final String MESSAGE_CALCULATION_ERROR = "An error occurred during calculation.";

    private HashMap<Person, Amount> personAmountMap;

    private List<DebtorCreditorAmount> debtorCreditorAmountList;

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

        this.personAmountMap = new HashMap<Person, Amount>();
        for (int i = 0; i < persons.size(); i++) {
            personAmountMap.put(persons.get(i), amounts.get(i));
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getLoansManager());

        long totalAmount = personAmountMap.values().stream()
                .map(Amount::toLong)
                .reduce(Long::sum)
                .orElseThrow(() -> new CommandException(MESSAGE_INVALID_TOTAL));

        long perPerson = totalAmount / personAmountMap.size();

        List<Participant> participants = new ArrayList<Participant>();
        for (Entry<Person, Amount> personAmountEntry : personAmountMap.entrySet()) {
            long amountPaid = personAmountEntry.getValue().toLong();
            long balance = amountPaid - perPerson;
            participants.add(new Participant(personAmountEntry.getKey(), balance));
        }

        if (participants.stream().allMatch(p -> p.getBalance() >= 0)) {
            throw new CommandException(MESSAGE_ALREADY_SPLIT);
        }

        Comparator<Participant> sortBalanceIncreasing = Comparator.comparingLong(Participant::getBalance);
        calculateSplitList(participants, sortBalanceIncreasing);
        List<Debtor> debtors = constructDebtorsList();

        LoansManager loansManager = model.getLoansManager();
        loansManager.setDebtors(debtors);
        return new CommandResult(MESSAGE_SUCCESS, CommandCategory.LOAN_SPLIT);
    }

    /**
     * Fills the {@code DebtorCreditorAmount} list.
     * @see DebtorCreditorAmount
     * @param participants The list of particpants to calculate the debts from.
     * @param balanceIncreasing A {@code Comparator} to sort participants in order of increasing balance.
     */
    private void calculateSplitList(
            List<Participant> participants, Comparator<Participant> balanceIncreasing) {
        requireAllNonNull(participants, balanceIncreasing);

        debtorCreditorAmountList = new ArrayList<DebtorCreditorAmount>();

        while (participants.size() > 1) {
            participants.sort(balanceIncreasing); // participants MUST be sorted in the order of increasing balance

            Participant debtor = participants.get(0);
            Participant creditor = participants.get(participants.size() - 1);

            // transfer money between the biggest debtor and biggest creditor
            long amountTransferred = 0;
            long debtorBalancePositive = debtor.getBalance() * -1;
            if (debtorBalancePositive >= creditor.getBalance()) {
                amountTransferred = creditor.getBalance();
                debtor.setBalance(debtor.getBalance() + creditor.getBalance());
                creditor.setBalance(0);
            } else if (debtorBalancePositive < creditor.getBalance()) {
                amountTransferred = debtorBalancePositive;
                creditor.setBalance(creditor.getBalance() - debtor.getBalance());
                debtor.setBalance(0);
            }

            if (debtor.getBalance() == 0) {
                participants.remove(0);
            }
            if (!participants.isEmpty() && creditor.getBalance() == 0) {
                participants.remove(participants.size() - 1);
            }

            if (amountTransferred != 0) {
                debtorCreditorAmountList.add(
                        new DebtorCreditorAmount(
                                debtor.person, creditor.person, new Amount(amountTransferred)));
            }
        }
    }

    /**
     * Constructs and returns a list of {@code Debtor} objects using {@code debtorCreditorAmountList}.
     */
    private List<Debtor> constructDebtorsList() {
        requireNonNull(debtorCreditorAmountList);

        debtorCreditorAmountList.sort(Comparator.comparing(dca -> dca.debtor.getName().toString()));

        List<Debtor> debtors = new ArrayList<Debtor>();

        Person currDebtor = debtorCreditorAmountList.get(0).debtor;
        HashMap<Person, Amount> currCreditors = new HashMap<Person, Amount>();
        for (DebtorCreditorAmount dca : debtorCreditorAmountList) {
            if (!dca.debtor.equals(currDebtor)) {
                debtors.add(new Debtor(currDebtor, currCreditors));
                currDebtor = dca.debtor;
                currCreditors.clear();
            }
            currCreditors.put(dca.creditor, dca.amount);
        }
        debtors.add(new Debtor(currDebtor, currCreditors));

        return debtors;
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
        return personAmountMap.equals(otherCommand.personAmountMap);
    }

    /**
     * A utility class that holds a {@code Person} and their {@code balance} relative to the total pool of money.
     * This class is used only for the calculations involved in splitting a group payment.
     */
    private class Participant {

        public final Person person;
        private long balance;

        public Participant(Person person, long balance) {
            this.person = person;
            this.balance = balance;
        }

        public long getBalance() {
            return balance;
        }

        public void setBalance(long balance) {
            this.balance = balance;
        }

        @Override
        public String toString() {
            return String.format("<%s, %d>", person, balance);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof Participant)) {
                return false;
            }

            Participant otherParticipant = (Participant) other;
            return person.equals(otherParticipant.person)
                    && balance == otherParticipant.balance;
        }
    }

    /**
     * A utility class containing information about a debtor and the money they owe to a creditor.
     */
    private static class DebtorCreditorAmount {

        public final Person debtor;
        public final Person creditor;
        public final Amount amount;

        public DebtorCreditorAmount(Person debtor, Person creditor, Amount amount) {
            this.debtor = debtor;
            this.creditor = creditor;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return String.format("%s, %s, %s", debtor, creditor, amount);
        }
    }
}
