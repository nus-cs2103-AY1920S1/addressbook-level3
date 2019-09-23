package seedu.address.model.expense.util;

import static seedu.address.model.expense.UniqueIdentifier.UNIQUE_IDENTIFIER_PREFIX;

import seedu.address.model.expense.UniqueIdentifier;

import java.util.UUID;

/**
 * A utility class to generate random unique identifiers for an {@code Expense}.
 */
public class UniqueIdentifierGenerator {


    public static UniqueIdentifier generateRandomUniqueIdentifier() {
        UniqueIdentifier uniqueIdentifier = new UniqueIdentifier(UNIQUE_IDENTIFIER_PREFIX + UUID.randomUUID().toString());

        return uniqueIdentifier;
    }

}
