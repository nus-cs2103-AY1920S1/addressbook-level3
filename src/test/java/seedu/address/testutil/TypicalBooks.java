package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Catalog;
import seedu.address.model.book.Book;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book BOOK_1 = new BookBuilder().withTitle("Harry Botter")
            .withSerialNumber("B00001").withAuthor("J K Rowling").withGenres("FICTION").build();
    public static final Book BOOK_2 = new BookBuilder().withTitle("Legend of the Condor Heroes")
            .withAuthor("Jin Yong").withSerialNumber("B00002").withGenres("FICTION", "ACTION").build();
    public static final Book BOOK_3 = new BookBuilder().withTitle("Animal the Farm").withSerialNumber("B00003")
            .withAuthor("George Orwell").build();
    public static final Book BOOK_4 = new BookBuilder().withTitle("Harri Botter and the Full Blood Prince")
            .withSerialNumber("B00004").withAuthor("J K Rowling").withGenres("FICTION", "ACTION").build();
    public static final Book BOOK_5 = new BookBuilder().withTitle("The Heavenly Sword and the Dragon Saber")
            .withSerialNumber("B00005").withAuthor("Jin Yong").withGenres("FICTION", "ACTION").build();
    public static final Book BOOK_6 = new BookBuilder().withTitle("My Book")
            .withSerialNumber("B00006").withAuthor("Jin Yong").withGenres("FICTION", "ACTION").build();
    public static final Book BOOK_7 = new BookBuilder().withTitle("The Hunger Games")
            .withSerialNumber("B00007").withAuthor("Suzanne Collins")
            .withGenres("FICTION", "ACTION").build();

    private TypicalBooks() {} // prevents instantiation

    /**
     * Returns a {@code Catalog} with all the typical books.
     */
    public static Catalog getTypicalCatalog() {
        Catalog catalog = new Catalog();
        for (Book book : getTypicalBooks()) {
            catalog.addBook(book);
        }
        return catalog;
    }

    public static List<Book> getTypicalBooks() {
        return new ArrayList<>(Arrays.asList(BOOK_1, BOOK_2, BOOK_3, BOOK_4));
    }
}
