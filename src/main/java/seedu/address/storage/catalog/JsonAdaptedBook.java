package seedu.address.storage.catalog;

import static seedu.address.commons.core.Messages.MESSAGE_LOAN_ID_DOES_NOT_EXISTS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.LoanRecords;
import seedu.address.model.ReadOnlyLoanRecords;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.Title;
import seedu.address.model.genre.Genre;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;
import seedu.address.model.loan.LoanList;

/**
 * Jackson-friendly version of {@link Book}.
 */
public class JsonAdaptedBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Book's %s field is missing!";

    private final String title;
    private final String serialNumber;
    private final String author;
    private final String loan;
    private final List<JsonAdaptedGenre> genres = new ArrayList<>();
    private final List<String> loanHistory = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBook} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedBook(@JsonProperty("title") String title,
                           @JsonProperty("serialNumber") String serialNumber,
                           @JsonProperty("author") String author,
                           @JsonProperty("loan") String loan,
                           @JsonProperty("genres") List<JsonAdaptedGenre> genres,
                           @JsonProperty("loanHistory") List<String> loanHistory) {
        this.title = title;
        this.serialNumber = serialNumber;
        this.author = author;
        if (genres != null) {
            this.genres.addAll(genres);
        }
        this.loan = loan;
        if (loanHistory != null) {
            this.loanHistory.addAll(loanHistory);
        }
    }

    /**
     * Converts a given {@code Book} into this class for Jackson use.
     */
    public JsonAdaptedBook(Book source) {
        title = source.getTitle().value;
        serialNumber = source.getSerialNumber().value;
        author = source.getAuthor().value;

        if (source.isCurrentlyLoanedOut()) {
            loan = source.getLoan().get().getLoanId().toString();
        } else {
            loan = null;
        }

        genres.addAll(source.getGenres().stream()
                .map(JsonAdaptedGenre::new)
                .collect(Collectors.toList()));

        if (source.getLoanHistory() != null) {
            LoanList loanHistoryToBeRead = source.getLoanHistory();
            for (Loan loan : loanHistoryToBeRead) {
                LoanId id = loan.getLoanId();
                String str = id.toString();
                loanHistory.add(str);
            }
            //source.getLoanHistory().forEach(loan -> loanHistory.add(loan.getLoanId().toString()));
        }
    }

    /**
     * Converts this Jackson-friendly adapted book object into the model's {@code Book} object.
     * Uses an empty LoanRecords.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Book toModelType() throws IllegalValueException {
        return toModelType(new LoanRecords());
    }

    /**
     * Converts this Jackson-friendly adapted book object into the model's {@code Book} object.
     * Loan objects of the Book is taken from the initialLoanRecords.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Book toModelType(ReadOnlyLoanRecords initialLoanRecords) throws IllegalValueException {
        final List<Genre> personGenres = new ArrayList<>();
        for (JsonAdaptedGenre tag : genres) {
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

        final Loan modelLoan;
        if (loan == null) {
            modelLoan = null;
        } else if (!LoanId.isValidLoanId(loan)) {
            throw new IllegalValueException(LoanId.MESSAGE_CONSTRAINTS);
        } else {
            LoanId loanId = new LoanId(loan);
            modelLoan = initialLoanRecords.getLoansMap().get(loanId);
            if (modelLoan == null) {
                throw new IllegalValueException(String.format(MESSAGE_LOAN_ID_DOES_NOT_EXISTS, loanId));
            }
        }

        final Set<Genre> modelGenres = new HashSet<>(personGenres);

        final LoanList modelLoanList = new LoanList();
        final HashMap<LoanId, Loan> loansMap = initialLoanRecords.getLoansMap();
        loanHistory.stream()
                .map(loanId -> loansMap.get(new LoanId(loanId)))
                .forEach(loan -> modelLoanList.add(loan));

        return new Book(modelTitle, modelSerialNumber, modelAuthor, modelLoan, modelGenres, modelLoanList);
    }

}
