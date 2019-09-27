package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.SerialNumber;

class SerialNumberGeneratorTest {
    private final Catalog catalog = new Catalog();

    @Test
    void generateSerialNumber_newCatalog_autoGenerateSuccess() {
        SerialNumberGenerator.setCatalog(catalog);
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B0001"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B0002"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B0003"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B0004"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B0005"));
    }

    @Test
    void generateSerialNumber_typicalCatalog_autoGenerateSuccess() {
        SerialNumberGenerator.setCatalog(getTypicalCatalog());
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B0005"));
    }
}
