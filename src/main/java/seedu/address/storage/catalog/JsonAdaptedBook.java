package seedu.address.storage.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.Title;
import seedu.address.model.genre.Genre;
import seedu.address.model.loan.Loan;

/**
 * Jackson-friendly version of {@link Book}.
 */
public class JsonAdaptedBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Book's %s field is missing!";

    private final String title;
    private final String serialNumber;
    private final String author;
    private final String loan;
    private final List<JsonAdaptedTag> genres = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBook} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedBook(@JsonProperty("title") String title, @JsonProperty("serialNumber") String serialNumber,
                           @JsonProperty("author") String author,
                           @JsonProperty("loan") String loan,
                           @JsonProperty("genres") List<JsonAdaptedTag> genres) {
        this.title = title;
        this.serialNumber = serialNumber;
        this.author = author;
        if (genres != null) {
            this.genres.addAll(genres);
        }
        this.loan = loan;
    }

    /**
     * Converts a given {@code Book} into this class for Jackson use.
     */
    public JsonAdaptedBook(Book source) {
        title = source.getTitle().value;
        serialNumber = source.getSerialNumber().value;
        author = source.getAuthor().value;
        boolean hasLoan = source.getLoan().isPresent();
        if (hasLoan) {
            // TODO CHANGE TO USE LOANID
            loan = source.getLoan().get().toString(); //toString for now
        } else {
            loan = null;
        }
        genres.addAll(source.getGenres().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted book object into the model's {@code Book} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Book toModelType() throws IllegalValueException {
        final List<Genre> personGenres = new ArrayList<>();
        for (JsonAdaptedTag tag : genres) {
            personGenres.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (serialNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SerialNumber.class.getSimpleName()));
        }
        if (!SerialNumber.isValidSerialNumber(serialNumber)) {
            throw new IllegalValueException(SerialNumber.MESSAGE_CONSTRAINTS);
        }
        final SerialNumber modelSerialNumber = new SerialNumber(serialNumber);

        if (author == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName()));
        }
        final Author modelAuthor = new Author(author);
        // TODO model Loan in book json
        final Loan modelLoan = null; //stub as null until we decide how to store loan in json

        final Set<Genre> modelGenres = new HashSet<>(personGenres);
        return new Book(modelTitle, modelSerialNumber, modelAuthor, modelLoan, modelGenres);
    }

}
