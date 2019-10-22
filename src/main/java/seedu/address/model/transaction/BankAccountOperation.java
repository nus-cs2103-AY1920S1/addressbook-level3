package seedu.address.model.transaction;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.Date;

import java.util.Set;

public interface BankAccountOperation {
    Amount handleBalance(Amount balance);

    Amount getAmount();

    Set<Tag> getTags();

    boolean isSameTransaction(BankAccountOperation transaction);

    Date getDate();
}
