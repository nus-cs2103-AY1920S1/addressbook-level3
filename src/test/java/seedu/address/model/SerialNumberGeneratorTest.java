package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.testutil.BookBuilder;

class SerialNumberGeneratorTest {
    private final Catalog catalog = new Catalog();

    @Test
    void generateSerialNumber_newCatalog_autoGenerateSuccess() {
        SerialNumberGenerator.setCatalog(catalog);
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B00001"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B00002"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B00003"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B00004"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B00005"));
    }

    @Test
    void generateSerialNumber_typicalCatalog_autoGenerateSuccess() {
        SerialNumberGenerator.setCatalog(getTypicalCatalog());
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B00005"));
    }

    @Test
    void generateSerialNumber_nonConcurrentSerialNumber_autoGenerateSuccess() {
        Catalog catalog = getTypicalCatalog();
        Book newBook = new BookBuilder().withTitle("testBook").withSerialNumber("B00006").build();
        catalog.addBook(newBook);
        SerialNumberGenerator.setCatalog(catalog);
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B00005"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B00007"));
    }

    @Test
    void generateSerialNumber_nullFloorKey_success() {
        Catalog catalog = new Catalog();
        Book newBook = new BookBuilder().withTitle("testBook").withSerialNumber("B00006").build();
        catalog.addBook(newBook);
        SerialNumberGenerator.setCatalog(catalog);
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B00001"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B00002"));
    }
}
