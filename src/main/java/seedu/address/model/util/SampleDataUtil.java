package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.book.Author;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.Title;
import seedu.address.model.book.Book;
import seedu.address.model.genre.Genre;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Book[] getSampleBooks() {
        return new Book[] {
            new Book(new Title("Harry Botter"), new SerialNumber("0001"), new Author("J K Rowling"),
                getTagSet("Fiction")),
            new Book(new Title("Legend of the Condor Heroes"), new SerialNumber("0002"),
                new Author("Jin Yong"),getTagSet("Fiction", "History")),
            new Book(new Title("Animal the Farm"), new SerialNumber("0003"), new Author("George Orwell"),
                getTagSet("Fiction")),
            new Book(new Title("Harry Botter and the Full Blood Prince"),
                new SerialNumber("0004"), new Author("J K Rowling"),
                getTagSet("Fiction", "Action")),
        };
    }

    public static ReadOnlyCatalogue getSampleCatalogue() {
        Catalogue sampleAb = new Catalogue();
        for (Book sampleBook : getSampleBooks()) {
            sampleAb.addBook(sampleBook);
        }
        return sampleAb;
    }

    /**
     * Returns a genre set containing the list of strings given.
     */
    public static Set<Genre> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Genre::new)
                .collect(Collectors.toSet());
    }

}
