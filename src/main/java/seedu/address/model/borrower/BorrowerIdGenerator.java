package seedu.address.model.borrower;

import java.util.stream.IntStream;

import seedu.address.model.BorrowerRecords;

/**
 * Borrower ID generator that generates a borrower ID when a new borrower is registered.
 */
public class BorrowerIdGenerator {
    private static final int BORROWER_ID_LENGTH = 4; //excluding prefix 'K'
    private static final String PREFIX = "K";

    private static int currentBorrowerIdIndex = 0;

    private static BorrowerRecords borrowers;

    public static void setBorrowers(BorrowerRecords borrowers) {
        BorrowerIdGenerator.borrowers = borrowers;
        currentBorrowerIdIndex = 0;
    }

    /**
     * Generates a new borrower ID based on the current borrower ID index.
     */
    public static BorrowerId generateBorrowerId() {
        currentBorrowerIdIndex++;
        String padding = getPadding(currentBorrowerIdIndex);
        BorrowerId id = new BorrowerId(PREFIX + padding + currentBorrowerIdIndex);
        while (borrowerIdExists(id)) {
            currentBorrowerIdIndex++;
            id = new BorrowerId(PREFIX + padding + currentBorrowerIdIndex);
        }
        return id;
    }

    /**
     * Checks if a certain borrower ID is already in the borrowers record.
     */
    public static boolean borrowerIdExists(BorrowerId id) {
        return borrowers.checkIfBorrowerIdExists(id);
    }

    private static String getPadding(int index) {
        String stringRepresentation = Integer.toString(index);
        int paddingLength = BORROWER_ID_LENGTH - stringRepresentation.length();
        return IntStream.rangeClosed(1, paddingLength)
                .mapToObj(x -> "0")
                .reduce("", (a, b) -> a + b);
    }
}

