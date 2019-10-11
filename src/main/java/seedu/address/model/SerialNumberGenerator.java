package seedu.address.model;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.model.book.SerialNumber;

/**
 * Serial Number generator when user does not input a preferred serial number.
 */
public class SerialNumberGenerator {
    public static final int SERIAL_NUMBER_LENGTH = 5; //excluding prefix 'B'
    public static final String PREFIX = "B";

    private static TreeSet<SerialNumber> serialNumberTree = new TreeSet<>();

    /**
     * Populates the serial number tree from a catalog.
     *
     * @param catalog catalog to retrieve books from.
     */
    public static void setCatalog(Catalog catalog) {
        serialNumberTree = new TreeSet<>();
        catalog.getBookList()
                .forEach(book -> serialNumberTree.add(book.getSerialNumber()));
    }

    /**
     * Generates a new serial number based on the current serial number index.
     */
    public static SerialNumber generateSerialNumber() {
        if (serialNumberTree.isEmpty()) {
            SerialNumber serialNumber = new SerialNumber(PREFIX + "00001");
            serialNumberTree.add(serialNumber);
            return serialNumber;
        }
        int key = serialNumberTree.size();
        SerialNumber keyCompare = constructSerialNumberFromInt(key);
        int newIndex = serialNumberTree.floor(keyCompare).serialNumberToInt() + 1;
        SerialNumber newSerialNumber = constructSerialNumberFromInt(newIndex);
        serialNumberTree.add(newSerialNumber);
        return newSerialNumber;
    }

    public static SerialNumber constructSerialNumberFromInt(int k) {
        return new SerialNumber(PREFIX + getPadding(k) + k);
    }

    private static String getPadding(int index) {
        String stringRepresentation = Integer.toString(index);
        int paddingLength = SERIAL_NUMBER_LENGTH - stringRepresentation.length();
        return IntStream.rangeClosed(1, paddingLength)
                .mapToObj(x -> "0")
                .reduce("", (a, b) -> a + b);
    }

}
