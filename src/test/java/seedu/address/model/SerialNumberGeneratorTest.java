package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import org.junit.jupiter.api.Test;

<<<<<<< HEAD
import seedu.address.model.book.SerialNumber;
=======
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.testutil.BookBuilder;
>>>>>>> 76edc3518025011d8a382c0c40e511828d7408d5

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
<<<<<<< HEAD
=======

    @Test
    void generateSerialNumber_nonConcurrentSerialNumber_autoGenerateSuccess() {
        Catalog catalog = getTypicalCatalog();
        Book newBook = new BookBuilder().withTitle("testBook").withSerialNumber("B0006").build();
        catalog.addBook(newBook);
        SerialNumberGenerator.setCatalog(catalog);
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B0005"));
        assertEquals(SerialNumberGenerator.generateSerialNumber(), new SerialNumber("B0007"));
    }
>>>>>>> 76edc3518025011d8a382c0c40e511828d7408d5
}
