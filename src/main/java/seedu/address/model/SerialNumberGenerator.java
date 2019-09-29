package seedu.address.model;

import java.util.stream.IntStream;

import seedu.address.model.book.SerialNumber;

/**
 * Serial Number generator when user does not input a preferred serial number.
 */
public class SerialNumberGenerator {
    public static final int SERIAL_NUMBER_LENGTH = 4; //excluding prefix 'B'
    public static final String PREFIX = "B";

    private static int currentSerialNumberIndex = 0;

    private static Catalog catalog = new Catalog();

    public static void setCatalog(Catalog catalog) {
        SerialNumberGenerator.catalog = catalog;
        currentSerialNumberIndex = 0;
    }
    /**
     * Generates a new serial number based on the current serial number index.
     */
    public static SerialNumber generateSerialNumber() {
        currentSerialNumberIndex++;
        int paddingLength = getPaddingLength();
        String padding = getPadding(paddingLength);
        SerialNumber sn = new SerialNumber(PREFIX + padding + currentSerialNumberIndex);
        while (catalog.checkIfSerialNumberExists(sn)) {
            currentSerialNumberIndex++;
            sn = new SerialNumber(PREFIX + padding + currentSerialNumberIndex);
        }
        return new SerialNumber(PREFIX + padding + currentSerialNumberIndex);
    }

<<<<<<< HEAD
=======
    /**
     * Checks if a certain serial number is already in the catalog.
     */
    public static boolean serialNumberExists(SerialNumber sn) {
        return catalog.checkIfSerialNumberExists(sn);
    }

>>>>>>> 76edc3518025011d8a382c0c40e511828d7408d5
    private static int getPaddingLength() {
        String stringRepresentation = Integer.toString(currentSerialNumberIndex);
        return SERIAL_NUMBER_LENGTH - stringRepresentation.length();
    }

    private static String getPadding(int paddingLength) {
        return IntStream.rangeClosed(1, paddingLength)
<<<<<<< HEAD
                        .mapToObj(x -> "0")
                        .reduce("", (a, b) -> a + b);
=======
                .mapToObj(x -> "0")
                .reduce("", (a, b) -> a + b);
>>>>>>> 76edc3518025011d8a382c0c40e511828d7408d5
    }
}
