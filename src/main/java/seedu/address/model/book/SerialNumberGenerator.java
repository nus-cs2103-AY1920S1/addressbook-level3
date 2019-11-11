package seedu.address.model.book;

import java.util.stream.IntStream;

import seedu.address.model.Catalog;

/**
 * Serial Number generator when user does not input a preferred serial number.
 */
public class SerialNumberGenerator {
    private static final int SERIAL_NUMBER_LENGTH = 5; //excluding prefix 'B'
    private static final String PREFIX = "B";

    private static int currentSerialNumberIndex = 0;
    private static Catalog catalog;

    /**
     * Populates the serial number tree from a catalog.
     *
     * @param catalog catalog to retrieve books from.
     */
    public static void setCatalog(Catalog catalog) {
        SerialNumberGenerator.catalog = catalog;
        currentSerialNumberIndex = 0;
    }

    /**
     * Generates a new serial number based on the current serial number index.
     */
    public static SerialNumber generateSerialNumber() {
        currentSerialNumberIndex++;
        String padding = getPadding(currentSerialNumberIndex);
        SerialNumber sn = new SerialNumber(PREFIX + padding + currentSerialNumberIndex);
        while (catalog.serialNumberExists(sn)) {
            currentSerialNumberIndex++;
            padding = getPadding(currentSerialNumberIndex);
            sn = new SerialNumber(PREFIX + padding + currentSerialNumberIndex);
        }
        return sn;
    }

    private static String getPadding(int index) {
        String stringRepresentation = Integer.toString(index);
        int paddingLength = SERIAL_NUMBER_LENGTH - stringRepresentation.length();
        return IntStream.rangeClosed(1, paddingLength)
                .mapToObj(x -> "0")
                .reduce("", (a, b) -> a + b);
    }
}
