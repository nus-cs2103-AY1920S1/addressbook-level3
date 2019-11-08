package budgetbuddy.logic.commands.loancommands.loansplitcommand;

import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * A utility class containing information about a debtor and the money they owe to a creditor.
 */
public class DebtorCreditorAmount {

    public final Person debtor;
    public final Person creditor;
    public final Amount amount;

    public DebtorCreditorAmount(Person debtor, Person creditor, Amount amount) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
    }
}
