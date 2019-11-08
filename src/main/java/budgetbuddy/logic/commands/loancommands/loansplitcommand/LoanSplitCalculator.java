package budgetbuddy.logic.commands.loancommands.loansplitcommand;

import static budgetbuddy.commons.util.CollectionUtil.generateCombinations;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * A utility class containing various methods for calculating the splitting of a group payment.
 */
public class LoanSplitCalculator {

    public static final String MESSAGE_MAX_SHARES_EXCEED_TOTAL_AMOUNT =
            "The sum of all limits cannot exceed the total amount paid by all persons.";

    private static final Comparator<Participant> sortBalanceIncreasing =
            Comparator.comparingLong(Participant::getBalance);

    private LoanSplitCalculator() {} // prevent instantiation

    /**
     * Sums all {@code amounts} to calculate the total amount paid by a group.
     */
    public static long calculateTotalAmountPaid(List<Amount> amounts) {
        return amounts.stream().map(Amount::toLong).reduce(0L, Long::sum);
    }

    /**
     * Calculates the default share per person, taking into account the limits in {@code maxShares}.
     * @param totalAmount The total amount to split among all {@code persons}.
     * @param persons The persons among which to split the {@code totalAmount}.
     * @param maxShares Limits on the share for one or more {@code persons}.
     * @return The default share per person among the {@code persons} without a limit in {@code maxShares}.
     * @throws CommandException If the total amount of {@code maxShares} exceeds {@code totalAmount}.
     */
    public static long calculateDefaultSharePerPerson(
            long totalAmount, List<Person> persons, List<Long> maxShares) throws CommandException {
        long maxShareToSetAside = maxShares.stream().reduce(0L, Long::sum);

        // check if max shares exceed total amount
        if (maxShareToSetAside > totalAmount) {
            throw new CommandException(MESSAGE_MAX_SHARES_EXCEED_TOTAL_AMOUNT);
        }

        return (totalAmount - maxShareToSetAside) / (persons.size() - maxShares.size());
    }

    /**
     * Takes all {@code persons} and constructs a list of {@link Participant}s.
     * @param persons The persons from which the list of {@code Participant}s is constructed.
     * @param amounts The amounts paid for each person in {@code persons} so far.
     * @param maxShares Limits on the share of one or more {@code persons}.
     * @param defaultSharePerPerson The default target share per person.
     * @return The list of {@code Participant}s.
     */
    public static List<Participant> calculateParticipantsBalance(
            List<Person> persons, List<Amount> amounts, List<Long> maxShares, long defaultSharePerPerson) {
        List<Participant> participants = new ArrayList<Participant>();

        for (int i = 0; i < persons.size(); i++) {
            long share = i < maxShares.size() ? maxShares.get(i) : defaultSharePerPerson;
            long balance = amounts.get(i).toLong() - share;
            participants.add(new Participant(persons.get(i), balance));
        }

        return participants;
    }

    /**
     * Returns a list of all possible sub-groups from the given {@code Participant}s.
     */
    public static List<List<Participant>> findSubGroups(List<Participant> participants) {
        requireNonNull(participants);
        return generateCombinations(participants);
    }

    /**
     * Returns the total balance of all members of the given {@code Participant} group.
     * This is calculated by summing the balances of all members in the {@code group}.
     */
    public static long calculateParticipantGroupBalance(List<Participant> group) {
        requireNonNull(group);
        return group.stream().mapToLong(Participant::getBalance).reduce(0L, Long::sum);
    }

    /**
     * Calculates who owes who how much amoung a group of {@link Participant}s.
     * @param participants The list of particpants to calculate the debts from.
     * @return A {@link DebtorCreditorAmount} list representing the calculated result.
     */
    public static List<DebtorCreditorAmount> calculateSplitList(List<Participant> participants) {
        requireNonNull(participants);

        List<DebtorCreditorAmount> debtorCreditorAmountList = new ArrayList<DebtorCreditorAmount>();

        // check if the participants have already split their balances equally (balance == 0)
        if (participants.stream().noneMatch(p -> p.getBalance() != 0)) {
            participants.clear();
            return debtorCreditorAmountList;
        }

        while (participants.size() > 1) {
            Participant debtor = participants.stream().min(sortBalanceIncreasing).get();
            Participant creditor = participants.stream().max(sortBalanceIncreasing).get();
            DebtorCreditorAmount debtorCreditorAmount = transferMoneyBetween(debtor, creditor);

            if (debtor.getBalance() == 0) {
                participants.remove(debtor);
            }
            if (creditor.getBalance() == 0) {
                participants.remove(creditor);
            }

            if (debtorCreditorAmount.amount.toLong() > 0) {
                debtorCreditorAmountList.add(debtorCreditorAmount);
            }
        }
        return debtorCreditorAmountList;
    }

    /**
     * Transfers money between a given {@code debtor} and {@code creditor}.
     * After the transfer, either one or both of their balances should be zero.
     * @param debtor A {@code Participant} with a negative balance.
     * @param creditor A {@code Participant} with a positive balance.
     * @return A {@link DebtorCreditorAmount} representing the details of the transfer.
     */
    private static DebtorCreditorAmount transferMoneyBetween(Participant debtor, Participant creditor) {
        // check that the balances of both debtor and creditor are not the same sign
        if (Long.signum(debtor.getBalance()) == Long.signum(creditor.getBalance())) {
            return new DebtorCreditorAmount(debtor.person, creditor.person, new Amount(0));
        }

        long amountTransferred = 0;
        long debtorBalancePositive = -1 * debtor.getBalance();
        if (debtorBalancePositive == creditor.getBalance()) {
            // the debtor and creditor's balances are equal in magnitude
            amountTransferred = creditor.getBalance();
            debtor.setBalance(0);
            creditor.setBalance(0);
        } else if (debtorBalancePositive > creditor.getBalance()) {
            // all of the creditor's balance is paid off with some of the debtor's balance
            amountTransferred = creditor.getBalance();
            debtor.setBalance(debtor.getBalance() + creditor.getBalance());
            creditor.setBalance(0);
        } else if (debtorBalancePositive < creditor.getBalance()) {
            // some of the creditor's balance is paid off with all of the debtor's balance
            amountTransferred = debtorBalancePositive;
            creditor.setBalance(creditor.getBalance() - debtorBalancePositive);
            debtor.setBalance(0);
        }

        return new DebtorCreditorAmount(debtor.person, creditor.person, new Amount(amountTransferred));
    }
}
