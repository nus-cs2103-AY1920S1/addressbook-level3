package seedu.address.model;

import java.util.stream.IntStream;

import seedu.address.model.borrower.BorrowerId;

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
        currentBorrowerIdIndex = borrowers.getSize();
    }
    /**
     * Generates a new borrower ID based on the current borrower ID index.
     */
    public static BorrowerId generateBorrowerId() {
        currentBorrowerIdIndex++;
        int paddingLength = getPaddingLength();
        String padding = getPadding(paddingLength);
        BorrowerId id = new BorrowerId(PREFIX + padding + currentBorrowerIdIndex);
        /*while (borrowers.checkIfBorrowerIdExists(id)) {
            currentBorrowerIdIndex++;
            id = new BorrowerId(PREFIX + padding + currentBorrowerIdIndex);
        }*/
        return new BorrowerId(PREFIX + padding + currentBorrowerIdIndex);
    }

    /**
     * Checks if a certain borrower ID is already in the borrowers record.
     */
    public static boolean borrowerIdExists(BorrowerId id) {
        return borrowers.checkIfBorrowerIdExists(id);
    }

    private static int getPaddingLength() {
        String stringRepresentation = Integer.toString(currentBorrowerIdIndex);
        return BORROWER_ID_LENGTH - stringRepresentation.length();
    }

    private static String getPadding(int paddingLength) {
        return IntStream.rangeClosed(1, paddingLength)
                .mapToObj(x -> "0")
                .reduce("", (a, b) -> a + b);
    }
}

