package budgetbuddy.logic.commands.loancommands.loansplitcommand;

import budgetbuddy.model.person.Person;

/**
 * A utility class that holds a {@code Person} and their {@code balance} relative to a total pool of money.
 * This class is used only for the calculations involved in splitting a group payment.
 */
public class Participant {

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
