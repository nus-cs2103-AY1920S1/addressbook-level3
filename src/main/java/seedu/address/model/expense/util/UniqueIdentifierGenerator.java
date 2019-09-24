package seedu.address.model.expense.util;

import static seedu.address.model.expense.UniqueIdentifier.UNIQUE_IDENTIFIER_PREFIX;

import java.util.UUID;

import seedu.address.model.expense.UniqueIdentifier;

/**
 * A utility class to generate random unique identifiers for an {@code Expense}.
 */
public class UniqueIdentifierGenerator {

    /**
     * Generates a random unique identifier consisting of a version 4 universally unique identifier (UUID)
     * prefixed with 'Expense@' e.g. Expense@13f213ea-0012-678c-c301-12ca22v21344.
     */
    public static UniqueIdentifier generateRandomUniqueIdentifier() {
        UniqueIdentifier uniqueIdentifier =
                new UniqueIdentifier(UNIQUE_IDENTIFIER_PREFIX + UUID.randomUUID().toString());

        return uniqueIdentifier;
    }

}
