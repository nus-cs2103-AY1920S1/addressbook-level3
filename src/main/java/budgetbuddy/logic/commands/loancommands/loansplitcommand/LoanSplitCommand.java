package budgetbuddy.logic.commands.loancommands.loansplitcommand;

import static budgetbuddy.commons.util.CollectionUtil.generateCombinations;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_MAX_SHARE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_USER;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Debtor;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Splits a group payment among a list of persons.
 * Optionally adds debts from the resulting list to the loans manager.
 */
public class LoanSplitCommand extends Command {

    public static final String COMMAND_WORD = "loan split";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Splits a group payment into a list of who owes who how much. "
                    + "Optionally limits the final amount paid of one or more persons.\n"
                    + "Optionally adds loans from the resulting list to your loan list.\n"
                    + "Parameters: "
                    + PREFIX_PERSON + "PERSON "
                    + PREFIX_AMOUNT + "AMOUNT "
                    + "[" + PREFIX_MAX_SHARE + "LIMIT] "
                    + "... "
                    + "["
                    + PREFIX_USER + "YOUR_NAME "
                    + PREFIX_DATE + "DATE "
                    + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
                    + "Example: " + COMMAND_WORD + " "
                    + PREFIX_PERSON + "Mary " + PREFIX_AMOUNT + "10 "
                    + PREFIX_PERSON + "John " + PREFIX_AMOUNT + "90 "
                    + PREFIX_PERSON + "Satan " + PREFIX_AMOUNT + "0";

    public static final String MESSAGE_SUCCESS = "Loans split.";
    public static final String MESSAGE_SUCCESS_LOANS_ADDED =
            "Loans split. Debts that you owe/are owed to you have been added to your loan list.";

    public static final String MESSAGE_PERSON_AMOUNT_NUMBERS_MISMATCH =
            "The number of persons does not match the number of payments.";

    public static final String MESSAGE_MAX_SHARES_EXCEED_TOTAL_AMOUNT =
            "The sum of all limits cannot exceed the total amount paid by all persons.";
    public static final String MESSAGE_ALREADY_SPLIT_EQUALLY = "The amounts have already been split equally.";

    public static final String MESSAGE_USER_NOT_FOUND =
            "Your name must match (case-sensitive) exactly one of those in the group.";

    private List<Person> persons;
    private List<Amount> amounts;
    private List<Long> maxShares;
    private List<DebtorCreditorAmount> debtorCreditorAmountList;

    private Optional<Person> optionalUser;
    private Optional<Description> optionalDescription;
    private Optional<LocalDate> optionalDate;

    public LoanSplitCommand(List<Person> persons, List<Amount> amounts, List<Long> maxShares,
                            Optional<Person> optionalUser,
                            Optional<Description> optionalDescription,
                            Optional<LocalDate> optionalDate) throws CommandException {
        requireAllNonNull(persons, amounts, optionalUser, optionalDescription, optionalDate);

        if (persons.size() != amounts.size()) {
            throw new CommandException(MESSAGE_PERSON_AMOUNT_NUMBERS_MISMATCH);
        }

        Person user = new Person(new Name("You"));

        this.persons = new ArrayList<Person>(persons);
        for (int i = 0; i < this.persons.size(); i++) {
            if (optionalUser.isEmpty()) {
                break;
            }
            if (this.persons.get(i).equals(optionalUser.get())) {
                this.persons.set(i, user);
                break;
            }
        }

        this.amounts = new ArrayList<Amount>(amounts);
        this.maxShares = new ArrayList<Long>(maxShares);
        this.debtorCreditorAmountList = new ArrayList<DebtorCreditorAmount>();

        this.optionalUser = optionalUser.isPresent() ? Optional.of(user) : optionalUser;
        this.optionalDescription = optionalDescription;
        this.optionalDate = optionalDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getLoansManager());

        long totalAmount = amounts.stream()
                .map(Amount::toLong)
                .reduce(0L, Long::sum);

        if (maxShares.stream().reduce(0L, Long::sum) > totalAmount) {
            throw new CommandException(MESSAGE_MAX_SHARES_EXCEED_TOTAL_AMOUNT);
        }

        long defaultPerPerson = (totalAmount - maxShares.stream()
                .filter(share -> share >= 0 && share <= totalAmount)
                .reduce(0L, Long::sum)) / (persons.size() - maxShares.size());

        List<Participant> participants = new ArrayList<Participant>();
        for (int i = 0; i < persons.size(); i++) {
            long targetAmount = i < maxShares.size() ? maxShares.get(i) : defaultPerPerson;
            long balance = amounts.get(i).toLong() - targetAmount;
            participants.add(new Participant(persons.get(i), balance));
        }

        if (participants.stream().allMatch(p -> p.getBalance() >= 0)) {
            throw new CommandException(MESSAGE_ALREADY_SPLIT_EQUALLY);
        }

        Comparator<Participant> sortBalanceIncreasing = Comparator.comparingLong(Participant::getBalance);
        debtorCreditorAmountList.addAll(findSubGroups(participants, sortBalanceIncreasing));
        debtorCreditorAmountList.addAll(calculateSplitList(participants, sortBalanceIncreasing));

        if (optionalUser.isPresent()) {
            constructUserLoansList(optionalUser.get()).forEach(loan -> model.getLoansManager().addLoan(loan));
        }

        model.getLoansManager().setDebtors(constructDebtorsList());
        return new CommandResult(
                optionalUser.isPresent() ? MESSAGE_SUCCESS_LOANS_ADDED : MESSAGE_SUCCESS,
                CommandCategory.LOAN_SPLIT);
    }

    /**
     * Finds all sub-groups among participants and checks the balance of each sub-group.
     * If the balance is zero, {@code calculateSplitList} is called for the sub-group.
     * This method, while expensive, will minimize the total number of debts.
     */
    private List<DebtorCreditorAmount> findSubGroups(
            List<Participant> participants, Comparator<Participant> balanceIncreasing) {
        requireAllNonNull(participants, balanceIncreasing);

        List<DebtorCreditorAmount> debtorCreditorAmountList = new ArrayList<DebtorCreditorAmount>();

        for (int i = 2; i < participants.size(); i++) {
            List<List<Participant>> participantSubGroups = generateCombinations(participants);
            for (List<Participant> subGroup : participantSubGroups) {
                long subGroupBalance = subGroup.stream()
                        .mapToLong(Participant::getBalance)
                        .reduce(0, Long::sum);
                if (subGroupBalance == 0) {
                    debtorCreditorAmountList.addAll(calculateSplitList(subGroup, balanceIncreasing));
                }
            }
        }

        return debtorCreditorAmountList;
    }

    /**
     * Fills and returns a {@link DebtorCreditorAmount} list.
     * @param participants The list of particpants to calculate the debts from.
     * @param balanceIncreasing A {@code Comparator} to sort participants in order of increasing balance.
     */
    private List<DebtorCreditorAmount> calculateSplitList(
            List<Participant> participants, Comparator<Participant> balanceIncreasing) {
        requireAllNonNull(participants, balanceIncreasing);

        List<DebtorCreditorAmount> debtorCreditorAmountList = new ArrayList<DebtorCreditorAmount>();

        if (participants.stream().noneMatch(p -> p.getBalance() != 0)) {
            participants.clear();
            return debtorCreditorAmountList;
        }

        while (participants.size() > 1) {
            Participant debtor = participants.stream().min(balanceIncreasing).get();
            Participant creditor = participants.stream().max(balanceIncreasing).get();

            // transfer money between the biggest debtor and biggest creditor
            long amountTransferred = 0;
            long debtorBalancePositive = debtor.getBalance() * -1;
            if (debtorBalancePositive >= creditor.getBalance()) {
                amountTransferred = creditor.getBalance();
                debtor.setBalance(debtor.getBalance() + creditor.getBalance());
                creditor.setBalance(0);
            } else if (debtorBalancePositive < creditor.getBalance()) {
                amountTransferred = debtorBalancePositive;
                creditor.setBalance(creditor.getBalance() - debtorBalancePositive);
                debtor.setBalance(0);
            }

            if (debtor.getBalance() == 0) {
                participants.remove(debtor);
            }
            if (creditor.getBalance() == 0) {
                participants.remove(creditor);
            }

            if (amountTransferred != 0) {
                debtorCreditorAmountList.add(
                        new DebtorCreditorAmount(
                                debtor.person, creditor.person, new Amount(amountTransferred)));
            }
        }
        return debtorCreditorAmountList;
    }

    /**
     * Uses the {@code debtorCreditorAmountList} to find all loans belonging to the given {@code user}.
     * @return A {@code List} of {@code Loan} objects to be added to the loans manager.
     * @throws CommandException If the user is not found in {@code persons}.
     */
    private List<Loan> constructUserLoansList(Person user) throws CommandException {
        requireAllNonNull(persons, debtorCreditorAmountList, user, optionalDescription);

        if (!persons.contains(user)) {
            throw new CommandException(MESSAGE_USER_NOT_FOUND);
        }

        List<Loan> userLoans = new ArrayList<Loan>();
        debtorCreditorAmountList.forEach(debtorCreditorAmount -> {
            if (debtorCreditorAmount.debtor.equals(user)) {
                userLoans.add(new Loan(
                        debtorCreditorAmount.creditor, Direction.IN, debtorCreditorAmount.amount,
                        optionalDate.orElse(LocalDate.now()), optionalDescription.orElse(new Description("")),
                        Status.UNPAID));
            } else if (debtorCreditorAmount.creditor.equals(user)) {
                userLoans.add(new Loan(
                        debtorCreditorAmount.debtor, Direction.OUT, debtorCreditorAmount.amount,
                        optionalDate.orElse(LocalDate.now()), optionalDescription.orElse(new Description("")),
                        Status.UNPAID));
            }
        });
        return userLoans;
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
                // new debtor reached in sorted list; add current debtor to list of debtors
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
        return persons.equals(otherCommand.persons)
                && amounts.equals(otherCommand.amounts)
                && maxShares.equals(otherCommand.maxShares);
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
